

package com.spring.web.core.constraints;

import com.spring.web.core.enums.BaseTypeStateEnum;
import com.spring.web.core.exception.BusinessException;
import com.spring.web.core.util.EnumUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义系统内的枚举验证注解实现类
 * @author geekidea
 * @date 2018-11-08
 */
public class EnumTypeValidator implements ConstraintValidator<EnumType, Integer> {

	private Class<? extends BaseTypeStateEnum> baseTypeStateEnum;

	@Override
	public void initialize(EnumType parameters) {
		baseTypeStateEnum = parameters.type();
		if (baseTypeStateEnum == null){
			throw new BusinessException("请传入枚举类型类");
		}
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
		if (value ==null){
			return true;
		}
		return EnumUtil.exists(baseTypeStateEnum,value);
	}
}
