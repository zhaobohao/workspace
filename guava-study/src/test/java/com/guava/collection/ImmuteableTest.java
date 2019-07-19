package com.guava.collection;

import com.google.common.collect.ImmutableList;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class ImmuteableTest {
 @Test
 public static void immuteableCollectionTest()
 {
     ImmutableList<String> list=ImmutableList.of("a","b","c");
     log.info("value is {}",list.toString());
 }
}
