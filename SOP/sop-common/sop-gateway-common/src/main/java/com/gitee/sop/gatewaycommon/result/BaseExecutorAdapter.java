package com.gitee.sop.gatewaycommon.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.bean.*;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.message.ErrorMeta;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.PabParameterFormatter;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.secret.IsvManager;
import com.gitee.sop.gatewaycommon.util.RouteInterceptorUtil;
import com.gitee.sop.gatewaycommon.validate.SignConfig;
import com.gitee.sop.gatewaycommon.validate.alipay.AlipaySignature;
import com.gitee.sop.gatewaycommon.validate.pab.PabSignature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 处理微服务返回结果
 *
 * @author tanghc
 */

/**
 * 处理微服务返回结果
 *
 * @author tanghc
 */
@Slf4j
public abstract class BaseExecutorAdapter<T, R> implements ResultExecutor<T, R> {

    private static Map<Integer, ErrorEnum> HTTP_STATUS_ERROR_ENUM_MAP = new HashMap<>(8);

    private static final String GATEWAY_CODE_NAME = "code";
    private static final String GATEWAY_MSG_NAME = "msg";
    private static final String ARRAY_START = "[";
    private static final String ARRAY_END = "]";
    private static final String ROOT_JSON = "{'items':%s}".replace("'", "\"");

    static {
        HTTP_STATUS_ERROR_ENUM_MAP.put(HttpStatus.OK.value(), ErrorEnum.SUCCESS);
        HTTP_STATUS_ERROR_ENUM_MAP.put(SopConstants.BIZ_ERROR_STATUS, ErrorEnum.BIZ_ERROR);
        HTTP_STATUS_ERROR_ENUM_MAP.put(HttpStatus.NOT_FOUND.value(), ErrorEnum.ISV_INVALID_METHOD);
    }


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
    public abstract ApiParam getApiParam(T t);

    /**
     * 获取locale
     *
     * @param t request
     * @return 返回locale
     */
    protected abstract Locale getLocale(T t);

    /**
     * 返回拦截器上下文
     *
     * @param t request
     * @return 返回拦截器上下文
     */
    protected abstract RouteInterceptorContext getRouteInterceptorContext(T t);

    @Override
    public String mergeResult(T request, String serviceResult) {
        serviceResult = formatResult(serviceResult);
        boolean isMergeResult = this.isMergeResult(request);
        int responseStatus = this.getResponseStatus(request);
        this.doAfterRoute(serviceResult, responseStatus, request);
        String finalResult;
        if (isMergeResult) {
            JSONObject responseData = this.parseServiceResult(serviceResult, responseStatus, request);
            finalResult = this.merge(request, responseData);
        } else {
            finalResult = serviceResult;
        }
        return finalResult;
    }

    /**
     * 执行拦截器after操作
     *
     * @param serviceResult  微服务结果
     * @param responseStatus 微服务状态码
     * @param requestContext 微服务状态码
     */
    private void doAfterRoute(String serviceResult, int responseStatus, T requestContext) {
        RouteInterceptorContext routeInterceptorContext = getRouteInterceptorContext(requestContext);
        if (routeInterceptorContext instanceof DefaultRouteInterceptorContext) {
            DefaultRouteInterceptorContext defaultRouteInterceptorContext = (DefaultRouteInterceptorContext) routeInterceptorContext;
            defaultRouteInterceptorContext.setResponseStatus(responseStatus);
            defaultRouteInterceptorContext.setServiceResult(serviceResult);
            defaultRouteInterceptorContext.setFinishTimeMillis(System.currentTimeMillis());
            defaultRouteInterceptorContext.setResponseDataSize(serviceResult.length());
            if (responseStatus != HttpStatus.OK.value() && responseStatus != SopConstants.BIZ_ERROR_STATUS) {
                String responseErrorMessage = getResponseErrorMessage(requestContext);
                if (StringUtils.isEmpty(responseErrorMessage)) {
                    responseErrorMessage = serviceResult;
                }
                defaultRouteInterceptorContext.setServiceErrorMsg(responseErrorMessage);
            }
        }
        RouteInterceptorUtil.runAfterRoute(routeInterceptorContext);
    }

