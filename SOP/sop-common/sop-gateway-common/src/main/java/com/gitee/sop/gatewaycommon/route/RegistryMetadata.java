package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.SopConstants;

import java.util.Map;

/**
 * @author tanghc
 */
public interface RegistryMetadata {

    default String getContextPath(Map<String, String> metadata) {
        return metadata.getOrDefault(SopConstants.METADATA_SERVER_CONTEXT_PATH
                , metadata.getOrDefault(SopConstants.METADATA_SERVER_CONTEXT_PATH_COMPATIBILITY, ""));
    }

}
