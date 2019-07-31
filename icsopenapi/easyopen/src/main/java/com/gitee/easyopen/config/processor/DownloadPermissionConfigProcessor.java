package com.gitee.easyopen.config.processor;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.config.NettyOpt;

import io.netty.channel.Channel;

/**
 * 下载权限配置
 * @author tanghc
 */
public class DownloadPermissionConfigProcessor extends AbstractNettyProcessor {

    public DownloadPermissionConfigProcessor(ConfigClient configClient, NettyOpt nettyOpt) {
        super(configClient, nettyOpt);
    }

    @Override
    protected void doProcess(Channel channel, String data) {
        try {
            String localConfigFile = ApiContext.getApiConfig().getLocalPermissionConfigFile();
            FileUtils.write(new File(localConfigFile), data, Consts.UTF8);
            logger.info("权限配置下载成功，保存路径：{}", localConfigFile);
            this.configClient.getPermissionManager().loadPermissionCache(data);
        } catch (IOException e) {
            logger.error("权限配置下载失败", e);
        }
    }

}
