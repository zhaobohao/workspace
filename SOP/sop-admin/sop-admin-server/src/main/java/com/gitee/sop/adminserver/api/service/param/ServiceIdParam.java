package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class ServiceIdParam {
    @ApiDocField(description = "serviceId")
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;
}
