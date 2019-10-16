package com.gitee.sop.gatewaycommon.secret;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * appkey，secret文件管理，功能同CacheAppSecretManager，这个是将appKey，secret放在属性文件中<br>
 * key为appKey，value为secret
 * @author tanghc
 *
 */
public class FileAppSecretManager implements AppSecretManager {

    private String appSecretFile = "appSecret.properties";

    private Properties properties;
    
    @Override
    public void addAppSecret(Map<String, String> appSecretStore) {
        properties.putAll(appSecretStore);
    }

    @Override
    public String getSecret(String appKey) {
        if (properties == null) {
            try {
                // 默认加载class根目录的appSecret.properties文件
                DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
                Resource resource = resourceLoader.getResource(appSecretFile);
                properties =   PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) {
                throw new IllegalArgumentException("在类路径下找不到appSecret.properties的应用密钥的属性文件");
            }
        }

        return properties.getProperty(appKey);
    }

    public void setAppSecretFile(String appSecretFile) {
        this.appSecretFile = appSecretFile;
    }

    @Override
    public boolean isValidAppKey(String appKey) {
    	if(appKey == null){
    		return false;
    	}
        return getSecret(appKey) != null;
    }
}

