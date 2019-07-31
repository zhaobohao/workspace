package com.gitee.easyopen.server.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.auth.Oauth2Manager;
import com.gitee.easyopen.auth.OpenUser;
import com.gitee.easyopen.auth.RefreshToken;
import com.gitee.easyopen.exception.LoginErrorException;
import com.gitee.easyopen.server.model.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * oauth2管理，默认谷歌缓存实现，跟redis实现只能用一个
 * @author tanghc
 *
 */
@Service
public class Oauth2CacheManagerImpl implements Oauth2Manager {
    
    private int codeTimeoutSeconds = 60;
    private int accessTokenTimeoutSeconds = 7200; // accessToken过期时间
    
    private LoadingCache<String, OpenUser> codeCache = buildCache(codeTimeoutSeconds);
    private LoadingCache<String, OpenUser> accessTokenCache = buildCache(accessTokenTimeoutSeconds);
    private LoadingCache<String, RefreshToken> refreshTokenCache = buildCache(accessTokenTimeoutSeconds * 3);
    

    private static <T> LoadingCache<String, T> buildCache(int timeout) {
        return CacheBuilder.newBuilder().expireAfterAccess(timeout, TimeUnit.SECONDS)
                .build(new CacheLoader<String, T>() {
                    @Override
                    public T load(String key) throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public void addAuthCode(String authCode, OpenUser authUser) {
        codeCache.put(authCode, authUser);
    }

    // 不用实现
    @Override
    public void addAccessToken(String accessToken, OpenUser authUser, long expiresIn) {
    }

    @Override
    public void addAccessToken(String accessToken, String refreshToken, OpenUser authUser, long expiresIn) {
        accessTokenCache.put(accessToken, authUser);
        refreshTokenCache.put(refreshToken, new RefreshToken(accessToken, authUser));
    }

    @Override
    public void removeAccessToken(String accessToken) {
        accessTokenCache.asMap().remove(accessToken);
    }

    @Override
    public void removeRefreshToken(String refreshToken) {
        refreshTokenCache.asMap().remove(refreshToken);
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenCache.getIfPresent(refreshToken);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return codeCache.asMap().containsKey(authCode);
    }

    @Override
    public OpenUser getUserByAuthCode(String authCode) {
        return codeCache.getIfPresent(authCode);
    }

    @Override
    public OpenUser getUserByAccessToken(String accessToken) {
        return accessTokenCache.getIfPresent(accessToken);
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
