package com.gitee.sop.storyweb.message;

import com.gitee.sop.servercommon.message.ServiceErrorMeta;

public enum  GoodsErrorEnum {
    /** 参数错误 */
    NO_GOODS_NAME("100"),
    /** 参数长度太短 */
    LESS_GOODS_NAME_LEN("101"),
    ;
    private ServiceErrorMeta errorMeta;

    GoodsErrorEnum(String subCode) {
        this.errorMeta = new ServiceErrorMeta("isp.goods_error_", subCode);
    }

    public ServiceErrorMeta getErrorMeta() {
        return errorMeta;
    }
}