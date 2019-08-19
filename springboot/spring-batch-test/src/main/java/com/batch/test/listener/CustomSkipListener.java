package com.batch.test.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
@Slf4j
public class CustomSkipListener implements SkipListener<Object, Object> {

    @Override
    public void onSkipInRead(Throwable throwable) {
        // bussiness logic
        log.info("recive Exception : {} ", throwable.getCause());
    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {
       log.info("Skipping Record in Wirte {},Cause:{}",o,throwable.getCause());
    }

    @Override
    public void onSkipInProcess(Object o, Throwable throwable) {
        log.info("Skipping Record in Process {},Cause:{}",o,throwable.getCause());
    }
}
