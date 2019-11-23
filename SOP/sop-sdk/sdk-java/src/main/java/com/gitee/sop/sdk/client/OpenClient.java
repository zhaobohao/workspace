package com.gitee.sop.sdk.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.sdk.common.DataNameBuilder;
import com.gitee.sop.sdk.common.OpenConfig;
import com.gitee.sop.sdk.common.RequestForm;
import com.gitee.sop.sdk.common.SopSdkErrors;
import com.gitee.sop.sdk.exception.SdkException;
import com.gitee.sop.sdk.request.BaseRequest;
import com.gitee.sop.sdk.response.BaseResponse;
import com.gitee.sop.sdk.response.ErrorResponse;
import com.gitee.sop.sdk.sign.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 请求客户端，申明一个即可
 *
 * @author tanghc
 */
public class OpenClient {
    private static final Log log = LogFactory.getLog(OpenClient.class);

    /**
     * 默认配置
     */
    private static final OpenConfig DEFAULT_CONFIG = new OpenConfig();

    /**
     * 接口请求url
     */
    private String url;

    /**
     * 平台提供的appId
     */
    private String appId;

    /**
     * 开放平台提供的私钥
     */
    private String privateKey;

    /**
     * 开放平台提供的公钥
     */
    private String publicKeyPlatform;

    /**
     * 配置项
     */
    private OpenConfig openConfig;

    /**
     * 请求对象
     */
    private OpenRequest openRequest;

    /**
     * 节点处理
     */
    private DataNameBuilder dataNameBuilder;

    /**
     * 构建请求客户端
     *
     * @param url           接口url
     * @param appId         平台分配的appId
     * @param privateKeyIsv 平台分配的私钥
     */
    public OpenClient(String url, String appId, String privateKeyIsv) {
        this(url, appId, privateKeyIsv, DEFAULT_CONFIG);
    }

    /**
     * 构建请求客户端
     *
     * @param url               接口url
     * @param appId             平台分配的appId
     * @param privateKeyIsv     平台分配的私钥
     * @param publicKeyPlatform 平台分配的公钥
     */
    public OpenClient(String url, String appId, String privateKeyIsv, String publicKeyPlatform) {
        this(url, appId, privateKeyIsv);
        this.publicKeyPlatform = publicKeyPlatform;
    }

    /**
     * 构建请求客户端
     *
     * @param url           接口url
     * @param appId         平台分配的appId
     * @param privateKeyIsv 平台分配的私钥
     * @param openConfig    配置项
     */
    public OpenClient(String url, String appId, String privateKeyIsv, OpenConfig openConfig) {
        if (openConfig == null) {
            throw new IllegalArgumentException("openConfig不能为null");
        }
        this.url = url;
        this.appId = appId;
        this.privateKey = privateKeyIsv;
        this.openConfig = openConfig;

        this.openRequest = new OpenRequest(openConfig);
        this.dataNameBuilder = openConfig.getDataNameBuilder();
    }

    /**
     * 构建请求客户端
     *
     * @param url               接口url
     * @param appId             平台分配的appId
     * @param privateKeyIsv     平台分配的私钥
     * @param publicKeyPlatform 平台分配的公钥
     * @param openConfig        配置项
     */
    public OpenClient(String url, String appId, String privateKeyIsv, String publicKeyPlatform, OpenConfig openConfig) {
        this(url, appId, privateKeyIsv, openConfig);
        this.publicKeyPlatform = publicKeyPlatform;
    }

    /**
     * 请求接口
     *
     * @param request 请求对象
     * @param <T>     返回对应的Response
     * @return 返回Response
     */
    public <T extends BaseResponse> T execute(BaseRequest<T> request) {
        return this.execute(request, null);
    }

