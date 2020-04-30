package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;

/**
 * 系统组群集合表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_grp_list")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GrpList对象", description = "系统组群集合表")
public class GrpList extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId(value = "grp_list_id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 集合码
     */
    @ApiModelProperty(value = "集合码")
    @TableField("CODE")
    private String code;
    /**
     * 集合名称
     */
    @ApiModelProperty(value = "集合名称")
    @TableField("NAME")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("GL_DESC")
    private String glDesc;
    /**
     * 集合类别
     */
    @ApiModelProperty(value = "集合类别")
    @TableField(value = "SORT", fill = FieldFill.INSERT)
    private String sort;
    /**
     * 集合类型
     */
    @ApiModelProperty(value = "集合类型")
    @TableField("TYPE")
    private String type;
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
