package com.easyopen.sdk.request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.easyopen.sdk.common.RequestForm;
import com.easyopen.sdk.common.RequestMethod;
import com.easyopen.sdk.common.UploadFile;
import com.easyopen.sdk.common.SdkConfig;
import com.easyopen.sdk.response.BaseResponse;
import com.easyopen.sdk.util.ClassUtil;

/**
 * 请求对象父类，后续请求对象都要继承这个类
 * @param <T> 对应的Response对象
 */
public abstract class BaseRequest<T extends BaseResponse<?>> {

    private static String FORMAT_TYPE = SdkConfig.FORMAT_TYPE;
    private static String TIMESTAMP_PATTERN = SdkConfig.TIMESTAMP_PATTERN;
    private static final String DEFAULT_ACCESS_TOKEN = "";

    private String name;
    private String version;
    private Object data;
    private String timestamp = new SimpleDateFormat(TIMESTAMP_PATTERN).format(new Date());
    private String access_token = DEFAULT_ACCESS_TOKEN;
    private String format = FORMAT_TYPE;

    /** 上传文件 */
    @JSONField(serialize = false)
    private List<UploadFile> files;

    @JSONField(serialize = false)
    private RequestMethod requestMethod = RequestMethod.POST;

    @JSONField(serialize = false)
    private Class<T> responseClass;

    @JSONField(serialize = false)
    public abstract String name();

    @SuppressWarnings("unchecked")
    public BaseRequest() {
        this.name = this.name();
        this.version = this.version();

        this.responseClass = (Class<T>) ClassUtil.getSuperClassGenricType(this.getClass(), 0);
    }

    @JSONField(serialize = false)
    protected String version() {
        return SdkConfig.DEFAULT_VERSION;
    }

    public RequestForm createRequestForm() {
        String json = JSON.toJSONString(this);
        JSONObject map = JSON.parseObject(json);
        RequestForm requestForm = new RequestForm(map);
        requestForm.setFiles(this.files);
        return requestForm;
    }

    public void setParam(Object param) {
        this.setData(param);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getData() {
        return data;
    }

    /** 等同setParam() */
    public void setData(Object data) {
        this.data = data;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public List<UploadFile> getFiles() {
        return files;
    }

    /**
     * 添加上传文件
     * @param files
     */
    public void setFiles(List<UploadFile> files) {
        this.files = files;
    }
}
