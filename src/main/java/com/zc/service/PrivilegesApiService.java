package com.zc.service;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zc.dao.PrivilegeMapper;
import com.zc.model.PrivilegeModel;
import com.zc.model.ResultEntity;
import com.zc.utils.Utils;

@Service
public class PrivilegesApiService {
	@Autowired
	private PrivilegeMapper privilegeMapper;

//	private static final Logger log = LoggerFactory.getLogger(PrivilegesApiService.class);
	
	/**
	 * 给用户添加索引查看、编辑权限
	 * 
	 * @param clusterId
	 *            集群id
	 * @param privilegeModelList
	 *            权限数组
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ResponseEntity<ResultEntity> addPrivilege(String clusterId, List<PrivilegeModel> privilegeModelList)
			throws Exception {
		for (PrivilegeModel privilegeModel : privilegeModelList) {
			// TODO 检查索引是否存在，userid是否存在。
			privilegeModel.setClusterId(clusterId);
			if (Utils.strIsEmpty(privilegeModel.getId())) {
				privilegeModel.setId(DigestUtils.md5Hex(privilegeModel.toString()));
			}
			privilegeMapper.save(privilegeModel);
		}
		return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
	}

	/**
	 * 删除用户的索引查看、编辑权限
	 * 
	 * @param clusterId
	 *            集群id
	 * @param privilegeModelList
	 * @return
	 */
	@Transactional
	public ResponseEntity<ResultEntity> deletePrivilege(String clusterId, List<PrivilegeModel> privilegeModelList) {
		for (PrivilegeModel privilegeModel : privilegeModelList) {
			privilegeMapper.deleteOneRecore(clusterId, privilegeModel.getUserId(), privilegeModel.getIndexRegex());
		}
		return new ResponseEntity<ResultEntity>(new ResultEntity(0, "success", 0, null), HttpStatus.OK);
	}

}
