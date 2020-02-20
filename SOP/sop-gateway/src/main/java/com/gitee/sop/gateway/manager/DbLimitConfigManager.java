package com.gitee.sop.gateway.manager;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.gateway.entity.ConfigLimit;
import com.gitee.sop.gateway.mapper.ConfigLimitMapper;
import com.gitee.sop.gatewaycommon.bean.ChannelMsg;
import com.gitee.sop.gatewaycommon.bean.ConfigLimitDto;
import com.gitee.sop.gatewaycommon.manager.DefaultLimitConfigManager;
import com.gitee.sop.gatewaycommon.util.MyBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 限流配置管理
 * @author tanghc
 */
@Slf4j
@Service
public class DbLimitConfigManager extends DefaultLimitConfigManager {

    @Autowired
    ConfigLimitMapper configLimitMapper;

    @Autowired
    Environment environment;

    @Override
    public void load(String serviceId) {
        Query query = new Query();
        if (StringUtils.isNotBlank(serviceId)) {
            query.eq("service_id", serviceId);
        }
        configLimitMapper.list(query)
                .forEach(this::putVal);

    }

    protected void putVal(ConfigLimit object) {
        ConfigLimitDto configLimitDto = new ConfigLimitDto();
        MyBeanUtil.copyPropertiesIgnoreNull(object, configLimitDto);
        this.update(configLimitDto);
    }

    @Override
    public void process(ChannelMsg channelMsg) {
        final ConfigLimitDto configLimitDto = channelMsg.toObject(ConfigLimitDto.class);
        switch (channelMsg.getOperation()) {
            case "reload":
                log.info("重新加载限流配置信息，configLimitDto:{}", configLimitDto);
                load(configLimitDto.getServiceId());
                break;
            case "update":
                log.info("更新限流配置信息，configLimitDto:{}", configLimitDto);
                update(configLimitDto);
                break;
            default:
        }
    }


}
