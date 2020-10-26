package org.springclouddev.crm.core.exception;

import cn.hutool.core.util.StrUtil;
import com.netflix.client.ClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.crm.core.common.R;
import org.springclouddev.crm.core.common.Result;
import org.springclouddev.crm.core.common.ResultCode;
import org.springclouddev.crm.core.common.SystemCodeEnum;
import org.springclouddev.crm.core.entity.UserExtraInfo;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;


/**
 * @author zhangzhiwei
 * 全局异常处理类
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {


    @ExceptionHandler(value = Exception.class)
    public Result defaultException(Exception ex) {
        //TODO 默认异常需要处理
        log.error("默认异常需要处理", ex);
        if (ex instanceof ResultCode) {
            return R.error(((ResultCode) ex).getCode(), ((ResultCode) ex).getMsg());
        }
        return Result.error(SystemCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = AuthException.class)
    public Result authException() {
        return Result.noAuth();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Result methodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("方法请求错误", e);
        return Result.noAuth();
    }

    @ExceptionHandler(value = ServletException.class)
    public Result servletException(ServletException e) {
        /*
          TODO 直接捕获NoHandlerFoundException异常gateway会报错，所以捕获ServletException
         */
        if (e instanceof NoHandlerFoundException) {
            log.error("请求路径未找到:", ((NoHandlerFoundException) e).getRequestURL());
            return Result.error(SystemCodeEnum.SYSTEM_NO_FOUND);
        }
        log.error("ServletException:", e);
        return Result.error(SystemCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = NoLoginException.class)
    public Result<UserExtraInfo> noLoginException(NoLoginException e) {
        Result<UserExtraInfo> result = Result.error(SystemCodeEnum.SYSTEM_NOT_LOGIN);
        result.setData(e.getInfo());
        return result;
    }

    @ExceptionHandler(value = BindException.class)
    public Result validException(BindException e) {
        log.error("BindException:", e);
        if (e.getGlobalError() != null) {
            return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, e.getGlobalError().getDefaultMessage());
        } else if (e.getFieldError() != null) {
            return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, e.getFieldError().getDefaultMessage());
        } else {
            return Result.error(SystemCodeEnum.SYSTEM_NO_VALID);
        }
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result argumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException:", e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.getGlobalError() != null) {
            return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, bindingResult.getGlobalError().getDefaultMessage());
        } else if (bindingResult.getFieldError() != null) {
            return Result.error(SystemCodeEnum.SYSTEM_NO_VALID, bindingResult.getFieldError().getDefaultMessage());
        } else {
            return Result.error(SystemCodeEnum.SYSTEM_NO_VALID);
        }
    }

    @ExceptionHandler(value = ClientException.class)
    public Result clientException() {
        return Result.error(SystemCodeEnum.SYSTEM_SERVER_ERROR);
    }

    @ExceptionHandler(value = FeignException.class)
    public Result feignException(FeignException ex) {
        log.error("feign异常：", ex);
        String message = ex.getMessage();
        if (StrUtil.isNotEmpty(message) && message.matches("[\\u4e00-\\u9fa5]+(!?|！?)")) {
            return Result.error(SystemCodeEnum.SYSTEM_SERVER_ERROR,message);
        } else {
            return Result.error(SystemCodeEnum.SYSTEM_SERVER_ERROR);
        }
    }
}
