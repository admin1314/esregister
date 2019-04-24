package com.zc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.dao.ClusterMapper;
import com.zc.dao.TypeMapper;
import com.zc.model.ClusterModel;
import com.zc.model.IndexModel;
import com.zc.model.ResultEntity;
import com.zc.model.TypeModel;
import com.zc.service.ClustersApiService;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClustersApiController implements ClustersApi {

	private static final Logger log = LoggerFactory.getLogger(ClustersApiController.class);
	private final HttpServletRequest request;

	@Autowired
	ClusterMapper clusterMapper;
	@Autowired
	TypeMapper typeMapper;
	@Autowired
	ClustersApiService clustersApiService;

	@org.springframework.beans.factory.annotation.Autowired
	public ClustersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.request = request;
	}

	public ResponseEntity<ResultEntity> addCluster(
			@ApiParam(value = "集群实体", required = true) @Valid @RequestBody ClusterModel body) {
		String accept = request.getHeader("Accept");
		log.info(body.toString());
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.addCluster(body);
		}
		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> addIndexInfo(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName,
			@ApiParam(value = "索引实体", required = true) @Valid @RequestBody IndexModel body) {

		try {
			String accept = request.getHeader("Accept");
			log.info(body.toString());
			if (accept != null && accept.contains("application/json")) {
				return clustersApiService.addIndexInfo(clusterId, indexName, body);
			}
			return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResultEntity> addTypeInfo(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName,
			@ApiParam(value = "type名称", required = true) @PathVariable("type-name") String typeName,
			@ApiParam(value = "索引实体", required = true) @Valid @RequestBody TypeModel body) {
		String accept = request.getHeader("Accept");
		log.info(body.toString());
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.addTypeInfo(clusterId, indexName, typeName, body);
		}
		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> deleteCluster(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.deleteCluster(clusterId);
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> deleteIndexInfo(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName) {
		try {
			String accept = request.getHeader("Accept");
			if (accept != null && accept.contains("application/json")) {
				return clustersApiService.deleteIndexInfo(clusterId, indexName);
			}
			return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<ResultEntity> deleteTypeInfo(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName,
			@ApiParam(value = "type名称", required = true) @PathVariable("type-name") String typeName) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				typeMapper.deleteByClusterIdAndIndexNameAndTypeName(clusterId, indexName, typeName);
				return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<List<ClusterModel>> getClusterList() {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<List<ClusterModel>>(clusterMapper.findAll(), HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<List<ClusterModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<List<ClusterModel>>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<IndexModel> getIndexInfo(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.getIndexInfo(clusterId, indexName);
		}

		return new ResponseEntity<IndexModel>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<List<IndexModel>> getIndexList(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "用户id") @Valid @RequestParam(value = "user_id", required = false) String userId) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.getIndexList(clusterId, userId);
		}

		return new ResponseEntity<List<IndexModel>>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<TypeModel> getTypeInfo(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName,
			@ApiParam(value = "type名称", required = true) @PathVariable("type-name") String typeName) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.getTypeInfo(clusterId, indexName, typeName);
		}

		return new ResponseEntity<TypeModel>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<List<TypeModel>> getTypeList(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "索引名称", required = true) @PathVariable("index-name") String indexName) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			return clustersApiService.getTypeList(clusterId, indexName);
		}

		return new ResponseEntity<List<TypeModel>>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> updateCluster(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "集群实体", required = true) @Valid @RequestBody ClusterModel body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				log.info(body.toString());
				return clustersApiService.updateCluster(clusterId, body);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}
}
