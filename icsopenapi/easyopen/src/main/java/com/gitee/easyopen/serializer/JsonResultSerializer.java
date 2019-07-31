package com.gitee.easyopen.serializer;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ResultSerializer;

/**
 * 序列化json
 * @author tanghc
 */
public class JsonResultSerializer implements ResultSerializer {

    @Override
    public String serialize(Object obj) {
        return JSON.toJSONString(obj);
    }

}
