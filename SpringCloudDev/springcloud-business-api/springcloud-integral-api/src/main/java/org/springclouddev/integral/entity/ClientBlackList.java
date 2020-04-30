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
 * 实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_client_black_list")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClientBlackList对象", description = "ClientBlackList对象")
public class ClientBlackList extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 黑名单编号
         */
                        @ApiModelProperty(value = "黑名单编号")
                                                            @TableId("BLACK_ID")
                                                                                  private Long id;
                                        /**
         * 规则名称
         */
                        @ApiModelProperty(value = "规则名称")
                                    @TableField("CLIENT_ID")
                                                  private Long clientId;
                                        /**
         * 客户姓名
         */
                        @ApiModelProperty(value = "客户姓名")
                                    @TableField("CLIENT_NAME")
                                                  private String clientName;
                                        /**
         * 开户行名称
         */
                        @ApiModelProperty(value = "开户行名称")
                                    @TableField("BANK_NAME")
                                                  private String bankName;
                                        /**
         * 性别
         */
                        @ApiModelProperty(value = "性别")
                                    @TableField("SEX")
                                                  private String sex;
                                        /**
         * 联系电话
         */
                        @ApiModelProperty(value = "联系电话")
                                    @TableField("MOBILE")
                                                  private String mobile;
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
