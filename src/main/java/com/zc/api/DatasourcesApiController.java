package com.zc.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.dao.DatasourceMapper;
import com.zc.model.DatasourceModel;
import com.zc.model.ResultEntity;
import com.zc.service.DatasourcesApiService;

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
public class DatasourcesApiController implements DatasourcesApi {

	private static final Logger log = LoggerFactory.getLogger(DatasourcesApiController.class);

	private final HttpServletRequest request;

	@org.springframework.beans.factory.annotation.Autowired
	public DatasourcesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.request = request;
	}

	@Autowired
	DatasourceMapper datasourcesMapper;
	@Autowired
	DatasourcesApiService datasourcesApiService;

	public ResponseEntity<ResultEntity> addDatasource(
			@ApiParam(value = "数据源实体", required = true) @Valid @RequestBody DatasourceModel body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			log.info(body.toString());
			try {
				return datasourcesApiService.addDatasource(body);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> deleteDatasource(
			@ApiParam(value = "数据源id", required = true) @PathVariable("source-id") String sourceId) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				datasourcesMapper.deleteById(sourceId);
				return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null),
						HttpStatus.NOT_IMPLEMENTED);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<List<DatasourceModel>> getDatasourceList() {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return new ResponseEntity<List<DatasourceModel>>(datasourcesMapper.findAll(), HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<List<DatasourceModel>>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<List<DatasourceModel>>(HttpStatus.NOT_IMPLEMENTED);
	}

	public ResponseEntity<ResultEntity> updateDatasource(
			@ApiParam(value = "数据源id", required = true) @PathVariable("source-id") String sourceId,
			@ApiParam(value = "数据源实体", required = true) @Valid @RequestBody DatasourceModel body) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				return datasourcesApiService.updateDatasource(sourceId, body);
			} catch (Exception e) {
				log.error(e.getMessage());
				return new ResponseEntity<ResultEntity>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResultEntity>(HttpStatus.NOT_IMPLEMENTED);
	}

}
