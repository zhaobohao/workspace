package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Getter
@Setter
public class IsvInfoFormUpdate extends IsvInfoForm {
    @ApiDocField(description = "id")
    @NotNull(message = "id不能为空")
    private Long id;
}
