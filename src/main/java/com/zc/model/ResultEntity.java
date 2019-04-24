package com.zc.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * 统一返回实体类
 */
@ApiModel(description = "统一返回实体类")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-04-12T05:02:57.304Z")

public class ResultEntity {

	public ResultEntity() {
	}

	public ResultEntity(int code, String message, int total, Object data) {
		this.code = code;
		this.message = message;
		this.total = total;
		this.data = data;
	}

	@JsonProperty("code")
	private Integer code = null;

	@JsonProperty("data")
	private Object data = null;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("total")
	private Integer total = null;

	public ResultEntity code(Integer code) {
		this.code = code;
		return this;
	}

	/**
	 * 状态码
	 * 
	 * @return code
	 **/
	@ApiModelProperty(value = "状态码")

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public ResultEntity data(Object data) {
		this.data = data;
		return this;
	}

	/**
	 * 返回数据
	 * 
	 * @return data
	 **/
	@ApiModelProperty(value = "返回数据")

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResultEntity message(String message) {
		this.message = message;
		return this;
	}

	/**
	 * 返回状态
	 * 
	 * @return message
	 **/
	@ApiModelProperty(value = "返回状态")

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResultEntity total(Integer total) {
		this.total = total;
		return this;
	}

	/**
	 * 返回的数据条数
	 * 
	 * @return total
	 **/
	@ApiModelProperty(value = "返回的数据条数")

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ResultEntity resultEntity = (ResultEntity) o;
		return Objects.equals(this.code, resultEntity.code) && Objects.equals(this.data, resultEntity.data)
				&& Objects.equals(this.message, resultEntity.message) && Objects.equals(this.total, resultEntity.total);
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, data, message, total);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ResultEntity {\n");

		sb.append("    code: ").append(toIndentedString(code)).append("\n");
		sb.append("    data: ").append(toIndentedString(data)).append("\n");
		sb.append("    message: ").append(toIndentedString(message)).append("\n");
		sb.append("    total: ").append(toIndentedString(total)).append("\n");
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
