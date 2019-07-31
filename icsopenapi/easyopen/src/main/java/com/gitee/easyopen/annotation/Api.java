package com.gitee.easyopen.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 作用在service类的方法上，service类被@ApiService标记
 * @author tanghc
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Api {

    /**
     * @return 接口名，建议命名规则: <strong>业务.模块.名字.动词</strong>。如：<br/>
     *          支付业务订单状态修改：pay.order.status.update<br/>
     *          充值业务用户余额充值：charge.user.money.recharge<br/>
     *          充值业务用户余额查询：charge.user.money.search<br/>
     */
    String name();

    /**
     * @return 接口版本号，默认""，建议命名规则：x.y，如1.0，1.1
     */
    String version() default "";

    /**
     * @return 忽略验证签名，默认false。为true接口不执行验签操作，但其它验证会执行。
     */
    boolean ignoreSign() default false;
    
    /**
     * @return 忽略所有验证，默认false。为true接口都不执行任何验证操作。
     */
    boolean ignoreValidate() default false;
    
    /**
     * @return 是否对返回结果进行包装，如果设置成false，则直接返回业务方法结果。
     */
    boolean wrapResult() default true;

    /**
     * @return 设置true，不会输出json到客户端，需要调用 {@link com.gitee.easyopen.ApiContext#getResponse()} 手动返回结果。
     */
    boolean noReturn() default false;

    /**
     * @return 设置true，此接口将忽略jwt认证（不管有没有jwt）。默认false
     */
    boolean ignoreJWT() default false;

    /**
     * @return 是否忽略token，true忽略。默认false
     */
    boolean isIgnoreToken() default false;
}
