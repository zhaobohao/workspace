package com.gitee.sop.gatewaycommon.zuul.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.param.FormHttpOutputMessage;
import com.gitee.sop.gatewaycommon.util.RequestUtil;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.util.RequestContentDataExtractor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * zuul参数工具
 * @author tanghc
 */
@Slf4j
public class ZuulParameterUtil {

    private static FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();

    /**
     * 格式化参数
     * @param apiParam 请求的参数
     * @param consumer 修改参数
     * @param <T> 参数类型
     */
    public static <T extends Map<String, Object>> void format(T apiParam, Consumer<T> consumer) {
        RequestContext requestContext = RequestContext.getCurrentContext();
        consumer.accept(apiParam);
        HttpServletRequest request = requestContext.getRequest();
        String contentType = request.getContentType();
        if (StringUtils.containsIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE)) {
            String json = (apiParam instanceof JSONObject) ?
                    ((JSONObject) apiParam).toJSONString()
                    : JSON.toJSONString(apiParam);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            requestContext.setRequest(new ChangeParamsHttpServletRequestWrapper(request, bytes));
        } else if(StringUtils.containsIgnoreCase(contentType, MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            String paramsStr = RequestUtil.convertMapToQueryString(apiParam);
            byte[] data = paramsStr.getBytes(StandardCharsets.UTF_8);
            requestContext.setRequest(new ChangeParamsHttpServletRequestWrapper(request, data));
        } else if(RequestUtil.isMultipart(request)) {
            FormHttpOutputMessage outputMessage = new FormHttpOutputMessage();
            try {
                // 转成MultipartRequest
                if (!(request instanceof MultipartHttpServletRequest)) {
                    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getServletContext());
                    request = commonsMultipartResolver.resolveMultipart(request);
                }
                // 重写新的值
                MultiValueMap<String, Object> builder = RequestContentDataExtractor.extract(request);
                for (Map.Entry<String, Object> entry : apiParam.entrySet()) {
                    Object value = entry.getValue();
                    if (value instanceof List) {
                        builder.put(entry.getKey(), (List)value);
                    } else {
                        builder.put(entry.getKey(), Collections.singletonList(String.valueOf(value)));
                    }
                }
                MediaType mediaType = MediaType.valueOf(request.getContentType());
                // 将字段以及上传文件重写写入到流中
                formHttpMessageConverter.write(builder, mediaType, outputMessage);
                // 获取新的上传文件流
                byte[] data = outputMessage.getInput();

                requestContext.setRequest(new ChangeParamsHttpServletRequestWrapper(request, data));
                // 必须要重新指定content-type，因为此时的boundary已经发生改变
                requestContext.getZuulRequestHeaders().put("content-type", outputMessage.getHeaders().getContentType().toString());
            } catch (Exception e) {
                log.error("修改上传文件请求参数失败, apiParam:{}", apiParam, e);
            }
        } else if(HttpMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
            Map<String, List<String>> newParams = new HashMap<>(apiParam.size() * 2);
            for (Map.Entry<String, Object> entry : apiParam.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof List) {
                    List<String> valueList = ((List<?>) value).stream().map(String::valueOf).collect(Collectors.toList());
                    newParams.put(entry.getKey(), valueList);
                } else {
                    newParams.put(entry.getKey(), Collections.singletonList(String.valueOf(value)));
                }
            }
            requestContext.setRequestQueryParams(newParams);
        }
    }

    private static class ChangeParamsHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private byte[] data;

        public ChangeParamsHttpServletRequestWrapper(HttpServletRequest request, byte[] data) {
            super(request);
            this.data = data;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new ServletInputStreamWrapper(data);
        }

        @Override
        public byte[] getContentData() {
            return data;
        }

        @Override
        public int getContentLength() {
            return data.length;
        }

        @Override
        public long getContentLengthLong() {
            return data.length;
        }
    }

}
