package com.zc.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zc.dao.ClusterMapper;
import com.zc.dao.ClusterUtils;
import com.zc.dao.ColumnMapper;
import com.zc.dao.IndexMapper;
import com.zc.dao.PrivilegeMapper;
import com.zc.dao.TypeMapper;
import com.zc.model.ClusterModel;
import com.zc.model.ColumnModel;
import com.zc.model.IndexModel;
import com.zc.model.PrivilegeModel;
import com.zc.model.ResultEntity;
import com.zc.model.TypeModel;
import com.zc.utils.Utils;

@Service
public class ClustersApiService {

	@Autowired
	private ClusterMapper clusterMapper;
	@Autowired
	private IndexMapper indexMapper;
	@Autowired
	private TypeMapper typeMapper;
	@Autowired
	private ColumnMapper columnMapper;

	@Autowired
	@Qualifier("esClients")
	Map<String, Client> clientsMap;

	@Autowired
	private PrivilegeMapper privilegeMapper;

	private static final Logger log = LoggerFactory.getLogger(ClustersApiService.class);

	/**
	 * 添加一个集群
	 * 
	 * @param clusterModel
	 * @return
	 */
	public ResponseEntity<ResultEntity> addCluster(ClusterModel clusterModel) {
		try {
			String id = clusterModel.getId();
			if (Utils.strIsEmpty(id)) {
				id = DigestUtils.md5Hex(clusterModel.toString());
				clusterModel.setId(id);
			}
			if (clientsMap.get(id) != null) {
				return new ResponseEntity<ResultEntity>(new ResultEntity(1, "cluster already exists", 0, null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Client client = ClusterUtils.getClient(clusterModel.getIp(), clusterModel.getClusterName(),
					clusterModel.getPort());
			ClusterUtils.clusterHealth(client); // 为了让非法地址的集群报错。
			clientsMap.put(id, client);
			clusterMapper.save(clusterModel);
			return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Transactional
	public ResponseEntity<ResultEntity> updateCluster(String clusterId, ClusterModel clusterModel) throws Exception {
		clusterModel.setId(clusterId);
		clusterMapper.deleteById(clusterId);
		clusterMapper.save(clusterModel);
		Client client = ClusterUtils.getClient(clusterModel.getIp(), clusterModel.getClusterName(),
				clusterModel.getPort());
		ClusterUtils.clusterHealth(client); // 为了让非法地址的集群报错。
		Client oldClient = clientsMap.get(clusterId);
		if (oldClient != null) {
			oldClient.close();
		}
		clientsMap.put(clusterId, client);
		return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, clusterId), HttpStatus.OK);
	}

	/**
	 * 删除一个集群
	 * 
	 * @param clusterId
	 * @return
	 */
	public ResponseEntity<ResultEntity> deleteCluster(String clusterId) {
		try {
			// TODO 集群下的索引、type注册信息怎么处理。
			Client client = clientsMap.get(clusterId);
			ClusterUtils.closeClient(client);
			clientsMap.remove(clusterId);
			clusterMapper.deleteById(clusterId);
			return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 获取索引列表
	 * 
	 * @param clusterId
	 *            集群id
	 * @param userId
	 *            用户id
	 * @return
	 */
	public ResponseEntity<List<IndexModel>> getIndexList(String clusterId, String userId) {
		try {
			List<IndexModel> result = new ArrayList<IndexModel>();
			Client client = clientsMap.get(clusterId);
			String[] dbindexes = ClusterUtils.getAllIndex(client); // 从es数据库中获取集群中所有的索引名称
			List<IndexModel> cfgIndexes = indexMapper.findByClusterId(clusterId); // 从mysql中获取索引的注册信息
			List<PrivilegeModel> privilegeModelList = privilegeMapper.findByClusterIdAndUserId(clusterId, userId);
			
			for (int i = 0, m = dbindexes.length; i < m; i++) {
				String indexName = dbindexes[i];
				boolean isHavePrivilege = false;
				// 判断用户是否有查看索引的权限
				if (Utils.strIsEmpty(userId)) {
					isHavePrivilege = true;
				} else {
					for (PrivilegeModel privilegeModel : privilegeModelList) {
						if (Utils.match(indexName, privilegeModel.getIndexRegex())) {
							isHavePrivilege = true;
							break;
						}
					}
				}
				if (!isHavePrivilege) {
					continue;
				}

				IndexModel indexModel = new IndexModel();
				indexModel.setIndexName(indexName);
				indexModel.setClusterId(clusterId);
				for (IndexModel cfgIndex : cfgIndexes) {
					if (indexName.equals(cfgIndex.getIndexName())) {
						indexModel.setChSName(cfgIndex.getChSName());
					}
				}
				result.add(indexModel);
			}
			return new ResponseEntity<List<IndexModel>>(result, HttpStatus.OK);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<IndexModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 获取索引详情
	 * 
	 * @param clusterId
	 *            集群id
	 * @param indexName
	 *            type名称
	 * @return
	 */
	public ResponseEntity<IndexModel> getIndexInfo(String clusterId, String indexName) {
		try {
			Client client = clientsMap.get(clusterId);
			IndicesStatsResponse idxStatus = client.admin().indices().prepareStats(indexName).execute().actionGet();
			IndexModel indexModel = indexMapper.findByClusterIdAndIndexName(clusterId, indexName);
			if (indexModel == null) {
				indexModel = new IndexModel();
			}

			// 获取索引创建时间
			String second = ClusterUtils.getSetting(client, indexName).get("index.creation_date");
			String format = "yyyy-MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String createTime = sdf.format(new Date(Long.valueOf(second)));

			// 获取字段注释
			List<ColumnModel> dbColumns = ClusterUtils.getColumns(client, indexName);
			List<ColumnModel> cfgComments = columnMapper.findByClusterIdAndIndexName(clusterId, indexName);
			if (dbColumns != null && cfgComments != null) {
				for (ColumnModel dbColumn : dbColumns) {
					for (ColumnModel cfgComment : cfgComments) {
						if (dbColumn.getColumnName().equals(cfgComment.getColumnName())) {
							dbColumn.setComments(cfgComment.getComments());
							break;
						}
					}
				}
			}

			indexModel.setClusterId(clusterId);
			indexModel.setIndexName(indexName);
			indexModel.setDocs((int) idxStatus.getTotal().docs.getCount()); // 文档数
			indexModel.setSize((int) idxStatus.getTotal().store.size().getKb()); // 大小(Kb)
			indexModel.setAlias(ClusterUtils.getAliases(client, indexName)); // 别名
			indexModel.setColumns(dbColumns);
			indexModel.setCreateTime(createTime);

			return new ResponseEntity<IndexModel>(indexModel, HttpStatus.OK);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<IndexModel>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 注册索引
	 * 
	 * @param clusterId
	 *            集群id
	 * @param indexName
	 *            索引名称
	 * @param indexModel
	 *            索引信息实体类
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResponseEntity<ResultEntity> addIndexInfo(String clusterId, String indexName, IndexModel indexModel)
			throws Exception {
		// TODO 判断索引是否在elasticsearch中存在
		indexModel.setClusterId(clusterId);
		indexModel.setIndexName(indexName);
		if (Utils.strIsEmpty(indexModel.getId())) {
			indexModel.setId(DigestUtils.md5Hex(indexModel.toString()));
		}
		indexMapper.deleteById(indexModel.getId());
		indexMapper.save(indexModel);
		List<ColumnModel> columns = indexModel.getColumns();
		if (columns != null) {
			for (ColumnModel columnModel : columns) {
				columnModel.setClusterId(clusterId);
				columnModel.setIndexName(indexName);
				if (Utils.strIsEmpty(columnModel.getId())) {
					columnModel.setId(DigestUtils.md5Hex(columnModel.toString()));
				}
				columnMapper.deleteById(columnModel.getId());
				columnMapper.save(columnModel);
			}
		}
		return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
	}

	/**
	 * 删除索引注册信息
	 * 
	 * @param clusterId
	 *            集群id
	 * @param indexName
	 *            索引名称
	 * @return
	 */
	@Transactional
	public ResponseEntity<ResultEntity> deleteIndexInfo(String clusterId, String indexName) {

		indexMapper.deleteByClusterIdAndIndexName(clusterId, indexName);
		typeMapper.deleteByClusterIdAndIndexName(clusterId, indexName);
		columnMapper.deleteByClusterIdAndIndexName(clusterId, indexName);
		return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
	}

	/**
	 * 获取索引下的type列表
	 * 
	 * @param clusterId
	 *            集群id
	 * @param indexName
	 *            索引名称
	 * @return
	 */
	public ResponseEntity<List<TypeModel>> getTypeList(String clusterId, String indexName) {
		try {
			List<TypeModel> result = new ArrayList<TypeModel>();
			Client client = clientsMap.get(clusterId);
			List<String> typeNameList = ClusterUtils.listTypes(client, indexName);
			
			List<TypeModel> cfgTypeList = typeMapper.findByClusterIdAndIndexName(clusterId, indexName);
			if (typeNameList != null && cfgTypeList != null) {
				for (int i = 0, m = typeNameList.size(); i < m; i++) {
					TypeModel typeModel = new TypeModel();
					typeModel.setTypeName(typeNameList.get(i));
					for (TypeModel cfgType : cfgTypeList) {
						if (typeNameList.get(i).equals(cfgType.getTypeName())) {
							typeModel.setId(cfgType.getId());
							typeModel.setChSName(cfgType.getChSName());
							// typeModel.setDatasourceId(cfgType.getDatasourceId());
							// typeModel.setUserId(cfgType.getUserId());
							// typeModel.setRemark(cfgType.getRemark());
							break;
						}
					}
					result.add(typeModel);
				}

			}
			return new ResponseEntity<List<TypeModel>>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<List<TypeModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 获取type详情
	 * 
	 * @param clusterId
	 *            集群id
	 * @param indexName
	 *            索引名称
	 * @param typeName
	 *            type名称
	 * @return
	 */
	public ResponseEntity<TypeModel> getTypeInfo(String clusterId, String indexName, String typeName) {
		try {
			Client client = clientsMap.get(clusterId);
			TypeModel typeModel = typeMapper.findByClusterIdAndIndexNameAndTypeName(clusterId, indexName, typeName);
			if (typeModel == null) {
				typeModel = new TypeModel();
				typeModel.setClusterId(clusterId);
				typeModel.setIndexName(indexName);
				typeModel.setTypeName(typeName);
			}
			// 获取字段注释
			List<ColumnModel> typeColumns = new ArrayList<ColumnModel>();
			List<ColumnModel> dbColumns = ClusterUtils.getColumns(client, indexName);
			List<ColumnModel> cfgComments = columnMapper.findByClusterIdAndIndexName(clusterId, indexName);
			if (dbColumns != null && cfgComments != null) {
				for (ColumnModel dbColumn : dbColumns) {
					if (dbColumn.getTypeName().equals(typeName)) {
						for (ColumnModel cfgComment : cfgComments) {
							if (dbColumn.getColumnName().equals(cfgComment.getColumnName())) {
								dbColumn.setComments(cfgComment.getComments());
								break;
							}
						}
						typeColumns.add(dbColumn);
					}
				}
			}
			typeModel.setColumns(typeColumns);
			return new ResponseEntity<TypeModel>(typeModel, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<TypeModel>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 注册type信息
	 * 
	 * @param clusterId
	 *            集群id
	 * @param indexName
	 *            索引名称
	 * @param typeName
	 *            type名称
	 * @param typeModel
	 *            type信息实体类
	 * @return
	 */
	public ResponseEntity<ResultEntity> addTypeInfo(String clusterId, String indexName, String typeName,
			TypeModel typeModel) {
		try {
			typeModel.setClusterId(clusterId);
			typeModel.setIndexName(indexName);
			String id = typeModel.getId();
			if (Utils.strIsEmpty(id)) {
				id = DigestUtils.md5Hex(typeModel.toString());
				typeModel.setId(id);
			}
			// TODO 判断type是否在es中存在
			typeMapper.deleteById(id);
			typeMapper.save(typeModel);
			return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
