package com.gitee.durcframework.easyopen;

import com.gitee.easyopen.exception.ApiException;
import com.google.common.base.Enums;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.util.ClassUtils;

/**
 * @author tanghc
 */
public class EnumTest extends TestCase {

    static interface IEnum{
        String getCode();
        String getDescription();
    }

    static enum Color implements IEnum {
        RED("红色"),
        BLACK("黑色"),
        ;
        private String color;

        Color(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }

        @Override
        public String getCode() {
            return this.name();
        }

        @Override
        public String getDescription() {
            return color;
        }
    }

    @Test
    public void testDo() {
        Class<?> colorClass = Color.class;
        Class<?>[] interfaces = colorClass.getInterfaces();
        boolean hasIEnum = false;
        for (Class<?> interfacesForClass : interfaces) {
            if (interfacesForClass == IEnum.class) {
                hasIEnum = true;
                break;
            }
        }
        if (!hasIEnum) {
            throw new ApiException(colorClass.getName() + "必须实现" + IEnum.class.getName() + "接口");
        }
        IEnum[] enums = (IEnum[])colorClass.getEnumConstants();
        StringBuilder sb = new StringBuilder();
        for (IEnum anEnum : enums) {
            sb.append(", ").append(anEnum.getCode()).append(":").append(anEnum.getDescription());
        }
        System.out.println(sb.toString().substring(1));
    }
}
