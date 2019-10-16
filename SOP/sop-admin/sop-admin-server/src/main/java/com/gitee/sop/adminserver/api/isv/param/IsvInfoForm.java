package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Collections;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class IsvInfoForm {

    /** 0启用，1禁用, 数据库字段：status */
    @ApiDocField(description = "状态：0：启用，1：禁用")
    private Byte status = 0;

    @ApiDocField(description = "备注")
    @Length(max = 100, message = "长度不得唱过100")
    private String remark;

    @ApiDocField(description = "roleCode数组", elementClass = String.class)
    private List<String> roleCode = Collections.emptyList();
}
