package com.gitee.apiconf;

import com.gitee.apiconf.interceptor.LoginInterceptor;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.session.ApiSessionManager;
import com.gitee.easyopen.session.RedisSessionManager;
import com.gitee.easyopen.support.ApiController;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins={"*"}) // 解决js跨域
public class IndexController extends ApiController {

    @Value("${accessToken.timeout}")
    private String accessTokenTimeout;

    @Override
    protected void initApiConfig(ApiConfig apiConfig) {
        apiConfig.setShowDoc(true); // 显示文档页面

        // 配置秘钥键值对
        Map<String, String> appSecretStore = new HashMap<String, String>();
        appSecretStore.put("test", "123456");
        apiConfig.setInterceptors(new ApiInterceptor[]{new LoginInterceptor()});

        apiConfig.addAppSecret(appSecretStore);
        // session管理，默认存在本地，重启token会失效。
        // 可替换成RedisSessionManager，存在redis中，重启token不会失效
        //RedisSessionManager apiSessionManager = new RedisSessionManager();
        ApiSessionManager apiSessionManager = new ApiSessionManager();
        // session有效期
        int timeout = NumberUtils.toInt(accessTokenTimeout, 30);
        apiSessionManager.setSessionTimeout(timeout);
        apiConfig.setSessionManager(apiSessionManager);
        apiConfig.setTimeoutSeconds(0);
    }

}
