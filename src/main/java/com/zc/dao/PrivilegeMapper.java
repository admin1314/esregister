package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zc.model.PrivilegeModel;

@Mapper
public interface PrivilegeMapper {
	@Insert("insert into user_indexes(id, cluster_id, user_id, index_name) values (#{id}, #{clusterId}, #{userId}, #{indexRegex})")
	void save(PrivilegeModel privilegeModel);

	@Delete("delete from user_indexes where id=#{id}")
	void deleteById(String id);

	@Delete("delete from user_indexes where cluster_id=#{clusterId} and user_id=#{userId} and index_name=#{indexRegex}")
	void deleteOneRecore(@Param("clusterId") String clusterId, @Param("userId") String userId,
			@Param("indexRegex") String indexRegex);

	@Select("select id, cluster_id, user_id, index_name from user_indexes")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "userId", column = "user_id"), //
			@Result(property = "indexRegex", column = "index_name") //
	})
	List<PrivilegeModel> findAll();

	@Select("select id, cluster_id, user_id, index_name from user_indexes where cluster_id=#{clusterId} and user_id=#{userId}")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "userId", column = "user_id"), //
			@Result(property = "indexRegex", column = "index_name") //
	})
	List<PrivilegeModel> findByClusterIdAndUserId(@Param("clusterId") String clusterId, @Param("userId") String userId);
}
