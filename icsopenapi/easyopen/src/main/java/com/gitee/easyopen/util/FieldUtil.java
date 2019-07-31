package com.gitee.easyopen.util;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * @author tanghc
 */
public class FieldUtil {

    private static final String CLASS_SUFFIX = ".class";

    private FieldUtil() {
    }

    /**
     * 判断这个字段是否是数字类型或字符串类型
     *
     * @param type 字段类型
     * @return true：是数字或字符串类型
     */
    public static boolean isNumberOrStringType(Class<?> type) {
        if (type == String.class) {
            return true;
        }
        if (type.getGenericSuperclass() == Number.class) {
            return true;
        }
        if (type.isPrimitive()) {
            return true;
        }
        return false;
    }

    /**
     * 获取指定类指定方法的参数名
     * @param clazz 要获取参数名的方法所属的类
     * @param method 要获取参数名的方法
     * @param index 参数索引，从0开始
     * @return 返回指定类指定方法的参数名，没有返回空字符串
     */
    public static String getMethodParameterName(Class<?> clazz, final Method method, int index) {
        String[] names = getMethodParameterNamesByAsm(clazz, method);
        if (names.length == 0) {
            return "";
        }
        return names[index];
    }

    /**
     * 获取指定类指定方法的参数名
     *
     * @param clazz  要获取参数名的方法所属的类
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表，如果没有参数，则返回空数组
     */
    public static String[] getMethodParameterNamesByAsm(Class<?> clazz, final Method method) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            return new String[0];
        }
        final Type[] types = new Type[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            types[i] = Type.getType(parameterTypes[i]);
        }
        final String[] parameterNames = new String[parameterTypes.length];
        // 解决clazz对象是cglib对象导致空指针异常
        // 获取真实的class，如果是cglib类，则返回父类class
        Class<?> realClass = ClassUtils.getUserClass(clazz);
        String className = realClass.getName();
        int lastDotIndex = className.lastIndexOf('.');
        className = className.substring(lastDotIndex + 1) + CLASS_SUFFIX;
        InputStream is = realClass.getResourceAsStream(className);
        try {
            ClassReader classReader = new ClassReader(is);
            classReader.accept(new ClassVisitor(Opcodes.ASM6) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    // 只处理指定的方法
                    Type[] argumentTypes = Type.getArgumentTypes(desc);
                    if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
                        return null;
                    }
                    return new MethodVisitor(Opcodes.ASM6) {
                        @Override
                        public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                            int i = index - 1;
                            // 如果是静态方法，则第一就是参数
                            // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                            if (Modifier.isStatic(method.getModifiers())) {
                                i = index;
                            }
                            if (i >= 0 && i < parameterNames.length) {
                                parameterNames[i] = name;
                            }
                            super.visitLocalVariable(name, desc, signature, start,
                                    end, index);
                        }

                    };

                }
            }, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parameterNames;
    }

}
