package com.gitee.sop.sopauth.auth.impl;

import com.gitee.sop.sopauth.auth.AppIdManager;
import com.gitee.sop.sopauth.auth.FetchTokenParam;
import com.gitee.sop.sopauth.auth.FetchTokenResult;
import com.gitee.sop.sopauth.auth.OAuth2Config;
import com.gitee.sop.sopauth.auth.OAuth2Manager;
import com.gitee.sop.sopauth.auth.OAuth2Service;
import com.gitee.sop.sopauth.auth.OpenUser;
import com.gitee.sop.sopauth.auth.RefreshToken;
import com.gitee.sop.sopauth.auth.TokenPair;
import com.gitee.sop.sopauth.auth.exception.LoginErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;

/**
 * oauth2服务端默认实现
 *
 * @author tanghc
 */
@Service
@Slf4j
public class OAuth2ServiceImpl implements OAuth2Service {


    private static final String TOKEN_TYPE = "Bearer";
    public static final String APP_ID_NAME = "app_id";

    private OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());

    @Autowired
    private OAuth2Manager oauth2Manager;

    @Autowired
    private AppIdManager appIdManager;

    @Override
    public OAuthResponse authorize(HttpServletRequest request, HttpServletResponse resp, OAuth2Config authConfig)
            throws URISyntaxException, OAuthSystemException {
        try {
            // 构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            String clientId = oauthRequest.getClientId();
            // 检查传入的客户端id是否正确
            if (!appIdManager.isValidAppId(clientId)) {
                return OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription(OAuthError.TokenResponse.INVALID_CLIENT).buildJSONMessage();
            }

            // 如果用户没有登录，跳转到登陆页面
            OpenUser user = null;
            try {
                user = oauth2Manager.login(request);
            } catch (LoginErrorException e) {
                log.error(e.getMessage(), e);
                request.setAttribute("error", e.getMessage());
                try {
                    request.getRequestDispatcher(authConfig.getOauth2LoginUri()).forward(request, resp);
                    return null;
                } catch (Exception e1) {
                    log.error(e1.getMessage(), e1);
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
            builder.setParam(APP_ID_NAME, clientId);
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
    public FetchTokenResult accessToken(FetchTokenParam fetchTokenParam, OAuth2Config authConfig) {
        try {
            // 这里需要检查client_id和client_secret，但是已经通过网关接口访问进来了
            // 表示client_id和client_secret是合法的

            // 检查验证类型，如果是第一次进来用code换取accessToken
            String grant_type = fetchTokenParam.getGrant_type();
            if (GrantType.AUTHORIZATION_CODE.toString().equals(grant_type)) {
                String authCode = fetchTokenParam.getCode();
                if (!oauth2Manager.checkAuthCode(authCode)) {
                    FetchTokenResult errorResult = new FetchTokenResult();
                    errorResult.setError(OAuthError.CodeResponse.INVALID_REQUEST);
                    errorResult.setError_description("invalid request");
                    return errorResult;
                }
                // 生成Access Token
                OpenUser user = oauth2Manager.getUserByAuthCode(authCode);
                if (user == null) {
                    throw OAuthProblemException.error("Can not found user by code.");
                }

                TokenPair tokenPair = this.createNewToken(user);

                oauth2Manager.addAccessToken(tokenPair.getAccessToken(), tokenPair.getRefreshToken(), user);

                // 生成OAuth响应
                return this.buildTokenResult(tokenPair, user);
            } else if (GrantType.REFRESH_TOKEN.toString().equals(grant_type)) {
                // 用refreshToken来刷新accessToken
                String refreshToken = fetchTokenParam.getRefresh_token();
                if (StringUtils.isEmpty(refreshToken)) {
                    FetchTokenResult errorResult = new FetchTokenResult();
                    errorResult.setError(OAuthError.ResourceResponse.EXPIRED_TOKEN);
                    errorResult.setError_description("expired token");
                    return errorResult;
                }

                RefreshToken refreshTokenObj = oauth2Manager.getRefreshToken(refreshToken);
                if (refreshTokenObj == null) {
                    FetchTokenResult errorResult = new FetchTokenResult();
                    errorResult.setError(OAuthError.ResourceResponse.INVALID_TOKEN);
                    errorResult.setError_description("invalid token");
                    return errorResult;
                }

                OpenUser user = refreshTokenObj.getOpenUser();
                // 老的token对
                TokenPair oldTokenPair = new TokenPair();
                oldTokenPair.setAccessToken(refreshTokenObj.getAccessToken());
                oldTokenPair.setRefreshToken(refreshToken);
                // 创建一对新的accessToken和refreshToken
                TokenPair newTokenPair = this.createRefreshToken(user, oldTokenPair);

                this.afterRefreshToken(oldTokenPair, newTokenPair, user);

                // 返回新的accessToken和refreshToken
                return this.buildTokenResult(newTokenPair, user);
            } else {
                FetchTokenResult errorResult = new FetchTokenResult();
                errorResult.setError(OAuthError.TokenResponse.INVALID_GRANT);
                errorResult.setError_description("invalid grant");
                return errorResult;
            }

        } catch (OAuthProblemException e) {
            log.error(e.getMessage(), e);
            FetchTokenResult errorResult = new FetchTokenResult();
            errorResult.setError(e.getMessage());
            errorResult.setError_description("invalid grant");
            return errorResult;
        }
    }

    /**
     * 刷新token后续操作
     *
     * @param oldTokenPair 老的token
     * @param newTokenPair 新的token
     * @param user         用户
     */
    protected void afterRefreshToken(TokenPair oldTokenPair, TokenPair newTokenPair, OpenUser user) {
        // 保存token
        oauth2Manager.addAccessToken(newTokenPair.getAccessToken(), newTokenPair.getRefreshToken(), user);

        // 删除老的accessToken
        oauth2Manager.removeAccessToken(oldTokenPair.getAccessToken());
        // 删除老的refreshToken
        oauth2Manager.removeRefreshToken(oldTokenPair.getRefreshToken());
    }

    /**
     * 创建新的token
     *
     * @param user
     * @return 返回新token
     */
    protected TokenPair createNewToken(OpenUser user) {
        return this.createDefaultTokenPair();
    }

    /**
     * 返回刷新后token
     *
     * @param user         用户
     * @param oldTokenPair 旧的token
     * @return 返回新的token
     */
    protected TokenPair createRefreshToken(OpenUser user, TokenPair oldTokenPair) {
        return this.createDefaultTokenPair();
    }

    private TokenPair createDefaultTokenPair() {
        TokenPair tokenPair = new TokenPair();
        try {
            String accessToken = oauthIssuer.accessToken();
            String refreshToken = oauthIssuer.refreshToken();

            tokenPair.setAccessToken(accessToken);
            tokenPair.setRefreshToken(refreshToken);

            return tokenPair;
        } catch (OAuthSystemException e) {
            throw new RuntimeException(e);
        }
    }

    private FetchTokenResult buildTokenResult(TokenPair tokenPair, OpenUser user) {
        OAuth2Config auth2Config = OAuth2Config.getInstance();
        FetchTokenResult fetchTokenResult = new FetchTokenResult();
        fetchTokenResult.setApp_auth_token(tokenPair.getAccessToken());
        fetchTokenResult.setApp_refresh_token(tokenPair.getRefreshToken());
        fetchTokenResult.setUser_id(user.getUserId());
        fetchTokenResult.setExpires_in(auth2Config.getAccessTokenExpiresIn());
        fetchTokenResult.setRe_expires_in(auth2Config.getRefreshTokenExpiresIn());
        return fetchTokenResult;
    }

}
