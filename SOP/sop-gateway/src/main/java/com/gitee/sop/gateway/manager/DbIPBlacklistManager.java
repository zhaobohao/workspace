package com.gitee.sop.gateway.manager;

import com.gitee.sop.gateway.mapper.IPBlacklistMapper;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.manager.DefaultIPBlacklistManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 限流配置管理
 *
 * @author tanghc
 */
@Slf4j
@Service
public class DbIPBlacklistManager extends DefaultIPBlacklistManager {

    @Autowired
    private IPBlacklistMapper ipBlacklistMapper;

    @Override
    public void load() {
        List<String> ipList = ipBlacklistMapper.listAllIP();
        log.info("加载IP黑名单, size:{}", ipList.size());
        ipList.forEach(this::add);

    }

    @Override
    public void process(ChannelMsg channelMsg) {
        final IPDto ipDto = channelMsg.toObject(IPDto.class);
        String ip = ipDto.getIp();
        switch (channelMsg.getOperation()) {
            case "add":
                log.info("添加IP黑名单，ip:{}", ip);
                add(ip);
                break;
            case "delete":
                log.info("移除IP黑名单，ip:{}", ip);
                remove(ip);
                break;
            default:
        }
    }


    @Data
    private static class IPDto {
        private String ip;
    }
}