    /**
     * 将微服务的返回结果解析成JSONObject
     *
     * @param serviceResult  微服务返回结果
     * @param responseStatus 返回状态
     * @param request        请求
     * @return 返回JSONObject
     */
    protected JSONObject parseServiceResult(String serviceResult, int responseStatus, T request) {
        ErrorEnum errorEnum = HTTP_STATUS_ERROR_ENUM_MAP.get(responseStatus);
        if (errorEnum == null) {
            // 其它异常不应该把异常信息告诉给客户端，将微服务内容设置成空的json
            serviceResult = SopConstants.EMPTY_JSON;
            errorEnum = ErrorEnum.ISP_UNKNOWN_ERROR;
        }
        ErrorMeta errorMeta = errorEnum.getErrorMeta();
        JSONObject responseData = JSON.parseObject(serviceResult);
        responseData.put(GATEWAY_CODE_NAME, errorMeta.getCode());
        responseData.put(GATEWAY_MSG_NAME, errorMeta.getError(getLocale(request)).getMsg());
        return responseData;
    }


    /**
     * 该路由是否合并结果
     *
     * @param request request
     * @return true：需要合并
     */
    protected boolean isMergeResult(T request) {
        ApiParam params = this.getApiParam(request);
        return params.fetchMergeResult();
    }

    protected String formatResult(String serviceResult) {
        if (StringUtils.isBlank(serviceResult)) {
            return SopConstants.EMPTY_JSON;
        }
        // 如果直接返回数组，需要进行包装，变成：{"items": [...]}
        if (serviceResult.startsWith(ARRAY_START) && serviceResult.endsWith(ARRAY_END)) {
            return String.format(ROOT_JSON, serviceResult);
        }
        return serviceResult;
    }

    public String merge(T exchange, JSONObject responseData) {
        JSONObject finalData = new JSONObject(true);
        ApiParam params = this.getApiParam(exchange);
        ApiConfig apiConfig = ApiConfig.getInstance();
        // 点换成下划线
        DataNameBuilder dataNameBuilder = apiConfig.getDataNameBuilder();
        // alipay_goods_get_response
        String responseDataNodeName = dataNameBuilder.build(params.fetchName());
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
        this.addResponseSign(apiConfig, params, finalData, responseDataNodeName);
        return finalData.toJSONString();
    }

    private void addResponseSign(ApiConfig apiConfig, ApiParam params, JSONObject finalData, String responseDataNodeName) {
        if (apiConfig.isShowReturnSign() && !CollectionUtils.isEmpty(params)) {
            // 添加try...catch，生成sign出错不影响结果正常返回
            try {
                //如果有pab的ParameterFormatter,则加密data对象。
                TargetRoute targetRoute = RouteRepositoryContext.getRouteRepository().get((String) params.get(ParamNames.API_NAME) + params.get(ParamNames.VERSION_NAME));
                if (ApiContext.getApiConfig().getParameterFormatter() instanceof PabParameterFormatter && targetRoute.getRouteDefinition().getIgnoreValidate() == 0) {
                    //当不忽略签名的时候，加密
                    IsvManager isvManager = apiConfig.getIsvManager();
                    // 根据appId获取秘钥
                    String appKey = params.fetchAppKey();
                    if (StringUtils.isEmpty(appKey)) {
                        log.info("appKey is null ,exit;");
                        return;
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

                    String signType = params.fetchSignMethod();
                    finalData.put(ParamNames.SIGN_TYPE_NAME, signType);
                    String responseSignContent = this.buildResponseSignContent(finalData);
                    String sign = this.createResponseSign(apiConfig.getIsvManager(), params, responseSignContent);
                    if (StringUtils.isNotBlank(sign)) {
                        finalData.put(ParamNames.RESPONSE_SIGN_NAME, sign);
                    }

                }
            } catch (Exception e) {
                log.error("生成平台签名失败, params: {}", params.toJSONString(), e);
            }
        }
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

    /**
     * 这里需要使用平台的私钥生成一个sign，需要配置两套公私钥。
     *
     * @param isvManager          isvManager
     * @param params              请求参数
     * @param responseSignContent 待签名内容
     * @return 返回平台生成的签名
     */
    protected String createResponseSign(IsvManager isvManager, ApiParam params, String responseSignContent) {
        if (StringUtils.isEmpty(responseSignContent)) {
            return null;
        }
        // 根据appId获取秘钥
        String appKey = params.fetchAppKey();
        if (StringUtils.isEmpty(appKey)) {
            return null;
        }
        Isv isvInfo = isvManager.getIsv(appKey);
        String privateKeyPlatform = isvInfo == null ? null : isvInfo.getPrivateKeyPlatform();
        if (StringUtils.isEmpty(privateKeyPlatform)) {
            return null;
        }
        return AlipaySignature.rsaSign(
                responseSignContent
                , privateKeyPlatform
                , params.fetchCharset()
                , params.fetchSignMethod()
        );
    }

}