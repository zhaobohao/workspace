package com.gitee.sop.sopauth.controller;

import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.sopauth.auth.FetchTokenParam;
import com.gitee.sop.sopauth.auth.FetchTokenResult;
import com.gitee.sop.sopauth.auth.OAuth2Config;
import com.gitee.sop.sopauth.auth.OAuth2Service;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 授权认证
 *
 * @author tanghc
 */
@Controller
@RequestMapping("oauth2")
public class OAuth2Controller {

    @Autowired
    private OAuth2Service oAuth2Service;

    // 第一步：授权URL拼装
    // https://localhost:8087/oauth2/appToAppAuth?app_id=2019032617262200001&redirect_uri=http%3a%2f%2flocalhost%3a8087%2foauth2callback
    @GetMapping("appToAppAuth")
    public String appToAppAuth(HttpServletRequest request, ModelMap modelMap) {
        String app_id = request.getParameter("app_id");
        String redirect_uri = request.getParameter("redirect_uri");
        modelMap.put("response_type", "code");
        modelMap.put("client_id", app_id);
        modelMap.put("redirect_uri", redirect_uri);
        return "oauth2login";
    }

    /**
     * 第二步：点击登录，到这里拿code
     * oauth2认证获取code
     * @param request
     * @param resp
     * @return 返回code
     * @throws URISyntaxException
     * @throws OAuthSystemException
     */
    @RequestMapping("authorize")
    public Object authorize(HttpServletRequest request, HttpServletResponse resp) throws URISyntaxException, OAuthSystemException {
        OAuthResponse response = oAuth2Service.authorize(request, resp, OAuth2Config.getInstance());
        if(response == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(response.getLocationUri()));
        return new ResponseEntity<String>(headers, HttpStatus.valueOf(response.getResponseStatus()));
    }


    /**
     * 第三步，通过code获取token
     * 或者，通过refresh_token换取token
     * @param param
     * @return
     */
    @ApiMapping("open.auth.token.app")
    @ResponseBody
    public FetchTokenResult fetchToken(FetchTokenParam param) {
        FetchTokenResult fetchTokenResult = oAuth2Service.accessToken(param, OAuth2Config.getInstance());
        return fetchTokenResult;
    }

    @RequestMapping("login")
    public String oauth2login() {
        return "oauth2login";
    }
}
