package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;

/**
 * 系统活动参数类别项表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_act_prm_cate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActPrmCate对象", description = "系统活动参数类别项表")
public class ActPrmCate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id编号
     */
    @ApiModelProperty(value = "表id编号")
    private Long id;
    /**
     * 所属活动类别编号
     */
    @ApiModelProperty(value = "所属活动类别编号")
    @TableField("AP_CODE")
    private String apCode;
    /**
     * 类别项类别编号
     */
    @ApiModelProperty(value = "类别项类别编号")
    @TableField("CODE")
    private String code;
    /**
     * 类别项类别名称
     */
    @ApiModelProperty(value = "类别项类别名称")
    @TableField("NAME")
    private String name;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("APC_DESC")
    private String apcDesc;
    /**
     * 预留字段1
     */
    @ApiModelProperty(value = "预留字段1")
    private String reserveCokumn1;
    /**
     * 预留字段2
     */
    @ApiModelProperty(value = "预留字段2")
    private String reserveCokumn2;
    /**
     * 预留字段3
     */
    @ApiModelProperty(value = "预留字段3")
    private String reserveCokumn3;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @TableField("AP_order")
    private Integer apOrder;


}
