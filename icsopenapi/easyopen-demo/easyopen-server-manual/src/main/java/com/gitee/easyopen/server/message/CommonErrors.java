package com.gitee.easyopen.server.message;

import com.gitee.easyopen.message.ErrorMeta;

// 公共异常放在这里
public interface CommonErrors {
    String isvModule = "isv.error_"; // error_zh_CN2.properties内容前缀
    
    ErrorMeta NUll_ERROR = new ErrorMeta(isvModule, "200", "不能为空");
}
