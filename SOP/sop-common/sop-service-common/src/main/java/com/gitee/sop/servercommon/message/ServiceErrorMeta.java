package com.gitee.sop.servercommon.message;

import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.exception.ServiceException;
import lombok.Getter;

/**
 * 错误对象
 *
 * @author tanghc
 */
@Getter
public class ServiceErrorMeta {

    private String modulePrefix;
    private String subCode;

    public ServiceErrorMeta(String modulePrefix, String subCode) {
        this.modulePrefix = modulePrefix;
        this.subCode = subCode;
    }

    public ServiceError getError() {
        return ServiceErrorFactory.getError(this, ServiceContext.getCurrentContext().getLocale());
    }

    /**
     * 返回网关exception
     *
     * @param params 参数
     * @return 返回exception
     */
    public ServiceException getException(Object... params) {
        ServiceError error = ServiceErrorFactory.getError(this, ServiceContext.getCurrentContext().getLocale(), params);
        return new ServiceException(error);
    }

}
