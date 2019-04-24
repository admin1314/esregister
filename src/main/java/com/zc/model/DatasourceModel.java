package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.zc.model.IndexModel.DiscardEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * 数据源实体类
 */
@ApiModel(description = "数据源实体类")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-16T04:58:45.299Z")

public class DatasourceModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  /**
   * 数据源类型
   */
  public enum TypeEnum implements BaseEnum<DiscardEnum, String> {
    MYSQL("mysql"),
    
    SQLSERVER("sqlserver");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }

	@Override
	public String getValue() {
		return String.valueOf(value);
	}
  }

  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("addTime")
  private String addTime = null;

  @JsonProperty("remark")
  private String remark = null;

  @JsonProperty("parameter")
  private String parameter = null;

  public DatasourceModel id(String id) {
    this.id = id;
    return this;
  }

  /**
   * 唯一标识
   * @return id
  **/
  @ApiModelProperty(value = "唯一标识")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DatasourceModel name(String name) {
    this.name = name;
    return this;
  }

  /**
   * 数据源名称
   * @return name
  **/
  @ApiModelProperty(required = true, value = "数据源名称")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DatasourceModel type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * 数据源类型
   * @return type
  **/
  @ApiModelProperty(required = true, value = "数据源类型")
  @NotNull


  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public DatasourceModel addTime(String addTime) {
    this.addTime = addTime;
    return this;
  }

  /**
   * 添加时间
   * @return addTime
  **/
  @ApiModelProperty(value = "添加时间")


  public String getAddTime() {
    return addTime;
  }

  public void setAddTime(String addTime) {
    this.addTime = addTime;
  }

  public DatasourceModel remark(String remark) {
    this.remark = remark;
    return this;
  }

  /**
   * 备注
   * @return remark
  **/
  @ApiModelProperty(value = "备注")


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public DatasourceModel parameter(String parameter) {
    this.parameter = parameter;
    return this;
  }

  /**
   * 链接参数
   * @return parameter
  **/
  @ApiModelProperty(required = true, value = "链接参数")
  @NotNull


  public String getParameter() {
    return parameter;
  }

  public void setParameter(String parameter) {
    this.parameter = parameter;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatasourceModel datasourceModel = (DatasourceModel) o;
    return Objects.equals(this.id, datasourceModel.id) &&
        Objects.equals(this.name, datasourceModel.name) &&
        Objects.equals(this.type, datasourceModel.type) &&
        Objects.equals(this.addTime, datasourceModel.addTime) &&
        Objects.equals(this.remark, datasourceModel.remark) &&
        Objects.equals(this.parameter, datasourceModel.parameter);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, type, addTime, remark, parameter);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatasourceModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    addTime: ").append(toIndentedString(addTime)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
    sb.append("    parameter: ").append(toIndentedString(parameter)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

