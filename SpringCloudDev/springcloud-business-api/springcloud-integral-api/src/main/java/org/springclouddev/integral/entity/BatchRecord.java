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
@TableName("tbl_batch_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "BatchRecord对象", description = "BatchRecord对象")
public class BatchRecord extends BaseEntity  {

private static final long serialVersionUID = 1L;

                                    /**
         * 批量编号
         */
                        @ApiModelProperty(value = "批量编号")
                                                            @TableId("BATCH_ID")
                                                                                  private Long id;
                                        /**
         * 批量所属类型
         */
                        @ApiModelProperty(value = "批量所属类型")
                                    @TableField("BATCH_TYPE")
                                                  private String batchType;
                                        /**
         * 批量描述
         */
                        @ApiModelProperty(value = "批量描述")
                                    @TableField("BATCH_DESC")
                                                  private String batchDesc;
                                        /**
         * 批量数量
         */
                        @ApiModelProperty(value = "批量数量")
                                    @TableField("BATCH_NM")
                                                  private Integer batchNm;
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
