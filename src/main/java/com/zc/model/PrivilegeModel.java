package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * 权限实体类
 */
@ApiModel(description = "权限实体类")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-17T00:18:06.358Z")

public class PrivilegeModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("clusterId")
  private String clusterId = null;

  @JsonProperty("userId")
  private String userId = null;

  @JsonProperty("indexRegex")
  private String indexRegex = null;

  public PrivilegeModel id(String id) {
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

  public PrivilegeModel clusterId(String clusterId) {
    this.clusterId = clusterId;
    return this;
  }

  /**
   * 集群唯一标识
   * @return clusterId
  **/
  @ApiModelProperty(required = true, value = "集群唯一标识")
  @NotNull


  public String getClusterId() {
    return clusterId;
  }

  public void setClusterId(String clusterId) {
    this.clusterId = clusterId;
  }

  public PrivilegeModel userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * 用户id
   * @return userId
  **/
  @ApiModelProperty(required = true, value = "用户id")
  @NotNull


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public PrivilegeModel indexRegex(String indexRegex) {
    this.indexRegex = indexRegex;
    return this;
  }

  /**
   * 索引名称正则表达式
   * @return indexRegex
  **/
  @ApiModelProperty(required = true, value = "索引名称正则表达式")
  @NotNull


  public String getIndexRegex() {
    return indexRegex;
  }

  public void setIndexRegex(String indexRegex) {
    this.indexRegex = indexRegex;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PrivilegeModel privilegeModel = (PrivilegeModel) o;
    return Objects.equals(this.id, privilegeModel.id) &&
        Objects.equals(this.clusterId, privilegeModel.clusterId) &&
        Objects.equals(this.userId, privilegeModel.userId) &&
        Objects.equals(this.indexRegex, privilegeModel.indexRegex);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clusterId, userId, indexRegex);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PrivilegeModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    clusterId: ").append(toIndentedString(clusterId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    indexRegex: ").append(toIndentedString(indexRegex)).append("\n");
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

