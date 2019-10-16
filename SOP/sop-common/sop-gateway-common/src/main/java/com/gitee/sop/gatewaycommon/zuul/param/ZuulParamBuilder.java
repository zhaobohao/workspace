package com.gitee.sop.gatewaycommon.zuul.param;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.BaseParamBuilder;
import com.gitee.sop.gatewaycommon.util.RequestUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 参数解析默认实现
 *
 * @author tanghc
 */
@Slf4j
public class ZuulParamBuilder extends BaseParamBuilder<RequestContext> {

    private static final String GET = "get";

    @Override
    public ApiParam buildRequestParams(RequestContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        Map<String, ?> params;
        ApiParam apiParam = new ApiParam();
        if (GET.equalsIgnoreCase(request.getMethod())) {
            params = RequestUtil.convertRequestParamsToMap(request);
        } else {
            String contentType = request.getContentType();
            if (contentType == null) {
                contentType = "";
            }
            contentType = contentType.toLowerCase();
            // json或者纯文本形式
            if (StringUtils.containsAny(contentType, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE)) {
                params = RequestUtil.convertJsonRequestToMap(request);
            } else if (ServletFileUpload.isMultipartContent(request)) {
                RequestUtil.UploadInfo uploadInfo = RequestUtil.getUploadInfo(request);
                params = uploadInfo.getUploadParams();
                apiParam.setUploadContext(uploadInfo.getUploadContext());
            } else {
                params = RequestUtil.convertRequestParamsToMap(request);
            }
        }
        apiParam.putAll(params);
        return apiParam;
    }

    @Override
    public String getIP(RequestContext ctx) {
        return RequestUtil.getIP(ctx.getRequest());
    }

    @Override
    public void setVersionInHeader(RequestContext ctx, String headerName, String version) {
        ctx.addZuulRequestHeader(headerName, version);
    }

    @Override
    protected void processApiParam(ApiParam apiParam, RequestContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        String method = (String) request.getAttribute(SopConstants.REDIRECT_METHOD_KEY);
        String version = (String) request.getAttribute(SopConstants.REDIRECT_VERSION_KEY);
        apiParam.setRestName(method);
        apiParam.setRestVersion(version);
    }

}
