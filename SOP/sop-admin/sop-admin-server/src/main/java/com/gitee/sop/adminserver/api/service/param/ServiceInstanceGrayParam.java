package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceInstanceGrayParam extends ServiceInstanceParam {

    @ApiDocField(description = "灰度发布用户，多个用英文逗号隔开")
    @NotBlank(message = "灰度发布用户不能为空")
    private String userKeyContent;

    @ApiDocField(description = "灰度发布接口名版本号如：order.get1.0=1.2，多个用英文逗号隔开")
    @NotBlank(message = "灰度发布接口名版本号不能为空")
    private String nameVersionContent;

    @ApiDocField(description = "是否仅更新灰度用户")
    private Boolean onlyUpdateGrayUserkey;
}
