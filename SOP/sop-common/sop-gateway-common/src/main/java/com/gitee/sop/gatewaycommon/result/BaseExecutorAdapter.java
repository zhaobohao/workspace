package com.gitee.sop.gatewaycommon.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.bean.*;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.message.ErrorMeta;
import com.gitee.sop.gatewaycommon.param.PabParameterFormatter;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.secret.IsvManager;
import com.gitee.sop.gatewaycommon.validate.SignConfig;
import com.gitee.sop.gatewaycommon.validate.alipay.AlipayConstants;
import com.gitee.sop.gatewaycommon.validate.alipay.AlipaySignature;
import com.gitee.sop.gatewaycommon.validate.pab.PabSignature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author tanghc
 */
@Slf4j
public abstract class BaseExecutorAdapter<T, R> implements ResultExecutor<T, R> {
    private static final ErrorMeta SUCCESS_META = ErrorEnum.SUCCESS.getErrorMeta();
    private static final ErrorMeta ISP_UNKNOW_ERROR_META = ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta();
    private static final ErrorMeta ISP_BIZ_ERROR = ErrorEnum.BIZ_ERROR.getErrorMeta();
    private static final ErrorMeta ISV_MISSING_METHOD_META = ErrorEnum.ISV_MISSING_METHOD.getErrorMeta();

    private static final String GATEWAY_CODE_NAME = "code";
    private static final String GATEWAY_MSG_NAME = "msg";
    private static final String ARRAY_START = "[";
    private static final String ARRAY_END = "]";
    private static final String ROOT_JSON = "{'items':%s}".replace("'", "\"");
    private static final String ERROR_METHOD = "error";


    /**
     * 获取业务方约定的返回码
     *
     * @param t request
     * @return 返回返回码
     */
    public abstract int getResponseStatus(T t);

    /**
     * 获取微服务端的错误信息
     *
     * @param t request
     * @return 返回错误信息
     */
    public abstract String getResponseErrorMessage(T t);

    /**
     * 返回Api参数
     *
     * @param t request
     * @return 返回api参数
     */
    public abstract Map<String, Object> getApiParam(T t);

    @Override
    public String mergeResult(T request, String serviceResult) {
        boolean isMergeResult = this.isMergeResult(request);
        if (!isMergeResult) {
            return serviceResult;
        }
        serviceResult = wrapResult(serviceResult);
        int responseStatus = this.getResponseStatus(request);
        JSONObject responseData;
        if (responseStatus == HttpStatus.OK.value()) {
            // 200正常返回
            responseData = JSON.parseObject(serviceResult);
            responseData.put(GATEWAY_CODE_NAME, SUCCESS_META.getCode());
            responseData.put(GATEWAY_MSG_NAME, SUCCESS_META.getError().getMsg());
        } else if (responseStatus == SopConstants.BIZ_ERROR_STATUS) {
            // 如果是业务出错
            this.storeError(request, ErrorType.BIZ);
            responseData = JSON.parseObject(serviceResult);
            responseData.put(GATEWAY_CODE_NAME, ISP_BIZ_ERROR.getCode());
            responseData.put(GATEWAY_MSG_NAME, ISP_BIZ_ERROR.getError().getMsg());
        } else if (responseStatus == HttpStatus.NOT_FOUND.value()) {
            responseData = JSON.parseObject(serviceResult);
            responseData.put(GATEWAY_CODE_NAME, ISV_MISSING_METHOD_META.getCode());
            responseData.put(GATEWAY_MSG_NAME, ISV_MISSING_METHOD_META.getError().getCode());
        } else {
            Map<String, Object> params = this.getApiParam(request);
            log.error("微服务端报错，params:{}, 微服务返回结果:{}", params, serviceResult);
            this.storeError(request, ErrorType.UNKNOWN);
            // 微服务端有可能返回500错误
            // {"path":"/book/getBook3","error":"Internal Server Error","message":"id不能为空","timestamp":"2019-02-13T07:41:00.495+0000","status":500}
            responseData = new JSONObject();
            responseData.put(GATEWAY_CODE_NAME, ISP_UNKNOW_ERROR_META.getCode());
            responseData.put(GATEWAY_MSG_NAME, ISP_UNKNOW_ERROR_META.getError().getMsg());
        }
        return this.merge(request, responseData);
    }

