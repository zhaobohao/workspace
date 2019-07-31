package com.gitee.easyopen;

import com.gitee.easyopen.auth.Oauth2Manager;
import com.gitee.easyopen.auth.Oauth2Service;
import com.gitee.easyopen.auth.impl.Oauth2ServiceImpl;
import com.gitee.easyopen.bean.ApiDefinition;
import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.jwt.JwtService;
import com.gitee.easyopen.jwt.impl.JwtServiceImpl;
import com.gitee.easyopen.limit.LimitConfigManager;
import com.gitee.easyopen.limit.LimitManager;
import com.gitee.easyopen.limit.LimitType;
import com.gitee.easyopen.monitor.ApiMonitorStore;
import com.gitee.easyopen.monitor.MonitorInterceptor;
import com.gitee.easyopen.monitor.MonitorStore;
import com.gitee.easyopen.permission.PermissionManager;
import com.gitee.easyopen.doc.DocFileCreator;
import com.gitee.easyopen.serializer.JsonResultSerializer;
import com.gitee.easyopen.serializer.XmlResultSerializer;
import com.gitee.easyopen.session.ApiSessionManager;
import com.gitee.easyopen.session.SessionManager;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置类,所有配置相关都在这里.
 * 
 * @author tanghc
 *
 */
public class ApiConfig {
    /**
     * 加密工具
     */
    private Encrypter encrypter = new ApiEncrypter();
    /**
     * app秘钥管理
     */
    private AppSecretManager appSecretManager = new CacheAppSecretManager();
    /**
     * 返回结果
     */
    private ResultCreator resultCreator = new ApiResultCreator();
    /**
     * json序列化
     */
    private ResultSerializer jsonResultSerializer = new JsonResultSerializer();
    /**
     * xml序列化
     */
    private ResultSerializer xmlResultSerializer = new XmlResultSerializer();
    /**
     * 签名工具
     */
    private Signer signer = new ApiSigner();

    private RespWriter respWriter = new ApiRespWriter();
    /**
     * 参数解析
     */
    private ParamParser paramParser = new ApiParamParser();

    /**
     * session管理
     */
    private SessionManager sessionManager = new ApiSessionManager();
    
    /**
     * 拦截器
     */
    private ApiInterceptor[] interceptors = {};
    

    /**
     * oauth2服务端默认实现
     */
    private Oauth2Service oauth2Service = new Oauth2ServiceImpl();

    
    /**
     * 存储监控信息
     */
    private MonitorStore monitorStore = new ApiMonitorStore();
    
    /**
     * 错误模块
     */
    private List<String> isvModules = new ArrayList<String>();

    /**
     * data部分解码
     */
    private DataDecoder dataDecoder = new ApiDataDecoder();

    /**
     * api注册事件
     */
    private ApiRegistEvent apiRegistEvent = new ApiRegistEvent() {
        @Override
        public void onSuccess(ApiDefinition apiDefinition) {
        }
    };

    /* ************在构造方法中初始化************ */
    /**
     * 负责监控的拦截器
     */
    private ApiInterceptor monitorInerceptor = new MonitorInterceptor();
    
    /**
     * 校验接口
     */
    private Validator validator = new ApiValidator();
    
    /**
     * JWT处理 
     */
    private JwtService jwtService = new JwtServiceImpl();
    /**
     * 请求转发
     */
    private Invoker invoker = new ApiInvoker();
    /**
     * oauth2认证服务，需要自己实现
     */
    private Oauth2Manager oauth2Manager;
    
    /** 限流管理 */
    private LimitManager limitManager;
    /** 限流配置管理 */
    private LimitConfigManager limitConfigManager;
    
    /** 权限管理 */
    private PermissionManager permissionManager;

    private DocFileCreator docFileCreator;
    
    /* ***************************** */
    
    
    private String appName = "app";

    /**
     * 默认版本号
     */
    private String defaultVersion = "";
    
    /**
     * 超时时间
     */
    private int timeoutSeconds = 3;

    /**
     * 是否生成doc文档
     */
    private boolean showDoc;
    
    /**
     * 文档页面密码，默认为null，如果不为null，文档页面一定开启。
     */
    private String docPassword;
    
    /**
     * 文档模板路径
     */
    private String docClassPath = "/easyopen_template/doc.html";

    /**
     * 文档下载模板路径
     */
    private String docPdfClassPath = "/easyopen_template/docPdf.html";
    private String docPdfCssClassPath = "/easyopen_template/docPdf.css";
    
    /**
     * 监控模板路径
     */
    private String monitorClassPath = "/easyopen_template/monitor.html";
    
    /**
     * 登录页模板路径
     */
    private String loginClassPath = "/easyopen_template/login.html";
    
