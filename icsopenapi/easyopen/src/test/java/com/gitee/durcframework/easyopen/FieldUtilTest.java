package com.gitee.durcframework.easyopen;

import com.gitee.easyopen.util.FieldUtil;
import junit.framework.TestCase;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author tanghc
 */
public class FieldUtilTest extends TestCase {

    public void testDo() {
        ReflectionUtils.doWithMethods(A.class, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                System.out.println("---" + method.getName());
                if (method.getName().equals("fun")) {
                    String name = FieldUtil.getMethodParameterName(A.class, method, 0);
                    System.out.println(name);
                }
            }
        });
    }

    static class A {
        public void fun(int id) {
            int i = 0;
            int j = 1;
        }
    }
}