    /**
     * 请求接口
     *
     * @param request     请求对象
     * @param accessToken jwt
     * @param <T>         返回对应的Response
     * @return 返回Response
     */
    public <T extends BaseResponse> T execute(BaseRequest<T> request, String accessToken) {
        RequestForm requestForm = request.createRequestForm(this.openConfig);
        // 表单数据
        Map<String, String> form = requestForm.getForm();
        if (accessToken != null) {
            form.put(this.openConfig.getAccessTokenName(), accessToken);
        }
        form.put(this.openConfig.getAppKeyName(), this.appId);
        if (openConfig.getEncrptBizContent()) {
            try {
                form.put(this.openConfig.getDataName(), SopSignature.rsaEncrypt(form.get(openConfig.getDataName()), publicKeyPlatform, openConfig.getCharset()));
            } catch (SopSignException e) {
                throw new SdkException("加密业务包数据错误", e);
            }
        }
        String content = SopSignature.getSignContent(form);
        String sign = null;
        try {
            sign = SopSignature.rsaSign(content, privateKey, openConfig.getCharset(), openConfig.getSignType());
        } catch (SopSignException e) {
            throw new SdkException("构建签名错误", e);
        }

        form.put(this.openConfig.getSignName(), sign);

        String resp = doExecute(this.url, requestForm, Collections.emptyMap());
        if (log.isDebugEnabled()) {
        log.info("----------- 请求信息 -----------"
                + "\n请求参数：" + SopSignature.getSignContent(form)
                + "\n待签名内容：" + content
                + "\n签名(sign)：" + sign
                + "\n----------- 返回结果 -----------"
                + "\n" + resp
        );
        }
        return this.parseResponse(resp, request);
    }

    protected String doExecute(String url, RequestForm requestForm, Map<String, String> header) {
        return openRequest.request(url, requestForm, header);
    }

    /**
     * 解析返回结果
     *
     * @param resp    返回结果
     * @param request 请求对象
     * @param <T>     返回结果
     * @return 返回对于的Response对象
     */
    protected <T extends BaseResponse> T parseResponse(String resp, BaseRequest<T> request) {
        String method = request.getMethod();
        String rootNodeName = dataNameBuilder.build(method);
        JSONObject jsonObject = JSON.parseObject(resp);
        String errorResponseName = this.openConfig.getErrorResponseName();
        boolean errorResponse = jsonObject.containsKey(errorResponseName);
        if (errorResponse) {
            rootNodeName = errorResponseName;
        }
        String data1 = jsonObject.getString(rootNodeName);
        JSONObject data = new JSONObject();
        if (!data1.startsWith("{")) {
            //需要解密
            try {
                data = JSON.parseObject(SopSignature.rsaDecrypt(data1, privateKey, "utf-8"));
            } catch (SopSignException e) {
                e.printStackTrace();
            }
        } else {
            data = jsonObject.getJSONObject(rootNodeName);
        }
        String sign = jsonObject.getString(openConfig.getSignName());
        // 是否要验证返回的sign
        if (StringUtils.areNotEmpty(sign, publicKeyPlatform)) {
            String signContent = buildBizJson(resp);
            if (!this.checkResponseSign(signContent, sign, publicKeyPlatform)) {
                ErrorResponse error = SopSdkErrors.CHECK_RESPONSE_SIGN_ERROR.getErrorResponse();
                data = JSON.parseObject(JSON.toJSONString(error));
            }
        }
        T t = data.toJavaObject(request.getResponseClass());
        t.setBody(data.toJSONString());
        t.setCode(jsonObject.getString(openConfig.getResponseCodeName()));
        t.setMsg(jsonObject.getString(openConfig.getResponseMsgName()));
        return t;
    }

    /**
     * 构建业务json内容。
     *
     * @param body 返回内容
     * @return 返回业务json
     */
    protected String buildBizJson(String body) {
        if (body == null) {
            return null;
        }
        JSONObject finalData = JSON.parseObject(body);
        finalData.remove(ParamNames.SIGN_NAME);
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(finalData.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = SignConfig.wrapVal(finalData.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }


    /**
     * 校验返回结果中的sign
     *
     * @param signContent       校验内容
     * @param sign              sign
     * @param publicKeyPlatform 平台公钥
     * @return true：正确
     */
    protected boolean checkResponseSign(String signContent, String sign, String publicKeyPlatform) {
        try {
            String charset = this.openConfig.getCharset();
            String signType = this.openConfig.getSignType();
            return SopSignature.rsaCheck(signContent, sign, publicKeyPlatform, charset, signType);
        } catch (SopSignException e) {
            log.error("验证服务端sign出错，signContent：" + signContent, e);
            return false;
        }
    }


}
