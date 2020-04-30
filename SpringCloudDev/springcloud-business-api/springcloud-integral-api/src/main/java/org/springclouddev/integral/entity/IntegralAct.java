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

import java.time.LocalDateTime;

/**
 * 积分活动管理表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_integral_act")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IntegralAct对象", description = "积分活动管理表")
public class IntegralAct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId(value = "integral_act_id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 积分活动编号
     */
    @ApiModelProperty(value = "积分活动编号")
    @TableField("ACT_CODE")
    private String actCode;
    /**
     * 积分活动名称
     */
    @ApiModelProperty(value = "积分活动名称")
    @TableField("ACT_NAME")
    private String actName;
    /**
     * 营销活动编号
     */
    @ApiModelProperty(value = "营销活动编号")
    @TableField("MARKET_ACT_ID")
    private String marketActId;
    /**
     * 营销活动结束时间
     */
    @ApiModelProperty(value = "营销活动结束时间")
    private String endTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    @TableField("DEPARTMENT")
    private String department;
    /**
     * 积分类型
     */
    @ApiModelProperty(value = "积分类型")
    @TableField("INTEGRAL_TYPE")
    private String integralType;
    /**
     * 积分有效期类型
     */
    @ApiModelProperty(value = "积分有效期类型")
    @TableField("INTEGRAL_LIMIT_TIME_TYPE")
    private String integralLimitTimeType;
    /**
     * 积分有效期年限
     */
    @ApiModelProperty(value = "积分有效期年限")
    @TableField("INTEGRAL_LIMIT_YEAR_NUM")
    private Long integralLimitYearNum;
    /**
     * 积分到期月份
     */
    @ApiModelProperty(value = "积分到期月份")
    @TableField("INTEGRAL_END_MONTH")
    private String integralEndMonth;
    /**
     * 计划积分
     */
    @ApiModelProperty(value = "计划积分")
    @TableField("PREPARE_INTEGRAL_NUM")
    private Long prepareIntegralNum;
    /**
     * 计划人数
     */
    @ApiModelProperty(value = "计划人数")
    @TableField("PREPARE_COUNT")
    private Long prepareCount;
    /**
     * 活动描述
     */
    @ApiModelProperty(value = "活动描述")
    @TableField("ACT_BREIF")
    private String actBreif;
    /**
     * 状态: audit_status_tosub：新增，audit_status_wait提交待审核，audit_status_pass审核通过，audit_status_nopass退回
     */
    @ApiModelProperty(value = "状态: audit_status_tosub：新增，audit_status_wait提交待审核，audit_status_pass审核通过，audit_status_nopass退回")
    @TableField("STATE")
    private String state;
    /**
     * 所属规则组
     */
    @ApiModelProperty(value = "所属规则组")
    @TableField("RULE_TEAM")
    private String ruleTeam;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @TableField("OPINION")
    private String opinion;
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
