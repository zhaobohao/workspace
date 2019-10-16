package com.gitee.sop.gatewaycommon.secret;

import java.util.HashMap;
import java.util.Map;

/**
 * appkey，secret默认管理，简单放在map中，如果要放在redis中，可以参照此方式实现AppSecretManager，然后在ApiConfig中setAppSecretManager()
 * @author tanghc
 *
 */
public class CacheAppSecretManager implements AppSecretManager {

    private Map<String, String> secretMap = new HashMap<>(64);

    @Override
    public void addAppSecret(Map<String, String> appSecretStore) {
        secretMap.putAll(appSecretStore);
    }

    @Override
    public String getSecret(String appKey) {
        return secretMap.get(appKey);
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        if (appKey == null) {
            return false;
        }
        return getSecret(appKey) != null;
    }

}
