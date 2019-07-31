package com.easyopen.sdk;

import com.alibaba.fastjson.JSON;
import com.easyopen.sdk.client.OpenClient;
import com.easyopen.sdk.common.OpenConfig;
import com.easyopen.sdk.param.GoodsParam;
import com.easyopen.sdk.request.GetGoodsRequest;
import com.easyopen.sdk.response.GetGoodsResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitTest extends BaseTest {


    public LimitTest() {
        OpenConfig openConfig = new OpenConfig();
        openConfig.setReadTimeoutSeconds(30);
        client = new OpenClient(url, appKey, secret, openConfig);
    }

    @Test
    public void testLimit() throws InterruptedException {
        int threadsCount = 50; // threadsCount个线程同时提交
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final CountDownLatch count = new CountDownLatch(threadsCount);
        final AtomicInteger success = new AtomicInteger();
        for (int i = 0; i < threadsCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await(); // 等在这里，执行countDownLatch.countDown();集体触发
                        // 业务方法
                        doBusiness(Thread.currentThread().getName());
                        success.incrementAndGet();
                    } catch (Exception e) {
                    } finally {
                        count.countDown();
                    }
                }
            }).start();
        }
        countDownLatch.countDown();
        count.await();
        System.out.println("成功次数：" + success);
    }

    private void doBusiness(String thead) throws IOException {
        // 创建请求对象
        GetGoodsRequest request = new GetGoodsRequest();
        // 请求参数
        GoodsParam param = new GoodsParam();
        param.setGoods_name("iphone6");
        request.setParam(param);

        // 发送请求
        GetGoodsResponse response = client.execute(request);

        if (response.isSuccess()) {
            System.out.println("成功(" + thead + "):" + response.getData());
        } else {
            System.err.println("失败(" + thead + "):" + JSON.toJSONString(response));
            throw new RuntimeException(response.getMsg());
        }
    }

}