package com.gitee.sop.adminserver.api.service.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class LogMonitorInstanceAddParam {
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;

    @NotBlank(message = "ipPort不能为空")
    private String ipPort;
}
