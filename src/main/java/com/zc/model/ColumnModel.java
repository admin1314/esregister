package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.*;

/**
 * ColumnModel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-16T07:53:03.298Z")

public class ColumnModel   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("clusterId")
  private String clusterId = null;

  @JsonProperty("indexName")
  private String indexName = null;

  @JsonProperty("typeName")
  private String typeName = null;

  @JsonProperty("columnName")
  private String columnName = null;

  /**
   * 字段类型
   */
  public enum ColumnTypeEnum {
    KEYWORD("keyword"),
    
    TEXT("text"),
    
    DATE("date"),
    
    BYTE("byte"),
    
    SHORT("short"),
    
    INTEGER("integer"),
    
    LONG("long"),
    
    FLOAT("float"),
    
    DOUBLE("double");

    private String value;

    ColumnTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ColumnTypeEnum fromValue(String text) {
      for (ColumnTypeEnum b : ColumnTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("columnType")
  private ColumnTypeEnum columnType = null;

  @JsonProperty("comments")
  private String comments = null;

  public ColumnModel id(String id) {
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

  public ColumnModel clusterId(String clusterId) {
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

  public ColumnModel indexName(String indexName) {
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

  public ColumnModel typeName(String typeName) {
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

  public ColumnModel columnName(String columnName) {
    this.columnName = columnName;
    return this;
  }

  /**
   * 字段名称
   * @return columnName
  **/
  @ApiModelProperty(required = true, value = "字段名称")
  @NotNull


  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public ColumnModel columnType(ColumnTypeEnum columnType) {
    this.columnType = columnType;
    return this;
  }

  /**
   * 字段类型
   * @return columnType
  **/
  @ApiModelProperty(value = "字段类型")


  public ColumnTypeEnum getColumnType() {
    return columnType;
  }

  public void setColumnType(ColumnTypeEnum columnType) {
    this.columnType = columnType;
  }

  public ColumnModel comments(String comments) {
    this.comments = comments;
    return this;
  }

  /**
   * 注释
   * @return comments
  **/
  @ApiModelProperty(required = true, value = "注释")
  @NotNull


  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ColumnModel columnModel = (ColumnModel) o;
    return Objects.equals(this.id, columnModel.id) &&
        Objects.equals(this.clusterId, columnModel.clusterId) &&
        Objects.equals(this.indexName, columnModel.indexName) &&
        Objects.equals(this.typeName, columnModel.typeName) &&
        Objects.equals(this.columnName, columnModel.columnName) &&
        Objects.equals(this.columnType, columnModel.columnType) &&
        Objects.equals(this.comments, columnModel.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clusterId, indexName, columnName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ColumnModel {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    clusterId: ").append(toIndentedString(clusterId)).append("\n");
    sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
    sb.append("    typeName: ").append(toIndentedString(typeName)).append("\n");
    sb.append("    columnName: ").append(toIndentedString(columnName)).append("\n");
    sb.append("    columnType: ").append(toIndentedString(columnType)).append("\n");
    sb.append("    comments: ").append(toIndentedString(comments)).append("\n");
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

