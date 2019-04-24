package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.zc.model.ColumnModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * 索引实体
 */
@ApiModel(description = "索引实体")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-15T01:47:31.151Z")

public class IndexModel {
	@JsonProperty("id")
	private String id = null;

	@JsonProperty("clusterId")
	private String clusterId = null;

	@JsonProperty("indexName")
	private String indexName = null;

	@JsonProperty("CHSName")
	private String chSName = null;

	@JsonProperty("purpose")
	private String purpose = null;

	@JsonProperty("remark")
	private String remark = null;

	@JsonProperty("alias")
	@Valid
	private List<String> alias = null;

	@JsonProperty("size")
	private Integer size = null;

	@JsonProperty("docs")
	private Integer docs = null;

	/**
	 * 是否打开
	 */
	public enum IsOpenEnum {
		YES("yes"),

		NO("no");

		private String value;

		IsOpenEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static IsOpenEnum fromValue(String text) {
			for (IsOpenEnum b : IsOpenEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}
	}

	@JsonProperty("isOpen")
	private IsOpenEnum isOpen = null;

	/**
	 * 是否废弃
	 */
	public enum DiscardEnum implements BaseEnum<DiscardEnum, String> {
		YES("yes"),

		NO("no");

		private String value;

		DiscardEnum(String value) {
			this.value = value;
		}

		@Override
		@JsonValue
		public String toString() {
			return String.valueOf(value);
		}

		@JsonCreator
		public static DiscardEnum fromValue(String text) {
			for (DiscardEnum b : DiscardEnum.values()) {
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

	@JsonProperty("discard")
	private DiscardEnum discard = null;

	@JsonProperty("createTime")
	private String createTime = null;

	@JsonProperty("columns")
	@Valid
	private List<ColumnModel> columns = null;

	public IndexModel id(String id) {
		this.id = id;
		return this;
	}

	/**
	 * 唯一标识
	 * 
	 * @return id
	 **/
	@ApiModelProperty(value = "唯一标识")

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IndexModel clusterId(String clusterId) {
		this.clusterId = clusterId;
		return this;
	}

	/**
	 * 集群标识
	 * 
	 * @return clusterId
	 **/
	@ApiModelProperty(value = "集群标识")

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public IndexModel indexName(String indexName) {
		this.indexName = indexName;
		return this;
	}

	/**
	 * 索引名称
	 * 
	 * @return indexName
	 **/
	@ApiModelProperty(required = true, value = "索引名称")
	@NotNull

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public IndexModel chSName(String chSName) {
		this.chSName = chSName;
		return this;
	}

	/**
	 * 中文名称
	 * 
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

	public IndexModel purpose(String purpose) {
		this.purpose = purpose;
		return this;
	}

	/**
	 * 用途
	 * 
	 * @return purpose
	 **/
	@ApiModelProperty(required = true, value = "用途")
	@NotNull

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public IndexModel remark(String remark) {
		this.remark = remark;
		return this;
	}

	/**
	 * 备注
	 * 
	 * @return remark
	 **/
	@ApiModelProperty(value = "备注")

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public IndexModel alias(List<String> alias) {
		this.alias = alias;
		return this;
	}

	public IndexModel addAliasItem(String aliasItem) {
		if (this.alias == null) {
			this.alias = new ArrayList<String>();
		}
		this.alias.add(aliasItem);
		return this;
	}

	/**
	 * Get alias
	 * 
	 * @return alias
	 **/
	@ApiModelProperty(value = "")

	public List<String> getAlias() {
		return alias;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

	public IndexModel size(Integer size) {
		this.size = size;
		return this;
	}

	/**
	 * 大小，单位：KB
	 * 
	 * @return size
	 **/
	@ApiModelProperty(value = "大小，单位：KB")

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public IndexModel docs(Integer docs) {
		this.docs = docs;
		return this;
	}

	/**
	 * 文档数
	 * 
	 * @return docs
	 **/
	@ApiModelProperty(value = "文档数")

	public Integer getDocs() {
		return docs;
	}

	public void setDocs(Integer docs) {
		this.docs = docs;
	}

	public IndexModel isOpen(IsOpenEnum isOpen) {
		this.isOpen = isOpen;
		return this;
	}

	/**
	 * 是否打开
	 * 
	 * @return isOpen
	 **/
	@ApiModelProperty(value = "是否打开")

	public IsOpenEnum getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(IsOpenEnum isOpen) {
		this.isOpen = isOpen;
	}

	public IndexModel discard(DiscardEnum discard) {
		this.discard = discard;
		return this;
	}

	/**
	 * 是否废弃
	 * 
	 * @return discard
	 **/
	@ApiModelProperty(value = "是否废弃")

	public DiscardEnum getDiscard() {
		return discard;
	}

	public void setDiscard(DiscardEnum discard) {
		this.discard = discard;
	}

	public IndexModel createTime(String createTime) {
		this.createTime = createTime;
		return this;
	}

	/**
	 * 创建时间
	 * 
	 * @return createTime
	 **/
	@ApiModelProperty(value = "创建时间")

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public IndexModel columns(List<ColumnModel> columns) {
		this.columns = columns;
		return this;
	}

	public IndexModel addColumnsItem(ColumnModel columnsItem) {
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnModel>();
		}
		this.columns.add(columnsItem);
		return this;
	}

	/**
	 * Get columns
	 * 
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
		IndexModel indexModel = (IndexModel) o;
		return Objects.equals(this.id, indexModel.id) && Objects.equals(this.clusterId, indexModel.clusterId)
				&& Objects.equals(this.indexName, indexModel.indexName)
				&& Objects.equals(this.chSName, indexModel.chSName) && Objects.equals(this.purpose, indexModel.purpose)
				&& Objects.equals(this.remark, indexModel.remark) && Objects.equals(this.alias, indexModel.alias)
				&& Objects.equals(this.size, indexModel.size) && Objects.equals(this.docs, indexModel.docs)
				&& Objects.equals(this.isOpen, indexModel.isOpen) && Objects.equals(this.discard, indexModel.discard)
				&& Objects.equals(this.createTime, indexModel.createTime)
				&& Objects.equals(this.columns, indexModel.columns);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, clusterId, indexName, chSName, purpose, remark, alias, size, docs, isOpen, discard,
				createTime, columns);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class IndexModel {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    clusterId: ").append(toIndentedString(clusterId)).append("\n");
		sb.append("    indexName: ").append(toIndentedString(indexName)).append("\n");
		sb.append("    chSName: ").append(toIndentedString(chSName)).append("\n");
		sb.append("    purpose: ").append(toIndentedString(purpose)).append("\n");
		sb.append("    remark: ").append(toIndentedString(remark)).append("\n");
		sb.append("    alias: ").append(toIndentedString(alias)).append("\n");
		sb.append("    size: ").append(toIndentedString(size)).append("\n");
		sb.append("    docs: ").append(toIndentedString(docs)).append("\n");
		sb.append("    isOpen: ").append(toIndentedString(isOpen)).append("\n");
		sb.append("    discard: ").append(toIndentedString(discard)).append("\n");
		sb.append("    createTime: ").append(toIndentedString(createTime)).append("\n");
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
