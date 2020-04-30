package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;

/**
 * 系统组群表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_grp_cls")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "GrpCls对象", description = "系统组群表")
public class GrpCls extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId("grp_cls_ID")
    private Long id;
    private String listId;
    /**
     * 组群码
     */
    @ApiModelProperty(value = "组群码")
    @TableField("CODE")
    private String code;
    /**
     * 组群名称
     */
    @ApiModelProperty(value = "组群名称")
    @TableField("NAME")
    private String name;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @TableField("GC_DESC")
    private String gcDesc;
    /**
     * 组群类别
     */
    @ApiModelProperty(value = "组群类别")
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
