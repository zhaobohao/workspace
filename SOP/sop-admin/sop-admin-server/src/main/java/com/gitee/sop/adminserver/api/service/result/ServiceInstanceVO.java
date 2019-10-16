package com.gitee.sop.adminserver.api.service.result;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import java.util.Map;

/**
 * @author tanghc
 */
@Data
public class ServiceInstanceVO {
    @ApiDocField(description = "id")
    private Integer id;

    @ApiDocField(description = "服务名称(serviceId)")
    private String serviceId;

    @ApiDocField(description = "instanceId")
    private String instanceId;

    @ApiDocField(description = "ipPort")
    private String ipPort;

    @ApiDocField(description = "ip")
    private String ip;

    @ApiDocField(description = "port")
    private int port;

    @ApiDocField(description = "status，服务状态，UP：已上线，OUT_OF_SERVICE：已下线")
    private String status;

    @ApiDocField(description = "最后更新时间")
    private String updateTime;

    @ApiDocField(description = "parentId")
    private Integer parentId;

    @ApiDocField(description = "metadata")
    private Map<String, String> metadata;

    public String getIpPort() {
        return ip != null && port > 0 ? ip + ":" + port : "";
    }

}
