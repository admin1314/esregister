package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * ClusterModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-16T03:15:36.847Z")

public class ClusterModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("ip")
  private String ip = null;

  @JsonProperty("port")
  private Integer port = null;

  @JsonProperty("clusterName")
  private String clusterName = null;

  @JsonProperty("CHSName")
  private String chSName = null;

  @JsonProperty("remark")
  private String remark = null;

  public ClusterModel id(String id) {
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

  public ClusterModel ip(String ip) {
    this.ip = ip;
    return this;
  }

  /**
   * ip地址,多个ip用英文逗号隔开
   * @return ip
  **/
  @ApiModelProperty(required = true, value = "ip地址,多个ip用英文逗号隔开")
  @NotNull


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public ClusterModel port(Integer port) {
    this.port = port;
    return this;
  }

  /**
   * tcp端口号
   * @return port
  **/
  @ApiModelProperty(required = true, value = "tcp端口号")
  @NotNull


  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public ClusterModel clusterName(String clusterName) {
    this.clusterName = clusterName;
    return this;
  }

  /**
   * 集群名称
   * @return clusterName
  **/
  @ApiModelProperty(required = true, value = "集群名称")
  @NotNull


  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public ClusterModel chSName(String chSName) {
    this.chSName = chSName;
    return this;
  }

  /**
   * 中文名称
   * @return chSName
  **/
  @ApiModelProperty(value = "中文名称")


  public String getChSName() {
    return chSName;
  }

  public void setChSName(String chSName) {
    this.chSName = chSName;
  }

  public ClusterModel remark(String remark) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClusterModel clusterModel = (ClusterModel) o;
    return Objects.equals(this.id, clusterModel.id) &&
        Objects.equals(this.ip, clusterModel.ip) &&
        Objects.equals(this.port, clusterModel.port) &&
        Objects.equals(this.clusterName, clusterModel.clusterName) &&
        Objects.equals(this.chSName, clusterModel.chSName) &&
        Objects.equals(this.remark, clusterModel.remark);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, ip, port, clusterName, chSName, remark);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClusterModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    clusterName: ").append(toIndentedString(clusterName)).append("\n");
    sb.append("    chSName: ").append(toIndentedString(chSName)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
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

