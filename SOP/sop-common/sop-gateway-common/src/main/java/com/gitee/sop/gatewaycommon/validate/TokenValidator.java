package com.gitee.sop.gatewaycommon.validate;

import com.gitee.sop.gatewaycommon.param.ApiParam;

/**
 * @author tanghc
 */
@FunctionalInterface
public interface TokenValidator {
    boolean validateToken(ApiParam apiParam);
}
