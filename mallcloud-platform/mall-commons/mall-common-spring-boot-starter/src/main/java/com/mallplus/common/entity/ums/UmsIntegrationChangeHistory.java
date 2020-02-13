package com.mallplus.common.entity.ums;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mallplus.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 积分变化历史记录表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Data
@TableName("ums_integration_change_history")
public class UmsIntegrationChangeHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("member_id")
    private Long memberId;

    @TableField("create_time")
    private Date createTime;

    /**
     * 改变类型：0->增加；1->减少
     */
    @TableField("change_type")
    private Integer changeType;

    /**
     * 积分改变数量
     */
    @TableField("change_count")
    private Integer changeCount;

    /**
     * 操作人员
     */
    @TableField("operate_man")
    private String operateMan;

    /**
     * 操作备注
     */
    @TableField("operate_note")
    private String operateNote;

    /**
     * 积分来源：0->购物；1->管理员修改
     */
    @TableField("source_type")
    private Integer sourceType;

    public UmsIntegrationChangeHistory() {
    }

    public UmsIntegrationChangeHistory(Long memberId, Date createTime, Integer changeType, Integer changeCount, String operateMan, String operateNote, Integer sourceType) {
        this.memberId = memberId;
        this.createTime = createTime;
        this.changeType = changeType;
        this.changeCount = changeCount;
        this.operateMan = operateMan;
        this.operateNote = operateNote;
        this.sourceType = sourceType;
    }
    @Override
    public String toString() {
        return "UmsIntegrationChangeHistory{" +
        ", id=" + id +
        ", memberId=" + memberId +
        ", createTime=" + createTime +
        ", changeType=" + changeType +
        ", changeCount=" + changeCount +
        ", operateMan=" + operateMan +
        ", operateNote=" + operateNote +
        ", sourceType=" + sourceType +
        "}";
    }
}
