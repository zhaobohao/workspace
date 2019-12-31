
package org.springbootdev.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springbootdev.core.mp.base.BaseEntity;
import org.springbootdev.core.mp.base.TenantEntity;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_param")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Param对象", description = "Param对象")
public class Param extends BaseEntity implements TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.NONE)
	@ApiModelProperty(value = "主键id")
	private Long id;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@TableField(fill = FieldFill.INSERT)
	private String tenantId;

	/**
	 * 参数名
	 */
	@ApiModelProperty(value = "参数名")
	private String paramName;

	/**
	 * 参数键
	 */
	@ApiModelProperty(value = "参数键")
	private String paramKey;

	/**
	 * 参数值
	 */
	@ApiModelProperty(value = "参数值")
	private String paramValue;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;


}
