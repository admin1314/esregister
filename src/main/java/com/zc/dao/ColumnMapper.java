package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zc.model.ColumnModel;

@Mapper
public interface ColumnMapper {
	@Insert("insert into columns(id, cluster_id, index_name, column_name, comments) values (#{id}, #{clusterId}, #{indexName}, #{columnName}, #{comments})")
	void save(ColumnModel columnModel);

	@Delete("delete from columns where cluster_id=#{clusterId} and index_name=#{indexName}")
	void deleteByClusterIdAndIndexName(@Param("clusterId") String clusterId, @Param("indexName") String indexName);
	
	@Delete("delete from columns where id=#{id}")
	void deleteById(@Param("id") String id);

	@Select("select id, cluster_id, index_name, column_name, comments from columns where cluster_id=#{clusterId} and index_name=#{indexName}")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "indexName", column = "index_name"), //
			@Result(property = "columnName", column = "column_name"), //
			@Result(property = "comments", column = "comments") })
	List<ColumnModel> findByClusterIdAndIndexName(@Param("clusterId") String clusterId, @Param("indexName") String indexName);
}
