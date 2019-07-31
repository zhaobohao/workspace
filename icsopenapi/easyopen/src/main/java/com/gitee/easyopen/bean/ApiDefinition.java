package com.gitee.easyopen.bean;

import com.gitee.easyopen.ApiMeta;

import java.lang.reflect.Method;

/**
 * 接口定义，负责存放定义的接口信息
 * @author tanghc
 */
public class ApiDefinition implements ApiMeta {
    /** 接口名 */
    private String name;
    /** 版本号 */
    private String version;
    /** 接口描述，如果定义了@ApiDocMethod注解会同步其description属性到这 */
    private String description;
    /** 模块名 */
    private String moduleName;
    /** 排序 */
    private int orderIndex;

    /** 接口对应的Service类 */
    private Object handler;
    /** 接口对应的方法 */
    private Method method;
    /** 方法参数的class */
    private Class<?> methodArguClass;

    private boolean ignoreSign;
    private boolean ignoreValidate;
    private boolean ignoreJWT;
    private boolean ignoreToken;
    private boolean wrapResult = Boolean.TRUE;

    private boolean singleParameter;

    private boolean noReturn;

    private MethodCaller methodCaller;

    public String getNameVersion() {
        return this.name + this.version;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public Class<?> getMethodArguClass() {
        return methodArguClass;
    }

    public void setMethodArguClass(Class<?> methodArguClass) {
        this.methodArguClass = methodArguClass;
    }

    @Override
    public boolean isIgnoreSign() {
        return ignoreSign;
    }

    public void setIgnoreSign(boolean ignoreSign) {
        this.ignoreSign = ignoreSign;
    }

    @Override
    public boolean isIgnoreValidate() {
        return ignoreValidate;
    }

    public void setIgnoreValidate(boolean ignoreValidate) {
        this.ignoreValidate = ignoreValidate;
    }

    @Override
    public boolean isWrapResult() {
        return wrapResult;
    }

    public void setWrapResult(boolean wrapResult) {
        this.wrapResult = wrapResult;
    }

    public boolean isSingleParameter() {
        return singleParameter;
    }

    public void setSingleParameter(boolean singleParameter) {
        this.singleParameter = singleParameter;
    }

    public boolean isNoReturn() {
        return noReturn;
    }

    public void setNoReturn(boolean noReturn) {
        this.noReturn = noReturn;
    }

    @Override
    public boolean noReturn() {
        return noReturn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public MethodCaller getMethodCaller() {
        return methodCaller;
    }

    public void setMethodCaller(MethodCaller methodCaller) {
        this.methodCaller = methodCaller;
    }

    @Override
    public boolean isIgnoreJWT() {
        return ignoreJWT;
    }

    public void setIgnoreJWT(boolean ignoreJWT) {
        this.ignoreJWT = ignoreJWT;
    }

    @Override
    public boolean isIgnoreToken() {
        return ignoreToken;
    }

    public void setIgnoreToken(boolean ignoreToken) {
        this.ignoreToken = ignoreToken;
    }
}
