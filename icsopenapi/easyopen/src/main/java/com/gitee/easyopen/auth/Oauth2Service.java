package com.gitee.easyopen.auth;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import com.gitee.easyopen.ApiConfig;

/**
 * @author tanghc
 */
public interface Oauth2Service {

    /**
     * oauth2授权,获取code.
     * <pre>
     *  1、首先通过如http://localhost:8080/api/authorize?client_id=test&response_type=code&redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Foauth2callback访问授权页面；
        2、该控制器首先检查clientId是否正确；如果错误将返回相应的错误信息；
        3、然后判断用户是否登录了，如果没有登录首先到登录页面登录；
        4、登录成功后生成相应的code即授权码，然后重定向到客户端地址，如http://localhost:8080/oauth2callback?code=6d250650831fea227749f49a5b49ccad；在重定向到的地址中会带上code参数（授权码），接着客户端可以根据授权码去换取accessToken。
     * </pre>
     * 
     * @param request req
     * @param response resp
     * @param apiConfig cfng
     * @return 返回响应内容
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    OAuthResponse authorize(HttpServletRequest request,HttpServletResponse response, ApiConfig apiConfig)
            throws URISyntaxException, OAuthSystemException;
    
    /**
     * 通过code获取accessToken.
     * <pre>
     * 1、首先通过如http://localhost:8080/api/accessToken，POST提交如下数据访问:
     *  <code>
     *  code:6d250650831fea227749f49a5b49ccad
        client_id:test
        client_secret:123456
        grant_type:authorization_code
        redirect_uri:http://localhost:8080/api/authorize
        </code>
        2、该控制器会验证client_id、client_secret、auth code的正确性，如果错误会返回相应的错误；
        3、如果验证通过会生成并返回相应的访问令牌accessToken。
     * </pre>
     * @param request
     * @param apiConfig api配置项
     * @return 返回响应内容
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    OAuthResponse accessToken(HttpServletRequest request, ApiConfig apiConfig) throws URISyntaxException, OAuthSystemException;
    
    /**
     * 设置Oauth2业务管理类
     * @param oauth2Manager Oauth2Manager
     */
    void setOauth2Manager(Oauth2Manager oauth2Manager);
}
