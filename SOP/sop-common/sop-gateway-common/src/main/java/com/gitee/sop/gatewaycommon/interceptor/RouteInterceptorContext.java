package com.gitee.sop.gatewaycommon.interceptor;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.springframework.http.HttpStatus;

/**
 * 拦截器参数
 *
 * @author tanghc
 */
public interface RouteInterceptorContext {

    /**
     * 返回ApiParam
     *
     * @return 返回ApiParam
     */
    ApiParam getApiParam();

    /**
     * 获取微服务返回的内容
     *
     * @return 微服务返回内容
     */
    String getServiceResult();

    /**
     * 获取微服务端的错误信息，status为200时，返回null。
     *
     * @return 返回错误信息
     */
    String getServiceErrorMsg();

    /**
     * 获取微服务返回状态码
     *
     * @return 返回状态码，正确为200，错误为非200
     */
    int getResponseStatus();

    /**
     * 获取路由开始时间
     *
     * @return 返回开始时间
     */
    long getBeginTimeMillis();

    /**
     * 获取路由结束时间
     *
     * @return 返回结束时间
     */
    long getFinishTimeMillis();

    /**
     * 获取上下文信息，zuul返回RequestContext，Gateway返回ServerWebExchange
     *
     * @return 返回上下文对象
     */
    Object getRequestContext();

    /**
     * 获取请求内容大小
     *
     * @return 返回请求内容大小
     */
    long getRequestDataSize();

    /**
     * 获取返回结果内容大小
     * @return 返回返回结果内容大小
     */
    long getResponseDataSize();

    /**
     * 是否是成功请求，微服务主动抛出的异常也算作成功，JSR303校验失败也算作成功。
     * 只有微服务返回未知的错误算作失败。
     * @return true：成功请求
     */
    default boolean isSuccessRequest() {
        int responseStatus = getResponseStatus();
        return responseStatus == HttpStatus.OK.value() || responseStatus == SopConstants.BIZ_ERROR_STATUS;
    }
}
