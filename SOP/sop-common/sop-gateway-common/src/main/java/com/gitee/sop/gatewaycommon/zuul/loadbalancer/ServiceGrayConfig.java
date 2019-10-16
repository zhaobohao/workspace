package com.gitee.sop.gatewaycommon.zuul.loadbalancer;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * @author tanghc
 */
@Data
public class ServiceGrayConfig {

    private String serviceId;

    /**
     * 用户id
     */
    private Set<String> userKeys;

    /** 存放接口隐射关系，key:nameversion，value:newVersion */
    private Map<String, String> grayNameVersion;

    public boolean containsKey(Object userKey) {
        return userKeys.contains(String.valueOf(userKey));
    }

    public String getVersion(String name) {
        return grayNameVersion.get(name);
    }

}
