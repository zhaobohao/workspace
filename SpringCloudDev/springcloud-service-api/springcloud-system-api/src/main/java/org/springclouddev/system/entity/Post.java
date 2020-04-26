package org.springclouddev.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springclouddev.core.mp.base.TenantEntity;

/**
 * 岗位表实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("blade_post")
@ApiModel(value = "Post对象", description = "岗位表")
public class Post implements TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer category;
    /**
     * 岗位编号
     */
    @ApiModelProperty(value = "岗位编号")
    private String postCode;
    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String postName;
    /**
     * 岗位排序
     */
    @ApiModelProperty(value = "岗位排序")
    private Integer sort;
    /**
     * 岗位描述
     */
    @ApiModelProperty(value = "岗位描述")
    private String remark;

    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;

}
