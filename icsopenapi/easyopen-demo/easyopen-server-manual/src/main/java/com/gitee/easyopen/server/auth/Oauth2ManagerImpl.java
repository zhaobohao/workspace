package com.gitee.easyopen.server.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.auth.AccessToken;
import com.gitee.easyopen.auth.Oauth2Manager;
import com.gitee.easyopen.auth.OpenUser;
import com.gitee.easyopen.auth.RefreshToken;
import com.gitee.easyopen.exception.LoginErrorException;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.server.model.User;

/**
 * oauth2认证实现类,不推荐用，可使用Oauth2CacheManagerImpl或Oauth2RedisManagerImpl
 * @author tanghc
 *
 */
@Deprecated
//@Service
public class Oauth2ManagerImpl implements Oauth2Manager {
    /****下面是缓存，最好用redis代替*****/
    // 存放用户对应的code,key=code,value=openUser
    private static Map<String, OpenUser> userCodeMap = new HashMap<>();
    
    // 存放用户对应的accessToken,key=accessToken，value=AccessToken
    // 如果使用redis可以set accessToken JSON.toJSONString(OpenUser) expireIn TimeUnit.SECONDS
    private static Map<String, AccessToken> userAccessTokenMap = new HashMap<>();
    
    // 存放refreshToken对应的用户
    // 如果使用redis，这里的过期时间可以设置长一点
    private static Map<String, RefreshToken> userRefreshTokennMap = new HashMap<>();

    @Override
    public void addAuthCode(String authCode, OpenUser openUser) {
        userCodeMap.put(authCode, openUser);
    }

    @Deprecated
    @Override
    public void addAccessToken(String accessToken, OpenUser openUser, long expiresIn) {
        userAccessTokenMap.put(accessToken, new AccessToken(expiresIn, openUser));
    }
    

    @Override
    public void addAccessToken(String accessToken, String refreshToken, OpenUser authUser, long expiresIn) {
        userAccessTokenMap.put(accessToken, new AccessToken(expiresIn, authUser));
        userRefreshTokennMap.put(refreshToken, new RefreshToken(accessToken, authUser));
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return userRefreshTokennMap.get(refreshToken);
    }

    @Override
    public void removeAccessToken(String accessToken) {
        userAccessTokenMap.remove(accessToken);
    }
    
    @Override
    public void removeRefreshToken(String refreshToken) {
        userRefreshTokennMap.remove(refreshToken);
    }

    @Override
    public Map<String, String> getParam(OpenUser user) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user.getUsername());
        return map;
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        if(StringUtils.isEmpty(authCode)) {
            return false;
        }
        return userCodeMap.keySet().contains(authCode);
    }

    @Override
    public OpenUser getUserByAuthCode(String authCode) {
        return userCodeMap.get(authCode);
    }

    @Override
    public OpenUser getUserByAccessToken(String accessToken) {
        if(accessToken == null) {
            throw Errors.ERROR_ACCESS_TOKEN.getException();
        }
        AccessToken accessTokenInfo = userAccessTokenMap.get(accessToken);
        
        if(accessTokenInfo == null) {
            throw Errors.ERROR_ACCESS_TOKEN.getException();
        }
        
        if(accessTokenInfo.isExpired()) {
            userAccessTokenMap.remove(accessToken);
            throw Errors.EXPIRED_ACCESS_TOKEN.getException();
        }
        return accessTokenInfo.getOpenUser();
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
