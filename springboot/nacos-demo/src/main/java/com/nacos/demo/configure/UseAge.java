package com.nacos.demo.configure;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.nacos.demo.convert.AdminConverter;
import com.nacos.demo.model.Admin;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseAge {
    @NacosInjected
    ConfigService configService;  //主服务
    private static final String ADMIN_DATA_ID = "admin.json";

    private static final String ADMIN_GROUP_ID = "spring-listener";
    //
    @NacosValue(value = "${nacos.test.propertie:123}", autoRefreshed = true)
    private String testProperties;

    @NacosConfigListener(dataId = ADMIN_DATA_ID, groupId = ADMIN_GROUP_ID, converter = AdminConverter.class)
    public void onReceived(Admin admin) {
//        LOGGER.info("onReceived(Admin) : {}", admin);
//        this.admin = admin;
    }


}
