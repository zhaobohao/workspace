package com.gitee.easyopen.doc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * 接口内容
 * 
 * @author tanghc
 *
 */
public class ApiDocItem implements Orderable {

    private String name;
    private String version;
    private String description;
    private String remark;
    private int order;

    private List<ApiDocFieldDefinition> paramDefinitions;
    private List<ApiDocFieldDefinition> resultDefinitions;
    /** 单值返回 */
    private ApiDocReturnDefinition apiDocReturnDefinition;

    private Object paramData;
    private Object resultData;

    private boolean customWrapper;

    public String getNameVersion() {
        return this.name + this.version;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ApiDocFieldDefinition> getParamDefinitions() {
        return paramDefinitions;
    }

    public void setParamDefinitions(List<ApiDocFieldDefinition> paramDefinitions) {
        this.paramDefinitions = paramDefinitions;
    }

    public List<ApiDocFieldDefinition> getResultDefinitions() {
        return resultDefinitions;
    }

    public void setResultDefinitions(List<ApiDocFieldDefinition> resultDefinitions) {
        this.resultDefinitions = resultDefinitions;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String getOrderName() {
        return this.getNameVersion();
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isCustomWrapper() {
        return customWrapper;
    }

    public void setCustomWrapper(boolean customWrapper) {
        this.customWrapper = customWrapper;
    }

    public ApiDocReturnDefinition getApiDocReturnDefinition() {
        return apiDocReturnDefinition;
    }

    public void setApiDocReturnDefinition(ApiDocReturnDefinition apiDocReturnDefinition) {
        this.apiDocReturnDefinition = apiDocReturnDefinition;
    }

    public boolean isSingleReturn() {
        return apiDocReturnDefinition != null;
    }

    public String getParamData() {
        return JSON.toJSONString(paramData, SerializerFeature.PrettyFormat);
    }

    public void setParamData(Object paramData) {
        this.paramData = paramData;
    }

    public String getResultData() {
        return JSON.toJSONString(resultData, SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero
        );
    }

    public Object fatchResultData() {
        return this.resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }
}
