package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Getter
@Setter
public class RouteAddParam {

    @NotBlank(message = "serviceId不能为空")
    @Length(max = 100, message = "serviceId长度不能超过100")
    @ApiDocField(description = "serviceId")
    private String serviceId;

    /**
     * 接口名
     */
    @NotBlank(message = "接口名不能为空")
    @Length(max = 100, message = "name长度不能超过100")
    @ApiDocField(description = "接口名")
    private String name;

    /**
     * 版本号
     */
    @NotBlank(message = "版本号不能为空")
    @Length(max = 100, message = "version长度不能超过100")
    @ApiDocField(description = "版本号")
    private String version;

    /**
     * 路由规则转发的目标uri
     */
    @NotBlank(message = "uri不能为空")
    @Length(max = 100, message = "uri长度不能超过100")
    @ApiDocField(description = "路由uri")
    private String uri;

    /**
     * uri后面跟的path
     */
    @ApiDocField(description = "路由path")
    @Length(max = 100, message = "path长度不能超过100")
    private String path;

    /**
     * 是否忽略验证，业务参数验证除外
     */
    @ApiDocField(description = "是否忽略验证，业务参数验证除外，1：忽略，0：不忽略")
    private Integer ignoreValidate;

    /**
     * 合并结果，统一格式输出
     */
    @ApiDocField(description = "合并结果，统一格式输出，1：合并，2：不合并")
    private Integer mergeResult;

    /**
     * 状态
     */
    @NotNull
    @ApiDocField(description = "状态，0：审核，1：启用，2：禁用")
    private Integer status;

    @ApiDocField(description = "是否是自定义路由，1：是，0：否")
    private Integer custom;

}
