package org.springclouddev.drools.entity;

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
import org.springclouddev.core.mp.base.BaseEntity;
import org.springclouddev.core.mp.base.TenantEntity;

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
@TableName("mk_drools_group")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DroolsGroup对象", description = "DroolsGroup对象")
public class DroolsGroup extends BaseEntity implements TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    @TableField("name")
    private String name;
    /**
     * 分组说明
     */
    @ApiModelProperty(value = "分组说明")
    @TableField("remarks")
    private String remarks;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;


}
