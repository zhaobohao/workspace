package com.guava.collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class BiMapTest {

    @Test
    public  void test1()
    {
        BiMap<Integer,String> bm= HashBiMap.create();
        bm.put(1,"a");
        bm.put(1,"a");
        //bm.put(1,"b");
        //bm.put(2,"a");// this will be error :value already present: a
        bm.put(3,"d");
        bm.put(4,"e");
        bm.put(5,"f");

        bm.keySet().forEach(k->log.info("key is {}",k));

        bm.values().forEach(v->log.info("value is {}",v));

    }
}
