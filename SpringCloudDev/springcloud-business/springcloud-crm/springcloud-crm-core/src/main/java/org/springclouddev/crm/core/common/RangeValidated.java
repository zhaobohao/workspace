package org.springclouddev.crm.core.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 范围校验注解(min不能大于max)
 * @author hmb
 */
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeValidator.class)
public @interface RangeValidated {
    String message() default "小范围要小于大范围";
    String[] minFieldName() default "startTime";
    String[] maxFieldName() default "endTime";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
