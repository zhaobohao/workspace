package com.guava.collection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.TreeMultiset;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class MultiSetTest {
    @Test
    public void multiSetTest1()
    {
        String strWorld="wer|dfd|dd|dfd|dda|de|dr";
        List<String> list= Arrays.asList(strWorld.split("\\|"));
        Multiset<String> ms= HashMultiset.create();
        ms.addAll(list);
        ms.add("zhaobo");
        ms.add("zhaobo");
        ms.add("zhaobo");
        ms.add("zhaobo");
        ms.add("zhaobo");
        ms.add("study");
        for (String key:ms.elementSet())
        {
            log.info("{} count is {}",key,ms.count(key));
        }

       log.info("multiset count is {}",ms.size());

        log.info("-------------------------------------------------");
        ms.forEach(key->log.info("{} count is {}",key,ms.count(key)));
        log.info("-------------------------------------------------");
    }
}
