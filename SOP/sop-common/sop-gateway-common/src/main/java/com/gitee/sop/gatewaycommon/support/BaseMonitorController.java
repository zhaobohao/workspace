package com.gitee.sop.gatewaycommon.support;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.monitor.MonitorManager;
import com.gitee.sop.gatewaycommon.result.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 提供监控数据
 *
 * @author tanghc
 */
public abstract class BaseMonitorController<T> extends SopBaseController<T> {

    @GetMapping("/sop/getMonitorData")
    public ApiResult doExecute(T request) {
        MonitorManager monitorManager = ApiConfig.getInstance().getMonitorManager();
        return execute(request, monitorManager::getMonitorData);
    }

}
