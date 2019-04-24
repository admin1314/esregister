package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zc.model.ColumnModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * type实体
 */
@ApiModel(description = "type实体")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-12T05:02:57.304Z")

public class TypeModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("clusterId")
  private String clusterId = null;

  @JsonProperty("indexName")
  private String indexName = null;

  @JsonProperty("typeName")
  private String typeName = null;

  @JsonProperty("remark")
  private String remark = null;

  @JsonProperty("datasourceId")
  private String datasourceId = null;

  @JsonProperty("CHSName")
  private String chSName = null;

  @JsonProperty("userId")
  private String userId = null;

  @JsonProperty("columns")
  @Valid
  private List<ColumnModel> columns = null;

  public TypeModel id(String id) {
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

  public TypeModel clusterId(String clusterId) {
    this.clusterId = clusterId;
    return this;
  }

  /**
   * 集群唯一标识
   * @return clusterId
  **/
  @ApiModelProperty(value = "集群唯一标识")


  public String getClusterId() {
    return clusterId;
  }

  public void setClusterId(String clusterId) {
    this.clusterId = clusterId;
  }

  public TypeModel indexName(String indexName) {
    this.indexName = indexName;
    return this;
  }

  /**
   * 所属索引名称
   * @return indexName
  **/
  @ApiModelProperty(value = "所属索引名称")


  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public TypeModel typeName(String typeName) {
    this.typeName = typeName;
    return this;
  }

  /**
   * type名称
   * @return typeName
  **/
  @ApiModelProperty(value = "type名称")


  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public TypeModel remark(String remark) {
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

  public TypeModel datasourceId(String datasourceId) {
    this.datasourceId = datasourceId;
    return this;
  }

  /**
   * 数据源id
   * @return datasourceId
  **/
  @ApiModelProperty(value = "数据源id")


  public String getDatasourceId() {
    return datasourceId;
  }

  public void setDatasourceId(String datasourceId) {
    this.datasourceId = datasourceId;
  }

  public TypeModel chSName(String chSName) {
    this.chSName = chSName;
    return this;
  }

  /**
   * 中文名称
   * @return chSName
  **/
  @ApiModelProperty(required = true, value = "中文名称")
  @NotNull


  public String getChSName() {
    return chSName;
  }

  public void setChSName(String chSName) {
    this.chSName = chSName;
  }

  public TypeModel userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * 责任人id
   * @return userId
  **/
  @ApiModelProperty(value = "责任人id")


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public TypeModel columns(List<ColumnModel> columns) {
    this.columns = columns;
    return this;
  }

  public TypeModel addColumnsItem(ColumnModel columnsItem) {
    if (this.columns == null) {
      this.columns = new ArrayList<ColumnModel>();
    }
    this.columns.add(columnsItem);
    return this;
  }

  /**
   * Get columns
   * @return columns
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ColumnModel> getColumns() {
    return columns;
  }

  public void setColumns(List<ColumnModel> columns) {
    this.columns = columns;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TypeModel typeModel = (TypeModel) o;
    return Objects.equals(this.id, typeModel.id) &&
        Objects.equals(this.clusterId, typeModel.clusterId) &&
        Objects.equals(this.indexName, typeModel.indexName) &&
        Objects.equals(this.typeName, typeModel.typeName) &&
        Objects.equals(this.remark, typeModel.remark) &&
        Objects.equals(this.datasourceId, typeModel.datasourceId) &&
        Objects.equals(this.chSName, typeModel.chSName) &&
        Objects.equals(this.userId, typeModel.userId) &&
        Objects.equals(this.columns, typeModel.columns);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, clusterId, indexName, typeName, remark, datasourceId, chSName, userId, columns);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypeModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    clusterId: ").append(toIndentedString(clusterId)).append("\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    typeName: ").append(toIndentedString(typeName)).append("\n");
    sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
    sb.append("    datasourceId: ").append(toIndentedString(datasourceId)).append("\n");
    sb.append("    chSName: ").append(toIndentedString(chSName)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    columns: ").append(toIndentedString(columns)).append("\n");
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

