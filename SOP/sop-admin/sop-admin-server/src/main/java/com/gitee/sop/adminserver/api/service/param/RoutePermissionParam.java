package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author tanghc
 */
@Getter
@Setter
public class RoutePermissionParam {
    @ApiDocField(description = "routeId", required = true)
    @NotBlank(message = "routeId不能为空")
    private String routeId;

    @ApiDocField(description = "角色")
    private List<String> roleCode;
}
