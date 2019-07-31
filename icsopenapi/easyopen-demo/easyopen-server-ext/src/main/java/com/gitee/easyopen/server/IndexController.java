package com.gitee.easyopen.server;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ext.hystrix.HystrixApiRegistEvent;
import com.gitee.easyopen.support.ApiController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 文档地址：http://localhost:8080/api/doc
 */
@Controller
@RequestMapping("api")
public class IndexController extends ApiController {
    @Override
    protected void initApiConfig(ApiConfig apiConfig) {
        apiConfig.setShowDoc(true); // 显示文档页面
        // 配置国际化消息
        Map<String, String> appSecretStore = new HashMap();
        appSecretStore.put("test", "123456");
        apiConfig.addAppSecret(appSecretStore);

        apiConfig.setApiRegistEvent(new HystrixApiRegistEvent());
    }
}
