package org.springclouddev.log.server.collect;

import org.springclouddev.log.server.client.ElasticLowerClient;
import org.springclouddev.log.core.constant.LogMessageConstant;
import org.springclouddev.log.core.util.ThreadPoolUtil;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * className：BaseLogCollect
 * description：BaseLogCollect 基类
 *
 * @author Frank.chen
 * @version 1.0.0
 */
public class BaseLogCollect {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(BaseLogCollect.class);
    public List<String> logList = new CopyOnWriteArrayList();
    public List<String> traceLogList = new CopyOnWriteArrayList();
    public ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getPool();
    public ElasticLowerClient elasticLowerClient;

    public void sendLog(String index, List<String> sendList) {
        try {
            elasticLowerClient.insertList(sendList, index, LogMessageConstant.ES_TYPE);
            logger.info("logList insert es success! count:{}", sendList.size());
        } catch (Exception e) {
            logger.error("logList insert es failed!", e);
        }
    }

    public void sendTraceLogList(String index, List<String> sendTraceLogList) {
        try {
            elasticLowerClient.insertList(sendTraceLogList, index, LogMessageConstant.ES_TYPE);
            logger.info("traceLogList insert es success! count:{}", sendTraceLogList.size());
        } catch (Exception e) {
            logger.error("traceLogList insert es failed!", e);
        }
    }
}
