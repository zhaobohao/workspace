package com.gitee.sop.adminserver.api.service.param;

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
public class ConfigIpBlacklistPageParam extends PageParam {
    @ApiDocField(description = "IP")
    @Condition(operator = Operator.like)
    private String ip;
}
