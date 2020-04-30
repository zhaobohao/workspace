                                package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springclouddev.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
/**
 * 用户活动映射关系表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_client_act_rel")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClientActRel对象", description = "用户活动映射关系表")
public class ClientActRel extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 表id编号
         */
                        @ApiModelProperty(value = "表id编号")
                                                                                                                      private Long id;
                                        /**
         * 客户ID
         */
                        @ApiModelProperty(value = "客户ID")
                                                                      private String clientId;
                                        /**
         * 积分活动ID
         */
                        @ApiModelProperty(value = "积分活动ID")
                                                                      private String actId;
                                        /**
         * 账户ID
         */
                        @ApiModelProperty(value = "账户ID")
                                                                      private String accountId;
            

    }
