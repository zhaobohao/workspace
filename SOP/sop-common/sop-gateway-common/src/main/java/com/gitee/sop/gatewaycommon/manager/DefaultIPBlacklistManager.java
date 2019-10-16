package com.gitee.sop.gatewaycommon.manager;

import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;

import java.util.Set;

/**
 * ip黑名单管理
 * @author tanghc
 */
public class DefaultIPBlacklistManager implements IPBlacklistManager {

    private static Set<String> ipList = Sets.newConcurrentHashSet();

    @Override
    public void add(String ip) {
        ipList.add(ip);
    }

    @Override
    public void remove(String ip) {
        ipList.remove(ip);
    }

    @Override
    public boolean contains(String ip) {
        if (StringUtils.isBlank(ip)) {
            return false;
        }
        return ipList.contains(ip);
    }

    @Override
    public void load() {

    }
}
