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
import org.springclouddev.core.mp.base.TreeEntity;

/**
 * 系统字典参数表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_data_dic_prm")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DataDicPrm对象", description = "系统字典参数表")
public class DataDicPrm extends BaseEntity implements TreeEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    @TableId("data_dic_prm_ID")
    private Long id;
    /**
     * 参数编号
     */
    @ApiModelProperty(value = "参数编号")
    @TableField("CODE")
    private String code;
    /**
     * 父级菜单
     */
    @ApiModelProperty(value = "父级菜单")
    private Long parentId;
    /**
     * 参数名称
     */
    @ApiModelProperty(value = "参数名称")
    @TableField("NAME")
    private String name;
    /**
     * 参数描述
     */
    @ApiModelProperty(value = "参数描述")
    @TableField("DD_DESC")
    private String ddDesc;
    /**
     * 参数类别
     */
    @ApiModelProperty(value = "参数类别")
    private String type;
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
     * 是否是叶子节点，0是，1不是
     */
    @ApiModelProperty(value = "是否是叶子节点，0是，1不是")
    private Integer isLeaf;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    @TableField(fill = FieldFill.INSERT)
    private Integer sort;


}
