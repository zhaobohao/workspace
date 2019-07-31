package com.gitee.easyopen.auth;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.exception.LoginErrorException;

/**
 * 认证服务，需要自己实现
 * @author tanghc
 *
 */
public interface Oauth2Manager {

    /**
     * 添加 auth code
     * 
     * @param authCode
     *            code值
     * @param authUser
     *            用户
     */
    void addAuthCode(String authCode, OpenUser authUser);

    /**
     * 添加 access token，废弃，使用addAccessToken(String accessToken, String refreshToken, OpenUser authUser, long expiresIn)
     * 
     * @param accessToken
     *            token值
     * @param authUser
     *            用户
     * @param expiresIn 时长,秒
     */
    @Deprecated
    void addAccessToken(String accessToken, OpenUser authUser, long expiresIn);
    
    /**
     * 添加accessToken
     * @param accessToken token值
     * @param refreshToken refreshToken
     * @param authUser 用户
     * @param expiresIn 时长,秒
     */
    void addAccessToken(String accessToken,String refreshToken, OpenUser authUser, long expiresIn);

    
    /**
     * 删除这个accessToken
     * @param accessToken
     */
    void removeAccessToken(String accessToken);
    
    /**
     * 删除这个refreshToken
     * @param refreshToken
     */
    void removeRefreshToken(String refreshToken);
    
    /**
     * 获取RefreshToken
     * @param refreshToken
     * @return 返回Token信息
     */
    RefreshToken getRefreshToken(String refreshToken);
    
    /**
     * 验证auth code是否有效
     * 
     * @param authCode
     * @return 无效返回false
     */
    boolean checkAuthCode(String authCode);

    /**
     * 根据auth code获取用户
     * 
     * @param authCode
     * @return 返回用户
     */
    OpenUser getUserByAuthCode(String authCode);

    /**
     * 根据access token获取用户名
     * 
     * @param accessToken
     *            token值
     * @return 返回用户
     */
    OpenUser getUserByAccessToken(String accessToken);
    
    /**
     * 返回accessToken中追加的参数
     * @param user
     * @return 返回追加的参数
     */
    Map<String, String> getParam(OpenUser user);

    /**
     * 获取auth code / access token 过期时间
     * @param apiConfig
     * @return 返回过期时间，单位秒
     */
    long getExpireIn(ApiConfig apiConfig);

    /**
     * 用户登录，需判断是否已经登录
     * @param request
     * @return 返回用户对象
     * @throws LoginErrorException 登录失败异常
     */
    OpenUser login(HttpServletRequest request) throws LoginErrorException;
}