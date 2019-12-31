
package org.springclouddev.develop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springclouddev.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Data
@TableName("db_db_instance")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DbInstance对象", description = "DbInstance对象")
public class DbInstance extends BaseEntity {

    private static final long serialVersionUID = 1L;

  '@TableId(value = "id", type = IdType.NONE)
  private Long id;
    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称")
    private String name;
    /**
     * data用户账号
     */
    @ApiModelProperty(value = "data用户账号")
    private String dataUser;
    /**
     * etl用户账号
     */
    @ApiModelProperty(value = "etl用户账号")
    private String etlUser;
    /**
     * opr用户账号
     */
    @ApiModelProperty(value = "opr用户账号")
    private String oprUser;
    /**
     * rpt用户账号
     */
    @ApiModelProperty(value = "rpt用户账号")
    private String rptUser;


}
