package com.gitee.easyopen.permission;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.bean.Consts;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 权限管理
 * @author tanghc
 */
public class ApiPermissionManager implements PermissionManager {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /** key:appKey， value:name+version */
    private static Map<String, List<String>> appKeyApiMap = new ConcurrentHashMap<>(64);

    @Override
    public synchronized boolean canVisit(String appKey, String name, String version) {
        // 能够访问的接口，里面是name+version
        List<String> clientApis = this.listClientApi(appKey);
        return clientApis.contains(name + version);
    }

    @Override
    public void loadLocal() {
        try {
            logger.info("开始读取本地权限配置文件");
            String localFile = ApiContext.getApiConfig().getLocalPermissionConfigFile();
            String configJson = FileUtils.readFileToString(new File(localFile), Consts.UTF8);
            this.loadPermissionCache(configJson);
            logger.info("本地权限配置文件读取成功，路径：{}", localFile);
        } catch (IOException e) {
            throw new RuntimeException("读取权限配置文件失败", e);
        }
    }

    @Override
    public void loadPermissionConfig() {
    }

    @Override
    public synchronized void loadPermissionCache(String configJson) {
        if (StringUtils.isEmpty(configJson)) {
            configJson = "[]";
        }
        List<ApiInfo> list = JSON.parseArray(configJson, ApiInfo.class);
        appKeyApiMap.clear();
        for (ApiInfo apiInfo : list) {
            String appKey = apiInfo.getApp_key();
            List<String> nameVersionList = appKeyApiMap.get(appKey);
            if (nameVersionList == null) {
                nameVersionList = new ArrayList<>();
                appKeyApiMap.put(appKey, nameVersionList);
            }
            nameVersionList.add(apiInfo.getName() + apiInfo.getVersion());
        }
    }

    @Override
    public List<String> listClientApi(String appKey) {
        List<String> list = appKeyApiMap.get(appKey);
        if (list == null) {
            list = Collections.emptyList();
        }
        return list;
    }


}
