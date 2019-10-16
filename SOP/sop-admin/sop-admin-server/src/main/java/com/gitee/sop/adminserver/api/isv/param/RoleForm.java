package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class RoleForm {
    @ApiDocField(description = "id")
    private Long id;

    @ApiDocField(description = "角色码")
    @NotBlank(message = "roleCode不能为空")
    @Length(max = 64)
    private String roleCode;

    @ApiDocField(description = "描述")
    private String description;
}
