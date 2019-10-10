package com.kyro;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class KryoTest {

    @Test
    public void kyroSerializableTest() {

        Serializer ser = new kryoSerializer(Msg.class);
        for (int i = 0; i < 10000; i++) {

            Msg msg = new Msg();

            msg.setName("name");
            msg.setId(10);
            msg.setNickName("nick").setCd(new Date());
            Map<String,Integer> m=Maps.newHashMap();
            m.put("s", RandomUtil.randomInt(10));
            m.put("d",RandomUtil.randomInt(20));
            msg.setNameMap(m);
            Person p=new Person();
            p.setAge(10);
            p.setName("pname");
            msg.setPerson(p);


            byte[] bytes = new byte[300];  //注意大小，太大的对象这里会outof
            long start = System.currentTimeMillis();
            ser.serialize(msg, bytes);
            System.err.println("序列化耗时：" + (System.currentTimeMillis() - start));
            System.out.println(msg);
//            System.out.println(Arrays.toString(bytes));
            //System.out.println(kryoSerializer.writeToString(bytes));
            //System.out.println(Arrays.toString(kryoSerializer.readFromString(kryoSerializer.writeToString(bytes))));

            Msg newmsg = null;
            start = System.currentTimeMillis();
            newmsg = ser.deserialize(bytes);
            System.err.println("反序列化耗时：" + (System.currentTimeMillis() - start));
            System.out.println(newmsg);


        }
    }
}
