package com.gitee.easyopen.limit;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.bean.Consts;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 本地限流管理
 * @author tanghc
 */
public class ApiLimitConfigLocalManager extends AbstractLimitConfigManager {

    protected static Logger logger = LoggerFactory.getLogger(ApiLimitConfigLocalManager.class);

    @Override
    public void loadLocal() {
        try {
            logger.info("开始读取本地限流配置文件");
            String localFile = ApiContext.getApiConfig().getLocalLimitConfigFile();
            String configJson = FileUtils.readFileToString(new File(localFile), Consts.UTF8);
            this.loadLimitCache(configJson);
            logger.info("本地限流配置文件读取成功，路径：{}", localFile);
        } catch (IOException e) {
            throw new RuntimeException("读取限流配置文件失败", e);
        }
    }

    @Override
    public void loadToLocalCache() {
        loadLocal();
    }

    @Override
    protected List<LimitConfig> listFromDb() {
        return null;
    }

    /** 交给配置中心处理 */
    @Override
    protected int doSave(LimitConfig configCached) {
        return 0;
    }

}
