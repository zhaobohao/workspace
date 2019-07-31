package com.gitee.easyopen.server;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.server.config.ControllerDocCreator;
import com.gitee.easyopen.support.ApiController;
import com.gitee.easyopen.support.LimitInterceptor;
import com.gitee.easyopen.support.PermissionInterceptor;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
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

//    @Autowired
//    private RedisTemplate redisTemplate; // 1 声明redis模板

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
        String docUrl = "http://localhost:8080/api/doc";
        ConfigClient configClient = new ConfigClient("app1", "localhost",8071, docUrl);
//        ConfigClient configClient = new ConfigClient("app1", "localhost",8071);
        // 如果要使用分布式业务限流，使用下面这句。默认是单机限流
        // configClient.setLimitManager(new ApiLimitManager(redisTemplate, new ApiLimitConfigLocalManager()));
        apiConfig.setConfigClient(configClient);
*/

    }

    @Override
    public void processDocVelocityContext(VelocityContext context) {
        // 文档菜单树默认状态, true：展开，false：收缩
        context.put("is_expand_all", true);
        // 文档名称
        context.put("title", "API文档");
        // 文档描述
        context.put("docRemark", "");
    }
}
