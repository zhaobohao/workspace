package com.gitee.easyopen.template;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.Invoker;
import com.gitee.easyopen.Result;
import com.gitee.easyopen.support.ResponseHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
public class InvokeTemplate extends AbstractTemplate {

    protected ResponseHandler responseHandler;
    protected Invoker invoker;

    public InvokeTemplate(ApiConfig apiConfig, ResponseHandler responseHandler) {
        super(apiConfig);
        this.responseHandler = responseHandler;
        this.invoker = apiConfig.getInvoker();
    }

    public Object processInvoke(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object result = this.invoker.invoke(request, response);
            this.afterInvoke(request, response, result);
            return result;
        } catch (Throwable e) {
            return this.processError(request, response, e);
        } finally {
            ApiContext.clean();
        }
    }

    public Object processInvokeMock(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object result = this.invoker.invokeMock(request, response);
            this.afterInvoke(request, response, result);
            return result;
        } catch (Throwable e) {
            return this.processError(request, response, e);
        } finally {
            ApiContext.clean();
        }
    }

    /** 最终错误会在这里处理 */
    public Result processError(HttpServletRequest request, HttpServletResponse response, Throwable e) {
        Result result = responseHandler.caugthException(e);
        this.afterInvoke(request, response, result);
        return result;
    }

    protected void afterInvoke(HttpServletRequest request, HttpServletResponse response, Object result) {
        responseResult(request, response, result);
    }

    /**
     * 写数据到客户端
     * @param response
     * @param result 结果
     */
    public void responseResult(HttpServletRequest request, HttpServletResponse response, Object result) {
        responseHandler.responseResult(request, response, result);
    }
}
