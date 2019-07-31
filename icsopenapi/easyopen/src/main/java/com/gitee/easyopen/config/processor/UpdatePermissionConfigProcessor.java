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
 * 更新权限配置
 * @author tanghc
 */
public class UpdatePermissionConfigProcessor extends AbstractNettyProcessorNoLock {

    public UpdatePermissionConfigProcessor(ConfigClient configClient, NettyOpt nettyOpt) {
        super(configClient, nettyOpt);
    }

    @Override
    protected void doProcess(Channel channel, String data) {
        logger.info("配置中心推送权限设置");
        try {
            String localConfigFile = ApiContext.getApiConfig().getLocalPermissionConfigFile();
            FileUtils.write(new File(localConfigFile), data, Consts.UTF8);
            this.configClient.getPermissionManager().loadPermissionCache(data);
        } catch (IOException e) {
            logger.error("配置中心推送权限写文件失败", e);
        }
    }

}
