package com.springclouddev.loges.demo;

import com.springclouddev.loges.trace.aspect.AbstractAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * className：AspectConfig
 * description： TODO
 * time：2020-05-26.11:24
 *
 * @author Tank
 * @version 1.0.0
 */
@Aspect
@Component
public class AspectConfig extends AbstractAspect {

    @Around("within(com.springclouddev.loges..*))")
    public Object around(JoinPoint joinPoint) throws Throwable {
        return aroundExecute(joinPoint);
    }
}
