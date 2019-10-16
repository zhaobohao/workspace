package com.gitee.sop.servercommon.manager;

import com.gitee.sop.servercommon.bean.ServiceApiInfo;

/**
 * @author tanghc
 */
public interface ApiMetaManager {
    /**
     * 上传API
     * @param serviceApiInfo
     */
    void uploadApi(ServiceApiInfo serviceApiInfo);
}
