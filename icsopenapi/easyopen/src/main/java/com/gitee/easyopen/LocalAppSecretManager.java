package com.gitee.easyopen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.bean.Secret;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * appkey，secret文件管理
 * @author tanghc
 *
 */
public class LocalAppSecretManager implements AppSecretManager, ManagerInitializer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, Secret> secretMap = new ConcurrentHashMap<>(64);

    @Override
    public void loadLocal() {
        try {
            logger.info("开始读取本地秘钥配置文件");
            String localFile = ApiContext.getApiConfig().getLocalSecretConfigFile();
            String configJson = FileUtils.readFileToString(new File(localFile), Consts.UTF8);
            this.loadSecretCache(configJson);
            logger.info("本地秘钥配置文件读取成功，路径：{}", localFile);
        } catch (IOException e) {
            throw new RuntimeException("读取秘钥配置文件失败", e);
        }
    }

    public void loadSecretCache(String json) {
        if(StringUtils.isEmpty(json)) {
            json = "[]";
        }
        secretMap.clear();
        JSONArray arr = JSON.parseArray(json);
        for (int i = 0; i < arr.size(); i++) {
            JSONObject jsonObj = arr.getJSONObject(i);
            Secret secret = jsonObj.toJavaObject(Secret.class);
            secretMap.put(secret.getAppKey(), secret);
        }
    }

    @Override
    public void addAppSecret(Map<String, String> appSecretStore) {
        throw new UnsupportedOperationException("无效操作，com.gitee.easyopen.LocalAppSecretManager.addAppSecret(Map<String, String> appSecretStore)");
    }

    @Override
    public String getSecret(String appKey) {
        if (secretMap.isEmpty()) {
            throw new RuntimeException("服务端尚未初始化秘钥");
        }
        Secret secret = secretMap.get(appKey);
        if(secret == null) {
            return null;
        }
        return secret.getSecret();
    }

    public Secret getSecretInfo(String appKey) {
        if(StringUtils.isEmpty(appKey)) {
            return null;
        }
        if (secretMap.isEmpty()) {
            throw new RuntimeException("服务端尚未初始化秘钥");
        }
        return secretMap.get(appKey);
    }

    @Override
    public boolean isValidAppKey(String appKey) {
        if (appKey == null) {
            return false;
        }
        return secretMap.containsKey(appKey);
    }
}
