package com.gitee.sop.gatewaycommon.param;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 客户端传来的参数放在这里.
 *
 * @author tanghc
 */
public class ApiParam extends JSONObject implements Param {



    public ApiParam() {
    }

    public ApiParam(Map<String, Object> map) {
        super(map);
    }

    private boolean ignoreSign;
    private boolean ignoreValidate;

    private String restName;
    private String restVersion;

    private String serviceId;
    private String ip;
    private boolean restful;
    private boolean mergeResult = true;

    private boolean isGrayRequest;

    private transient UploadContext uploadContext;

    public static ApiParam createRestfulApiParam(String path) {
        ApiParam apiParam = new ApiParam();
        apiParam.setName(path);
        apiParam.setVersion("");
        apiParam.setRestful(true);
        apiParam.setMergeResult(false);
        return apiParam;
    }

    public void fitNameVersion() {
        if (restName != null) {
            this.put(ParamNames.API_NAME, restName);
        }
        if (restVersion != null) {
            this.put(ParamNames.VERSION_NAME, restVersion);
        }
    }

    public static ApiParam build(Map<String, ?> map) {
        ApiParam apiParam = new ApiParam();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            apiParam.put(entry.getKey(), entry.getValue());
        }
        return apiParam;
    }

    @JSONField(serialize = false)
    public String getRouteId() {
        return this.fetchNameVersion();
    }

    /**
     * 获取sign，并从param中删除
     *
     * @return 返回sign内容
     */
    public String fetchSignAndRemove() {
        String sign = this.fetchSign();
        this.remove(ParamNames.SIGN_NAME);
        return sign;
    }

    /**
     * 是否忽略验证签名
     *
     * @return 返回true，忽略签名
     */
    public boolean fetchIgnoreSign() {
        return ignoreSign;
    }

    public void setIgnoreSign(boolean ignoreSign) {
        this.ignoreSign = ignoreSign;
    }

    public boolean fetchIgnoreValidate() {
        return ignoreValidate;
    }

    public void setIgnoreValidate(boolean ignoreValidate) {
        this.ignoreValidate = ignoreValidate;
    }

    /**
     * 接口名,如:goods.list
     */
    @Override
    public String fetchName() {
        String name = getString(ParamNames.API_NAME);
        if (name == null) {
            name = this.restName;
        }
        return name;
    }

    public void setName(String name) {
        this.restName = name;
    }

    public String fetchNameVersion() {
        return buildNameVersion(this.fetchName(), this.fetchVersion());
    }

    public static String buildNameVersion(String name, String version) {
        if (name == null && version == null) {
            return null;
        }
        if (StringUtils.isEmpty(version)) {
            return name;
        } else {
            return name + version;
        }
    }

    /**
     * 版本号
     */
    @Override
    public String fetchVersion() {
        String version = getString(ParamNames.VERSION_NAME);
        if (version == null) {
            version = this.restVersion;
        }
        return version;
    }

    public void setVersion(String version) {
        this.restVersion = version;
    }

    /**
     * 接入应用ID
     */
    @Override
    public String fetchAppKey() {
        return getString(ParamNames.APP_KEY_NAME);
    }

    public void setAppKey(String appKey) {
        put(ParamNames.APP_KEY_NAME, appKey);
    }

    /**
     * 参数,urlencode后的
     */
    @Override
    public String fetchData() {
        return getString(ParamNames.BIZ_CONTENT_NAME);
    }

    public void setData(String json) {
        put(ParamNames.BIZ_CONTENT_NAME, json);
    }

    /**
     * 时间戳，格式为yyyy-MM-dd HH:mm:ss，例如：2015-01-01 12:00:00
     */
    @Override
    public String fetchTimestamp() {
        return getString(ParamNames.TIMESTAMP_NAME);
    }

    public void setTimestamp(String timestamp) {
        put(ParamNames.TIMESTAMP_NAME, timestamp);
    }

    /**
     * 签名串
     */
    @Override
    public String fetchSign() {
        return getString(ParamNames.SIGN_NAME);
    }

    public void setSign(String sign) {
        put(ParamNames.SIGN_NAME, sign);
    }

    @Override
    public String fetchFormat() {
        String format = getString(ParamNames.FORMAT_NAME);
        if (format == null || "".equals(format)) {
            return SopConstants.FORMAT_JSON;
        }
        return format;
    }

    public void setFormat(String format) {
        put(ParamNames.FORMAT_NAME, format);
    }

    @Override
    public String fetchAccessToken() {
        return getString(ParamNames.APP_AUTH_TOKEN_NAME);
    }

    @Override
    public String fetchSignMethod() {
        return getString(ParamNames.SIGN_TYPE_NAME);
    }

    @Override
    public String fetchCharset() {
        return getString(ParamNames.CHARSET_NAME);
    }

    public void setUploadContext(UploadContext uploadContext) {
        this.uploadContext = uploadContext;
    }

    public UploadContext fetchUploadContext() {
        return uploadContext;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public void setRestVersion(String restVersion) {
        this.restVersion = restVersion;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String fetchIp() {
        return ip;
    }

    public boolean fetchGrayRequest() {
        return isGrayRequest;
    }

    public void setGrayRequest(boolean grayRequest) {
        isGrayRequest = grayRequest;
    }

    public String fetchServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean fetchMergeResult() {
        return mergeResult;
    }

    public void setMergeResult(boolean mergeResult) {
        this.mergeResult = mergeResult;
    }

    public boolean fetchRestful() {
        return restful;
    }

    public void setRestful(boolean restful) {
        this.restful = restful;
    }
}
