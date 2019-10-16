package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Getter
@Setter
public class ServiceAddParam {

    @ApiDocField(description = "服务名serviceId")
    @Length(max = 100, message = "长度不能超过100")
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;
}
