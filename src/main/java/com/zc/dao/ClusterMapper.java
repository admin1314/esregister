package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zc.model.ClusterModel;

@Mapper
public interface ClusterMapper {
	
	@Insert("insert into cluster_info(id, ip, tcp_port, cluster_name, chs_name, remark) values (#{id}, #{ip}, #{port}, #{clusterName}, #{chSName}, #{remark})")
	void save(ClusterModel clusterModel);
	
	@Delete("delete from cluster_info where id=#{id}")
	void deleteById(String id);
	
	@Select("select id, ip, tcp_port, cluster_name, chs_name, remark from cluster_info")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "ip", column = "ip"), //
			@Result(property = "port", column = "tcp_port"), //
			@Result(property = "clusterName", column = "cluster_name"), //
			@Result(property = "chSName", column = "chs_name"), //
			@Result(property = "remark", column = "remark") //
	})
	List<ClusterModel> findAll();
}
