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
@TableName("tbl_client_group")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClientGroup对象", description = "ClientGroup对象")
public class ClientGroup extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 组群编号
         */
                        @ApiModelProperty(value = "组群编号")
                                                            @TableId("GROUP_ID")
                                                                                  private Long id;
                                        /**
         * 组群号码
         */
                        @ApiModelProperty(value = "组群号码")
                                    @TableField("GROUP_NM")
                                                  private Long groupNm;
                                        /**
         * 组群名称
         */
                        @ApiModelProperty(value = "组群名称")
                                    @TableField("GROUP_NAME")
                                                  private String groupName;
                                        /**
         * 组群描述
         */
                        @ApiModelProperty(value = "组群描述")
                                    @TableField("GROUP_DESC")
                                                  private String groupDesc;
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
