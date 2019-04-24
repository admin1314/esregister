package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zc.model.TypeModel;

@Mapper
public interface TypeMapper {

	@Insert("insert into type_info(id, cluster_id, index_name, type_name, chs_name, user_id, datasource_id, remark) values (#{id}, #{clusterId}, #{indexName}, #{typeName}, #{chSName}, #{userId}, #{datasourceId}, #{remark})")
	void save(TypeModel typeModel);

	@Delete("delete from type_info where cluster_id=#{clusterId} and index_name=#{indexName} and type_name=#{typeName}")
	void deleteByClusterIdAndIndexNameAndTypeName(@Param("clusterId") String clusterId,
			@Param("indexName") String indexName, @Param("typeName") String typeName);

	@Delete("delete from type_info where cluster_id=#{clusterId} and index_name=#{indexName}")
	void deleteByClusterIdAndIndexName(@Param("clusterId") String clusterId, @Param("indexName") String indexName);
	
	@Delete("delete from type_info where id=#{id}")
	void deleteById(@Param("id") String id);

	@Select("select id, cluster_id, index_name, type_name, chs_name, user_id, datasource_id, remark from type_info where cluster_id=#{clusterId} and index_name=#{indexName}")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "indexName", column = "index_name"), //
			@Result(property = "typeName", column = "type_name"), //
			@Result(property = "chSName", column = "chs_name"), //
			@Result(property = "userId", column = "user_id"), //
			@Result(property = "datasourceId", column = "datasource_id"), //
			@Result(property = "remark", column = "remark") })
	List<TypeModel> findByClusterIdAndIndexName(@Param("clusterId") String clusterId,
			@Param("indexName") String indexName);

	@Select("select id, cluster_id, index_name, type_name, chs_name, user_id, datasource_id, remark from type_info where cluster_id=#{clusterId} and index_name=#{indexName} and type_name=#{typeName}")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "indexName", column = "index_name"), //
			@Result(property = "typeName", column = "type_name"), //
			@Result(property = "chSName", column = "chs_name"), //
			@Result(property = "userId", column = "user_id"), //
			@Result(property = "datasourceId", column = "datasource_id"), //
			@Result(property = "remark", column = "remark") })
	TypeModel findByClusterIdAndIndexNameAndTypeName(@Param("clusterId") String clusterId,
			@Param("indexName") String indexName, @Param("typeName") String typeName);

}
