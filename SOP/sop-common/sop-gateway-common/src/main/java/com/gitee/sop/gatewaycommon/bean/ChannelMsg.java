package com.gitee.sop.gatewaycommon.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class ChannelMsg {
    private String operation;
    private JSONObject data;

    public <T> T toObject(Class<T> clazz) {
        return data.toJavaObject(clazz);
    }
}
