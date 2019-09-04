

package com.spring.web.core.exception;

import com.alibaba.fastjson.JSON;
import com.spring.web.core.api.ApiCode;
import com.spring.web.core.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhaobohao
 * @date 2018-11-08
 */
@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 非法参数验证异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResult handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error("fieldErrors"+ JSON.toJSONString(list));
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION,list);
    }

    /**
     * 系统登录异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = SysLoginException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult sysLoginExceptionHandler(SysLoginException exception) {
        log.warn("系统登录异常:" + exception.getMessage());
        return ApiResult.fail(ApiCode.SYSTEM_LOGIN_EXCEPTION);
    }


    /**
     * HTTP解析请求参数异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("httpMessageNotReadableException:",exception);
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, ApiCode.PARAMETER_PARSE_EXCEPTION);
    }

    /**
     * HTTP
     * @param exception
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult httpMediaTypeException(HttpMediaTypeException exception) {
        log.error("httpMediaTypeException:",exception);
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, ApiCode.HTTP_MEDIA_TYPE_EXCEPTION);
    }

    /**
     * 默认的异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult exceptionHandler(Exception exception) {
        log.error("exception:",exception);
        return ApiResult.fail(ApiCode.SYSTEM_EXCEPTION);
    }

}
