package com.batch.test.listener;

import org.springframework.batch.core.SkipListener;

public class CustomSkipListener implements SkipListener<String, String> {
    @Override
    public void onSkipInRead(Throwable t) {
        // business logic
    }

    @Override
    public void onSkipInWrite(String item, Throwable t) {
        // no need
    }

    @Override
    public void onSkipInProcess(String item, Throwable t) {
        // no need
    }
}
