package com.gitee.sop.adminserver.api.service.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.util.CopyUtil;
import com.gitee.sop.adminserver.bean.ServiceInstance;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class ServiceInstanceParam {
    @ApiDocField(description = "serviceId")
    @NotBlank(message = "serviceId不能为空")
    private String serviceId;

    @ApiDocField(description = "instanceId")
    @NotBlank(message = "instanceId不能为空")
    private String instanceId;

    /**
     * ip
     */
    @ApiDocField(description = "ip")
    private String ip;

    /**
     * port
     */
    @ApiDocField(description = "port")
    private int port;

    /**
     * 服务状态，UP：已上线，OUT_OF_SERVICE：已下线
     */
    @ApiDocField(description = "status")
    private String status;


    public ServiceInstance buildServiceInstance() {
        ServiceInstance serviceInstance = new ServiceInstance();
        CopyUtil.copyPropertiesIgnoreNull(this, serviceInstance);
        return serviceInstance;
    }
}
