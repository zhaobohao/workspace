package com.gitee.sop.servercommon.result;

import com.gitee.sop.servercommon.exception.ServiceException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理业务返回结果
 *
 * @author tanghc
 */
@Slf4j
public class DefaultServiceResultBuilder implements ServiceResultBuilder {

    public static final String ISP_UNKNOWN_ERROR = "isp.unknown-error";

    @Override
    public Object buildError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        String subCode, subMsg;
        if (throwable instanceof ServiceException) {
            ServiceException ex = (ServiceException) throwable;
            subCode = ex.getError().getSub_code();
            subMsg = ex.getError().getSub_msg();
        } else {
            subCode = ISP_UNKNOWN_ERROR;
            subMsg = throwable.getMessage();
        }
        return this.buildError(subCode, subMsg);
    }

    @Override
    public Object buildError(String subCode, String subMsg) {
        AlipayResult result = new AlipayResult();
        result.setSub_code(subCode);
        result.setSub_msg(subMsg);
        return result;
    }

    @Data
    public static class AlipayResult {
        private String sub_code;
        private String sub_msg;
    }
}
