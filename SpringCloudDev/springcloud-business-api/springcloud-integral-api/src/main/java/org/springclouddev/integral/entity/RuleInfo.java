package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_rule_info")
@ApiModel(value = "RuleInfo对象", description = "RuleInfo对象")
public class RuleInfo {

    private static final long serialVersionUID = 1234112345678567L;

    /**
     * 规则编号
     */
    @ApiModelProperty(value = "规则编号")
    @TableId(value = "RULE_ID", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    @TableField("RULE_NAME")
    private String ruleName;
    /**
     * 规则状态
     */
    @ApiModelProperty(value = "规则状态")
    @TableField("RULE_STATUS")
    private String ruleStatus;
    @TableField("RULE_EXP")
    private String ruleExp;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField("CRT_USER")
    private String crtUser;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @TableField("CRT_DATE")
    private LocalDateTime crtDate;
    /**
     * 送审人
     */
    @ApiModelProperty(value = "送审人")
    @TableField("LST_UPD_USER")
    private String lstUpdUser;
    /**
     * 送审日期
     */
    @ApiModelProperty(value = "送审日期")
    @TableField("update_time")
    private LocalDateTime lstUpdTime;
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
