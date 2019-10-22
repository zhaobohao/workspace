

package com.spring.web.security.controller;


import com.spring.web.core.api.ApiCode;
import com.spring.web.core.api.ApiResult;
import com.spring.web.core.web.controller.BaseController;
import com.spring.web.dao.param.LoginParam;
import com.spring.web.service.security.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *
 * </p>
 * @author zhaobohao
 * @date 2019-05-15
 **/
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @PostMapping("")
    public ApiResult login(@Validated @RequestBody LoginParam loginParam) throws Exception{
        return loginService.login(loginParam);
    }

    @GetMapping("/non")
    public ApiResult nonLogin() throws Exception{
        log.info("nonLogin....");
        return ApiResult.fail(ApiCode.UNAUTHORIZED);
    }
}
