package com.easyopen.sdk.client;

import com.easyopen.sdk.common.OpenConfig;
import com.easyopen.sdk.common.RequestForm;
import com.easyopen.sdk.request.BaseRequest;
import com.easyopen.sdk.response.BaseResponse;
import com.easyopen.sdk.util.JsonUtil;
import com.easyopen.sdk.util.SignUtil;
import com.easyopen.sdk.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求客户端
 * @author tanghc
 */
public class OpenClient {
    private static final String ACCEPT_LANGUAGE = "Accept-Language";
    private static final String AUTHORIZATION = "Authorization";
    private static final String PREFIX_BEARER = "Bearer ";
    private static final OpenConfig DEFAULT_CONFIG = new OpenConfig();

    private String url;
    private String appKey;
    private String secret;

    private OpenConfig openConfig;
    private OpenRequest openRequest;

    public OpenClient(String url, String appKey, String secret) {
        this(url, appKey, secret, DEFAULT_CONFIG);
    }

    public OpenClient(String url, String appKey, String secret, OpenConfig openConfig) {
        if (openConfig == null) {
            throw new IllegalArgumentException("openConfig不能为null");
        }
        this.url = url;
        this.appKey = appKey;
        this.secret = secret;
        this.openConfig = openConfig;

        this.openRequest = new OpenRequest(openConfig);
    }

    /**
     * 请求接口
     * @param request 请求对象
     * @param <T> 返回对应的Response
     * @return 返回Response
     */
    public <T extends BaseResponse<?>> T execute(BaseRequest<T> request) {
        return this.execute(request, null);
    }

    /**
     * 请求接口
     * @param request 请求对象
     * @param jwt jwt
     * @param <T> 返回对应的Response
     * @return 返回Response
     */
    public <T extends BaseResponse<?>> T execute(BaseRequest<T> request, String jwt) {
        RequestForm requestForm = request.createRequestForm();
        // 表单数据
        Map<String, Object> form = requestForm.getForm();

        form.put(this.openConfig.getAppKeyName(), this.appKey);
        // 将data部分转成json并urlencode
        Object data = form.get(this.openConfig.getDataName());
        String dataJson = JsonUtil.toJSONString(data);
        dataJson = StringUtil.encodeUrl(dataJson);
        form.put(this.openConfig.getDataName(), dataJson);
        // 生成签名，并加入到form中
        String sign = SignUtil.createSign(form, this.secret);
        form.put(this.openConfig.getSignName(), sign);

        // 构建http请求header
        Map<String, String> header = this.buildHeader(jwt);

        String resp = doExecute(this.url, requestForm, header);

        return this.parseResponse(resp, request);
    }

    protected String doExecute(String url, RequestForm requestForm, Map<String, String> header) {
        return openRequest.request(this.url, requestForm, header);
    }

    protected <T extends BaseResponse<?>> T parseResponse(String resp, BaseRequest<T> request) {
        return JsonUtil.parseObject(resp, request.getResponseClass());
    }

    protected Map<String, String> buildHeader(String jwt) {
        Map<String, String> header = new HashMap<String, String>();
        header.put(ACCEPT_LANGUAGE, this.openConfig.getLocale());
        if (StringUtil.isNotEmpty(jwt)) {
            header.put(AUTHORIZATION, PREFIX_BEARER + jwt);
        }
        return header;
    }

    public OpenRequest getOpenRequest() {
        return openRequest;
    }

    public void setOpenRequest(OpenRequest openRequest) {
        this.openRequest = openRequest;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public OpenConfig getOpenConfig() {
        return openConfig;
    }

}
