package com.gitee.apiconf.service;

import com.alibaba.fastjson.JSON;
import com.gitee.apiconf.common.ChannelContext;
import com.gitee.apiconf.entity.LimitAppConfig;
import com.gitee.apiconf.entity.PermClient;
import com.gitee.apiconf.mapper.ApiInfoMapper;
import com.gitee.apiconf.mapper.LimitAppConfigMapper;
import com.gitee.apiconf.mapper.PermClientMapper;
import com.gitee.apiconf.mapper.PermRolePermissionMapper;
import com.gitee.easyopen.config.ConfigMsg;
import com.gitee.easyopen.config.NettyOpt;
import com.gitee.easyopen.permission.ApiInfo;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

@Service
public class SyncService {
    @Autowired
    PermClientMapper permClientMapper;
    @Autowired
    LimitAppConfigMapper limitAppConfigMapper;
    @Autowired
    ApiInfoMapper apiInfoMapper;
    @Autowired
    PermRolePermissionMapper permRolePermissionMapper;
    
    @Autowired
    PermService permService;

    /**
     * 更新客户端配置，同时更新权限
     * @param appList
     */
    public void syncAppSecretConfig(Set<String> appList) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (String app : appList) {
                    List<PermClient> list = permClientMapper.listByColumn("app", app);
                    ConfigMsg msg = new ConfigMsg();
                    msg.setApp(app);
                    msg.setCode(NettyOpt.UPDATE_SECRET_CONFIG.getCode());
                    msg.setData(JSON.toJSONString(list));

                    // 主动推给客户端
                    ChannelContext.writeAndFlush(msg);
                }
            }
        });
        this.syncPermissionConfigByAppList(appList);
    }


    /**
     * 同步限流配置
     *
     * @param appList
     */
    public void syncLimitConfig(Set<String> appList) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (String app : appList) {
                    List<LimitAppConfig> list = limitAppConfigMapper.listByColumn("app", app);
                    ConfigMsg msg = new ConfigMsg();
                    msg.setApp(app);
                    msg.setCode(NettyOpt.UPDATE_LIMIT_CONFIG.getCode());
                    msg.setData(JSON.toJSONString(list));

                    // 主动推给客户端
                    ChannelContext.writeAndFlush(msg);
                }
            }
        });
    }

    /**
     *  根据app同步权限
     */
    public void syncPermissionConfigByApp(String app) {
        this.syncPermissionConfigByAppList(Sets.newHashSet(app));
    }

    /**
     * 根据app同步配置
     *
     * @param appList
     */
    public void syncPermissionConfigByAppList(Set<String> appList) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (String app : appList) {
                    List<ApiInfo> list = apiInfoMapper.listAppAuth(app);

                    ConfigMsg msg = new ConfigMsg();
                    msg.setApp(app);
                    msg.setCode(NettyOpt.UPDATE_PERMISSION_CONFIG.getCode());
                    msg.setData(JSON.toJSONString(list));

                    // 主动推给客户端
                    ChannelContext.writeAndFlush(msg);
                }
            }
        });
    }
}
