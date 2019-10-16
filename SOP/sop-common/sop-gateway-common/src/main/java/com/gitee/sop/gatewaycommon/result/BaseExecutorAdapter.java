package com.gitee.sop.gatewaycommon.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.bean.ErrorDefinition;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.Isv;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.message.ErrorMeta;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.secret.IsvManager;
import com.gitee.sop.gatewaycommon.validate.alipay.AlipayConstants;
import com.gitee.sop.gatewaycommon.validate.alipay.AlipaySignature;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

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
        } else if(responseStatus == HttpStatus.NOT_FOUND.value()) {
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
                String responseSignContent = this.buildResponseSignContent(responseDataNodeName, finalData);
                String sign = this.createResponseSign(apiConfig, params, responseSignContent);
                if (StringUtils.hasLength(sign)) {
                    finalData.put(ParamNames.RESPONSE_SIGN_NAME, sign);
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
     * @param rootNodeName 业务数据节点
     * @param finalData    最终结果
     * @return 返回待签名内容
     */
    protected String buildResponseSignContent(String rootNodeName, JSONObject finalData) {
        String body = finalData.toJSONString();
        int indexOfRootNode = body.indexOf(rootNodeName);
        if (indexOfRootNode > 0) {
            int signDataStartIndex = indexOfRootNode + rootNodeName.length() + 2;
            int length = body.length() - 1;
            return body.substring(signDataStartIndex, length);
        }
        return null;
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
        String privateKeyPlatform = isvInfo.getPrivateKeyPlatform();
        if (StringUtils.isEmpty(privateKeyPlatform)) {
            return null;
        }
        String charset = Optional.ofNullable(params.get(ParamNames.CHARSET_NAME))
                .map(String::valueOf)
                .orElse(SopConstants.UTF8);
        String signType = getParamValue(params, ParamNames.SIGN_TYPE_NAME, AlipayConstants.SIGN_TYPE_RSA2);
        return AlipaySignature.rsaSign(responseSignContent, privateKeyPlatform, charset, signType);
    }

    @Getter
    @Setter
    protected static class ApiInfo {
        private String name;
        private String version;
        private String serviceId;
        private RouteDefinition gatewayRouteDefinition;
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
}
