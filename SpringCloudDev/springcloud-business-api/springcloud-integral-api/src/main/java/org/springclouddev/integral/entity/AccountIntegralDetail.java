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
 * 账户积分交易明细表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_account_integral_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AccountIntegralDetail对象", description = "账户积分交易明细表")
public class AccountIntegralDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    @TableId(value = "account_integral_detail_id", type = IdType.NONE)
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
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    @TableField("IDENTY_TYPE")
    private String identyType;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @TableField("IDENTY_CARD")
    private String identyCard;
    /**
     * 交易积分
     */
    @ApiModelProperty(value = "交易积分")
    @TableField("CHANGE_INTEGRAL")
    private String changeIntegral;
    /**
     * 交易日期
     */
    @ApiModelProperty(value = "交易日期")
    @TableField("CHANGE_DATE")
    private String changeDate;
    /**
     * 积分有效期余额
     */
    @ApiModelProperty(value = "积分有效期余额")
    @TableField("INTEGRAL_VALID_BLANCE")
    private String integralValidBlance;
    /**
     * 积分有效期
     */
    @ApiModelProperty(value = "积分有效期")
    @TableField("INTEGRAL_VALID_DATE")
    private String integralValidDate;
    /**
     * 账户余额
     */
    @ApiModelProperty(value = "账户余额")
    @TableField("ACCOUNT_BLANCE")
    private String accountBlance;
    /**
     * 业务
     */
    @ApiModelProperty(value = "业务")
    @TableField("BUSINESS")
    private String business;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String remark;
    /**
     * 积分类型
     */
    @ApiModelProperty(value = "积分类型")
    @TableField("INTEGRAL_TYPE")
    private String integralType;
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
