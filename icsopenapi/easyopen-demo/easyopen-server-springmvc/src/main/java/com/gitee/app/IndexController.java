package com.gitee.app;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.support.ApiController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api")
// @CrossOrigin(origins={"*"}) // 解决js跨域
public class IndexController extends ApiController {

    @Override
    protected void initApiConfig(ApiConfig apiConfig) {
        apiConfig.setShowDoc(true); // 显示文档页面
        // apiConfig.setDocPassword("doc123"); // 设置文档页面密码
        // 配置国际化消息
        apiConfig.getIsvModules().add("i18n/isv/goods_error");
        Map<String, String> appSecretStore = new HashMap();
        appSecretStore.put("test", "123456");
        apiConfig.addAppSecret(appSecretStore);

        /* -----------------配置中心------------------
        // 配置拦截器
        apiConfig.setInterceptors(new ApiInterceptor[] {
                // 限流拦截器（配置中心）
                new LimitInterceptor() ,
                // 权限拦截器（配置中心）
                new PermissionInterceptor()
        });

        // appName 应用名称
        // host    配置中心ip
        // port    配置中心端口
        ConfigClient configClient = new ConfigClient("app-springmvc", "localhost", 8071);
        // 如果要使用分布式业务限流，使用下面这句。默认是单机限流
        // configClient.setLimitManager(new ApiLimitManager(redisTemplate, new ApiLimitConfigLocalManager()));
        apiConfig.setConfigClient(configClient);
         -------------------------------------- */
    }

}
