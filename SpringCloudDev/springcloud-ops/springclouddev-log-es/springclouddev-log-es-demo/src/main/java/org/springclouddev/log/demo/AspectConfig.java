package org.springclouddev.log.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springclouddev.log.trace.aspect.AbstractAspect;
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

    @Around("within(org.springclouddev.log.demo.controller..*))")
    public Object around(JoinPoint joinPoint) throws Throwable {
        return aroundExecute(joinPoint);
    }
}