    /**
     * 限流 模板路径
     */
    private String limitClassPath = "/easyopen_template/limit.html";
    /**
     * 进入限流页面密码
     */
    private String limitPassword = "limit123";
    
    /** 默认限流策略 */
    private LimitType defaultLimitType = LimitType.LIMIT;

    /** 默认每秒可处理请求数*/
    private int defaultLimitCount = 50;

    /** 默认令牌桶个数 */
    private int defaultTokenBucketCount = 50;
    
    private static String CONFIG_FOLDER = System.getProperty("user.dir") +  File.separator + "local-config" + File.separator;

    /** 本地限流缓存全路径 */
    private String localLimitConfigFile;
    
    /** 本地权限缓存全路径 */
    private String localPermissionConfigFile;
    
    /** 本地秘钥缓存全路径 */
    private String localSecretConfigFile;
    

    private ConfigClient configClient;
    
    /**
     * 忽略验证
     */
    private boolean ignoreValidate;

    /**
     * 登录视图页面用于，mvc视图，如：loginView
     */
    private String oauth2LoginUri = "/oauth2login";

    /**
     * oauth2的accessToken过期时间,单位秒,默认2小时
     */
    private long oauth2ExpireIn = 7200;

    /**
     * jwt过期时间,秒,默认2小时
     */
    private int jwtExpireIn = 7200;
    /**
     * RSA加密对应的私钥
     */
    private String privateKey;

    /**
     * 私钥文件存放的classpath地址
     */
    private String priKeyPath = "/pri.key";
    
    /**
     * 是否开启监控
     */
    private boolean showMonitor = true;
    /**
     * 进入监控页面密码
     */
    private String monitorPassword = "monitor123";
    /**
     * 存放监控错误信息队列长度。超出长度，新值替换旧值
     */
    private int monitorErrorQueueSize = 5;
    /**
     * 处理线程池大小
     */
    private int monitorExecutorSize = 2;

    /**
     * 最外包装类class
     */
    private Class<? extends Result> wrapperClass;

    /**
     * jwt对应的secret
     */
    private String jwtSecret = "#56gu25@41tCVr]>0$";

    /** markdown文档保存目录 */
    private String markdownDocDir;
    
    public ApiConfig() {
        isvModules.add("i18n/isv/error");
    }

    public void setAppName(String appName) {
        this.appName = appName;
        if(localLimitConfigFile == null) {
            /** 本地限流缓存全路径 */
            localLimitConfigFile = System.getProperty("conflimit.file", CONFIG_FOLDER + appName + "-limit.json");
        }
        if(localPermissionConfigFile == null) {
            /** 本地权限缓存全路径 */
            localPermissionConfigFile = System.getProperty("confperm.file", CONFIG_FOLDER + appName + "-permission.json");
        }
        if(localSecretConfigFile == null) {
            /** 本地秘钥缓存全路径 */
            localSecretConfigFile = System.getProperty("confsecret.file", CONFIG_FOLDER + appName + "-secret.json");
        }
    }

    /**
     * 开启app对接模式，开启后不进行timeout校验。<br>
     * 如果平台直接跟Android或IOS对接，可开启这个功能。因为手机上的时间有可能跟服务端的时间不一致（用户的手机情况不可控）。<br>
     * 失去了时间校验，一个请求有可能被反复调用，服务端需要防止重复提交，有必要的话上HTTPS。
     */
    public void openAppMode() {
        this.timeoutSeconds = 0;
    }
    
    public Encrypter getEncrypter() {
        return encrypter;
    }

    public void setEncrypter(Encrypter encrypter) {
        this.encrypter = encrypter;
    }
    
