package com.gitee.sop.adminserver.common;

import com.gitee.easyopen.exception.ApiException;
import com.gitee.easyopen.message.Error;

/**
 * @author tanghc
 */
public class BizException extends ApiException {
    public BizException(String msg) {
        super(msg, "4000");
    }

    public BizException(Exception e) {
        super(e);
    }

    public BizException(Error<String> error) {
        super(error);
    }

    public BizException(String msg, String code) {
        super(msg, code);
    }

    public BizException(String msg, String code, Object data) {
        super(msg, code, data);
    }

    public BizException(Error<String> error, Object data) {
        super(error, data);
    }
}
