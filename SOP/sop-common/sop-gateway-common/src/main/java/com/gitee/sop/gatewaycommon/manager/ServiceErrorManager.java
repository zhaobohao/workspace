package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.ErrorDefinition;
import com.gitee.sop.gatewaycommon.bean.ErrorEntity;

import java.util.Collection;

/**
 * @author tanghc
 */
public interface ServiceErrorManager {

    /**
     * 保存业务错误，一般由开发人员自己throw的异常
     * @param errorDefinition
     */
    void saveBizError(ErrorDefinition errorDefinition);

    /**
     * 保存未知的错误信息
     * @param errorDefinition
     */
    void saveUnknownError(ErrorDefinition errorDefinition);

    /**
     * 清除日志
     */
    void clear();

    /**
     * 获取所有错误信息
     * @return
     */
    Collection<ErrorEntity> listAllErrors();
}