    /**
     * 保存错误信息
     *
     * @param request request
     */
    protected void storeError(T request, ErrorType errorType) {
        ApiInfo apiInfo = this.getApiInfo(request);
        String errorMsg = this.getResponseErrorMessage(request);
        ErrorDefinition errorDefinition = new ErrorDefinition();
        BeanUtils.copyProperties(apiInfo, errorDefinition);
        errorDefinition.setErrorMsg(errorMsg);
        if (errorType == ErrorType.UNKNOWN) {
            ApiConfig.getInstance().getServiceErrorManager().saveUnknownError(errorDefinition);
        }
        if (errorType == ErrorType.BIZ) {
            ApiConfig.getInstance().getServiceErrorManager().saveBizError(errorDefinition);
        }
    }


    /**
     * 该路由是否合并结果
     *
     * @param request request
     * @return true：需要合并
     */
    protected boolean isMergeResult(T request) {
        // 默认全局设置
        Boolean defaultSetting = ApiContext.getApiConfig().getMergeResult();
        if (defaultSetting != null) {
            return defaultSetting;
        }
        ApiInfo apiInfo = this.getApiInfo(request);
        RouteDefinition baseRouteDefinition = apiInfo.gatewayRouteDefinition;
        return Optional.ofNullable(baseRouteDefinition)
                .map(routeDefinition -> {
                    int mergeResult = baseRouteDefinition.getMergeResult();
                    return BooleanUtils.toBoolean(mergeResult);
                })
                .orElse(true);
    }

    protected ApiInfo getApiInfo(T request) {
        Map<String, Object> params = this.getApiParam(request);
        String name = this.getParamValue(params, ParamNames.API_NAME, SopConstants.UNKNOWN_METHOD);
        String version = this.getParamValue(params, ParamNames.VERSION_NAME, SopConstants.UNKNOWN_VERSION);

        TargetRoute targetRoute = RouteRepositoryContext.getRouteRepository().get(name + version);

        String serviceId = Optional.ofNullable(targetRoute)
                .flatMap(route -> Optional.ofNullable(route.getServiceRouteInfo()))
                .map(ServiceRouteInfo::getServiceId)
                .orElse(SopConstants.UNKNOWN_SERVICE);

        RouteDefinition baseRouteDefinition = Optional.ofNullable(targetRoute)
                .map(TargetRoute::getRouteDefinition)
                .orElse(null);

        ApiInfo apiInfo = new ApiInfo();
        apiInfo.name = name;
        apiInfo.version = version;
        apiInfo.serviceId = serviceId;
        apiInfo.gatewayRouteDefinition = baseRouteDefinition;
        return apiInfo;
    }

    protected String wrapResult(String serviceResult) {
        if (serviceResult == null) {
            serviceResult = "";
        }
        serviceResult = serviceResult.trim();
        if (StringUtils.isEmpty(serviceResult)) {
            return SopConstants.EMPTY_JSON;
        }
        if (serviceResult.startsWith(ARRAY_START) && serviceResult.endsWith(ARRAY_END)) {
            return String.format(ROOT_JSON, serviceResult);
        }
        return serviceResult;
    }

