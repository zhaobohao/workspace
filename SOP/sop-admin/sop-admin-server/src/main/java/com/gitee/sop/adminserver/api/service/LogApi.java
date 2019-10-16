package com.gitee.sop.adminserver.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.verify.DefaultMd5Verifier;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.sop.adminserver.api.service.param.LogMonitorInstanceAddParam;
import com.gitee.sop.adminserver.api.service.result.LogMonitorInstanceVO;
import com.gitee.sop.adminserver.common.BizException;
import com.gitee.sop.adminserver.common.QueryUtil;
import com.gitee.sop.adminserver.entity.ConfigCommon;
import com.gitee.sop.adminserver.mapper.ConfigCommonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@ApiService
@ApiDoc("服务管理-日志监控")
@Slf4j
public class LogApi {

    public static final String LOG_MONITOR_INSTANCE = "log.monitor.instance";
    public static final String CODE_SUCCESS = "10000";
    private static final String CODE_KEY = "code";

    @Autowired
    ConfigCommonMapper configCommonMapper;

    RestTemplate restTemplate = new RestTemplate();

    @Value("${zuul.secret}")
    private String secret;

    @Api(name = "monitor.log.list")
    @ApiDocMethod(description = "获取监控日志")
    List<LogMonitorInstanceVO> listLog() {
        List<ConfigCommon> configCommonList = configCommonMapper.listByColumn("config_group", LOG_MONITOR_INSTANCE);
        List<LogMonitorInstanceVO> ret = new ArrayList<>();
        int id = 1;
        for (ConfigCommon configCommon : configCommonList) {
            int pid = id++;
            String ipPort = configCommon.getConfigKey();
            // 父节点
            LogMonitorInstanceVO logMonitorInstanceVOParent = new LogMonitorInstanceVO();
            logMonitorInstanceVOParent.setRawId(configCommon.getId());
            logMonitorInstanceVOParent.setTreeId(pid);
            logMonitorInstanceVOParent.setMonitorName(configCommon.getContent());
            ret.add(logMonitorInstanceVOParent);
            try {
                String logData = this.requestLogServer(ipPort, "listErrors");
                JSONObject jsonObject = JSON.parseObject(logData);
                if (CODE_SUCCESS.equals(jsonObject.getString("code"))) {
                    int errorTotal = 0;
                    List<LogMonitorInstanceVO> data = JSON.parseArray(jsonObject.getString("data"), LogMonitorInstanceVO.class);
                    for (LogMonitorInstanceVO instanceVO : data) {
                        instanceVO.setTreeId(id++);
                        instanceVO.setParentId(pid);
                        errorTotal += instanceVO.getCount();
                    }
                    ret.addAll(data);
                    logMonitorInstanceVOParent.setCount(errorTotal);
                }
            } catch (Exception e) {
                log.error("获取日志信息出错", e);
                logMonitorInstanceVOParent.setMonitorName(logMonitorInstanceVOParent.getMonitorName() + "(请求出错)");
            }
        }
        Collections.sort(ret, Comparator.comparing(LogMonitorInstanceVO::getCount));
        return ret;
    }

    @Api(name = "monitor.log.clear")
    @ApiDocMethod(description = "清空日志")
    void clearLog(@NotNull(message = "id不能为空") Long id) {
        ConfigCommon configCommon = configCommonMapper.getById(id);
        if (configCommon == null) {
            return;
        }
        try {
            String ipPort = configCommon.getConfigKey();
            this.requestLogServer(ipPort, "clearErrors");
        } catch (Exception e) {
            throw new BizException("清除失败");
        }
    }

    @Api(name = "monitor.instance.list")
    @ApiDocMethod(description = "获取已添加的监控实例")
    List<String> listServiceInstance() {
        List<ConfigCommon> configCommonList = configCommonMapper.listByColumn("config_group", LOG_MONITOR_INSTANCE);
        return configCommonList.stream()
                .map(ConfigCommon::getConfigKey)
                .collect(Collectors.toList());
    }

    @Api(name = "monitor.instance.add")
    @ApiDocMethod(description = "添加监控实例")
    void addServiceInstance(LogMonitorInstanceAddParam param) {
        String ipPort = param.getIpPort();
        this.checkInstance(ipPort);

        Query query = new Query();
        query.eq("config_group", LOG_MONITOR_INSTANCE)
                .eq("config_key", ipPort);
        ConfigCommon rec = configCommonMapper.getByQuery(query);
        if (rec != null) {
            throw new BizException("该实例已添加");
        }
        ConfigCommon configCommon = new ConfigCommon();
        configCommon.setConfigGroup(LOG_MONITOR_INSTANCE);
        configCommon.setConfigKey(ipPort);
        configCommon.setContent(param.getServiceId() + "(" + ipPort + ")");
        configCommonMapper.saveIgnoreNull(configCommon);
    }

    private void checkInstance(String ipPort) {
        try {
            String json = this.requestLogServer(ipPort, "listErrors");
            JSONObject jsonObject = JSON.parseObject(json);
            if (!CODE_SUCCESS.equals(jsonObject.getString(CODE_KEY))) {
                log.error("请求结果:{}", json);
                throw new BizException("添加失败");
            }
        } catch (Exception e) {
            log.error("添加失败", e);
            throw new BizException("添加失败");
        }
    }

    private String requestLogServer(String ipPort, String path) throws Exception {
        DefaultMd5Verifier md5Verifier = new DefaultMd5Verifier();
        Map<String, Object> params = new HashMap<>(16);
        params.put("time", System.currentTimeMillis());
        String sign = md5Verifier.buildSign(params, secret);
        params.put("sign", sign);
        String query = QueryUtil.buildQueryString(params);
        String url = "http://" + ipPort + "/" + path + "?" + query;
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        if (entity.getStatusCode() != HttpStatus.OK) {
            throw new IllegalAccessException("无权访问");
        }
        return entity.getBody();
    }

    @Api(name = "monitor.instance.del")
    @ApiDocMethod(description = "删除监控实例")
    void delServiceInstance(@NotNull(message = "id不能为空") Long id) {
        configCommonMapper.deleteById(id);
    }

}
