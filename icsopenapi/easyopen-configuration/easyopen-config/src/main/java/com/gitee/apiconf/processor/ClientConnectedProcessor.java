package com.gitee.apiconf.processor;

import com.alibaba.fastjson.JSON;
import com.gitee.apiconf.common.ChannelContext;
import com.gitee.apiconf.common.SpringContext;
import com.gitee.apiconf.entity.AppInfo;
import com.gitee.apiconf.mapper.AppInfoMapper;
import com.gitee.easyopen.config.App;
import com.gitee.easyopen.config.ConfigMsg;
import com.gitee.fastmybatis.core.util.MyBeanUtil;
import io.netty.channel.Channel;

public class ClientConnectedProcessor extends AbstractNettyServerProcessor {
    @Override
    public void process(Channel channel, ConfigMsg msg) {
        ChannelContext.saveChannel(msg.getApp(), channel);

        App app = JSON.parseObject(msg.getData(), App.class);
        this.saveAppInfo(app);
    }

    private void saveAppInfo(App app) {
        String appName = app.getApp();
        AppInfoMapper appInfoMapper = SpringContext.getBean(AppInfoMapper.class);

        AppInfo appInfo = appInfoMapper.getByColumn("app", appName);
        if (appInfo == null) {
            appInfo = new AppInfo();
            MyBeanUtil.copyPropertiesIgnoreNull(app, appInfo);
            appInfoMapper.save(appInfo);
        } else {
            MyBeanUtil.copyPropertiesIgnoreNull(app, appInfo);
            appInfoMapper.update(appInfo);
        }
    }
}
