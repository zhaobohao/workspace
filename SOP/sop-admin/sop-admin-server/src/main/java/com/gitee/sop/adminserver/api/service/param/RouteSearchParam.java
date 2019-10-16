package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.param.PageParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Getter
@Setter
public class RouteSearchParam extends PageParam {
    @ApiDocField(description = "服务名serviceId")
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;

    @ApiDocField(description = "路由id")
    @Condition(column = "id", operator = Operator.like)
    private String id;

    @ApiDocField(description = "是否授权接口，1：是")
    @Condition(ignoreValue = "0")
    private Integer permission;
}
