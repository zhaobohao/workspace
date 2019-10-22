package com.spring.web.entity;

import com.spring.web.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * SystemUser
 * </p>
 *
 * @author zhaobohao
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysUser对象", description="SystemUser")
public class SysUser extends SuperEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "account")
    private String account;

    @ApiModelProperty(value = "password")
    private String pwd;

    @ApiModelProperty(value = "remark")
    private String remark;

    @ApiModelProperty(value = "create time")
    private Date createTime;

    @ApiModelProperty(value = "update time")
    private Date updateTime;

}
