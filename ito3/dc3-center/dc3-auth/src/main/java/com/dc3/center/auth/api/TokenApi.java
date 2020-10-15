package com.dc3.center.auth.api;

import com.dc3.api.center.auth.token.feign.TokenClient;
import com.dc3.center.auth.service.TokenService;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 令牌 Feign Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_AUTH_TOKEN_URL_PREFIX)
public class TokenApi implements TokenClient {
    @Resource
    private TokenService tokenService;

    @Override
    public R<String> generateSalt(String username) {
            String salt = tokenService.generateSalt(username);
            return null != salt ? R.ok(salt, "ok") : R.fail();
    }

    @Override
    public R<String> generateToken(User user) {
            String token = tokenService.generateToken(user);
            return null != token ? R.ok(token, "ok") : R.fail();
    }

    @Override
    public R<Boolean> checkTokenValid(String username, String token) {
            return tokenService.checkTokenValid(username, token) ? R.ok() : R.fail();
    }

    @Override
    public R<Boolean> cancelToken(String username) {
            return tokenService.cancelToken(username) ? R.ok() : R.fail();
    }
}
