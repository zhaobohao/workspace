

package com.spring.web.core.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统登录异常
 * @author geekidea
 * @date 2019-08-04
 */
public class SysLoginException extends RuntimeException{
    private Integer code;
    public SysLoginException() {
    }

    public SysLoginException(String message) {
        super(message);
    }

    public SysLoginException(Integer code , String message){
        this(StringUtils.isBlank(message)?String.valueOf(code):message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
