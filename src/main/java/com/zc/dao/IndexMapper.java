package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zc.model.IndexModel;
import com.zc.model.IndexModel.DiscardEnum;

@Mapper
public interface IndexMapper {
	/**
	 * 插入一条索引信息
	 * 
	 * @param indexModel
	 */
	@Insert("insert into index_info(id, cluster_id, index_name, chs_name, discard, purpose, remark) values (#{id}, #{clusterId}, #{indexName}, #{chSName}, #{discard}, #{purpose}, #{remark})")
	void save(IndexModel indexModel);

	/**
	 * 根据id删除一条索引信息
	 * 
	 * @param id
	 */
	@Delete("delete from index_info where id=#{id}")
	void deleteById(String id);

	/**
	 * 根据集群id和索引名称删除索引信息
	 * 
	 * @param clusterId
	 * @param indexName
	 */
	@Delete("delete from index_info where cluster_id=#{clusterId} and index_name=#{indexName}")
	void deleteByClusterIdAndIndexName(@Param("clusterId") String clusterId, @Param("indexName") String indexName);

	/**
	 * 根据集群id获取索引列表
	 * 
	 * @param clusterId
	 * @return
	 */
	@Select("select id, cluster_id, index_name, chs_name, discard, purpose, remark from index_info where cluster_id=#{clusterId}")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "indexName", column = "index_name"), //
			@Result(property = "chSName", column = "chs_name"), //
			@Result(property = "discard", column = "discard", typeHandler = BaseEnumTypeHandler.class, javaType = DiscardEnum.class), //
			@Result(property = "purpose", column = "purpose"), //
			@Result(property = "remark", column = "remark") //
	})
	List<IndexModel> findByClusterId(@Param("clusterId") String clusterId);

	/**
	 * 根据集群id和索引名称获取索引详情
	 * 
	 * @param clusterId
	 * @param indexName
	 * @return
	 */
	@Select("select id, cluster_id, index_name, chs_name, discard, purpose, remark from index_info where cluster_id=#{clusterId} and index_name=#{indexName}")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "clusterId", column = "cluster_id"), //
			@Result(property = "indexName", column = "index_name"), //
			@Result(property = "chSName", column = "chs_name"), //
			@Result(property = "discard", column = "discard", typeHandler = BaseEnumTypeHandler.class, javaType = DiscardEnum.class), //
			@Result(property = "purpose", column = "purpose"), //
			@Result(property = "remark", column = "remark") //
	})
	IndexModel findByClusterIdAndIndexName(@Param("clusterId") String clusterId, @Param("indexName") String indexName);
}
