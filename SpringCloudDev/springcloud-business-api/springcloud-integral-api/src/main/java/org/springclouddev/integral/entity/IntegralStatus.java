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
 * 积分冻结解冻状态表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_integral_status")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IntegralStatus对象", description = "积分冻结解冻状态表")
public class IntegralStatus extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 自增主键
         */
                        @ApiModelProperty(value = "自增主键")
                                                            @TableId(value = "integral_status_id", type = IdType.NONE)
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
         * 调整状态
         */
                        @ApiModelProperty(value = "调整状态")
                                    @TableField("STATUS_Value")
                                                  private String statusValue;
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
