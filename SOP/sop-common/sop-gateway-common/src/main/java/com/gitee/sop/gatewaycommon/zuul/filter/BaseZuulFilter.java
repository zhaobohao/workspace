package com.gitee.sop.gatewaycommon.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanghc
 */
public abstract class BaseZuulFilter extends ZuulFilter {

    protected Logger log = LoggerFactory.getLogger(getClass());

    public static final int HTTP_SERVLET_REQUEST_WRAPPER_FILTER_ORDER = -2000;

    public static final int SERVLET_30_WRAPPER_FILTER_ORDER = HTTP_SERVLET_REQUEST_WRAPPER_FILTER_ORDER + 1;

    public static final int FORM_BODY_WRAPPER_FILTER_ORDER = SERVLET_30_WRAPPER_FILTER_ORDER + 1;

    /** 签名验证过滤 */
    public static final int PRE_VALIDATE_FILTER_ORDER = -1000;

    /** 限流过滤 */
    public static final int PRE_LIMIT_FILTER_ORDER = -990;

    /** 参数格式化过滤器 */
    public static final int PRE_PARAMETER_FORMATTER_FILTER_ORDER = -980;

    /** 灰度发布过滤器 */
    public static final int PRE_ENV_GRAY_FILTER_ORDER = -970;

    private Integer filterOrder;

    /**
     * 获取过滤器类型
     * @return 返回FilterType
     * @see ZuulFilter#filterType() filterType()
     */
    protected abstract FilterType getFilterType();

    /**
     * 获取过滤器顺序
     * @return 返回顺序，越小优先执行
     * @see ZuulFilter#filterOrder() filterOrder()
     */
    protected abstract int getFilterOrder();

    /**
     * 执行run
     * @param requestContext
     * @return Some arbitrary artifact may be returned. Current implementation ignores it.
     * @throws ZuulException
     */
    protected abstract Object doRun(RequestContext requestContext) throws ZuulException;

    /**
     * 设置过滤器顺序
     *
     * @param filterOrder 顺序，值越小优先执行
     * @return 返回自身对象
     */
    public BaseZuulFilter order(int filterOrder) {
        this.filterOrder = filterOrder;
        return this;
    }

    @Override
    public int filterOrder() {
        return filterOrder != null ? filterOrder : this.getFilterOrder();
    }

    @Override
    public String filterType() {
        return this.getFilterType().getType();
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        return this.doRun(RequestContext.getCurrentContext());
    }

    /**
     * 过滤该请求，不往下级服务去转发请求，到此结束。并填充responseBody
     *
     * @param requestContext
     * @param result
     */
    public static void stopRouteAndReturn(RequestContext requestContext, Object result) {
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseBody(JSON.toJSONString(result));
    }

    /**
     * to classify a filter by type. Standard types in Zuul are "pre" for pre-routing filtering,
     * "routeDefinition" for routing to an origin, "post" for post-routing filters, "error" for error handling.
     * We also support a "static" type for static responses see  StaticResponseFilter.
     * Any filterType made be created or added and doRun by calling FilterProcessor.runFilters(type)
     */
    public enum FilterType {
        /** zuul过滤器pre类型 */
        PRE("pre"),
        /** zuul过滤器route类型 */
        ROUTE("routeDefinition"),
        /** zuul过滤器post类型 */
        POST("post"),
        /** zuul过滤器error类型 */
        ERROR("error"),
        /** zuul过滤器static类型 */
        STATIC("static"),
        ;

        FilterType(String type) {
            this.type = type;
        }

        private String type;

        public String getType() {
            return type;
        }
    }
}
