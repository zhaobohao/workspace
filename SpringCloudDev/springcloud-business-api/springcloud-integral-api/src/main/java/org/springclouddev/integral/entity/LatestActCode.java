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

/**
 * 最新活动积分编号表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_latest_act_code")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "LatestActCode对象", description = "最新活动积分编号表")
public class LatestActCode extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 活动codeid
     */
    @ApiModelProperty(value = "活动codeid")
    @TableId("ACT_CODE_ID")
    private Long id;
    /**
     * 活动编号前缀
     */
    @ApiModelProperty(value = "活动编号前缀")
    @TableField("PREFIX_ACT_CODE")
    private String prefixActCode;
    /**
     * 活动编号后缀
     */
    @ApiModelProperty(value = "活动编号后缀")
    @TableField("SUFFIX_ACT_CODE")
    private String suffixActCode;


}
