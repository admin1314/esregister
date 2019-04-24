package com.zc.dao;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.zc.model.ColumnModel;
import com.zc.model.ColumnModel.ColumnTypeEnum;

/**
 * elasticsearch数据库工具类
 * 
 * @author wangtao88
 *
 */
public class ClusterUtils {

	private static Client staticClient = null;

	public static Client clientInstance(String ip, String clusterName, int port) {

		if (staticClient == null) {
			try {
				staticClient = getClient(ip, clusterName, port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return staticClient;
	}

	/**
	 * 获取es链接
	 * 
	 * @param ip
	 *            ip地址
	 * @param clusterName
	 *            集群名称
	 * @param port
	 *            tcp端口号
	 * @return
	 * @throws Exception
	 */
	public static Client getClient(String ip, String clusterName, int port) throws Exception {
		TransportClient client = null;
		if (client == null) {
			Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
					.put("client.transport.ping_timeout", "500s").build();
			client = new PreBuiltTransportClient(settings);
			client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
		}
		return client;
	}

	/**
	 * 关闭连接
	 * 
	 * @param client
	 */
	public static void closeClient(Client client) {
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断索引是否存在
	 * 
	 * @param client
	 * @param index
	 *            索引名称
	 * @return
	 */
	public static boolean indexExist(Client client, String index) {
		IndicesExistsRequest inExistsRequest = new IndicesExistsRequest(index);
		IndicesExistsResponse inExistsResponse = client.admin().indices().exists(inExistsRequest).actionGet();
		return inExistsResponse.isExists();
	}

	/**
	 * 判断type是否存在
	 * 
	 * @param client
	 * @param index
	 *            索引名称
	 * @param type
	 *            type名称
	 * @return
	 */
	public static boolean typeIsExists(Client client, String index, String type) {
		if (!indexExist(client, index)) {
			return false;
		}
		TypesExistsResponse response = client.admin().indices().prepareTypesExists(index).setTypes(type).execute()
				.actionGet();
		return response.isExists();
	}

	/**
	 * 删除索引
	 * 
	 * @param client
	 * @param index
	 *            索引名称
	 * @return
	 */
	public static Boolean deleteIndex(Client client, String index) {
		if (indexExist(client, index)) {
			DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
			return dResponse.isAcknowledged();
		}
		return true;
	}

	// 创建索引
	public static Boolean createIndex(Client client, String indexName, String alias, int shards, int replications) {
		try {
			CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).addAlias(new Alias(alias))
					.setSettings(Settings.builder().put("index.number_of_shards", shards)
							.put("index.number_of_replicas", replications))
					.execute().actionGet();
			return response.isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	/**
	 * 创建索引，创建索引时指定索引最大查询返回的条数
	 * 
	 * 对于es查询返回条数由两方面进行控制，一方面是创建索引时指定索引最大查询返回的条数，另一方面是获取数据时设置（setSize()）
	 * 
	 * @param client
	 * @param indexName
	 * @param alias
	 * @param shards
	 * @param replications
	 * @return
	 */
	public static boolean createIndexSetPieces(Client client, String indexName, String alias, int shards,
			int replications) {
		try {
			Settings settings = Settings.builder().put("number_of_shards", shards).put("max_result_window", 100000) // 此参数
					.put("number_of_replicas", replications).build();
			@SuppressWarnings("unused")
			CreateIndexResponse response = client.admin().indices().prepareCreate(indexName).addAlias(new Alias(alias))
					.setSettings(settings).execute().actionGet();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取所有索引名称
	 * 
	 * @param client
	 * @return
	 * @throws Exception
	 */
	public static String[] getAllIndex(Client client) throws Exception {
		ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
		String[] indexs = response.getState().getMetaData().getConcreteAllIndices();
		return indexs;
	}

	/**
	 * 更新索引副本数目
	 * 
	 * @param client
	 * @param indexName
	 *            索引名称
	 * @param numOfReplicas
	 *            副本数量
	 * @return
	 */
	public static boolean updateReplicas(Client client, String indexName, int numOfReplicas) {
		UpdateSettingsResponse response = client.admin().indices().prepareUpdateSettings(indexName)
				.setSettings(Settings.builder().put("index.number_of_replicas", numOfReplicas)).get();
		return response.isAcknowledged();
	}

	// 增加别名
	public static boolean setAliases(Client client, String indexName, String aliases) {

		IndicesAliasesResponse response = client.admin().indices().prepareAliases().addAlias(indexName, aliases).get();
		return response.isAcknowledged();

	}

	// 删除别名
	public static boolean removeAliases(Client client, String indexName, String aliases) {
		IndicesAliasesResponse response = client.admin().indices().prepareAliases().removeAlias(indexName, aliases)
				.get();
		return response.isAcknowledged();
	}

	/**
	 * 获取索引的别名
	 * 
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static List<String> getAliases(Client client, String indexName) {
		List<String> list = new ArrayList<String>();
		IndicesAdminClient indicesAdminClient = client.admin().indices();
		GetAliasesResponse response = indicesAdminClient.prepareGetAliases("_all").get();
		ImmutableOpenMap<String, List<AliasMetaData>> aliasesMap = response.getAliases();
		Iterator<String> iterator = aliasesMap.keysIt();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.equals(indexName)) {
				List<AliasMetaData> aliasMetaDataList = aliasesMap.get(key);
				for (AliasMetaData aliasMetaData : aliasMetaDataList) {
					list.add(aliasMetaData.getAlias());
				}
				break;
			}
		}
		return list;
	}

	/**
	 * {index.creation_date=1555296563739, index.number_of_replicas=0,
	 * index.number_of_shards=5, index.provided_name=test2,
	 * index.uuid=22kNxHb0S4i_Bq9HMrr83A, index.version.created=5020099}
	 * 
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static Map<String, String> getSetting(Client client, String indexName) {
		try {
			Settings setting = client.admin().cluster().prepareState().execute().actionGet().getState().getMetaData()
					.getIndices().get(indexName).getSettings();
			return setting.getAsMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取字段信息
	 * 
	 * @param client
	 * @param indexName
	 * @return
	 */
	public static List<ColumnModel> getColumns(Client client, String indexName) {
		List<ColumnModel> result = new ArrayList<ColumnModel>();
		try {
			ImmutableOpenMap<String, MappingMetaData> mappingsMap = client.admin().cluster().prepareState().execute()
					.actionGet().getState().getMetaData().getIndices().get(indexName).getMappings();
			Iterator<String> iterator = mappingsMap.keysIt();
			while (iterator.hasNext()) {
				String typeName = iterator.next(); // type 名称
				MappingMetaData mappingMetaData = mappingsMap.get(typeName);
				Map<String, Object> mappings = mappingMetaData.getSourceAsMap();
				for (Map.Entry<String, Object> deep0 : mappings.entrySet()) {
					if (deep0.getKey().equals("properties")) {
						Map<?, ?> properties = (Map<?, ?>) deep0.getValue();
						for (Map.Entry<?, ?> deep1 : properties.entrySet()) {
							Map<?, ?> field = (Map<?, ?>) deep1.getValue();
							String columnName = (String) deep1.getKey();
							String columnType = (String) field.get("type");
							ColumnModel columnModel = new ColumnModel();
							columnModel.setColumnName(columnName);
							columnModel.setColumnType(ColumnTypeEnum.fromValue(columnType));
							columnModel.setTypeName(typeName);
							if (!result.contains(columnModel)) {
								result.add(columnModel);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 列出某个索引下的所有type名
	public static List<String> listTypes(Client client, String indexName) {
		List<String> typeList = new ArrayList<String>();
		try {
			GetMappingsResponse response = client.admin().indices().getMappings(new GetMappingsRequest()).actionGet();
			ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> maps = response.getMappings();
			Iterator<ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>>> itor = maps.iterator();
			while (itor.hasNext()) {
				ObjectObjectCursor<String, ImmutableOpenMap<String, MappingMetaData>> index = itor.next();
				if (index.key.equals(indexName)) {
					for (ObjectObjectCursor<String, MappingMetaData> typeName : index.value) {
						typeList.add(typeName.key);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return typeList;

	}

	// type是否存在
	public static Boolean typeIsExist(Client client, String index, String type) {
		TypesExistsResponse response = client.admin().indices().prepareTypesExists(index).setTypes(type).execute()
				.actionGet();
		return response.isExists();
	}

	// 创建mapping
	public static Boolean createMapping(Client client, String index, String type, Map<String, String> fields) {
		XContentBuilder jsonBuild;
		try {
			jsonBuild = XContentFactory.jsonBuilder().startObject().startObject(type).field("dynamic", "true");
			jsonBuild = jsonBuild.startObject("properties");
			for (Map.Entry<String, String> entry : fields.entrySet()) {
				String fieldType = entry.getValue();
				if (fieldType.equals("keyword") || fieldType.equals("text")) {
					jsonBuild = jsonBuild.startObject(entry.getKey()).field("type", fieldType)
							.field("index", "not_analyzed").field("ignore_above", 30000).endObject();
				} else if (fieldType.equals("long")) {
					jsonBuild = jsonBuild.startObject(entry.getKey()).field("type", "long")
							.field("index", "not_analyzed").endObject();
				} else if (fieldType.equals("date")) {
					jsonBuild = jsonBuild.startObject(entry.getKey()).field("type", "date")
							.field("index", "not_analyzed")
							.field("format", "yyy-MM-dd HH:mm:ss||yyyMMddHHmmss||yyyy-MM-dd||yyyyMMdd||epoch_millis")
							.endObject();
				}
			}
			// jsonBuild = jsonBuild.startObject("es_auto_rksj").field("type",
			// "long").endObject(); // 自动为每个type加上入库时间
			jsonBuild = jsonBuild.endObject();
			jsonBuild = jsonBuild.endObject().endObject();

			// System.out.println(jsonBuild.toString());
			// System.out.println(jsonBuild.string());
			// System.out.println(DigestUtils.md5Hex(jsonBuild.string()));

			PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(jsonBuild);
			PutMappingResponse response = client.admin().indices().putMapping(mappingRequest).actionGet();
			return response.isAcknowledged();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 合并段
	public static void forceMerge(Client client, String index) {
		try {
			IndicesStatsResponse indexStatus = client.admin().indices().prepareStats(index).execute().actionGet();
			long dataCount = indexStatus.getTotal().docs.getCount();
			long deleteCount = indexStatus.getTotal().docs.getDeleted();
			long segmentCount = indexStatus.getTotal().segments.getCount();
			System.out.println(
					String.format("开始段合并...\n文档总条数[%d]\n需要删除的条数[%d]\n段条数[%d]", dataCount, deleteCount, segmentCount));

			int segmentNum = 1;
			ForceMergeResponse response = client.admin().indices().forceMerge(
					new ForceMergeRequest(index).flush(true).maxNumSegments(segmentNum).onlyExpungeDeletes(true))
					.actionGet();
			if (response.getShardFailures().length == response.getTotalShards()) {
				System.out.println(String.format("段合并失败!失败条数为[%d]", response.getTotalShards()));
			} else if (response.getShardFailures().length > 0) {
				System.out.println(String.format("部分合并并失败!失败条数为[%d]", response.getShardFailures().length));
			} else {
				System.out.println("段合并成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 集群运行健康状态
	public static void clusterHealth(Client client) throws Exception {
		ClusterHealthResponse response = null;
		response = client.admin().cluster().prepareHealth().execute().actionGet();
		System.out.println("集群" + response.getClusterName() + "当前基本信息为:处于ACTIVE状态的分片有" + response.getActiveShards()
				+ "片，处于init状态的有" + response.getInitializingShards() + "片,处于unassigned未分配状态的有"
				+ response.getUnassignedShards() + "片,处于reload状态的有" + response.getRelocatingShards() + "片。");

		ClusterHealthStatus status = response.getStatus();
		if (status.compareTo(ClusterHealthStatus.GREEN) == 0) {
			System.out.println("集群状态良好!");
		} else if (status.compareTo(ClusterHealthStatus.YELLOW) == 0) {
			System.out.println("集群有丢失的副本，但是不影响使用。或者有未分配的分片。请查看未分片数");
		} else {
			System.out.println("集群遭到破坏，出现严重问题。请检查集群，并进行修复");
		}

	}

	// 索引状态
	public static void indicesStatus(Client client, String index) {
		IndicesStatsResponse irs = null;
		try {
			irs = client.admin().indices().prepareStats(index).execute().actionGet();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\n" + index + "库中有效数据条数为:" + irs.getTotal().docs.getCount() //
				+ ",\n需要删除的数据条数为:" + irs.getTotal().docs.getDeleted()//
				+ ",\n已经合并的记录条数为:" + irs.getTotal().merge.getTotalNumDocs()//
				+ ",\n已经合并的桶数为:" + irs.getTotal().merge.getTotal()//
				+ ",\n正在合并的桶数为:" + irs.getTotal().merge.getCurrent()//
				+ ",\n正在合并的记录数为:" + irs.getTotal().merge.getCurrentNumDocs()//
				+ ",\n合并消耗的时间为:" + irs.getTotal().merge.getTotalTimeInMillis()//
				+ ",\n库中总的段数,即桶数为:" + irs.getTotal().segments.getCount()//
				+ ",\n库中总的物理占用空间为:" + irs.getTotal().store.size());
	}

	// 获取集群信息
	public static void clusterState(Client client) {

		ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
		ClusterState state = response.getState();
		System.out.println(state.toString());
	}

	public static void main(String[] args) throws IOException {

		Settings settings = Settings.builder().put("cluster.name", "elasticsearch").put("client.transport.sniff", true)
				.put("client.transport.ping_timeout", "500s").build();
		TransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

		// Client client = ClusterUtils.getClient("127.0.0.1", "elasticsearch", 9300);
		// clusterState(client);
		// setAliases(client, "test2", "alias2");
		listTypes(client, "student");
	}

}