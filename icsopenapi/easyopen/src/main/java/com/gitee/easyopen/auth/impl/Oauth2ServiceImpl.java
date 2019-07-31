package com.gitee.easyopen.auth.impl;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.as.response.OAuthASResponse.OAuthTokenResponseBuilder;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.auth.Oauth2Manager;
import com.gitee.easyopen.auth.Oauth2Service;
import com.gitee.easyopen.auth.OpenUser;
import com.gitee.easyopen.auth.RefreshToken;
import com.gitee.easyopen.auth.TokenPair;
import com.gitee.easyopen.exception.LoginErrorException;

/**
 * oauth2服务端默认实现
 * 
 * @author tanghc
 *
 */
public class Oauth2ServiceImpl implements Oauth2Service {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2ServiceImpl.class);

    private static final String TOKEN_TYPE = "Bearer";
    
    private OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());
    
    private Oauth2Manager oauth2Manager;

    @Override
    public OAuthResponse authorize(HttpServletRequest request, HttpServletResponse resp, ApiConfig apiConfig)
            throws URISyntaxException, OAuthSystemException {
        try {
            // 构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            // 检查传入的客户端id是否正确
            if (!checkClientId(oauthRequest.getClientId(), apiConfig)) {
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(OAuthError.TokenResponse.INVALID_CLIENT).buildJSONMessage();
            }

            // 如果用户没有登录，跳转到登陆页面
            OpenUser user = null;
            try {
                user = oauth2Manager.login(request);
            } catch (LoginErrorException e) {
                logger.error(e.getMessage(), e);
                request.setAttribute("error", e.getMessage());
                try {
                    request.getRequestDispatcher(apiConfig.getOauth2LoginUri()).forward(request, resp);
                    return null;
                } catch (Exception e1) {
                    logger.error(e1.getMessage(), e1);
                    throw new RuntimeException(e1);
                }
            }

            // 生成授权码
            String authorizationCode = null;
            // responseType目前仅支持CODE，另外还有TOKEN
            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if (responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oauthIssuerImpl.authorizationCode();
                oauth2Manager.addAuthCode(authorizationCode, user);
            }
            // 进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request,
                    HttpServletResponse.SC_FOUND);
            // 设置授权码
            builder.setCode(authorizationCode);
            // 得到到客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

            // 构建响应
            return builder.location(redirectURI).buildQueryMessage();
        } catch (OAuthProblemException e) {
            // 出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                // 告诉客户端没有传入redirectUri直接报错
                String error = "OAuth redirectUri needs to be provided by client!!!";
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                        .error(OAuthProblemException.error(error)).location(redirectUri).buildQueryMessage();
            } else {
                // 返回错误消息（如?error=）
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e).location(redirectUri)
                        .buildQueryMessage();
            }

        }
    }

    @Override
    public OAuthResponse accessToken(HttpServletRequest request, ApiConfig apiConfig)
            throws URISyntaxException, OAuthSystemException {
        try {
            // 构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

            // 检查提交的客户端id是否正确
            if (!checkClientId(oauthRequest.getClientId(), apiConfig)) {
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(OAuthError.TokenResponse.INVALID_CLIENT).buildJSONMessage();
            }

            // 检查客户端安全KEY是否正确
            if (!checkClientSecret(oauthRequest.getClientId(), oauthRequest.getClientSecret(), apiConfig)) {
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                        .setErrorDescription(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT).buildJSONMessage();
            }

            
            // 检查验证类型，如果是第一次进来用code换取accessToken
            String grant_type = oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
            if (GrantType.AUTHORIZATION_CODE.toString().equals(grant_type)) {
                String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
                if (!oauth2Manager.checkAuthCode(authCode)) {
                    return  OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                            .setErrorDescription("invalid request")
                            .buildJSONMessage();
                }
                // 生成Access Token
                OpenUser user = oauth2Manager.getUserByAuthCode(authCode);
                if (user == null) {
                    throw OAuthProblemException.error("Can not found user by code.");
                }
                
                long expiresIn = oauth2Manager.getExpireIn(apiConfig);
                TokenPair tokenPair = this.createNewToken(user);
                
                oauth2Manager.addAccessToken(tokenPair.getAccessToken(), tokenPair.getRefreshToken(), user, expiresIn);
                
                // 生成OAuth响应
                return this.buildAccessTokenResponse(tokenPair, expiresIn, user);
            } else if(GrantType.REFRESH_TOKEN.toString().equals(grant_type)) {
                // 用refreshToken来刷新accessToken
                String refreshToken = oauthRequest.getParam(OAuth.OAUTH_REFRESH_TOKEN);
                if(StringUtils.isEmpty(refreshToken)) {
                    return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN).setErrorDescription("expired token")
                            .buildJSONMessage();
                }
                
                RefreshToken refreshTokenObj = oauth2Manager.getRefreshToken(refreshToken);
                if (refreshTokenObj == null) {
                    return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                            .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
                            .setErrorDescription("invalid token")
                            .buildJSONMessage();
                }
                
                OpenUser user = refreshTokenObj.getOpenUser();
                long expiresIn = oauth2Manager.getExpireIn(apiConfig);
                // 老的token对
                TokenPair oldTokenPair = new TokenPair();
                oldTokenPair.setAccessToken(refreshTokenObj.getAccessToken());
                oldTokenPair.setRefreshToken(refreshToken);
                // 创建一对新的accessToken和refreshToken
                TokenPair newTokenPair = this.createRefreshToken(user, oldTokenPair);

                this.afterRefreshToken(oldTokenPair, newTokenPair, user, expiresIn);
                
                // 返回新的accessToken和refreshToken
                return this.buildAccessTokenResponse(newTokenPair, expiresIn, user);
            } else {
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription("invalid grant")
                        .buildJSONMessage();
            }

        } catch (OAuthProblemException e) {
            logger.error(e.getMessage(), e);
            // 构建错误响应
            return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildJSONMessage();
        }
    }
    
    /**
     * 刷新token后续操作
     * @param oldTokenPair 老的token
     * @param newTokenPair 新的token
     * @param user 用户
     * @param expiresIn 过期时间
     */
    protected void afterRefreshToken(TokenPair oldTokenPair,TokenPair newTokenPair, OpenUser user, long expiresIn) {
        // 保存token
        oauth2Manager.addAccessToken(newTokenPair.getAccessToken(), newTokenPair.getRefreshToken(), user, expiresIn);
        
        // 删除老的accessToken
        oauth2Manager.removeAccessToken(oldTokenPair.getAccessToken());
        // 删除老的refreshToken
        oauth2Manager.removeRefreshToken(oldTokenPair.getRefreshToken());
    }
    
    /**
     * 创建新的token
     * @param user
     * @return 返回新token
     */
    protected TokenPair createNewToken(OpenUser user) {
        return this.createDefaultTokenPair();
    }
    
    /**
     * 返回刷新后token
     * @param user 用户
     * @param oldTokenPair 旧的token
     * @return 返回新的token
     */
    protected TokenPair createRefreshToken(OpenUser user, TokenPair oldTokenPair) {
        return this.createDefaultTokenPair();
    }
    
    private TokenPair createDefaultTokenPair() {
        TokenPair tokenPair = new TokenPair();
        try {
            String accessToken =  oauthIssuer.accessToken();
            String refreshToken = oauthIssuer.refreshToken();
            
            tokenPair.setAccessToken(accessToken);
            tokenPair.setRefreshToken(refreshToken);
            
            return tokenPair;
        } catch (OAuthSystemException e) {
            throw new RuntimeException(e);
        }
    }
    
    private OAuthResponse buildAccessTokenResponse(TokenPair tokenPair,long expiresIn, OpenUser user) throws OAuthSystemException {
        OAuthTokenResponseBuilder resp = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK);
        
        Map<String, String> param = oauth2Manager.getParam(user);
        if(param != null) {
           Set<Entry<String, String>> entrySet = param.entrySet();
           for (Entry<String, String> entry : entrySet) {
               resp.setParam(entry.getKey(), entry.getValue());
           }
        }
        
        return resp
                .setAccessToken(tokenPair.getAccessToken())
                .setRefreshToken(tokenPair.getRefreshToken())
                .setTokenType(TOKEN_TYPE)
                .setExpiresIn(String.valueOf(expiresIn))
                .buildJSONMessage();
        
    }

    private boolean checkClientId(String clientId, ApiConfig apiConfig) {
        return apiConfig.getAppSecretManager().isValidAppKey(clientId);
    }

    private boolean checkClientSecret(String clientId, String clientSecret, ApiConfig apiConfig) {
        String secret = apiConfig.getAppSecretManager().getSecret(clientId);
        if (secret == null) {
            return false;
        }
        return secret.equals(clientSecret);
    }

    public Oauth2Manager getOauth2Manager() {
        return oauth2Manager;
    }

    @Override
    public void setOauth2Manager(Oauth2Manager oauth2Manager) {
        Assert.notNull(oauth2Manager, "oauth2Manager不能为空");
        this.oauth2Manager = oauth2Manager;
    }
    
}
