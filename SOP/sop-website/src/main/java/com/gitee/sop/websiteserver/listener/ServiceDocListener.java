package com.gitee.sop.websiteserver.listener;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.route.BaseServiceListener;
import com.gitee.sop.websiteserver.manager.DocManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author tanghc
 */
@Slf4j
public class ServiceDocListener extends BaseServiceListener {

    private static final String SECRET = "b749a2ec000f4f29";

    @Autowired
    private DocManager docManager;

    @Override
    public void onRemoveService(String serviceId) {
        docManager.remove(serviceId);
    }

    @Override
    public void onAddInstance(InstanceDefinition instance) {
        String serviceId = instance.getServiceId();
        String url = getRouteRequestUrl(instance);
        ResponseEntity<String> responseEntity = getRestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            docManager.addDocInfo(
                    serviceId
                    , body
                    , callback -> log.info("加载服务文档，serviceId={}, 机器={}"
                            , serviceId
                            , instance.getIp() + ":" + instance.getPort())
            );
        } else {
            log.error("加载文档失败, status:{}, body:{}", responseEntity.getStatusCodeValue(), responseEntity.getBody());
        }
    }

    private static String getRouteRequestUrl(InstanceDefinition instance) {
        String query = buildQuery(SECRET);
        return "http://" + instance.getIp() + ":" + instance.getPort() + "/v2/api-docs" + query;
    }
}
