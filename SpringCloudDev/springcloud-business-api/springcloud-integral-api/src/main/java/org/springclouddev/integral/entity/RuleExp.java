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

import java.time.LocalDateTime;

/**
 * 规则表达式表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_rule_exp")
@ApiModel(value = "RuleExp对象", description = "规则表达式表")
public class RuleExp  {

    private static final long serialVersionUID = 123424163456L;

    @TableId("RULE_ID")
    private Long id;
    /**
     * 表达式
     */
    @ApiModelProperty(value = "表达式")
    @TableField("EXP_ID")
    private String expId;
    /**
     * MCC码
     */
    @ApiModelProperty(value = "MCC码")
    @TableField("MCC_ID")
    private String mccId;
    /**
     * 名单码
     */
    @ApiModelProperty(value = "名单码")
    @TableField("NAME_ID")
    private String nameId;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("CRT_TIME")
    private LocalDateTime crtTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField("UPD_TIME")
    private LocalDateTime updTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    @TableField("UPD_USER")
    private String updUser;
    private String version;
    private String params;
    /**
     * 规则名称
     */
    @ApiModelProperty(value = "规则名称")
    @TableField(exist = false)
    private String ruleName;
}
