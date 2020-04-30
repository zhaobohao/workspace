package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 系统活动参数表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_act_prm")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActPrm对象", description = "系统活动参数表")
public class ActPrm extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id编号
     */
    @ApiModelProperty(value = "表id编号")
    @TableId("act_prm_ID")
    private Long id;
    /**
     * 活动类别编号
     */
    @ApiModelProperty(value = "活动类别编号")
    @TableField("CODE")
    private String code;
    /**
     * 活动类别名称
     */
    @ApiModelProperty(value = "活动类别名称")
    @TableField("NAME")
    private String name;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("AP_DESC")
    private String apDesc;
    /**
     * 状态取自参数表
     */
    @ApiModelProperty(value = "状态取自参数表")
    private String stat;
    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @TableField(fill = FieldFill.INSERT)
    private Integer sort;
    @TableField("HIDE_DEPT_ID")
    private String hideDeptId;
    @TableField("DATA_MODE_ID")
    private String dataModeId;
    @TableField("PARENT_CODER_ID")
    private String parentCoderId;
    @TableField("SELECTOR_MODE_ID")
    private String selectorModeId;
    @TableField("CONDITION_TYPE_ID")
    private String conditionTypeId;
    /**
     * 所属系统ID
     */
    @ApiModelProperty(value = "所属系统ID")
    @TableField("SYS_ID")
    private String sysId;
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
     * 日期类型（当DATA_MODE为DATE时的扩展）
     */
    @ApiModelProperty(value = "日期类型（当DATA_MODE为DATE时的扩展）")
    @TableField("DATE_TYPE")
    private String dateType;
    /**
     * 试算是否显示字段，0显示1不显示
     */
    @ApiModelProperty(value = "试算是否显示字段，0显示1不显示")
    private Integer trialShow;
    /**
     * 隐藏预留字段
     */
    @ApiModelProperty(value = "隐藏预留字段")
    private String parentValue;
    /**
     * 转换表达式
     */
    @ApiModelProperty(value = "转换表达式")
    private String value;


}
