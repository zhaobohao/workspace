package com.gitee.easyopen.server.message;

import com.gitee.easyopen.message.ErrorMeta;

// 按模块来定义异常消息,团队开发可以分开进行
public interface GoodsErrors {

    String isvModule = "isv.goods_error_"; // goods_error_zh_CN.properties内容的前缀

    ErrorMeta NO_GOODS_NAME = new ErrorMeta(isvModule, "100", "商品名称不能为空.");
    ErrorMeta SHORT_GOODS_NAME = new ErrorMeta(isvModule, "101", "商品名称太短.");
}
