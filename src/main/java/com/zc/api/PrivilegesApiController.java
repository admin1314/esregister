package com.zc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.dao.PrivilegeMapper;
import com.zc.model.PrivilegeModel;
import com.zc.model.ResultEntity;
import com.zc.service.PrivilegesApiService;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PrivilegesApiController implements PrivilegesApi {

	private static final Logger log = LoggerFactory.getLogger(PrivilegesApiController.class);

	private final HttpServletRequest request;

	@Autowired
	public PrivilegesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.request = request;
	}

	@Autowired
	private PrivilegesApiService privilegesApiService;
	@Autowired
	private PrivilegeMapper privilegeMapper;

	public ResponseEntity<ResultEntity> addPrivilege(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "集群实体", required = true) @Valid @RequestBody List<PrivilegeModel> body) {
		String accept = request.getHeader("Accept");
		log.info(body.toString());
		if (accept != null && accept.contains("application/json")) {
			try {
				return privilegesApiService.addPrivilege(clusterId, body);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> deletePrivilege(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId,
			@ApiParam(value = "集群实体", required = true) @Valid @RequestBody List<PrivilegeModel> body) {
		String accept = request.getHeader("Accept");
		log.info(body.toString());
		if (accept != null && accept.contains("application/json")) {
			try {
				return privilegesApiService.deletePrivilege(clusterId, body);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<List<PrivilegeModel>> getPrivilegeList(
			@ApiParam(value = "集群唯一标识", required = true) @PathVariable("cluster-id") String clusterId) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<List<PrivilegeModel>>(privilegeMapper.findAll(), HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<List<PrivilegeModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<List<PrivilegeModel>>(HttpStatus.NOT_IMPLEMENTED);
	}

}
