package com.gitee.easyopen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 参数解析默认实现
 *
 * @author tanghc
 */
public class ApiParamParser implements ParamParser {

    private static final Logger logger = LoggerFactory.getLogger(ApiParamParser.class);

    private static final String CONTENT_TYPE_MULTIPART = MediaType.MULTIPART_FORM_DATA_VALUE;
    private static final String CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_VALUE;
    private static final String CONTENT_TYPE_TEXT = MediaType.TEXT_PLAIN_VALUE;

    public static final String UPLOAD_FORM_DATA_NAME = "body_data";

    private static String REQUEST_DATA_NAME = ParamNames.DATA_NAME;


    @Override
    public ApiParam parse(HttpServletRequest request) {
        String requestJson = null;
        try {
            requestJson = this.getJson(request);
        } catch (Exception e) {
            logger.error("parse error", e);
        }

        if (StringUtils.isEmpty(requestJson)) {
            throw Errors.ERROR_PARAM.getException();
        }

        if (ApiContext.hasUseNewSSL(request)) {
            requestJson = this.decryptData(requestJson);
        } else if (ApiContext.isEncryptMode()) {
            String randomKey = ApiContext.getRandomKey();
            if (randomKey == null) {
                logger.error("未找到randomKey");
                throw Errors.ERROR_SSL.getException();
            }
            requestJson = ApiContext.decryptAES(requestJson);
        }
        ApiParam param = this.jsonToApiParam(requestJson);
        this.bindRestParam(param, request);
        return param;
    }

    private String decryptData(String requestJson) {
        JSONObject result = JSON.parseObject(requestJson);
        String data = result.getString(REQUEST_DATA_NAME);
        try {
            // aes解密
            data = ApiContext.decryptAESFromBase64String(data);
            // 重新赋值
            result.put(REQUEST_DATA_NAME, data);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败");
        }
        return result.toJSONString();
    }

    public String getJson(HttpServletRequest request) throws Exception {
        String requestJson = null;

        if (RequestUtil.isGetRequest(request)) {
            Map<String, Object> params = RequestUtil.convertRequestParamsToMap(request);
            requestJson = JSON.toJSONString(params);
        } else {
            String contectType = request.getContentType();

            if (contectType == null) {
                contectType = "";
            }

            contectType = contectType.toLowerCase();

            // json或者纯文本形式
            if (contectType.contains(CONTENT_TYPE_JSON) || contectType.contains(CONTENT_TYPE_TEXT)) {
                requestJson = RequestUtil.getText(request);
            } else if (contectType.contains(CONTENT_TYPE_MULTIPART)) {
                // 上传文件形式
                requestJson = this.parseUploadRequest(request);
            } else {
                Map<String, Object> params = RequestUtil.convertRequestParamsToMap(request);
                requestJson = JSON.toJSONString(params);
            }
        }

        return requestJson;
    }

    /**
     * 解析文件上传请求
     *
     * @param request
     * @return 返回json字符串
     */
    protected String parseUploadRequest(HttpServletRequest request) {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
            Map<String, MultipartFile> finalMap = new HashMap<>(fileMap.size());

            Set<String> keys = fileMap.keySet();
            for (String name : keys) {
                MultipartFile file = fileMap.get(name);
                if (file.getSize() > 0) {
                    finalMap.put(name, file);
                }
            }

            if (finalMap.size() > 0) {
                // 保存上传文件
                ApiContext.setUploadContext(new ApiUploadContext(finalMap));
            }
        }

        String json = request.getParameter(UPLOAD_FORM_DATA_NAME);
        if (json != null) {
            return json;
        }

        Map<String, Object> params = RequestUtil.convertRequestParamsToMap(request);
        return JSON.toJSONString(params);
    }

    protected ApiParam jsonToApiParam(String json) {
        if (StringUtils.isEmpty(json)) {
            throw Errors.ERROR_PARAM.getException();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(json);
        } catch (Exception e) {
            throw Errors.ERROR_JSON_DATA.getException(e.getMessage());
        }

        return new ApiParam(jsonObject);
    }
    
    protected void bindRestParam(ApiParam param, HttpServletRequest request) {
    	String name = (String)request.getAttribute(Consts.REST_PARAM_NAME);
    	String version = (String)request.getAttribute(Consts.REST_PARAM_VERSION);
    	if(name != null) {
    		param.setName(name);
    	}
    	if(version != null) {
    		param.setVersion(version);
    	}
    }
}
