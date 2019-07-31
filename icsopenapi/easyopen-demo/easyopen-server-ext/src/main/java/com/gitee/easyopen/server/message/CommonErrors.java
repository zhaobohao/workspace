package com.gitee.easyopen.server.message;

import com.gitee.easyopen.message.ErrorMeta;

/**
 * 错误码放在这里
 *
 * 使用方式：throw CommonErrors.NUll_ERROR.getException();
 */
public interface CommonErrors {
    String isvModule = "isv.error_"; // error_zh_CN2.properties内容前缀
    
    ErrorMeta NUll_ERROR = new ErrorMeta(isvModule, "200", "不能为空");


}
