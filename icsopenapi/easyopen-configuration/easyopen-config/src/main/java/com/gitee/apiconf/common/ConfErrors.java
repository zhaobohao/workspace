package com.gitee.apiconf.common;

import com.gitee.easyopen.message.ErrorMeta;

/**
 * 定义错误的地方
 * @author tanghc
 */
public class ConfErrors {
    private ConfErrors(){}

    static String isvModule = "isv.error_"; // error_zh_CN2.properties内容前缀

    
    public static final ErrorMeta NO_LOGIN = new ErrorMeta(isvModule, "-100", "用户未登录");
    
    public static final ErrorMeta ERROR_USERNAME_PWD = new ErrorMeta(isvModule, "1", "用户名密码错误");
    public static final ErrorMeta DUPLICATE_USERNAME = new ErrorMeta(isvModule, "2", "该用户名已被注册");
    
    public static final ErrorMeta NO_RECORD = new ErrorMeta(isvModule, "1000", "无操作记录");
    public static final ErrorMeta ERROR_VALIDATE = new ErrorMeta(isvModule, "1001", "验证失败");
    public static final ErrorMeta NULL_OBJECT = new ErrorMeta(isvModule, "1002", "null对象");
    public static final ErrorMeta ERROR_SERACH = new ErrorMeta(isvModule, "1004", "查询错误");
    public static final ErrorMeta ERROR_EXPORT = new ErrorMeta(isvModule, "1005", "导出错误");
    public static final ErrorMeta CLASS_NEW_ERROR = new ErrorMeta(isvModule, "1007", "系统错误");

    public static final ErrorMeta ERROR_SAVE = new ErrorMeta(isvModule, "10010", "保存失败");
    public static final ErrorMeta ERROR_UPDATE = new ErrorMeta(isvModule, "10011", "修改失败");
    public static final ErrorMeta DELETE_UPDATE = new ErrorMeta(isvModule, "10012", "删除失败");
    public static final ErrorMeta RECORD_EXSIT = new ErrorMeta(isvModule, "10013", "记录已存在");
    public static final ErrorMeta ERROR_OPT = new ErrorMeta(isvModule, "10014", "非法操作");
    
    public static final ErrorMeta NO_USER = new ErrorMeta(isvModule, "10015", "用户不存在");
    
}
