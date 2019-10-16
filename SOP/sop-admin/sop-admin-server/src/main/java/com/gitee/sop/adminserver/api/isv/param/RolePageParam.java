package com.gitee.sop.adminserver.api.isv.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.param.PageParam;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tanghc
 */
@Getter
@Setter
public class RolePageParam extends PageParam {
    @ApiDocField(description = "角色码")
    @Condition(operator = Operator.like)
    private String roleCode;
}
