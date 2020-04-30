                                                                                                                                                        package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springclouddev.core.mp.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/**
 * 客户积分明细表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_cust_integral_detail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustIntegralDetail对象", description = "客户积分明细表")
public class CustIntegralDetail extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 表id主键
         */
                        @ApiModelProperty(value = "表id主键")
                                                            @TableId("cust_integral_detail_ID")
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
         * 客户姓名
         */
                        @ApiModelProperty(value = "客户姓名")
                                    @TableField("CUST_NAME")
                                                  private String custName;
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
         * 客户电话
         */
                        @ApiModelProperty(value = "客户电话")
                                    @TableField("PHONE_NUM")
                                                  private String phoneNum;
                                        /**
         * 客户地址
         */
                        @ApiModelProperty(value = "客户地址")
                                    @TableField("CUST_ADDRESS")
                                                  private String custAddress;
                                        /**
         * 积分类型
         */
                        @ApiModelProperty(value = "积分类型")
                                    @TableField("INTEGRAL_TYPE")
                                                  private String integralType;
                                        /**
         * 积分类型ID
         */
                        @ApiModelProperty(value = "积分类型ID")
                                    @TableField("INTEGRAL_TYPE_ID")
                                                  private String integralTypeId;
                                        /**
         * 积分有效期
         */
                        @ApiModelProperty(value = "积分有效期")
                                    @TableField("INTEGRAL_VALID_DATE")
                                                  private String integralValidDate;
                                        /**
         * 积分有效期余额
         */
                        @ApiModelProperty(value = "积分有效期余额")
                                    @TableField("INTEGRAL_VALID_BLANCE")
                                                  private String integralValidBlance;
                                        /**
         * 账户余额
         */
                        @ApiModelProperty(value = "账户余额")
                                    @TableField("ACCOUNT_BLANCE")
                                                  private String accountBlance;
                                        /**
         * 已使用总额
         */
                        @ApiModelProperty(value = "已使用总额")
                                    @TableField("USED_TOATL")
                                                  private String usedToatl;
                                        /**
         * 账户状态
         */
                        @ApiModelProperty(value = "账户状态")
                                    @TableField("ACCOUNT_STATUS")
                                                  private String accountStatus;
                                        /**
         * 账户状态ID
         */
                        @ApiModelProperty(value = "账户状态ID")
                                    @TableField("ACCOUNT_STATUS_ID")
                                                  private String accountStatusId;
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
