package com.gitee.sop.gatewaycommon.validate;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.bean.Isv;
import com.gitee.sop.gatewaycommon.bean.RouteConfig;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.manager.IPBlacklistManager;
import com.gitee.sop.gatewaycommon.manager.IsvRoutePermissionManager;
import com.gitee.sop.gatewaycommon.manager.RouteConfigManager;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.param.UploadContext;
import com.gitee.sop.gatewaycommon.secret.IsvManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 负责校验,校验工作都在这里
 *
 * @author tanghc
 */
@Slf4j
@Getter
public class ApiValidator implements Validator {

    private static final int MILLISECOND_OF_ONE_SECOND = 1000;
    private static final int STATUS_FORBIDDEN = 2;

    private static List<String> FORMAT_LIST = Arrays.asList("json", "xml");

    @Autowired
    private IsvManager isvManager;

    @Autowired
    private IsvRoutePermissionManager isvRoutePermissionManager;

    @Autowired
    private IPBlacklistManager ipBlacklistManager;

    @Autowired
    private RouteConfigManager routeConfigManager;

    /**
     * 单个文件大小
     */
    @Value("${upload.max-file-size:10MB}")
    private String maxFileSize;


    @Override
    public void validate(ApiParam param) {
        checkIP(param);
        checkEnable(param);
        ApiConfig apiConfig = ApiContext.getApiConfig();
        if (apiConfig.isIgnoreValidate() || param.fetchIgnoreValidate()) {
            if (log.isDebugEnabled()) {
                log.debug("忽略所有验证(ignoreValidate=true), name:{}, version:{}", param.fetchName(), param.fetchVersion());
            }
            return;
        }
        // 需要验证签名,先校验appKey，后校验签名,顺序不能变
        checkAppKey(param);
        checkSign(param);
        checkTimeout(param);
        checkFormat(param);
        checkUploadFile(param);
        checkPermission(param);
    }

    /**
     * 是否在IP黑名单中
     *
     * @param param 接口参数
     */
    protected void checkIP(ApiParam param) {
        String ip = param.fetchIp();
        if (ipBlacklistManager.contains(ip)) {
            throw ErrorEnum.ISV_IP_FORBIDDEN.getErrorMeta().getException();
        }
    }

    /**
     * 检测能否访问
     *
     * @param param 接口参数
     */
    protected void checkEnable(ApiParam param) {
        String name = param.fetchName();
        if (name == null) {
            throw ErrorEnum.ISV_MISSING_METHOD.getErrorMeta().getException();
        }
        String version = param.fetchVersion();
        if (version == null) {
            throw ErrorEnum.ISV_MISSING_VERSION.getErrorMeta().getException();
        }
        String routeId = param.fetchNameVersion();
        // 检查路由是否存在
        RouteRepositoryContext.checkExist(routeId, ErrorEnum.ISV_INVALID_METHOD);
        // 检查路由是否启用
        RouteConfig routeConfig = routeConfigManager.get(routeId);
        if (!routeConfig.enable()) {
            throw ErrorEnum.ISP_API_DISABLED.getErrorMeta().getException();
        }
    }

    /**
     * 校验上传文件内容
     *
     * @param param
     */
    protected void checkUploadFile(ApiParam param) {
        UploadContext uploadContext = param.fetchUploadContext();
        if (uploadContext != null) {
            try {
                List<MultipartFile> files = uploadContext.getAllFile();
                for (MultipartFile file : files) {
                    checkSingleFileSize(file);
                    checkFileMd5(param, file);
                }
            } catch (IOException e) {
                log.error("验证上传文件MD5错误", e);
                throw ErrorEnum.ISV_UPLOAD_FAIL.getErrorMeta().getException();
            }
        }
    }

    private void checkFileMd5(ApiParam param, MultipartFile file) throws IOException {
        // 客户端传来的文件md5
        String clientMd5 = param.getString(file.getName());
        if (clientMd5 != null) {
            String fileMd5 = DigestUtils.md5Hex(file.getBytes());
            if (!clientMd5.equals(fileMd5)) {
                throw ErrorEnum.ISV_UPLOAD_FAIL.getErrorMeta().getException();
            }
        }
    }

