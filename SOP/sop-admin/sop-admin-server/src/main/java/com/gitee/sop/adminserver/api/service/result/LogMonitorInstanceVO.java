package com.gitee.sop.adminserver.api.service.result;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class LogMonitorInstanceVO {
    private String id;
    private int treeId;
    /** 表主键 */
    private long rawId;
    private String name;
    private String version;
    private String serviceId;
    private String errorMsg;
    private long count;

    private int parentId;
    private String monitorName;
}
