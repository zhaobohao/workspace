package com.gitee.sop.gatewaycommon.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.bean.NacosConfigs;
import com.gitee.sop.gatewaycommon.secret.IsvManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 用不到了，这个类的作用是监听消息推送用的。由admin推送一条config配置，然后这里触发事件。
 * 现在改为直接由admin请求网关提供的接口进行配置修改。
 * 考虑
 *
 * @author tanghc
 */
@Slf4j
@Deprecated
public class NacosEventProcessor {

    @NacosInjected
    private ConfigService configService;

    @Autowired
    private EnvGrayManager envGrayManager;

    @Autowired
    private IPBlacklistManager ipBlacklistManager;

    @Autowired
    private IsvManager isvManager;

    @Autowired
    private IsvRoutePermissionManager isvRoutePermissionManager;

    @Autowired
    private LimitConfigManager limitConfigManager;

    @Autowired
    private RouteConfigManager routeConfigManager;

    @PostConstruct
    public void after() throws NacosException {
        log.debug("初始化nacos事件监听");
        initEnvGray();
        initIPBlack();
        initIsv();
        initIsvRoutePermission();
        initLimitConfig();
        initRouteConfig();
    }

    private void initEnvGray() throws NacosException {
        configService.addListener(NacosConfigs.DATA_ID_GRAY, NacosConfigs.GROUP_CHANNEL, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                ChannelMsg channelMsg = JSON.parseObject(configInfo, ChannelMsg.class);
                envGrayManager.process(channelMsg);
            }
        });
    }

    private void initIPBlack() throws NacosException {
        configService.addListener(NacosConfigs.DATA_ID_IP_BLACKLIST, NacosConfigs.GROUP_CHANNEL, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                ChannelMsg channelMsg = JSON.parseObject(configInfo, ChannelMsg.class);
                ipBlacklistManager.process(channelMsg);
            }
        });
    }

    private void initIsv() throws NacosException {
        configService.addListener(NacosConfigs.DATA_ID_ISV, NacosConfigs.GROUP_CHANNEL, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                ChannelMsg channelMsg = JSON.parseObject(configInfo, ChannelMsg.class);
                isvManager.process(channelMsg);
            }
        });
    }

    private void initIsvRoutePermission() throws NacosException {
        configService.addListener(NacosConfigs.DATA_ID_ROUTE_PERMISSION, NacosConfigs.GROUP_CHANNEL, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                ChannelMsg channelMsg = JSON.parseObject(configInfo, ChannelMsg.class);
                isvRoutePermissionManager.process(channelMsg);
            }
        });
    }

    private void initLimitConfig() throws NacosException {
        configService.addListener(NacosConfigs.DATA_ID_LIMIT_CONFIG, NacosConfigs.GROUP_CHANNEL, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                ChannelMsg channelMsg = JSON.parseObject(configInfo, ChannelMsg.class);
                limitConfigManager.process(channelMsg);
            }
        });
    }

    private void initRouteConfig() throws NacosException {
        configService.addListener(NacosConfigs.DATA_ID_ROUTE_CONFIG, NacosConfigs.GROUP_CHANNEL, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                ChannelMsg channelMsg = JSON.parseObject(configInfo, ChannelMsg.class);
                routeConfigManager.process(channelMsg);
            }
        });
    }

}
