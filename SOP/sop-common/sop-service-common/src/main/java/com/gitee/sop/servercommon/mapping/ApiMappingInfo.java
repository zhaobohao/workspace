package com.gitee.sop.servercommon.mapping;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class ApiMappingInfo {
    private String name;
    private String version;
    private boolean ignoreValidate;
    private boolean mergeResult;
    private boolean permission;

    public ApiMappingInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }
}
