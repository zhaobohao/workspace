package com.gitee.easyopen;

import com.gitee.easyopen.exception.BusinessParamException;
import com.gitee.easyopen.message.ErrorFactory;
import com.gitee.easyopen.message.Errors;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 负责校验,校验工作都在这里
 * 
 * @author tanghc
 *
 */
public class ApiValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(ApiValidator.class);

    private static final int MILLISECOND_OF_ONE_SECOND = 1000;

    private static final String LEFT_TOKEN = "{";
    private static final String RIGHT_TOKEN = "}";

    private static List<String> FORMAT_LIST = Arrays.asList("json", "xml");
    private static Object[] EMPTY_OBJ_ARRAY = {};

    private static ValidatorFactory factory;
    private static javax.validation.Validator validator;
    static {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public void validate(ApiParam param) {
        if (ApiContext.getApiConfig().isIgnoreValidate() || param.fatchIgnoreValidate()) {
            logger.debug("忽略所有验证(ignoreValidate=true), name:{}, version:{}", param.fatchName(), param.fatchVersion());
            return;
        }
        Assert.notNull(ApiContext.getApiConfig().getAppSecretManager(), "appSecretManager未初始化");
        
        if (param.fatchIgnoreSign() || ApiContext.isEncryptMode()) {
            logger.debug("忽略签名验证, name:{}, version:{}", param.fatchName(), param.fatchVersion());
        } else {
            // 需要验证签名
            checkAppKey(param);
            checkSign(param);
        }
        checkUploadFile(param);
        checkTimeout(param);
        checkFormat(param);
    }
    
    /**
     * 校验上传文件内容
     * @param param
     */
    protected void checkUploadFile(ApiParam param) {
        UploadContext uploadContext = ApiContext.getUploadContext();
        if(uploadContext != null) {
            try {
                List<MultipartFile> files = uploadContext.getAllFile();
                for (MultipartFile file : files) {
                    // 客户端传来的文件md5
                    String clientMd5 = param.getString(file.getName());
                    if(clientMd5 != null) {
                        String fileMd5 = DigestUtils.md5Hex(file.getBytes());
                        if(!clientMd5.equals(fileMd5)) {
                            throw Errors.ERROR_UPLOAD_FILE.getException();
                        }
                    }
                }
            }catch (IOException e) {
                logger.error("验证上传文件MD5错误", e);
                throw Errors.ERROR_UPLOAD_FILE.getException();
            }
        }
        
    }

    protected void checkTimeout(ApiParam param) {
        int timeoutSeconds = ApiContext.getApiConfig().getTimeoutSeconds();
        // 如果设置为0，表示不校验
        if(timeoutSeconds == 0) {
            return;
        }
        if (timeoutSeconds < 0) {
            throw new IllegalArgumentException("服务端timeoutSeconds设置错误");
        }
        String requestTime = param.fatchTimestamp();
        try {
            Date requestDate = new SimpleDateFormat(ParamNames.TIMESTAMP_PATTERN).parse(requestTime);
            long requestMilliseconds = requestDate.getTime();
            if (System.currentTimeMillis() - requestMilliseconds > timeoutSeconds * MILLISECOND_OF_ONE_SECOND) {
                throw Errors.TIMEOUT.getException(param.fatchNameVersion(), timeoutSeconds);
            }
        } catch (ParseException e) {
            throw Errors.TIME_INVALID.getException(param.fatchNameVersion());
        }
    }

    protected void checkAppKey(ApiParam param) {
        Assert.notNull(ApiContext.getApiConfig().getAppSecretManager(), "appSecretManager未初始化");
        if (StringUtils.isEmpty(param.fatchAppKey())) {
            throw Errors.NO_APP_ID.getException(param.fatchNameVersion(), ParamNames.APP_KEY_NAME);
        }
        boolean isTrueAppKey = ApiContext.getApiConfig().getAppSecretManager().isValidAppKey(param.fatchAppKey());
        if (!isTrueAppKey) {
            throw Errors.ERROR_APP_ID.getException(param.fatchNameVersion(), ParamNames.APP_KEY_NAME);
        }
    }

    protected void checkSign(ApiParam param) {
        if (StringUtils.isEmpty(param.fatchSign())) {
            throw Errors.NO_SIGN_PARAM.getException(param.fatchNameVersion(), ParamNames.SIGN_NAME);
        }
        String secret = ApiContext.getApiConfig().getAppSecretManager().getSecret(param.fatchAppKey());
        
        Signer signer = ApiContext.getApiConfig().getSigner();
        boolean isRightSign = signer.isRightSign(param, secret, param.fatchSignMethod());
        // 错误的sign
        if(!isRightSign) {
            throw Errors.ERROR_SIGN.getException(param.fatchNameVersion());
        }
    }
    
    
    protected void checkFormat(ApiParam param) {
        String format = param.fatchFormat();
        boolean contains = FORMAT_LIST.contains(format.toLowerCase());

        if (!contains) {
            throw Errors.NO_FORMATTER.getException(param.fatchNameVersion(), format);
        }
    }

    @Override
    public void validateBusiParam(Object obj) {
        if(obj == null) {
            return;
        }
        Set<ConstraintViolation<Object>> set = validator.validate(obj);
        if (CollectionUtils.isNotEmpty(set)) {
            ConstraintViolation<Object> oneError = set.iterator().next();
            String errorMsg = oneError.getMessage();
            throw this.getValidateBusiParamException(errorMsg);
        }
    }

    private RuntimeException getValidateBusiParamException(String errorMsg) {
        String code = Errors.BUSI_PARAM_ERROR.getCode();
        String[] msgToken = errorMsg.split("=");
        String msg = msgToken[0];
        if (msg.startsWith(LEFT_TOKEN) && msg.endsWith(RIGHT_TOKEN)) {
            String module = msg.substring(1, msg.length() - 1);
            Object[] params = this.buildParams(msgToken);
            String error = ErrorFactory.getErrorMessage(module, ApiContext.getLocal(), params);
            return new BusinessParamException(error, code);
        } else {
            return new BusinessParamException(errorMsg, code);
        }
    }

    private Object[] buildParams(String[] msgToken) {
        if (msgToken.length == 2) {
            return msgToken[1].split(",");
        } else {
            return EMPTY_OBJ_ARRAY;
        }
    }

    public static javax.validation.Validator getValidator() {
        return validator;
    }

    public static void setValidator(javax.validation.Validator validator) {
        ApiValidator.validator = validator;
    }

}
