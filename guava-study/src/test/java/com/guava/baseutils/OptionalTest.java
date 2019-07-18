package com.guava.baseutils;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.OptionalDouble;

@Slf4j
public class OptionalTest {
    @Test
    public void baseTest() {
        OptionalDouble d=OptionalDouble.of(22 );
        log.info("the value is {}",d.getAsDouble());


    }

}
