package com.guava.baseutils;

import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

@Slf4j
public class OptionalTest {
    @Test
    public void baseTest() {
        OptionalDouble d = OptionalDouble.of(22);
        log.info("the value is {}", d.getAsDouble());

        Optional<String> optional = Optional.empty();
        if (optional.isPresent()) {
            Assert.assertNotNull(optional.get());
            log.info("the optional is set ,value is {}", optional.orElseGet(() -> "defalut String"));
        } else {
            Assert.assertNull(optional.orElseGet(() -> null));
            log.info("the otional is not set");
        }

    }

    @Test
    public void advanceTest() {
        OptionalInt op = OptionalInt.of(6);

        op.ifPresent(i -> log.info("the value is {}", i));

        Optional<String> stringOptional = Optional.ofNullable("advance");
        stringOptional.filter(v -> v.contains("a")).flatMap(v -> {
            log.info("we get {}", v);
            return Optional.of(v);
        });
    }


}