    public void loadPrivateKey() {
        ClassPathResource res = new ClassPathResource(this.priKeyPath);
        if(res.exists()) {
            try {
                this.privateKey = IOUtils.toString(res.getInputStream(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initOauth2Service(Oauth2Manager oauth2Manager) {
        this.oauth2Manager = oauth2Manager;
        this.oauth2Service.setOauth2Manager(oauth2Manager);
    }
    
    public boolean isShowDoc() {
        return showDoc;
    }

    public void setShowDoc(boolean showDoc) {
        this.showDoc = showDoc;
    }
    
    public boolean isShowMonitor() {
        return showMonitor;
    }

    public void setShowMonitor(boolean showMonitor) {
        this.showMonitor = showMonitor;
    }

    /**
     * 添加秘钥配置，map中存放秘钥信息，key对应appKey，value对应secret
     * 
     * @param appSecretStore
     */
    public void addAppSecret(Map<String, String> appSecretStore) {
        this.appSecretManager.addAppSecret(appSecretStore);
    }

    public void setAppSecret(Map<String, String> appSecret) {
        this.addAppSecret(appSecret);
    }

    public AppSecretManager getAppSecretManager() {
        return appSecretManager;
    }

    public void setAppSecretManager(AppSecretManager appSecretManager) {
        this.appSecretManager = appSecretManager;
    }

    public ResultCreator getResultCreator() {
        return resultCreator;
    }

    public void setResultCreator(ResultCreator resultCreator) {
        this.resultCreator = resultCreator;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public ResultSerializer getJsonResultSerializer() {
        return jsonResultSerializer;
    }

    public void setJsonResultSerializer(ResultSerializer jsonResultSerializer) {
        this.jsonResultSerializer = jsonResultSerializer;
    }

    public ResultSerializer getXmlResultSerializer() {
        return xmlResultSerializer;
    }

    public void setXmlResultSerializer(ResultSerializer xmlResultSerializer) {
        this.xmlResultSerializer = xmlResultSerializer;
    }

    public List<String> getIsvModules() {
        return isvModules;
    }

    public void setIsvModules(List<String> isvModules) {
        this.isvModules = isvModules;
    }

    public boolean isIgnoreValidate() {
        return ignoreValidate;
    }

    public void setIgnoreValidate(boolean ignoreValidate) {
        this.ignoreValidate = ignoreValidate;
    }

    public RespWriter getRespWriter() {
        return respWriter;
    }

    public void setRespWriter(RespWriter respWriter) {
        this.respWriter = respWriter;
    }

    public String getOauth2LoginUri() {
        return oauth2LoginUri;
    }

    public void setOauth2LoginUri(String oauth2LoginUri) {
        this.oauth2LoginUri = oauth2LoginUri;
    }

    public long getOauth2ExpireIn() {
        return oauth2ExpireIn;
    }

    public void setOauth2ExpireIn(long oauth2ExpireIn) {
        this.oauth2ExpireIn = oauth2ExpireIn;
    }

    public Oauth2Service getOauth2Service() {
        return oauth2Service;
    }

    public void setOauth2Service(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    public Oauth2Manager getOauth2Manager() {
        return oauth2Manager;
    }

    public void setOauth2Manager(Oauth2Manager oauth2Manager) {
        this.oauth2Manager = oauth2Manager;
    }

    public int getJwtExpireIn() {
        return jwtExpireIn;
    }

    public void setJwtExpireIn(int jwtExpireIn) {
        this.jwtExpireIn = jwtExpireIn;
    }

    public JwtService getJwtService() {
        return jwtService;
    }

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public Signer getSigner() {
        return signer;
    }

    public void setSigner(Signer signer) {
        this.signer = signer;
    }

    public ApiInterceptor[] getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(ApiInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }
    
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    
    public String getDefaultVersion() {
        return defaultVersion;
    }

    public void setDefaultVersion(String defaultVersion) {
        this.defaultVersion = defaultVersion;
    }

    public ParamParser getParamParser() {
        return paramParser;
    }

    public void setParamParser(ParamParser paramParser) {
        this.paramParser = paramParser;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }
    
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    
    public String getDocClassPath() {
        return docClassPath;
    }

    public void setDocClassPath(String docClassPath) {
        this.docClassPath = docClassPath;
    }

    public MonitorStore getMonitorStore() {
        return monitorStore;
    }

    public void setMonitorStore(MonitorStore monitorStore) {
        this.monitorStore = monitorStore;
    }
    

    public ApiInterceptor getMonitorInerceptor() {
        return monitorInerceptor;
    }

    public void setMonitorInerceptor(ApiInterceptor monitorInerceptor) {
        this.monitorInerceptor = monitorInerceptor;
    }
    
    public String getMonitorClassPath() {
        return monitorClassPath;
    }

    public void setMonitorClassPath(String monitorClassPath) {
        this.monitorClassPath = monitorClassPath;
    }
    
    public int getMonitorErrorQueueSize() {
        return monitorErrorQueueSize;
    }

    public void setMonitorErrorQueueSize(int monitorErrorQueueSize) {
        this.monitorErrorQueueSize = monitorErrorQueueSize;
    }

    public int getMonitorExecutorSize() {
        return monitorExecutorSize;
    }

    public void setMonitorExecutorSize(int monitorExecutorSize) {
        this.monitorExecutorSize = monitorExecutorSize;
    }
    

    public String getMonitorPassword() {
        return monitorPassword;
    }

    public void setMonitorPassword(String monitorPassword) {
        this.monitorPassword = monitorPassword;
    }

    public String getLoginClassPath() {
        return loginClassPath;
    }

    public void setLoginClassPath(String loginClassPath) {
        this.loginClassPath = loginClassPath;
    }
    
    public String getDocPassword() {
        return docPassword;
    }

    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }
    

    public LimitManager getLimitManager() {
        return limitManager;
    }

    public void setLimitManager(LimitManager limitManager) {
        this.limitManager = limitManager;
        this.limitConfigManager = limitManager.getLimitConfigManager();
    }

    public LimitConfigManager getLimitConfigManager() {
        return limitConfigManager;
    }

    public void setLimitConfigManager(LimitConfigManager limitConfigManager) {
        this.limitConfigManager = limitConfigManager;
    }

    public String getLimitClassPath() {
        return limitClassPath;
    }

    public void setLimitClassPath(String limitClassPath) {
        this.limitClassPath = limitClassPath;
    }

    public String getLimitPassword() {
        return limitPassword;
    }

    public void setLimitPassword(String limitPassword) {
        this.limitPassword = limitPassword;
    }
    
    public LimitType getDefaultLimitType() {
        return defaultLimitType;
    }

    public void setDefaultLimitType(LimitType defaultLimitType) {
        this.defaultLimitType = defaultLimitType;
    }

    public int getDefaultLimitCount() {
        return defaultLimitCount;
    }

    public void setDefaultLimitCount(int defaultLimitCount) {
        this.defaultLimitCount = defaultLimitCount;
    }

    public int getDefaultTokenBucketCount() {
        return defaultTokenBucketCount;
    }

    public void setDefaultTokenBucketCount(int defaultTokenBucketCount) {
        this.defaultTokenBucketCount = defaultTokenBucketCount;
    }
    
    public String getAppName() {
        return appName;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public void setPermissionManager(PermissionManager permissionManager) {
        this.permissionManager = permissionManager;
    }

    public String getLocalLimitConfigFile() {
        return localLimitConfigFile;
    }

    public void setLocalLimitConfigFile(String localLimitConfigFile) {
        this.localLimitConfigFile = localLimitConfigFile;
    }

    public String getLocalPermissionConfigFile() {
        return localPermissionConfigFile;
    }

    public void setLocalPermissionConfigFile(String localPermissionConfigFile) {
        this.localPermissionConfigFile = localPermissionConfigFile;
    }
    
    public ConfigClient getConfigClient() {
        return configClient;
    }

    public void setConfigClient(ConfigClient configClient) {
        this.configClient = configClient;
    }
    
    public String getLocalSecretConfigFile() {
        return localSecretConfigFile;
    }

    public void setLocalSecretConfigFile(String localSecretConfigFile) {
        this.localSecretConfigFile = localSecretConfigFile;
    }

    public String getDocPdfClassPath() {
        return docPdfClassPath;
    }

    public void setDocPdfClassPath(String docPdfClassPath) {
        this.docPdfClassPath = docPdfClassPath;
    }

    public String getDocPdfCssClassPath() {
        return docPdfCssClassPath;
    }

    public void setDocPdfCssClassPath(String docPdfCssClassPath) {
        this.docPdfCssClassPath = docPdfCssClassPath;
    }

    public Class<? extends Result> getWrapperClass() {
        return wrapperClass;
    }

    public void setWrapperClass(Class<? extends Result> wrapperClass) {
        this.wrapperClass = wrapperClass;
    }

    public DataDecoder getDataDecoder() {
        return dataDecoder;
    }

    public void setDataDecoder(DataDecoder dataDecoder) {
        this.dataDecoder = dataDecoder;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public ApiRegistEvent getApiRegistEvent() {
        return apiRegistEvent;
    }

    public void setApiRegistEvent(ApiRegistEvent apiRegistEvent) {
        this.apiRegistEvent = apiRegistEvent;
    }

    public void setApiName(String apiName) {
        ParamNames.API_NAME = apiName;
    }

    public void setVersionName(String versionName) {
        ParamNames.VERSION_NAME = versionName;
    }

    public void setAppKeyName(String appKeyName) {
        ParamNames.APP_KEY_NAME = appKeyName;
    }

    public void setDataName(String dataName) {
        ParamNames.DATA_NAME = dataName;
    }

    public void setTimestampName(String timestampName) {
        ParamNames.TIMESTAMP_NAME = timestampName;
    }

    public void setSignName(String signName) {
        ParamNames.SIGN_NAME = signName;
    }

    public String getMarkdownDocDir() {
        return markdownDocDir;
    }

    public void setMarkdownDocDir(String markdownDocDir) {
        this.markdownDocDir = markdownDocDir;
    }

    public DocFileCreator getDocFileCreator() {
        return docFileCreator;
    }

    public void setDocFileCreator(DocFileCreator docFileCreator) {
        this.docFileCreator = docFileCreator;
    }
}
