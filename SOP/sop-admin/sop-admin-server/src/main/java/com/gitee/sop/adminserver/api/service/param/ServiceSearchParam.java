package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tanghc
 */
@Getter
@Setter
public class ServiceSearchParam {

    @ApiDocField(description = "服务名serviceId")
    private String serviceId;

    @ApiDocField(description = "instanceId")
    private String instanceId;
}
