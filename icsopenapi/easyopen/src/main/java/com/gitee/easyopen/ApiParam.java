package com.gitee.easyopen;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.bean.Consts;

/**
 * 客户端传来的参数放在这里.
 * 
 * @author tanghc
 *
 */
public class ApiParam extends JSONObject implements Param {
    private static final long serialVersionUID = 6718200590738465201L;

    public ApiParam(Map<String, Object> map) {
        super(map);
    }

    private boolean ignoreSign;
    private boolean ignoreValidate;
    
    private String restName;
    private String restVersion;
    
    /**
     * 获取sign，并从param中删除
     * @return 返回sign内容
     */
    public String fatchSignAndRemove() {
        String sign = this.fatchSign();
        this.remove(ParamNames.SIGN_NAME);
        return sign;
    }
    
    public HttpServletRequest fatchRequest() {
        return ApiContext.getRequest();
    }

    /**
     * 是否忽略验证签名
     * @return 返回true，忽略签名
     */
    public boolean fatchIgnoreSign() {
        return ignoreSign;
    }

    public void setIgnoreSign(boolean ignoreSign) {
        this.ignoreSign = ignoreSign;
    }
    
    public boolean fatchIgnoreValidate() {
        return ignoreValidate;
    }

    public void setIgnoreValidate(boolean ignoreValidate) {
        this.ignoreValidate = ignoreValidate;
    }

    /**
     * 接口名,如:goods.list
     */
    @Override
    public String fatchName() {
        String name = getString(ParamNames.API_NAME);
        if(name == null) {
        	name = this.restName;
        }
        return name;
    }

    public void setName(String name) {
        this.restName = name;
    }
    
    public String fatchNameVersion() {
        return buildNameVersion(this.fatchName(), this.fatchVersion());
    }
    
    public static String buildNameVersion(String name,String version) {
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
    public String fatchVersion() {
        String version = getString(ParamNames.VERSION_NAME);
        if(version == null) {
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
    public String fatchAppKey() {
        return getString(ParamNames.APP_KEY_NAME);
    }

    public void setAppKey(String appKey) {
        put(ParamNames.APP_KEY_NAME, appKey);
    }

    /**
     * 参数,urlencode后的
     */
    @Override
    public String fatchData() {
        return getString(ParamNames.DATA_NAME);
    }

    public void setData(String json) {
        put(ParamNames.DATA_NAME, json);
    }

    /**
     * 时间戳，格式为yyyy-MM-dd HH:mm:ss，例如：2015-01-01 12:00:00
     */
    @Override
    public String fatchTimestamp() {
        return getString(ParamNames.TIMESTAMP_NAME);
    }

    public void setTimestamp(String timestamp) {
        put(ParamNames.TIMESTAMP_NAME, timestamp);
    }

    /**
     * 签名串
     */
    @Override
    public String fatchSign() {
        return getString(ParamNames.SIGN_NAME);
    }

    public void setSign(String sign) {
        put(ParamNames.SIGN_NAME, sign);
    }

    @Override
    public String fatchFormat() {
        String format = getString(ParamNames.FORMAT_NAME);
        if (format == null || "".equals(format)) {
            return Consts.FORMAT_JSON;
        }
        return format;
    }

    public void setFormat(String format) {
        put(ParamNames.FORMAT_NAME, format);
    }

    @Override
    public String fatchAccessToken() {
        return getString(ParamNames.ACCESS_TOKEN_NAME);
    }

    @Override
    public String fatchSignMethod() {
        String signMethod = getString(ParamNames.SIGN_METHOD_NAME);
        if(signMethod == null) {
            return Consts.DEFAULT_SIGN_METHOD;
        }else{
            return signMethod;
        }
    }
    
    @Override
    public ApiParam clone() {
        ApiParam param = new ApiParam(this);
        param.ignoreSign = this.ignoreSign;
        param.ignoreValidate = this.ignoreValidate;
        return param;
    }
    
    
}
