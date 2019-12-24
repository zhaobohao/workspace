
package org.springclouddev.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springclouddev.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
    @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
    /**
     * 区域编码
     */
    @ApiModelProperty(value = "区域编码")
    private String areaCode;
    /**
     * 父级编号
     */
    @ApiModelProperty(value = "父级编号")
    private String parentId;
    /**
     * 本级排序号（升序）
     */
    @ApiModelProperty(value = "本级排序号（升序）")
    private BigDecimal sort;
    /**
     * 是否叶子节点
     */
    @ApiModelProperty(value = "是否叶子节点")
    private String isLeaf;
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
