package com.gitee.easyopen;

import com.auth0.jwt.interfaces.Claim;
import com.gitee.easyopen.auth.Oauth2Manager;
import com.gitee.easyopen.auth.OpenUser;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.bean.RequestMode;
import com.gitee.easyopen.jwt.JwtService;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

/**
 * 应用上下文,方便获取信息
 * 
 * @author tanghc
 *
 */
public class ApiContext {
    private static Logger logger = LoggerFactory.getLogger(ApiContext.class);
    private static final String CLASS_NAME = ApiContext.class.getName();
    
    private static final String NEW_SSL_KEY = CLASS_NAME + "new_ssl_key";
    private static final String ATTR_PARAM = CLASS_NAME + "param";
    private static final String ATTR_API_META = CLASS_NAME + "apimeta";
    private static final String ATTR_JWT_DATA = CLASS_NAME + "jwtdata";
    private static final String ATTR_REQUEST_MODE = CLASS_NAME + "requestmode";
    private static final String ATTR_UPLOAD_CONTEXT = CLASS_NAME + "uploadcontext";

    private static ThreadLocal<HttpServletRequest> request = new InheritableThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> response = new InheritableThreadLocal<>();

    private static ApplicationContext applicationContext;
    private static ServletContext servletContext;

    private static ApiConfig apiConfig = new ApiConfig();

    private ApiContext(){}

    /** 获取应用名称 */
    public static String getAppName() {
        return apiConfig.getAppName();
    }

