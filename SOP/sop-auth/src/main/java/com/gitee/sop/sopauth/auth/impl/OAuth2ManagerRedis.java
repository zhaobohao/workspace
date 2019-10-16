package com.gitee.sop.sopauth.auth.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.sopauth.auth.OAuth2Config;
import com.gitee.sop.sopauth.auth.OAuth2Manager;
import com.gitee.sop.sopauth.auth.OpenUser;
import com.gitee.sop.sopauth.auth.RefreshToken;
import com.gitee.sop.sopauth.auth.exception.LoginErrorException;
import com.gitee.sop.sopauth.entity.UserInfo;
import com.gitee.sop.sopauth.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 
 * oauth2管理redis实现，这个类跟OAuth2ManagerCache类只能用一个，
 * 如果要用这个类，
 * 1、注释掉OAuth2ManagerCache的@Service。
 * 2、打开yml中redis部分
 * 3、启用这个类的@Service
 *
 * @author tanghc
 */
//@Service
public class OAuth2ManagerRedis implements OAuth2Manager {
    
    private static String CODE_PREFIX = "com.gitee.sop.oauth2_code:";
    private static String ACCESS_TOKEN_PREFIX = "com.gitee.sop.oauth2_access_token:";
    private static String REFRESH_TOKEN_PREFIX = "com.gitee.sop.oauth2_refresh_token:";
    
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;
    
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
        long codeTimeoutSeconds = OAuth2Config.getInstance().getCodeTimeoutSeconds();
        redisTemplate.opsForValue().set(getCodeKey(authCode),
                JSON.toJSONString(authUser), 
                codeTimeoutSeconds, 
                TimeUnit.SECONDS);
    }


    @Override
    public void addAccessToken(String accessToken, String refreshToken, OpenUser authUser) {
        // 存accessToken
        long expiresIn = OAuth2Config.getInstance().getAccessTokenExpiresIn();
        long reExpiresIn = OAuth2Config.getInstance().getRefreshTokenExpiresIn();
        if (expiresIn > 0) {
            redisTemplate.opsForValue().set(getAccessTokenKey(accessToken), JSON.toJSONString(authUser), expiresIn, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(getAccessTokenKey(accessToken), JSON.toJSONString(authUser));
        }
        // 存refreshToken
        if (reExpiresIn > 0) {
            redisTemplate.opsForValue().set(
                    getRefreshTokenKey(refreshToken),
                    JSON.toJSONString(new RefreshToken(accessToken, authUser)),
                    // refreshToken过期时间
                    reExpiresIn,
                    TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(
                    getRefreshTokenKey(refreshToken),
                    JSON.toJSONString(new RefreshToken(accessToken, authUser)));
        }

    }

    @Override
    public void removeAccessToken(String accessToken) {
        String accessTokenKey = getAccessTokenKey(accessToken);
        int afterRefreshExpiresIn = OAuth2Config.getInstance().getAfterRefreshExpiresIn();
        // 刷新令牌后，保证老的app_auth_token从刷新开始10分钟内可继续使用
        redisTemplate.expire(accessTokenKey, afterRefreshExpiresIn, TimeUnit.SECONDS);
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
        UserInfo user = JSON.parseObject(userJson, UserInfo.class);
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
        return JSON.parseObject(json, UserInfo.class);
    }

    @Override
    public OpenUser getUserByAccessToken(String accessToken) {
        String json = redisTemplate.opsForValue().get(getAccessTokenKey(accessToken));
        if(StringUtils.isEmpty(json)) {
            return null;
        }
        return JSON.parseObject(json, UserInfo.class);
    }


    @Override
    public OpenUser login(HttpServletRequest request) throws LoginErrorException {
        // 这里应该先检查用户有没有登录，如果登录直接返回openUser

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new LoginErrorException("用户名密码不能为空");
        }

        Query query = new Query();
        query.eq("username", username)
                .eq("password", password);
        UserInfo userInfo = userInfoMapper.getByQuery(query);

        if(userInfo == null) {
            throw new LoginErrorException("用户名密码不正确");
        }

        return userInfo;
    }

}
