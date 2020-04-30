                                                                                                        package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 积分调整表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_integral_adjust")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IntegralAdjust对象", description = "积分调整表")
public class IntegralAdjust extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 自增主键
         */
                        @ApiModelProperty(value = "自增主键")
                                                            @TableId(value = "integral_adjust_id", type = IdType.NONE)
                                @JsonSerialize(using= ToStringSerializer.class)
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
         * 调整类型
         */
                        @ApiModelProperty(value = "调整类型")
                                    @TableField("ADJUST_TYPE")
                                                  private String adjustType;
                                        /**
         * 调整数量
         */
                        @ApiModelProperty(value = "调整数量")
                                    @TableField("ADJUST_NUM")
                                                  private String adjustNum;
                                        /**
         * 调整原因
         */
                        @ApiModelProperty(value = "调整原因")
                                    @TableField("ADJUST_REASON")
                                                  private String adjustReason;
                                        /**
         * 调整人
         */
                        @ApiModelProperty(value = "调整人")
                                    @TableField("CRT_USER")
                                                  private String crtUser;
                                        /**
         * 调整日期
         */
                        @ApiModelProperty(value = "调整日期")
                                    @TableField("CRT_TIME")
                                                  private String crtTime;
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
