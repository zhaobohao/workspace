package com.gitee.easyopen.register;

import com.gitee.easyopen.util.FieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author tanghc
 */
public class SingleParameterContext {
    private static final Logger logger = LoggerFactory.getLogger(SingleParameterContext.class);

    private static SingleParameterWrapper singleFieldWrapper = new SingleParameterWrapper();

    private static Map<String, SingleParameterContextValue> context = new ConcurrentHashMap<>(16);

    public static void add(Object handler, Method method, Parameter parameter) {
        String key = method.toString();
        String paramName = FieldUtil.getMethodParameterName(handler.getClass(), method, 0);
        Class<?> wrapClass = singleFieldWrapper.wrapParam(parameter, paramName);
        SingleParameterContextValue value = new SingleParameterContextValue();
        value.setHandler(handler);
        value.setMethod(method);
        value.setWrapClass(wrapClass);
        value.setParamName(paramName);
        value.setParameter(parameter);
        logger.info("包装参数，方法：{}，参数名：{}", method, paramName);
        context.put(key, value);
    }

    public static SingleParameterContextValue get(Method method) {
        return context.get(method.toString());
    }

    public static class SingleParameterContextValue {
        private Object handler;
        private Method method;
        private String paramName;
        private Parameter parameter;
        private Class<?> wrapClass;

        public Object getHandler() {
            return handler;
        }

        public void setHandler(Object handler) {
            this.handler = handler;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }

        public Parameter getParameter() {
            return parameter;
        }

        public void setParameter(Parameter parameter) {
            this.parameter = parameter;
        }

        public Class<?> getWrapClass() {
            return wrapClass;
        }

        public void setWrapClass(Class<?> wrapClass) {
            this.wrapClass = wrapClass;
        }

    }

    public static void setSingleFieldWrapper(SingleParameterWrapper singleFieldWrapper) {
        SingleParameterContext.singleFieldWrapper = singleFieldWrapper;
    }
}
