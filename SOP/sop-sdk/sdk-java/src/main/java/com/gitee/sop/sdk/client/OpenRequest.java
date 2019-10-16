package com.gitee.sop.sdk.client;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.sdk.common.OpenConfig;
import com.gitee.sop.sdk.common.RequestForm;
import com.gitee.sop.sdk.common.RequestMethod;
import com.gitee.sop.sdk.common.SopSdkErrors;
import com.gitee.sop.sdk.common.UploadFile;
import com.gitee.sop.sdk.response.ErrorResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 负责请求操作
 *
 * @author tanghc
 */
public class OpenRequest {

    private OpenHttp openHttp;

    public OpenRequest(OpenConfig openConfig) {
        this.openHttp = new OpenHttp(openConfig);
    }

    public String request(String url, RequestForm requestForm, Map<String, String> header) {
        try {
            Map<String, String> form = requestForm.getForm();
            List<UploadFile> files = requestForm.getFiles();
            if (files != null && files.size() > 0) {
                return openHttp.requestFile(url, form, header, files);
            } else {
                RequestMethod requestMethod = requestForm.getRequestMethod();
                if (requestMethod == RequestMethod.GET) {
                    String query = this.buildGetQueryString(form, requestForm.getCharset());
                    if (query != null && query.length() > 0) {
                        url = url + "?" + query;
                    }
                    return openHttp.get(url, header);
                } else {
                    return openHttp.requestJson(url, JSON.toJSONString(form), header);
                }
            }
        } catch (IOException e) {
            return this.causeException(e);
        }
    }

    protected String buildGetQueryString(Map<String, String> params, String charset) throws UnsupportedEncodingException {
        if (params == null || params.size() == 0) {
            return "";
        }
        StringBuilder query = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (i++ > 0) {
                query.append("&");
            }
            query.append(name).append("=").append(URLEncoder.encode(value, charset));
        }
        return query.toString();
    }

    protected String causeException(Exception e) {
        ErrorResponse result = SopSdkErrors.HTTP_ERROR.getErrorResponse();
        return JSON.toJSONString(result);
    }
}
