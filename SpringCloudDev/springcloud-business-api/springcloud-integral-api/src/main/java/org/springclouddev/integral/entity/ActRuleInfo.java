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
 * 实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_act_rule_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ActRuleInfo对象", description = "ActRuleInfo对象")
public class ActRuleInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id编号
     */
    @ApiModelProperty(value = "表id编号")
    @TableId("act_rule_info_ID")
    private Long id;
    @TableField("ACT_CODE")
    private String actCode;
    @TableField("RULE_ID")
    private Long ruleId;
    /**
     * 规则计算类型
     */
    @ApiModelProperty(value = "规则计算类型 ")
    private String type;
    /**
     * 优先级
     */
    @ApiModelProperty(value = "优先级")
    @TableField("GRADE")
    private String grade;
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
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    @TableField(exist = false)
    private String ruleName;
    @ApiModelProperty(value = "所属规则组")
    @TableField(exist = false)
    private String ruleTeam;
    @TableField(exist = false)
    private String actName;
}
