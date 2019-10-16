package com.gitee.sop.servercommon.param;

import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.exception.ServiceException;
import com.gitee.sop.servercommon.message.ServiceErrorEnum;
import com.gitee.sop.servercommon.message.ServiceErrorFactory;
import com.gitee.sop.servercommon.param.validation.ValidationGroupSequence;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * 参数验证，JSR-303
 * @author tanghc
 */
public class ServiceParamValidator implements ParamValidator {
    private static final String LEFT_TOKEN = "{";
    private static final String RIGHT_TOKEN = "}";
    private static final String EQ = "=";
    private static final String COMMA = ",";
    private static Object[] EMPTY_OBJ_ARRAY = {};

    private static javax.validation.Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public void validateBizParam(Object obj) {
        if (obj == null) {
            return;
        }
        Set<ConstraintViolation<Object>> set = validator.validate(obj, ValidationGroupSequence.class);
        if (!CollectionUtils.isEmpty(set)) {
            ConstraintViolation<Object> oneError = set.iterator().next();
            String errorMsg = oneError.getMessage();
            throw this.getValidateBizParamException(errorMsg);
        }
    }

    private RuntimeException getValidateBizParamException(String errorMsg) {
        String subCode = ServiceErrorEnum.ISV_PARAM_ERROR.getErrorMeta().getSubCode();
        String[] msgToken = errorMsg.split(EQ);
        String msg = msgToken[0];
        if (msg.startsWith(LEFT_TOKEN) && msg.endsWith(RIGHT_TOKEN)) {
            String module = msg.substring(1, msg.length() - 1);
            Object[] params = this.buildParams(msgToken);
            String error = ServiceErrorFactory.getErrorMessage(module, ServiceContext.getCurrentContext().getLocale(), params);
            return new ServiceException(subCode, error);
        } else {
            return new ServiceException(subCode, errorMsg);
        }
    }

    private Object[] buildParams(String[] msgToken) {
        if (msgToken.length == 2) {
            return msgToken[1].split(COMMA);
        } else {
            return EMPTY_OBJ_ARRAY;
        }
    }
}
