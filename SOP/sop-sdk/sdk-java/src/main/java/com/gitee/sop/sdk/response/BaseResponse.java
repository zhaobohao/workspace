package com.gitee.sop.sdk.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.gitee.sop.sdk.sign.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 返回对象，后续返回对象都要继承这个类
 * {
 * "alipay_trade_close_response": {
 * "code": "20000",
 * "msg": "Service Currently Unavailable",
 * "sub_code": "isp.unknown-error",
 * "sub_msg": "系统繁忙"
 * },
 * "sign": "ERITJKEIJKJHKKKKKKKHJEREEEEEEEEEEE"
 * }
 * @author tanghc
 */
@Setter
@Getter
public abstract class BaseResponse {

    private String code;
    private String msg;
    @JSONField(name = "sub_code")
    private String subCode;
    @JSONField(name = "sub_msg")
    private String subMsg;
    @JSONField(serialize = false)
    private String body;

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return StringUtils.isEmpty(subCode);
    }


}
