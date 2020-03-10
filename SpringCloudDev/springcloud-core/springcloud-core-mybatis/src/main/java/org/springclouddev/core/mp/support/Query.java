
package org.springclouddev.core.mp.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 分页工具
 *
 * @author zhaobohao
 */
@ApiModel(description = "查询条件")
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@ToString
public class Query {

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	private Integer current;

	/**
	 * 每页的数量
	 */
	@ApiModelProperty(value = "每页的数量")
	private Integer size;

	/**
	 * 排序的字段名
	 */
	@ApiModelProperty(hidden = true)
	private String ascs;

	/**
	 * 排序方式
	 */
	@ApiModelProperty(hidden = true)
	private String descs;

}
