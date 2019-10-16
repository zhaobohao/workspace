package com.gitee.sop.sopauth.auth.impl;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.sopauth.auth.OAuth2Config;
import com.gitee.sop.sopauth.auth.OAuth2Manager;
import com.gitee.sop.sopauth.auth.OpenUser;
import com.gitee.sop.sopauth.auth.RefreshToken;
import com.gitee.sop.sopauth.auth.exception.LoginErrorException;
import com.gitee.sop.sopauth.entity.UserInfo;
import com.gitee.sop.sopauth.mapper.UserInfoMapper;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * oauth2管理，默认谷歌缓存实现，跟redis实现只能用一个。
 * 这里为了演示，使用本地缓存，正式环境请使用redis保存
 * @see OAuth2ManagerRedis OAuth2ManagerRedis
 * @author tanghc
 *
 */
@Service
public class OAuth2ManagerCache implements OAuth2Manager {
    /**
     * 应用授权的app_auth_code唯一的；app_auth_code使用一次后失效，一天（从生成app_auth_code开始的24小时）未被使用自动过期；
     * app_auth_token永久有效。
     */
    private int codeTimeoutSeconds = OAuth2Config.getInstance().getCodeTimeoutSeconds();
    /**
     * accessToken过期时间
     * -1 永久有效
     */
    private int accessTokenTimeoutSeconds = OAuth2Config.getInstance().getAccessTokenExpiresIn();

    private int refreshTokenTimeoutSeconds = OAuth2Config.getInstance().getRefreshTokenExpiresIn();

    private LoadingCache<String, OpenUser> codeCache = buildCache(codeTimeoutSeconds);
    private LoadingCache<String, OpenUser> accessTokenCache = buildCache(accessTokenTimeoutSeconds);
    private LoadingCache<String, RefreshToken> refreshTokenCache = buildCache(refreshTokenTimeoutSeconds);

    @Autowired
    private UserInfoMapper userInfoMapper;
    

    private static <T> LoadingCache<String, T> buildCache(int timeout) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if (timeout > 0) {
            cacheBuilder.expireAfterAccess(timeout, TimeUnit.SECONDS);
        }
        return cacheBuilder
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


    @Override
    public void addAccessToken(String accessToken, String refreshToken, OpenUser authUser) {
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
