package com.zc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.zc.model.DatasourceModel;
import com.zc.model.DatasourceModel.TypeEnum;

@Mapper
public interface DatasourceMapper {
	@Insert("insert into datasource_info(id, name, type, parameter, remark) values (#{id}, #{name}, #{type}, #{parameter}, #{remark})")
	void save(DatasourceModel datasourceModel);
	
	@Delete("delete from datasource_info where id=#{id}")
	void deleteById(String id);
	
	@Select("select id, name, type, parameter, remark from datasource_info")
	@Results({ //
			@Result(property = "id", column = "id"), //
			@Result(property = "name", column = "name"), //
			@Result(property = "type", column = "type", typeHandler = BaseEnumTypeHandler.class, javaType = TypeEnum.class), //
			@Result(property = "parameter", column = "parameter"), //
			@Result(property = "remark", column = "remark") //
	})
	List<DatasourceModel> findAll();
}
