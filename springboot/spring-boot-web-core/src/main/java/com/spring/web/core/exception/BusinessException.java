

package com.spring.web.core.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 业务异常
 * @author zhaobohao
 * @date 2018-11-08
 */
public class BusinessException extends RuntimeException{
    private Integer code;
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code ,String message){
        this(StrUtil.isBlank(message)?String.valueOf(code):message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
