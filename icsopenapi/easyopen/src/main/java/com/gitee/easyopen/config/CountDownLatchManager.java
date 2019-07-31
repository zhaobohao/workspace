package com.gitee.easyopen.config;

import java.util.concurrent.CountDownLatch;

/**
 * @author tanghc
 */
public class CountDownLatchManager {
    public static volatile CountDownLatch latch;

    public static CountDownLatch initCountDownLatch(int count) {
        latch = new CountDownLatch(count);
        return latch;
    }

    public static void countDown() {
        latch.countDown();
    }
}
