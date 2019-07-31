package com.gitee.durcframework.easyopen;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.util.Assert;

import com.gitee.easyopen.util.CopyUtil;

import junit.framework.TestCase;

public class CopyTest extends TestCase {

    class A {
        public void say() {
            System.out.println("A");
        }
    }

    class B extends A {
    }

    class Pojo {
        private A a;
        private A b;

        public A getA() {
            return a;
        }

        public void setA(A a) {
            this.a = a;
        }

        public A getB() {
            return b;
        }

        public void setB(A b) {
            this.b = b;
        }
    }

    @Test
    public void testCopy() {
        
        Map<String, Object> map = new HashMap<>();
        map.put("a",  new A());
        map.put("b",  new B());
        
        Pojo pojo = new Pojo();
        
        CopyUtil.copyProperties(map, pojo);
        
        Assert.notNull(pojo.getA());
        Assert.notNull(pojo.getB());
        
        pojo.getA().say();
        pojo.getB().say();
        
        System.out.println("pojo.getB() instanceof B : " + (pojo.getB() instanceof B));
        System.out.println("A.class.isInstance(pojo.getB()) : " + A.class.isInstance(pojo.getB()));
        System.out.println("pojo.getA() == map.get(\"a\") : " +(pojo.getA() == map.get("a")));
        
    }

    @Test
    public void testCopy2() {

        Map<String, Object> map = new HashMap<>();
        map.put("a", new A());

        Map<String, Object> map2 = new HashMap<>();

        CopyUtil.copyProperties(map, map2);

        Assert.notNull(map2.get("a"));

        System.out.println(map2);

    }
}
