

package com.spring.web.core.exception;

import com.spring.web.core.api.ApiCode;
import com.spring.web.core.api.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 全局Error/404处理
 * @author zhaobohao
 * @date 2018-11-08
 */
@ApiIgnore
@RestController
@Slf4j
public class GlobalErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ApiResult handleError(){
        log.error("404 NOT FOUND");
        return ApiResult.fail(ApiCode.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        log.error("errorPath....");
        return ERROR_PATH;
    }
}
