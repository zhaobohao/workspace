package com.gitee.easyopen.server;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.support.ApiController;
import com.gitee.easyopen.support.LimitInterceptor;
import com.gitee.easyopen.support.PermissionInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class IndexController extends ApiController {
    
    // http://localhost:8080/api/mono
    @RequestMapping("mono")
    @ResponseBody
    public Mono<Object> mono(HttpServletRequest request, HttpServletResponse response) {
        Object obj = this.getWebfluxInvokeTemplate().processInvoke(request, response);
        return Mono.just(obj);
    }
    
    @Override
    protected void initApiConfig(ApiConfig apiConfig) {
        apiConfig.setShowDoc(true); // 显示文档页面
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

        ConfigClient configClient = new ConfigClient("app-WebFlux", "localhost", 8071);
//        如果要使用分布式业务限流，使用下面这句。默认是单机限流
//        configClient.setLimitManager(new ApiLimitManager(redisTemplate, new ApiLimitConfigLocalManager()));
        apiConfig.setConfigClient(configClient);
         */
    }

}

