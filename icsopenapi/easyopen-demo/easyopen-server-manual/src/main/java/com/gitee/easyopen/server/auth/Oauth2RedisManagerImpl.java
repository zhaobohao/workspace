package com.gitee.easyopen.server.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.auth.Oauth2Manager;
import com.gitee.easyopen.auth.OpenUser;
import com.gitee.easyopen.auth.RefreshToken;
import com.gitee.easyopen.exception.LoginErrorException;
import com.gitee.easyopen.server.model.User;

/**
 * 
 * oauth2管理redis实现，这个类跟Oauth2CacheManagerImpl类只能用一个，
 * 如果要用这个类，注释掉Oauth2CacheManagerImpl的@Service
 * 启用这个类的@Service
 */
//@Service
public class Oauth2RedisManagerImpl implements Oauth2Manager  {
    
    private static String CODE_PREFIX = "oauth2_code:";
    private static String ACCESS_TOKEN_PREFIX = "oauth2_access_token:";
    private static String REFRESH_TOKEN_PREFIX = "oauth2_refresh_token:";
    
    private int codeTimeoutSeconds = 60;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    public static String getCodeKey(String code) {
        return CODE_PREFIX + code;
    }
    
    public static String getAccessTokenKey(String accessToken) {
        return ACCESS_TOKEN_PREFIX + accessToken;
    }
    
    public static String getRefreshTokenKey(String refreshToken) {
        return REFRESH_TOKEN_PREFIX + refreshToken;
    }

    @Override
    public void addAuthCode(String authCode, OpenUser authUser) {
        redisTemplate.opsForValue().set(getCodeKey(authCode), 
                JSON.toJSONString(authUser), 
                codeTimeoutSeconds, 
                TimeUnit.SECONDS);
    }

    // 不需要实现，该方法已废弃
    @Override
    public void addAccessToken(String accessToken, OpenUser authUser, long expiresIn) {
    }

    @Override
    public void addAccessToken(String accessToken, String refreshToken, OpenUser authUser, long expiresIn) {
        // 存accessToken
        redisTemplate.opsForValue().set(getAccessTokenKey(accessToken), JSON.toJSONString(authUser), expiresIn, TimeUnit.SECONDS);
        // 存refreshToken
        redisTemplate.opsForValue().set(
                getRefreshTokenKey(refreshToken), 
                JSON.toJSONString(new RefreshToken(accessToken, authUser)), 
                expiresIn * 3, // refreshToken过期时间，是accessToken的三倍
                TimeUnit.SECONDS);
    }

    @Override
    public void removeAccessToken(String accessToken) {
        redisTemplate.delete(getAccessTokenKey(accessToken));
    }

    @Override
    public void removeRefreshToken(String refreshToken) {
        redisTemplate.delete(getRefreshTokenKey(refreshToken));
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        String json = redisTemplate.opsForValue().get(getRefreshTokenKey(refreshToken));
        if(StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObj = JSON.parseObject(json);
        
        String userJson = jsonObj.getString("openUser");
        User user = JSON.parseObject(userJson, User.class);
        String accessToken = jsonObj.getString("accessToken");
        
        return new RefreshToken(accessToken, user);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        if(StringUtils.isEmpty(authCode)) {
            return false;
        }
        return redisTemplate.hasKey(getCodeKey(authCode));
    }

    @Override
    public OpenUser getUserByAuthCode(String authCode) {
        String json = redisTemplate.opsForValue().get(getCodeKey(authCode));
        if(StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, User.class);
    }

    @Override
    public OpenUser getUserByAccessToken(String accessToken) {
        String json = redisTemplate.opsForValue().get(getAccessTokenKey(accessToken));
        if(StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, User.class);
    }

    @Override
    public Map<String, String> getParam(OpenUser user) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        return map;
    }

    @Override
    public long getExpireIn(ApiConfig apiConfig) {
        return apiConfig.getOauth2ExpireIn();
    }

    @Override
    public OpenUser login(HttpServletRequest request) throws LoginErrorException {
        // 这里应该先检查用户有没有登录，如果登录直接返回openUser
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new LoginErrorException("用户名密码不能为空");
        }
        // 此处应该从数据库中查
        if(!("admin".equals(username) && "123456".equals(password))) {
            throw new LoginErrorException("用户名密码不正确");
        }
        // 模拟登录..
        
        // 登录成功
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        user.setPassword(password);
        
        return user;
    }

}
