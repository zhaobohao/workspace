
package org.springbootdev.modules.develop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springbootdev.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Data
@TableName("db_table_info")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TableInfo对象", description = "TableInfo对象")
@Accessors(chain = true)
public class TableInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
  private Long id;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 字段类型
     */
    @ApiModelProperty(value = "字段类型")
    private String typeKey;
    /**
     * 字段类型值
     */
    @ApiModelProperty(value = "字段类型值")
    private String typeValue;
    /**
     * 是否为空 1为空2不为空
     */
    @ApiModelProperty(value = "是否为空 1为空2不为空")
    private Integer isEmpty;
    /**
     * 默认值 
     */
    @ApiModelProperty(value = "默认值 ")
    private String defaultValue;
    /**
     * 注释说明
     */
    @ApiModelProperty(value = "注释说明")
    private String comment;
    /**
     * 父级菜单
     */
    @ApiModelProperty(value = "父级菜单")
    private Long parentId;
    /**
     * 1表，2字段
     */
    @ApiModelProperty(value = "1表，2字段")
    private Integer category;
    /**
     * 数据库id
     */
    @ApiModelProperty(value = "数据库id")
    private Long dbInstanceId;
    /**
     * 是否是叶子节点，0是，1不是
     */
    @ApiModelProperty(value = "是否是叶子节点，0是，1不是")
    private Integer isLeaf;


}
