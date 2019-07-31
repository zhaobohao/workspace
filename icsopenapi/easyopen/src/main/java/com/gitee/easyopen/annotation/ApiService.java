package com.gitee.easyopen.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * 标记类具有接口提供能力，该注解同样具备SpringBean管理功能，因为继承了@Service
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Service
public @interface ApiService {
    
    /**
     * @return 忽略验证签名，默认false，为true则仅仅忽略ApiService下面所有的接口的验签操作，但其它验证会执行。
     */
    boolean ignoreSign() default false;
    
    /**
     * @return 忽略所有验证，默认false，为true则忽略ApiService下面所有接口的验证操作。
     */
    boolean ignoreValidate() default false;
    
    /**
     * @return 是否对返回结果进行包装，影响其下所有接口。
     */
    boolean wrapResult() default true;
}
