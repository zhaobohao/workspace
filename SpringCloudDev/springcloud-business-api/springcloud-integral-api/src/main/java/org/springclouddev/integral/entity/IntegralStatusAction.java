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
 * 积分冻结解冻状态动作表实体类
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Data
@Accessors(chain = true)
@TableName("tbl_integral_status_action")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IntegralStatusAction对象", description = "积分冻结解冻状态动作表")
public class IntegralStatusAction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @ApiModelProperty(value = "自增主键")
    private Long id;
    /**
     * 账户号
     */
    @ApiModelProperty(value = "账户号")
    @TableField("ACCOUNT_ID")
    private String accountId;
    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户编号")
    @TableField("CUST_ID")
    private String custId;
    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @TableField("CUST_NAME")
    private String custName;
    /**
     * 积分类型
     */
    @ApiModelProperty(value = "积分类型")
    @TableField("INTEGRAL_TYPE")
    private String integralType;
    /**
     * 当前余额
     */
    @ApiModelProperty(value = "当前余额")
    @TableField("BLANCE")
    private String blance;
    /**
     * 操作
     */
    @ApiModelProperty(value = "操作")
    @TableField("OPERATION_TYPE")
    private String operationType;
    /**
     * 退回原因
     */
    @ApiModelProperty(value = "退回原因")
    @TableField("REJECT_REASON")
    private String rejectReason;
    /**
     * 状态id
     */
    @ApiModelProperty(value = "状态id")
    @TableField("ACCOUNT_STATUS_ID")
    private String accountStatusId;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("ACCOUNT_STATUS")
    private String accountStatus;
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
