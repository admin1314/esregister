package com.zc.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zc.dao.DatasourceMapper;
import com.zc.model.DatasourceModel;
import com.zc.model.ResultEntity;
import com.zc.utils.Utils;

@Service
public class DatasourcesApiService {
	@Autowired
	private DatasourceMapper datasourcesMapper;

	
	private static final Logger log = LoggerFactory.getLogger(DatasourcesApiService.class);
	
	/**
	 * 添加数据源信息
	 * 
	 * @param datasourceModel
	 *            数据源实体类
	 * @return
	 */
	public ResponseEntity<ResultEntity> addDatasource(DatasourceModel datasourceModel) {
		try {
			String id = datasourceModel.getId();
			if (Utils.strIsEmpty(id)) {
				id = DigestUtils.md5Hex(datasourceModel.toString());
				datasourceModel.setId(id);
			}
			datasourcesMapper.save(datasourceModel);
			return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, id), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<ResultEntity>(new ResultEntity(1, e.getMessage(), 0, null), HttpStatus.OK);
		}
	}

	/**
	 * 修改数据源信息
	 * 
	 * @param sourceId
	 *            数据源id
	 * @param datasourceModel
	 *            数据源信息实体类
	 * @return
	 */
	@Transactional
	public ResponseEntity<ResultEntity> updateDatasource(String sourceId, DatasourceModel datasourceModel) {
		datasourceModel.setId(sourceId);
		datasourcesMapper.deleteById(sourceId);
		datasourcesMapper.save(datasourceModel);
		return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, sourceId), HttpStatus.OK);
	}
}
