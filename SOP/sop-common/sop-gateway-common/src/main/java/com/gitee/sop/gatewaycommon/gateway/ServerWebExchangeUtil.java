package com.gitee.sop.gatewaycommon.gateway;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.gateway.common.FileUploadHttpServletRequest;
import com.gitee.sop.gatewaycommon.gateway.common.RequestContentDataExtractor;
import com.gitee.sop.gatewaycommon.gateway.common.SopServerHttpRequestDecorator;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.FormHttpOutputMessage;
import com.gitee.sop.gatewaycommon.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.gitee.sop.gatewaycommon.bean.SopConstants.CACHE_REQUEST_BODY_FOR_MAP;
import static com.gitee.sop.gatewaycommon.bean.SopConstants.CACHE_REQUEST_BODY_OBJECT_KEY;

/**
 * @author tanghc
 */
@Slf4j
public class ServerWebExchangeUtil {

    private static FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();

    /**
     * 获取请求参数
     *
     * @param exchange ServerWebExchange
     * @return 返回请求参数
     */
    public static ApiParam getApiParam(ServerWebExchange exchange) {
        return exchange.getAttribute(SopConstants.CACHE_API_PARAM);
    }

    /**
     * 设置请求参数
     *
     * @param exchange ServerWebExchange
     * @param apiParam 请求参数
     */
    public static void setApiParam(ServerWebExchange exchange, ApiParam apiParam) {
        exchange.getAttributes().put(SopConstants.CACHE_API_PARAM, apiParam);
    }

    /**
     * 获取Spring Cloud Gateway请求的原始参数。前提是要使用ReadBodyRoutePredicateFactory
     *
     * @param exchange ServerWebExchange
     * @return 没有参数返回null
     * @see com.gitee.sop.gatewaycommon.gateway.route.ReadBodyRoutePredicateFactory
     */
    public static ApiParam getRequestParams(ServerWebExchange exchange) {
        ApiParam apiParamExist = exchange.getAttribute(CACHE_REQUEST_BODY_FOR_MAP);
        if (apiParamExist != null) {
            return apiParamExist;
        }
        ApiParam apiParam = new ApiParam();
        Map<String, ?> params = null;
        if (exchange.getRequest().getMethod() == HttpMethod.GET) {
            MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
            params = buildParams(queryParams);
        } else {
            String cachedBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
            if (cachedBody != null) {
                MediaType contentType = exchange.getRequest().getHeaders().getContentType();
                String contentTypeStr = contentType == null ? "" : contentType.toString().toLowerCase();
                // 如果是json方式提交
                if (StringUtils.containsAny(contentTypeStr, "json", "text")) {
                    params = JSON.parseObject(cachedBody);
                } else if (StringUtils.containsIgnoreCase(contentTypeStr, "multipart")) {
                    // 如果是文件上传请求
                    HttpServletRequest fileUploadRequest = getFileUploadRequest(exchange, cachedBody);
                    setFileUploadRequest(exchange, fileUploadRequest);
                    RequestUtil.UploadInfo uploadInfo = RequestUtil.getUploadInfo(fileUploadRequest);
                    params = uploadInfo.getUploadParams();
                    apiParam.setUploadContext(uploadInfo.getUploadContext());
                } else {
                    params = RequestUtil.parseQueryToMap(cachedBody);
                }
            }
        }
        if (params != null) {
            apiParam.putAll(params);
            exchange.getAttributes().put(CACHE_REQUEST_BODY_FOR_MAP, apiParam);
        }
        return apiParam;
    }


