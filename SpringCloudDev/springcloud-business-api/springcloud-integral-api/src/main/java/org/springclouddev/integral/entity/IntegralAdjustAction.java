package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;

/**
 * 积分调整动作表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_integral_adjust_action")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IntegralAdjustAction对象", description = "积分调整动作表")
public class IntegralAdjustAction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @TableId(value = "integral_adjust_action_id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    @TableField("ACCOUNT_ID")
    private String accountId;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @TableField("CUST_ID")
    private String custId;
    /**
     * 调整数量
     */
    @ApiModelProperty(value = "调整数量")
    @TableField("ADJUST_NUM")
    private String adjustNum;
    /**
     * 调整状态id
     */
    @ApiModelProperty(value = "调整状态id")
    @TableField("STATUS_VALUE")
    private String statusValue;
    /**
     * 调整状态name
     */
    @ApiModelProperty(value = "调整状态name")
    @TableField("STATUS_NAME")
    private String statusName;
    /**
     * 调整人
     */
    @ApiModelProperty(value = "调整人")
    @TableField("CRT_USER")
    private String crtUser;
    /**
     * 积分类型
     */
    @ApiModelProperty(value = "积分类型")
    @TableField("INTEGRAL_TYPE")
    private String integralType;
    /**
     * 积分类型id
     */
    @ApiModelProperty(value = "积分类型id")
    @TableField("INTEGRAL_TYPE_ID")
    private String integralTypeId;
    /**
     * 最后更新人
     */
    @ApiModelProperty(value = "最后更新人")
    @TableField("LST_UPD_USER")
    private String lstUpdUser;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "最后更新时间")
    @TableField("update_time")
    private String lstUpdTime;
    /**
     * 调整日期
     */
    @ApiModelProperty(value = "调整日期")
    @TableField("ADJUST_DATE")
    private String adjustDate;
    /**
     * 调整原因
     */
    @ApiModelProperty(value = "调整原因")
    @TableField("ADJUST_REASON")
    private String adjustReason;
    /**
     * 预留字段一
     */
    @ApiModelProperty(value = "预留字段一")
    @TableField("RESERVE_COLUMN1")
    private String reserveColumn1;
    /**
     * 预留字段二
     */
    @ApiModelProperty(value = "预留字段二")
    @TableField("RESERVE_COLUMN2")
    private String reserveColumn2;
    /**
     * 预留字段三
     */
    @ApiModelProperty(value = "预留字段三")
    @TableField("RESERVE_COLUMN3")
    private String reserveColumn3;


}
