package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Getter
@Setter
public class RouteDeleteParam {
    @ApiDocField(description = "服务名serviceId")
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;

    @ApiDocField(description = "路由id")
    @NotBlank(message = "id不能为空")
    private String id;
}