    /**
     * 校验单个文件大小
     *
     * @param file 文件
     */
    private void checkSingleFileSize(MultipartFile file) {
        long fileSize = file.getSize();
        if (fileSize > DataSize.parse(maxFileSize).toBytes()) {
            throw ErrorEnum.ISV_INVALID_FILE_SIZE.getErrorMeta().getException(file.getName(), maxFileSize);
        }
    }

    protected void checkTimeout(ApiParam param) {
        int timeoutSeconds = ApiContext.getApiConfig().getTimeoutSeconds();
        // 如果设置为0，表示不校验
        if (timeoutSeconds == 0) {
            return;
        }
        if (timeoutSeconds < 0) {
            throw new IllegalArgumentException("服务端timeoutSeconds设置错误");
        }
        String requestTime = param.fetchTimestamp();
        try {
            Date requestDate = new SimpleDateFormat(ParamNames.TIMESTAMP_PATTERN).parse(requestTime);
            long requestMilliseconds = requestDate.getTime();
            if (System.currentTimeMillis() - requestMilliseconds > timeoutSeconds * MILLISECOND_OF_ONE_SECOND) {
                throw ErrorEnum.ISV_INVALID_TIMESTAMP.getErrorMeta().getException();
            }
        } catch (ParseException e) {
            throw ErrorEnum.ISV_INVALID_TIMESTAMP.getErrorMeta().getException(param.fetchNameVersion());
        }
    }

    protected void checkAppKey(ApiParam param) {
        if (StringUtils.isEmpty(param.fetchAppKey())) {
            throw ErrorEnum.ISV_MISSING_APP_ID.getErrorMeta().getException();
        }
        Isv isv = isvManager.getIsv(param.fetchAppKey());
        // 没有用户
        if (isv == null) {
            throw ErrorEnum.ISV_INVALID_APP_ID.getErrorMeta().getException();
        }
        // 禁止访问
        if (isv.getStatus() == null || isv.getStatus() == STATUS_FORBIDDEN) {
            throw ErrorEnum.ISV_ACCESS_FORBIDDEN.getErrorMeta().getException();
        }
    }

    protected void checkSign(ApiParam param) {
        String clientSign = param.fetchSign();
        try {
            if (StringUtils.isEmpty(clientSign)) {
                throw ErrorEnum.ISV_MISSING_SIGNATURE.getErrorMeta().getException(param.fetchNameVersion(), ParamNames.SIGN_NAME);
            }
            ApiConfig apiConfig = ApiContext.getApiConfig();
            // 根据appId获取秘钥
            Isv isvInfo = isvManager.getIsv(param.fetchAppKey());
            String secret = isvInfo.getSecretInfo();
            if (StringUtils.isEmpty(secret)) {
                throw ErrorEnum.ISV_MISSING_SIGNATURE_CONFIG.getErrorMeta().getException();
            }
            Signer signer = apiConfig.getSigner();
            // 错误的sign
            if (!signer.checkSign(param, secret)) {
                throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(param.fetchNameVersion());
            }
        } finally {
            // 校验过程中会移除sign，这里需要重新设置进去
            param.setSign(clientSign);
        }
    }


    protected void checkFormat(ApiParam param) {
        String format = param.fetchFormat();
        boolean contains = FORMAT_LIST.contains(format.toLowerCase());

        if (!contains) {
            throw ErrorEnum.ISV_INVALID_FORMAT.getErrorMeta().getException(param.fetchNameVersion(), format);
        }
    }

    /**
     * 校验访问权限
     *
     * @param apiParam
     */
    protected void checkPermission(ApiParam apiParam) {
        String routeId = apiParam.fetchNameVersion();
        TargetRoute targetRoute = RouteRepositoryContext.getRouteRepository().get(routeId);
        RouteDefinition routeDefinition = targetRoute.getRouteDefinition();
        boolean needCheckPermission = BooleanUtils.toBoolean(routeDefinition.getPermission());
        if (needCheckPermission) {
            String appKey = apiParam.fetchAppKey();
            boolean hasPermission = isvRoutePermissionManager.hasPermission(appKey, routeId);
            if (!hasPermission) {
                throw ErrorEnum.ISV_ROUTE_NO_PERMISSIONS.getErrorMeta().getException();
            }
        }
    }

}
