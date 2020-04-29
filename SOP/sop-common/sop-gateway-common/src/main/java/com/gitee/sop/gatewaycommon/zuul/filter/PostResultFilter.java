package com.gitee.sop.gatewaycommon.zuul.filter;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.result.ResultExecutor;
import com.gitee.sop.gatewaycommon.zuul.RequestContextUtil;
import com.netflix.util.Pair;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * 合并微服务结果，统一返回格式
 *
 * @author tanghc
 */
public class PostResultFilter extends BaseZuulFilter {

    @Override
    protected FilterType getFilterType() {
        return FilterType.POST;
    }

    @Override
    protected int getFilterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    protected Object doRun(RequestContext requestContext) throws ZuulException {
        HttpServletResponse response = requestContext.getResponse();
        if (response.isCommitted()) {
            return null;
        }
        String contentType = RequestContextUtil.getZuulContentType(requestContext);
        // 如果是文件下载直接返回
        if (StringUtils.containsIgnoreCase(contentType, MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            return null;
        }
        ApiConfig apiConfig = ApiContext.getApiConfig();
        ResultExecutor<RequestContext, String> resultExecutor = apiConfig.getZuulResultExecutor();
        String serviceResult = getServiceResponseBody(requestContext);
        String finalResult = resultExecutor.mergeResult(requestContext, serviceResult);
        requestContext.setResponseBody(finalResult);
        requestContext.getZuulResponseHeaders().add(new Pair<>(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE));
        return null;
    }

    /**
     * 获取微服务端返回的结果
     *
     * @param requestContext RequestContext
     * @return 返回结果
     */
    private String getServiceResponseBody(RequestContext requestContext) {
        String serviceResult;
        if(!requestContext.getResponseGZipped()) {
            InputStream responseDataStream = requestContext.getResponseDataStream();
            try {
                serviceResult = IOUtils.toString(responseDataStream, SopConstants.CHARSET_UTF8);
            } catch (Exception e) {
                log.error("业务方无数据返回", e);
                serviceResult = SopConstants.EMPTY_JSON;
            }
        }else{
            try {
                GZIPInputStream gzipInputStream=new GZIPInputStream(requestContext.getResponseDataStream());
                serviceResult = IOUtils.toString(gzipInputStream, SopConstants.CHARSET_UTF8);
            } catch (Exception e) {
                log.error("业务方无数据返回", e);
                serviceResult = SopConstants.EMPTY_JSON;
            }
        }
        return serviceResult;
    }

}
