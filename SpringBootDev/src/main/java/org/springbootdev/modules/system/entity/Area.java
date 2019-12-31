
package org.springbootdev.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springbootdev.core.mp.base.BaseEntity;

/**
 * 行政区划实体类
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
@Data
@Accessors(chain = true)
@TableName("mk_area")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Area对象", description = "行政区划")
public class Area extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.NONE)
  private Long id;
    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private Long areaCode;
    /**
     * 父级编号
     */
    @ApiModelProperty(value = "父级编号")
    @JsonSerialize(using= ToStringSerializer.class)
private Long parentId;
    /**
     * 本级排序号（升序）
     */
    @ApiModelProperty(value = "本级排序号（升序）")
    private Integer sort;
    /**
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点")
    private Integer isLeaf;
    /**
     * 区域名称
     */
    @ApiModelProperty(value = "区域名称")
    private String areaName;
    /**
     * 区域类型
     */
    @ApiModelProperty(value = "区域类型")
    private String areaType;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remarks;
}
