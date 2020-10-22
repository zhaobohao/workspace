package org.springclouddev.desk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springclouddev.core.mp.base.BaseEntity;

import java.util.Date;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mk_notice")
public class Notice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.NONE)
    @ApiModelProperty(value = "主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型")
    private Integer category;

    /**
     * 发布日期
     */
    @ApiModelProperty(value = "发布日期")
    private Date releaseTime;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;


}
