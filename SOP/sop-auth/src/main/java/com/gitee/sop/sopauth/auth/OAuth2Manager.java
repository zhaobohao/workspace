package com.gitee.sop.sopauth.auth;


import com.gitee.sop.sopauth.auth.exception.LoginErrorException;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证服务，需要自己实现
 * @author tanghc
 *
 */
public interface OAuth2Manager {

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
     * 添加accessToken
     * @param accessToken token值
     * @param refreshToken refreshToken
     * @param authUser 用户
     */
    void addAccessToken(String accessToken, String refreshToken, OpenUser authUser);

    
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
     * 根据access token获取用户
     * 
     * @param accessToken
     *            token值
     * @return 返回用户
     */
    OpenUser getUserByAccessToken(String accessToken);
    
    /**
     * 用户登录，需判断是否已经登录
     * @param request
     * @return 返回用户对象
     * @throws LoginErrorException 登录失败异常
     */
    OpenUser login(HttpServletRequest request) throws LoginErrorException;
}