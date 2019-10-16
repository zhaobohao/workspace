package com.gitee.sop.sdk.common;

/**
 * 返回固定的
 * {
 *     "result": {
 *         "code": "20000",
 *         "msg": "Service Currently Unavailable",
 *         "sub_code": "isp.unknown-error",
 *         "sub_msg": "系统繁忙"
 *     },
 *     "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
 * }
 * @author tanghc
 */
public class CustomDataNameBuilder implements DataNameBuilder {
    private String dataName = "result";

    public CustomDataNameBuilder() {
    }

    public CustomDataNameBuilder(String dataName) {
        this.dataName = dataName;
    }

    @Override
    public String build(String method) {
        return dataName;
    }
}
