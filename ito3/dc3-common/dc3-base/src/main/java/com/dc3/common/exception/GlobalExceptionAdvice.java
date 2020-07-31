

package com.dc3.common.exception;

import com.alibaba.fastjson.JSON;
import com.dc3.common.bean.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author pnoker
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * Global Exception
     *
     * @param exception Exception
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Exception exception) {
        log.error("Global Exception:{}", exception.getMessage(), exception);
        return R.fail(exception.getMessage());
    }

    /**
     * Validation Exception
     *
     * @param exception MethodArgumentNotValidException
     * @return R
     */
    @ExceptionHandler({
            BindException.class,
            MethodArgumentNotValidException.class
    })
    public R bodyValidExceptionHandler(MethodArgumentNotValidException exception) {
        HashMap<String, String> map = new HashMap<>(16);
        List<FieldError> errorList = exception.getBindingResult().getFieldErrors();
        errorList.forEach(error -> {
            log.warn("Method Argument Not Valid Exception:{}({})", error.getField(), error.getDefaultMessage());
            map.put(error.getField(), error.getDefaultMessage());
        });
        return R.fail(JSON.toJSONString(map));
    }

}
