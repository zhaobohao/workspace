package com.gitee.sop.storyweb.message;

import com.gitee.sop.servercommon.message.ServiceErrorMeta;

/**
 * @author tanghc
 */
public enum  StoryErrorEnum {
    /** 参数错误 */
    param_error("isv.invalid-parameter"),
    ;
    private ServiceErrorMeta errorMeta;

    StoryErrorEnum(String subCode) {
        this.errorMeta = new ServiceErrorMeta("isp.error_", subCode);
    }

    public ServiceErrorMeta getErrorMeta() {
        return errorMeta;
    }
}
