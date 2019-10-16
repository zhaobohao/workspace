package com.gitee.sop.adminserver.api.service.result;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class RouteServiceInfo {
    @ApiDocField(description = "serviceId")
    private String serviceId;

    @ApiDocField(description = "创建时间")
    private String createTime;

    @ApiDocField(description = "修改时间")
    private String updateTime;

    @ApiDocField(description = "描述")
    private String description;

    /** 是否是自定义服务，1：是，0：否 */
    @ApiDocField(description = "是否是自定义服务，1：是，0：否")
    private Integer custom;
}
