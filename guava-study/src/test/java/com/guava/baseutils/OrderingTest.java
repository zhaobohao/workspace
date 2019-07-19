package com.guava.baseutils;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.List;
@Log4j2
public class OrderingTest {
    @Test
    public void orderingTest(){
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");

        System.out.println("list:"+ list);

        Ordering<String> naturalOrdering = Ordering.natural();
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();

        log.info("naturalOrdering:"+ naturalOrdering.sortedCopy(list));
        log.info("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));
        log.info("arbitraryOrdering:"+ arbitraryOrdering.sortedCopy(list));

    }

}
