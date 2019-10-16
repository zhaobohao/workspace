package com.gitee.sop.servercommon.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

import static com.gitee.sop.servercommon.bean.ParamNames.API_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.APP_AUTH_TOKEN_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.APP_KEY_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.BIZ_CONTENT_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.CHARSET_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.FORMAT_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.NOTIFY_URL_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.SIGN_TYPE_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.TIMESTAMP_NAME;
import static com.gitee.sop.servercommon.bean.ParamNames.TIMESTAMP_PATTERN;
import static com.gitee.sop.servercommon.bean.ParamNames.VERSION_NAME;

/**
 * @author tanghc
 */
public class OpenContextImpl<T> implements OpenContext<T> {
    private JSONObject rootJsonObject;
    private T bizObject;

    public OpenContextImpl(JSONObject rootJsonObject) {
        this(rootJsonObject, null);
    }

    public OpenContextImpl(JSONObject rootJsonObject, Class<?> bizClass) {
        if (rootJsonObject == null) {
            throw new IllegalArgumentException("rootJsonObject can not be null");
        }
        this.rootJsonObject = rootJsonObject;
        if (bizClass != null) {
            String bizContent = getBizContent();
            if (bizContent != null) {
                bizObject = (T) JSON.parseObject(bizContent, bizClass);
            }
        }
    }

    @Override
    public String getAppId() {
        return rootJsonObject.getString(APP_KEY_NAME);
    }

    @Override
    public T getBizObject() {
        return bizObject;
    }

    @Override
    public String getBizContent() {
        return rootJsonObject.getString(BIZ_CONTENT_NAME);
    }

    @Override
    public String getCharset() {
        return rootJsonObject.getString(CHARSET_NAME);
    }

    @Override
    public String getMethod() {
        return rootJsonObject.getString(API_NAME);
    }

    @Override
    public String getVersion() {
        return rootJsonObject.getString(VERSION_NAME);
    }

    @Override
    public String getFormat() {
        return rootJsonObject.getString(FORMAT_NAME);
    }

    @Override
    public String getSignType() {
        return rootJsonObject.getString(SIGN_TYPE_NAME);
    }

    @Override
    public Date getTimestamp() {
        String timestampStr = rootJsonObject.getString(TIMESTAMP_NAME);
        try {
            return DateUtils.parseDate(timestampStr, TIMESTAMP_PATTERN);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public String appAuthToken() {
        return rootJsonObject.getString(APP_AUTH_TOKEN_NAME);
    }

    @Override
    public String getNotifyUrl() {
        return rootJsonObject.getString(NOTIFY_URL_NAME);
    }

    @Override
    public <E> E getBizObject(Class<E> clazz) {
        if (bizObject != null && bizObject.getClass() == clazz) {
            return (E) bizObject;
        }
        String bizContent = getBizContent();
        if (bizContent == null) {
            return null;
        }
        return JSON.parseObject(bizContent, clazz);
    }

    @Override
    public String toString() {
        return rootJsonObject.toString();
    }
}
