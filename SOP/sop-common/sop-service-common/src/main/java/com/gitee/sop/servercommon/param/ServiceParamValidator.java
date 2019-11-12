package com.gitee.sop.servercommon.param;

import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.exception.ServiceException;
import com.gitee.sop.servercommon.message.ServiceErrorEnum;
import com.gitee.sop.servercommon.message.ServiceErrorFactory;
import com.gitee.sop.servercommon.param.validation.ValidationGroupSequence;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    private static final List<String> SYSTEM_PACKAGE_LIST = Arrays.asList("java.lang", "java.math");

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
        // 先校验属性对象
        List<Object> fields = listObjectField(obj);
        if (!fields.isEmpty()) {
            fields.forEach(this::validateBizParam);
        }
        Set<ConstraintViolation<Object>> set = validator.validate(obj, ValidationGroupSequence.class);
        if (!CollectionUtils.isEmpty(set)) {
            ConstraintViolation<Object> oneError = set.iterator().next();
            String errorMsg = oneError.getMessage();
            throw this.getValidateBizParamException(errorMsg);
        }
    }

    private List<Object> listObjectField(Object object) {
        List<Object> ret = new ArrayList<>();
        ReflectionUtils.doWithFields(object.getClass(), field -> {
            ReflectionUtils.makeAccessible(field);
            ret.add(field.get(object));
        }, this::isMatchField);
        return ret;
    }

    /**
     * 匹配校验字段。
     *
     * 1. 不为基本类型；
     * 2. 不为java自带的类型；
     * 3. 不为枚举
     * 4. 不为Map
     * @param field field
     * @return true，是自定义的
     */
    private boolean isMatchField(Field field) {
        Class<?> fieldType = field.getType();
        if (fieldType.isPrimitive()) {
            return false;
        }
        if (Map.class.isAssignableFrom(fieldType)) {
            return false;
        }
        Class<?> declaringClass = field.getDeclaringClass();
        boolean isEnum = declaringClass == field.getType() && declaringClass.isEnum();
        if (isEnum) {
            return false;
        }
        Package aPackage = fieldType.getPackage();
        if (aPackage == null) {
            return false;
        }
        String packageName = aPackage.getName();
        for (String prefix : SYSTEM_PACKAGE_LIST) {
            if (packageName.startsWith(prefix)) {
                return false;
            }
        }
        return true;
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
