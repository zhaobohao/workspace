package com.guava.collection;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.HashMultimap;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class MultiMapTest {

@Test
    public void test1() {
    HashMultimap<Integer, String> m = HashMultimap.create();
    for (int i = 0; i < 100; i++) {
        m.put(RandomUtil.randomInt(10), RandomUtil.randomString(10));
    }
    m.keySet().stream().forEach(s->log.info("{} count value is {}",s ,m.get(s).size()));


}

}