    public String merge(T exchange, JSONObject responseData) {
        JSONObject finalData = new JSONObject(true);
        Map<String, Object> params = this.getApiParam(exchange);
        String name = this.getParamValue(params, ParamNames.API_NAME, ERROR_METHOD);
        ApiConfig apiConfig = ApiConfig.getInstance();
        // 点换成下划线
        DataNameBuilder dataNameBuilder = apiConfig.getDataNameBuilder();
        // alipay_goods_get_response
        String responseDataNodeName = dataNameBuilder.build(name);
        //TODO:外送的包体结构
        finalData.put(GATEWAY_CODE_NAME, responseData.getString(GATEWAY_CODE_NAME));
        finalData.put(GATEWAY_MSG_NAME, responseData.getString(GATEWAY_MSG_NAME));
        responseData.remove(GATEWAY_CODE_NAME);
        responseData.remove(GATEWAY_MSG_NAME);
        finalData.put(responseDataNodeName, responseData);
        ResultAppender resultAppender = apiConfig.getResultAppender();
        // 追加额外的结果
        if (resultAppender != null) {
            resultAppender.append(finalData, params, exchange);
        }
        // 添加服务端sign
        if (apiConfig.isShowReturnSign() && !CollectionUtils.isEmpty(params)) {
            // 添加try...catch，生成sign出错不影响结果正常返回
            try {
                //如果有pab的ParameterFormatter,则加密data对象。
                TargetRoute targetRoute = RouteRepositoryContext.getRouteRepository().get((String) params.get(ParamNames.API_NAME) + params.get(ParamNames.VERSION_NAME));
                if (ApiContext.getApiConfig().getParameterFormatter() instanceof PabParameterFormatter && targetRoute.getRouteDefinition().getIgnoreValidate() == 0) {
                    //当不忽略签名的时候，加密
                    IsvManager isvManager = apiConfig.getIsvManager();
                    // 根据appId获取秘钥
                    String appKey = this.getParamValue(params, ParamNames.APP_KEY_NAME, "");
                    if (StringUtils.isEmpty(appKey)) {
                        return null;
                    }
                    Isv isvInfo = isvManager.getIsv(appKey);
                    String publicKey = isvInfo.getSecretInfo();
                    String charset = Optional.ofNullable(params.get(ParamNames.CHARSET_NAME))
                            .map(String::valueOf)
                            .orElse(SopConstants.UTF8);
                    //加密后设置到对象里
                    finalData.put(responseDataNodeName, PabSignature.rsaEncrypt(finalData.getString(responseDataNodeName), publicKey, charset));
                }
                //如果接口是忽略验证的话，不要加签
                if (targetRoute.getRouteDefinition().getIgnoreValidate() == 0) {

                    String signType = getParamValue(params, ParamNames.SIGN_TYPE_NAME, AlipayConstants.SIGN_TYPE_RSA2);
                    finalData.put(ParamNames.SIGN_TYPE_NAME, signType);
                    String responseSignContent = this.buildResponseSignContent(finalData);
                    String sign = this.createResponseSign(apiConfig, params, responseSignContent);
                    if (StringUtils.hasLength(sign)) {

                        finalData.put(ParamNames.RESPONSE_SIGN_NAME, sign);
                    }

                }
            } catch (Exception e) {
                log.error("生成平台签名失败, params: {}, serviceResult:{}", JSON.toJSONString(params), responseData, e);
            }
        }
        return finalData.toJSONString();
    }

    /**
     * 获取待签名内容
     *
     * @param params 最终结果
     * @return 返回待签名内容
     */
    protected String buildResponseSignContent(JSONObject params) {
        if (params == null) {
            return null;
        }
        params.remove(ParamNames.SIGN_NAME);
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = SignConfig.wrapVal(params.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    protected String getParamValue(Map<String, Object> apiParam, String key, String defaultValue) {
        return CollectionUtils.isEmpty(apiParam) ? defaultValue : (String) apiParam.getOrDefault(key, defaultValue);
    }


    /**
     * 这里需要使用平台的私钥生成一个sign，需要配置两套公私钥。
     *
     * @param apiConfig           配置
     * @param params              请求参数
     * @param responseSignContent 待签名内容
     * @return 返回平台生成的签名
     */
    protected String createResponseSign(ApiConfig apiConfig, Map<String, Object> params, String responseSignContent) {
        if (StringUtils.isEmpty(responseSignContent)) {
            return null;
        }
        IsvManager isvManager = apiConfig.getIsvManager();
        // 根据appId获取秘钥
        String appKey = this.getParamValue(params, ParamNames.APP_KEY_NAME, "");
        if (StringUtils.isEmpty(appKey)) {
            return null;
        }
        Isv isvInfo = isvManager.getIsv(appKey);
        String privateKeyPlatform = isvInfo==null?null: isvInfo.getPrivateKeyPlatform();
        if (StringUtils.isEmpty(privateKeyPlatform)) {
            return null;
        }
        String charset = Optional.ofNullable(params.get(ParamNames.CHARSET_NAME))
                .map(String::valueOf)
                .orElse(SopConstants.UTF8);
        String signType = getParamValue(params, ParamNames.SIGN_TYPE_NAME, AlipayConstants.SIGN_TYPE_RSA2);
        return AlipaySignature.rsaSign(responseSignContent, privateKeyPlatform, charset, signType);
    }

    enum ErrorType {
        /**
         * 未知错误
         */
        UNKNOWN,
        /**
         * 业务错误
         */
        BIZ
    }

    @Getter
    @Setter
    protected static class ApiInfo {
        private String name;
        private String version;
        private String serviceId;
        private RouteDefinition gatewayRouteDefinition;
    }
}
