package org.springclouddev.integral.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_marketing_act")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MarketingAct对象" , description = "MarketingAct对象")
public class MarketingAct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @ApiModelProperty(value = "表id")
    private Long id;
    /**
     * 活动CODE
     */
    @ApiModelProperty(value = "活动CODE")
    @TableField("ACT_CODE")
    private String actCode;
    /**
     * 活动名称
     */
    @ApiModelProperty(value = "活动名称")
    @TableField("ACT_NAME")
    private String actName;
    /**
     * 活动开始时间
     */
    @ApiModelProperty(value = "活动开始时间")
    @TableField("BEGIN_TIME")
    private String beginTime;
    /**
     * 活动结束时间
     */
    @ApiModelProperty(value = "活动结束时间")
    @TableField("END_TIME")
    private String endTime;
    /**
     * 卡券编号
     */
    @ApiModelProperty(value = "卡券编号")
    @TableField("CARD_TIEKET_ID")
    private String cardTieketId;
    /**
     * 积分类型
     */
    @ApiModelProperty(value = "积分类型")
    @TableField("INTEGRAL_TYPE")
    private String integralType;
    /**
     * 积分类型ID
     */
    @ApiModelProperty(value = "积分类型ID")
    @TableField("INTEGRAL_ID")
    private String integralId;
    /**
     * 活动链接
     */
    @ApiModelProperty(value = "活动链接")
    @TableField("ACT_LINK")
    private String actLink;
    /**
     * 活动状态
     */
    @ApiModelProperty(value = "活动状态")
    @TableField("ACT_STATUS")
    private String actStatus;
    /**
     * 活动状态ID
     */
    @ApiModelProperty(value = "活动状态ID")
    @TableField("ACT_STATUS_ID")
    private String actStatusId;
    /**
     * 活动说明
     */
    @ApiModelProperty(value = "活动说明")
    @TableField("ACT_EXPLAIN")
    private String actExplain;
    /**
     * 活动目标描述
     */
    @ApiModelProperty(value = "活动目标描述")
    @TableField("ACT_TARGET_BREIF")
    private String actTargetBreif;
    /**
     * 活动卡券数量
     */
    @ApiModelProperty(value = "活动卡券数量")
    @TableField("ACT_CARD_TICKET_NUM")
    private Long actCardTicketNum;
    /**
     * 活动目标类型
     */
    @ApiModelProperty(value = "活动目标类型")
    @TableField("ACT_TARGET_TYPE")
    private String actTargetType;
    /**
     * 活动目标数量
     */
    @ApiModelProperty(value = "活动目标数量")
    @TableField("ACT_TARGET_NUM_NUM")
    private Long actTargetNumNum;
    /**
     * 活动积分数量
     */
    @ApiModelProperty(value = "活动积分数量")
    @TableField("ACT_INTEGRAL_NUM_NUM")
    private Long actIntegralNumNum;
    /**
     * 审核评语
     */
    @ApiModelProperty(value = "审核评语")
    @TableField("REVIEW_COMMENTS")
    private String reviewComments;
    /**
     * 预留字段一
     */
    @ApiModelProperty(value = "预留字段一")
    @TableField("RESERVE_COLUMN1")
    private String reserveColumn1;
    /**
     * 预留字段二
     */
    @ApiModelProperty(value = "预留字段二")
    @TableField("RESERVE_COLUMN2")
    private String reserveColumn2;
    /**
     * 预留字段三
     */
    @ApiModelProperty(value = "预留字段三")
    @TableField("RESERVE_COLUMN3")
    private String reserveColumn3;


}