    public static Map<String, String> buildParams(MultiValueMap<String, String> queryParams) {
        if (queryParams == null || queryParams.size() == 0) {
            return null;
        }
        Map<String, String> params = new HashMap<>(queryParams.size());
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            params.put(entry.getKey(), entry.getValue().get(0));
        }
        return params;
    }

    /**
     * 添加header
     *
     * @param exchange        当前ServerWebExchange
     * @param headersConsumer headers
     * @return 返回一个新的ServerWebExchange
     */
    public static ServerWebExchange addHeaders(ServerWebExchange exchange, Consumer<HttpHeaders> headersConsumer) {
        // 创建一个新的request
        ServerHttpRequest serverHttpRequestNew = exchange.getRequest()
                .mutate()
                .headers(headersConsumer)
                .build();
        // 创建一个新的exchange对象
        return exchange
                .mutate()
                .request(serverHttpRequestNew)
                .build();
    }

    /**
     * 获取一个文件上传request
     *
     * @param exchange    当前ServerWebExchange
     * @param requestBody 上传文件请求体内容
     * @return 返回文件上传request
     */
    public static HttpServletRequest getFileUploadRequest(ServerWebExchange exchange, String requestBody) {
        byte[] data = requestBody.getBytes(StandardCharsets.UTF_8);
        return new FileUploadHttpServletRequest(exchange.getRequest(), data);
    }

    public static HttpServletRequest getFileUploadRequest(ServerWebExchange exchange) {
        return exchange.getAttribute(SopConstants.CACHE_UPLOAD_REQUEST);
    }

    public static void setFileUploadRequest(ServerWebExchange exchange, HttpServletRequest request) {
        exchange.getAttributes().put(SopConstants.CACHE_UPLOAD_REQUEST, request);
    }

    /**
     * 修改请求参数。参考自：https://blog.csdn.net/fuck487/article/details/85166162
     *
     * @param exchange       ServerWebExchange
     * @param apiParam       原始请求参数
     * @param paramsConsumer 执行参数更改
     * @param headerConsumer header更改
     * @param <T>            参数类型
     * @return 返回新的ServerWebExchange
     */
    public static <T extends Map<String, Object>> ServerWebExchange format(
            ServerWebExchange exchange
            , T apiParam
            , Consumer<T> paramsConsumer
            , Consumer<HttpHeaders> headerConsumer
    ) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        // 新的request
        ServerHttpRequest newRequest;
        paramsConsumer.accept(apiParam);
        if (serverHttpRequest.getMethod() == HttpMethod.GET) {
            // 新的查询参数
            String queryString = RequestUtil.convertMapToQueryString(apiParam);
            // 创建一个新的request，并使用新的uri
            newRequest = new SopServerHttpRequestDecorator(serverHttpRequest, queryString);
        } else {
            MediaType mediaType = serverHttpRequest.getHeaders().getContentType();
            if (mediaType == null) {
                mediaType = MediaType.APPLICATION_FORM_URLENCODED;
            }
            String contentType = mediaType.toString().toLowerCase();
            // 修改后的请求体
            // 处理json请求（application/json）
            if (StringUtils.containsAny(contentType, "json", "text")) {
                String bodyStr = JSON.toJSONString(apiParam);
                byte[] bodyBytes = bodyStr.getBytes(StandardCharsets.UTF_8);
                newRequest = new SopServerHttpRequestDecorator(serverHttpRequest, bodyBytes);
            } else if (StringUtils.contains(contentType, "multipart")) {
                // 处理文件上传请求
                FormHttpOutputMessage outputMessage = new FormHttpOutputMessage();
                HttpServletRequest request = ServerWebExchangeUtil.getFileUploadRequest(exchange);
                try {
                    // 转成MultipartRequest
                    if (!(request instanceof MultipartHttpServletRequest)) {
                        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
                        request = commonsMultipartResolver.resolveMultipart(request);
                    }
                    // 重写新的值
                    MultiValueMap<String, Object> builder = RequestContentDataExtractor.extract(request);
                    for (Map.Entry<String, Object> entry : apiParam.entrySet()) {
                        Object value = entry.getValue();
                        if (value instanceof List) {
                            builder.put(entry.getKey(), (List) value);
                        } else {
                            builder.put(entry.getKey(), Collections.singletonList(String.valueOf(value)));
                        }
                    }
                    // 将字段以及上传文件重写写入到流中
                    formHttpMessageConverter.write(builder, mediaType, outputMessage);
                    // 获取新的上传文件流
                    byte[] bodyBytes = outputMessage.getInput();
                    newRequest = new SopServerHttpRequestDecorator(serverHttpRequest, bodyBytes);
                    // 必须要重新指定content-type，因为此时的boundary已经发生改变
                    MediaType contentTypeMultipart = outputMessage.getHeaders().getContentType();
                    newRequest.getHeaders().setContentType(contentTypeMultipart);
                } catch (IOException e) {
                    log.error("修改上传文件请求参数失败, apiParam:{}", apiParam, e);
                    throw new RuntimeException(e);
                }
            } else {
                // 否则一律按表单请求处理
                String bodyStr = RequestUtil.convertMapToQueryString(apiParam);
                byte[] bodyBytes = bodyStr.getBytes(StandardCharsets.UTF_8);
                newRequest = new SopServerHttpRequestDecorator(serverHttpRequest, bodyBytes);
            }
        }
        HttpHeaders headers = newRequest.getHeaders();
        // 自定义header
        headerConsumer.accept(headers);
        // 创建一个新的exchange
        return exchange.mutate().request(newRequest).build();
    }

}
