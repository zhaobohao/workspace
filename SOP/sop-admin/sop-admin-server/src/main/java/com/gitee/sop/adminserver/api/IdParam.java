package com.gitee.sop.adminserver.api;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Getter
@Setter
public class IdParam {
    @NotNull(message = "id不能为空")
    @ApiDocField(description = "id")
    private Long id;
}
