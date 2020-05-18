package org.springbootdev.modules.drools.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springbootdev.core.mp.base.BaseEntity;
import org.springbootdev.core.mp.base.TenantEntity;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@TableName("mk_drools_ruls")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DroolsRuls对象", description = "DroolsRuls对象")
public class DroolsRuls extends BaseEntity implements TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * drools_group表id
     */
    @ApiModelProperty(value = "drools_group表id")
    @TableField("group_id")
    private Long groupId;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    @TableField("remarks")
    private String remarks;
    /**
     * 规则程序
     */
    @ApiModelProperty(value = "规则程序")
    @TableField("rule_body")
    private String ruleBody;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;


}
