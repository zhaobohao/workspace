package com.gitee.sop.adminserver.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.exception.ApiException;
import com.gitee.sop.adminserver.api.service.param.RouteParam;
import com.gitee.sop.adminserver.api.service.param.RouteSearchParam;
import com.gitee.sop.adminserver.api.service.param.ServiceSearchParam;
import com.gitee.sop.adminserver.api.service.result.MonitorInfoVO;
import com.gitee.sop.adminserver.api.service.result.MonitorResult;
import com.gitee.sop.adminserver.api.service.result.ServiceInstanceVO;
import com.gitee.sop.adminserver.common.QueryUtil;
import com.gitee.sop.adminserver.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("服务管理-监控")
@Slf4j
public class MonitorApi {

    private static final String GATEWAY_MONITOR_PATH = "/sop/getMonitorData";

    @Autowired
    private ServerService serverService;

    @Value("${sop.secret}")
    private String secret;

    @Api(name = "monitor.data.list")
    @ApiDocMethod(description = "获取监控数据")
    public MonitorResult listMonitorData(RouteParam param) {
        ServiceSearchParam serviceSearchParam = new ServiceSearchParam();
        serviceSearchParam.setServiceId("sop-gateway");
        String searchRouteId = param.getRouteId();
        List<MonitorInfoVO> monitorInfoList = new ArrayList<>();
        List<ServiceInstanceVO> serviceInstanceVOS = serverService.listService(serviceSearchParam);
        for (ServiceInstanceVO serviceInstanceVO : serviceInstanceVOS) {
            if (StringUtils.isBlank(serviceInstanceVO.getInstanceId())) {
                continue;
            }
            String ipPort = serviceInstanceVO.getIpPort();
            try {
                String data = QueryUtil.requestServer(ipPort, GATEWAY_MONITOR_PATH, secret);
                JSONObject jsonObject = JSON.parseObject(data);
                List<MonitorInfoVO> monitorInfoVOList = this.buildMonitorInfoVO(serviceInstanceVO, jsonObject.getJSONObject("data"));
                List<MonitorInfoVO> newList = monitorInfoVOList.stream()
                        .filter(monitorInfoVO -> StringUtils.isBlank(searchRouteId)
                                || StringUtils.containsIgnoreCase(monitorInfoVO.getRouteId(), searchRouteId))
                        .collect(Collectors.toList());
                monitorInfoList.addAll(newList);
            } catch (Exception e) {
                log.error("请求服务失败, ipPort:{}, path:{}", ipPort, GATEWAY_MONITOR_PATH, e);
                throw new ApiException("请求数据失败");
            }
        }

        MonitorResult monitorResult = new MonitorResult();
        List<MonitorInfoVO> monitorInfoTreeData = this.buildTreeData(monitorInfoList);
        monitorResult.setMonitorInfoData(monitorInfoTreeData);
        return monitorResult;
    }

    private List<MonitorInfoVO> buildTreeData(List<MonitorInfoVO> monitorInfoList) {
        AtomicInteger id = new AtomicInteger();
        List<MonitorInfoVO> treeData = new ArrayList<>(8);
        monitorInfoList.stream()
                .collect(Collectors.groupingBy(MonitorInfoVO::getRouteId))
                .forEach((routeId, items) -> {
                    MonitorInfoVO monitorInfoVOTotal = getStatistics(items, id);
                    monitorInfoVOTotal.setId(id.incrementAndGet());
                    treeData.add(monitorInfoVOTotal);
                });

        Comparator<MonitorInfoVO> comparator = Comparator
                // 根据错误次数降序
                .comparing(MonitorInfoVO::getErrorCount).reversed()
                // 然后根据请求耗时降序
                .thenComparing(Comparator.comparing(MonitorInfoVO::getAvgTime).reversed());
        treeData.sort(comparator);
        return treeData;
    }

    private MonitorInfoVO getStatistics(List<MonitorInfoVO> children, AtomicInteger id) {
        long totalRequestDataSize = 0;
        long totalResponseDataSize = 0;
        long maxTime = 0;
        long minTime = 0;
        long totalTime = 0;
        long totalCount = 0;
        long successCount = 0;
        long errorCount = 0;
        List<String> errorMsgList = new ArrayList<>();

        String name = null,version = null, serviceId = null;

        for (MonitorInfoVO child : children) {
            name = child.getName();
            version = child.getVersion();
            serviceId = child.getServiceId();

            child.setId(id.incrementAndGet());
            totalRequestDataSize += child.getTotalRequestDataSize();
            totalResponseDataSize += child.getTotalResponseDataSize();
            if (minTime == 0 || child.getMinTime() < minTime) {
                minTime = child.getMinTime();
            }
            if (child.getMaxTime() > maxTime) {
                maxTime = child.getMaxTime();
            }
            totalTime += child.getTotalTime();
            totalCount += child.getTotalCount();
            successCount += child.getSuccessCount();
            errorCount += child.getErrorCount();
            errorMsgList.addAll(child.getErrorMsgList());
        }

        MonitorInfoVO total = new MonitorInfoVO();
        total.setName(name);
        total.setVersion(version);
        total.setServiceId(serviceId);
        total.setErrorCount(errorCount);
        total.setMaxTime(maxTime);
        total.setMinTime(minTime);
        total.setSuccessCount(successCount);
        total.setTotalCount(totalCount);
        total.setTotalRequestDataSize(totalRequestDataSize);
        total.setTotalResponseDataSize(totalResponseDataSize);
        total.setTotalTime(totalTime);
        total.setChildren(children);
        total.setErrorMsgList(errorMsgList);
        return total;
    }

    private List<MonitorInfoVO> buildMonitorInfoVO(ServiceInstanceVO serviceInstanceVO, JSONObject monitorData) {
        Set<String> routeIdList = monitorData.keySet();
        List<MonitorInfoVO> ret = new ArrayList<>(routeIdList.size());
        routeIdList.forEach(routeId -> {
            JSONObject monitorInfo = monitorData.getJSONObject(routeId);
            MonitorInfoVO monitorInfoVO = monitorInfo.toJavaObject(MonitorInfoVO.class);
            monitorInfoVO.setInstanceId(serviceInstanceVO.getIpPort());
            ret.add(monitorInfoVO);
        });
        return ret;
    }

}
