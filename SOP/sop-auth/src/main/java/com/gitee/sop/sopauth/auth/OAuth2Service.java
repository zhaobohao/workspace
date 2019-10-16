package com.gitee.sop.sopauth.auth;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;

/**
 * @author tanghc
 */
public interface OAuth2Service {

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
     * @param authConfig 配置
     * @return 返回响应内容
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    OAuthResponse authorize(HttpServletRequest request, HttpServletResponse response, OAuth2Config authConfig)
            throws URISyntaxException, OAuthSystemException;
    

    /**
     * 通过code获取accessToken.
     * @param fetchTokenParam
     * @param authConfig 配置项
     * @return 返回响应内容
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    FetchTokenResult accessToken(FetchTokenParam fetchTokenParam, OAuth2Config authConfig);
}
