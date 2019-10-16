package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class ServiceGrayConfigParam {
    @ApiDocField(description = "服务名serviceId")
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;

    @ApiDocField(description = "灰度发布用户，多个用英文逗号隔开")
    @NotBlank(message = "灰度发布用户不能为空")
    private String userKeyContent;

    @ApiDocField(description = "灰度发布接口名版本号如：order.get1.0=1.2，多个用英文逗号隔开")
    @NotBlank(message = "灰度发布接口名版本号不能为空")
    private String nameVersionContent;

}
