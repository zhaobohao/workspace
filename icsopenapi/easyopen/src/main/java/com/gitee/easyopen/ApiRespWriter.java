package com.gitee.easyopen;

import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.message.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责结果输出
 * 
 * @author tanghc
 *
 */
public class ApiRespWriter implements RespWriter {

    private static Logger logger = LoggerFactory.getLogger(ApiRespWriter.class);

    private static Map<String, String> contentTypeMap = new HashMap<>();
    
    static {
        contentTypeMap.put(Consts.FORMAT_JSON.toLowerCase(), MediaType.APPLICATION_JSON_UTF8_VALUE);
        contentTypeMap.put(Consts.FORMAT_XML.toLowerCase(), MediaType.APPLICATION_XML_VALUE);
    }

    private static String getContentType(String format) {
        return contentTypeMap.get(format.toLowerCase());
    }

    @Override
    public void write(HttpServletRequest request, HttpServletResponse response, Object result) {
        if (result == null) {
            return;
        }
        ApiConfig apiConfig = ApiContext.getApiConfig();
        ApiParam param = ApiContext.getApiParam();
        String format = param == null ? Consts.FORMAT_JSON : param.fatchFormat();
        String returnText = "";

        // json格式输出
        if (Consts.FORMAT_JSON.equalsIgnoreCase(format)) {
            returnText = apiConfig.getJsonResultSerializer().serialize(result);
        } else if (Consts.FORMAT_XML.equalsIgnoreCase(format)) {
            // xml格式输出
            returnText = apiConfig.getXmlResultSerializer().serialize(result);
        } else {
            throw Errors.NO_FORMATTER.getException(format);
        }

        boolean isEncryptMode = ApiContext.isEncryptMode() || ApiContext.hasUseNewSSL(request);
        // 如果是加密模式,对结果加密
        if(isEncryptMode) {
            String randomKey = ApiContext.getRandomKey();
            if (randomKey != null) {
                try {
                    String text = apiConfig.getEncrypter().aesEncryptToBase64String(returnText, randomKey);
                    this.writeText(response, text);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    this.writeText(response, e.getMessage());
                }
            } else {
                throw Errors.ERROR_SSL.getException();
            }
        } else {
            String contentType = getContentType(format);
            doWriter(response, contentType, returnText);
        }
    }

    public void writeText(HttpServletResponse response, String text) {
        doWriter(response, MediaType.TEXT_PLAIN_VALUE, text);
    }

    /**
     * 发送内容
     * 
     * @param response
     * @param contentType
     * @param text
     */
    public static void doWriter(HttpServletResponse response, String contentType, String text) {
        response.setContentType(contentType);
        response.setCharacterEncoding(Consts.UTF8);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            logger.error("doWriter", e);
        }
    }

}