    private static void setAttr(String name, Object val) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(name, val);
        }
    }

    private static Object getAttr(String name) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        return request.getAttribute(name);
    }

    /**
     * 获取随机码
     * @return 返回随机码
     */
    public static String getRandomKey() {
        HttpSession session = getSession();
        if (session == null) {
            return null;
        }
        return (String) session.getAttribute(Consts.RANDOM_KEY_NAME);
    }

    public static void setRequestMode(RequestMode mode) {
        setAttr(ATTR_REQUEST_MODE, mode);
    }

    /**
     * AES解密
     * @param value 待解密的值
     * @return 返回原文
     */
    public static String decryptAES(String value) {
        String randomKey = getRandomKey();
        try {
            Encrypter encrypter = apiConfig.getEncrypter();
            return encrypter.aesDecryptFromHex(value, randomKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Errors.ERROR_SSL.getException();
        }
    }

    /**
     * AES解密
     * @param value 待解密的值
     * @return 返回原文
     */
    public static String decryptAESFromBase64String(String value) {
        if (value == null) {
            logger.error("aes value is null");
            throw Errors.ERROR_SSL.getException();
        }
        String randomKey = getRandomKey();
        try {
            if (randomKey == null) {
                throw new NullPointerException("randomKey is null");
            }
            Encrypter encrypter = apiConfig.getEncrypter();
            return encrypter.aesDecryptFromBase64String(value, randomKey);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Errors.ERROR_SSL.getException();
        }
    }

    /**
     * 是否加密模式
     * @return true，是加密模式
     */
    public static boolean isEncryptMode() {
        RequestMode mode = (RequestMode) getAttr(ATTR_REQUEST_MODE);
        if (mode == null) {
            return false;
        }
        return RequestMode.ENCRYPT == mode;
    }

    /**
     * 创建JWT
     * 
     * @param data
     * @return 返回jwt字符串
     */
    public static String createJwt(Map<String, String> data) {
        Assert.notNull(apiConfig, "apiConfig尚未初始化");
        JwtService jwtService = apiConfig.getJwtService();
        return jwtService.createJWT(data);
    }

    /**
     * 获取jwt数据
     * 
     * @return 返回jwt数据,没有返回null
     */
    public static Map<String, Claim> getJwtData() {
        return (Map<String, Claim>) getAttr(ATTR_JWT_DATA);
    }

    public static void setJwtData(Map<String, Claim> data) {
        setAttr(ATTR_JWT_DATA, data);
    }

    /**
     * 获取accessToken对应的用户
     * 
     * @return 没有返回null
     */
    public static OpenUser getAccessTokenUser() {
        String accessToken = getSessionId();
        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        Oauth2Manager manager = apiConfig.getOauth2Manager();
        return manager.getUserByAccessToken(accessToken);
    }

   

    /**
     * 获取HttpServletRequest
     * 
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest req = request.get();
        if (req == null) {
            RequestAttributes atri = RequestContextHolder.getRequestAttributes();
            if(atri != null) {
                req = ((ServletRequestAttributes) atri).getRequest();
            }
        }
        return req;
    }

    /**
     * 返回默认的HttpServletRequest.getSession();
     * 
     * @return 没有返回null
     */
    public static HttpSession getSession() {
        HttpServletRequest req = getRequest();
        if (req == null) {
            return null;
        } else {
            return req.getSession();
        }
    }
    
    /**
     * 获取session管理器
     * @return 返回SessionManager
     */
    public static SessionManager getSessionManager() {
        return apiConfig.getSessionManager();
    }

    /**
     * 返回自定义的session,被SessionManager管理
     * 
     * @return 如果sessionId为null，则返回null
     */
    public static HttpSession getManagedSession() {
        String sessionId = getSessionId();
        if (sessionId != null) {
            return getSessionManager().getSession(sessionId);
        } else {
            return null;
        }
    }

    /**
     * 同getSessionId()
     * @return 返回accessToken,没有返回null
     */
    public static String getAccessToken() {
        return getSessionId();
    }
    
    
    /**
     * 获取登陆的token
     * 
     * @return 没有返回null
     */
    public static String getSessionId() {
        ApiParam apiParam = getApiParam();
        if (apiParam == null) {
            return null;
        }
        return apiParam.fatchAccessToken();
    }

    /**
     * 设置request，保存在ThreadLocal中
     * @param req
     */
    public static void setRequest(HttpServletRequest req) {
        request.set(req);
    }

    /**
     * 获取本地化，从HttpServletRequest中获取，没有则返回Locale.SIMPLIFIED_CHINESE
     * 
     * @return Locale
     */
    public static Locale getLocal() {
        HttpServletRequest req = getRequest();
        if (req == null) {
            return Locale.SIMPLIFIED_CHINESE;
        }
        return req.getLocale();
    }

    public static void setApiParam(ApiParam apiParam) {
        setAttr(ATTR_PARAM, apiParam);
    }

    /**
     * 获取系统参数
     * 
     * @return 返回ApiParam
     */
    public static ApiParam getApiParam() {
        return (ApiParam) getAttr(ATTR_PARAM);
    }

    public static ApiConfig getApiConfig() {
        return apiConfig;
    }

    public static void setApiConfig(ApiConfig apiConfig) {
        ApiContext.apiConfig = apiConfig;
    }

    /**
     * 获取spring应用上下文
     * @return 返回spring应用上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApiContext.applicationContext = applicationContext;
        if (applicationContext instanceof ConfigurableWebApplicationContext) {
            servletContext = ((ConfigurableWebApplicationContext) applicationContext).getServletContext();
        }
    }

    public static ServletContext getServletContext() {
        if (servletContext != null) {
            return servletContext;
        } else {
            ServletContext ctx = null;
            HttpSession session = getSession();
            if (session != null) {
                ctx = session.getServletContext();
            }
            return ctx;
        }
    }
    
    /**
     * 获取上传文件，如果客户端有文件上传，从这里取。
     * @return 如果没有文件上传，返回null
     */
    public static UploadContext getUploadContext() {
        return (UploadContext) getAttr(ATTR_UPLOAD_CONTEXT);
    }

    public static void setUploadContext(UploadContext uploadCtx) {
        setAttr(ATTR_UPLOAD_CONTEXT, uploadCtx);
    }

    public static void useNewSSL(HttpServletRequest request) {
        request.setAttribute(NEW_SSL_KEY, true);
    }

    public static boolean hasUseNewSSL(HttpServletRequest request) {
        return request.getAttribute(NEW_SSL_KEY) != null;
    }

    /**
     * <strong>！！！禁止调用此方法！！！！do NOT use this method!</strong>
     * 清除数据，防止内存泄露。
     */
    public static void clean() {
        request.remove();
        response.remove();
    }

    /**
     * 设置response
     * @param resp response
     */
    public static void setResponse(HttpServletResponse resp) {
        response.set(resp);
    }

    /**
     * 获取response
     * @return 返回response
     */
    public static HttpServletResponse getResponse() {
        return response.get();
    }

    public static void setApiMeta(ApiMeta apiMeta) {
    	setAttr(ATTR_API_META, apiMeta);
    }
    
    /**
     * 获取ApiMeta
     * @return 返回ApiMeta
     */
    public static ApiMeta getApiMeta() {
    	return (ApiMeta)getAttr(ATTR_API_META);
    }

}
