
package org.springbootdev.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springbootdev.core.mp.base.BaseEntity;
import org.springbootdev.core.mp.base.TenantEntity;

import java.util.Date;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements TenantEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.NONE)
	@ApiModelProperty(value = "主键id")
	@JsonSerialize(using= ToStringSerializer.class)
	private Long id;
	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@TableField(fill = FieldFill.INSERT)
	private String tenantId;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 真名
	 */
	private String realName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 部门id
	 */
	private String deptId;
	/**
	 * 个人介绍
	 */
	private String introduction;

	/**
	 * 个人头像
	 */
	private String avatar;

}
