package com.gitee.sop.test;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.test.alipay.AlipaySignature;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 限流测试，根据appKey限流
 */
public class LimitDemoPostTest3 extends TestBase {

    String url = "http://localhost:8081";
    String appId = "20190401562373796095328256";
    // 平台提供的私钥
    String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJvGfQSfZyPb23yL6bV1Pux4X1dDHdwRqgKVcVOWZzvxjVm5AVvJj31VLC/wlu2kbE+8FJUP1I+ZdY0FEFtIdP6DFK1x1cP1B4PeScYXL/VX7PcTZGP/osiUWaBOUaHV+YSC20+OxRbUOPvTj/J3IivD4IDIAwpDKsWDTBiY1b5RAgMBAAECgYAE27/ycPZKjATgcYyseCeqQGbY1eMMhhCDXB3YuYwmtnXuInMEZdjv08Q5CovqhYJLSlZp/8BlaifcahgEgNIFQXmxAF0U0HsNC6W4Dk1gGgQaVmYaZv5ex7uIcFB1qFvlO60kWf82YeRnO5KsFBODOJ1XSNwqjL2GeLSHBSVyQQJBAOsvDmClBsETSdiNSFMz+D9WCnCh1Ip4AoCzA/yG+PRSwYjZDdceP2DXieiZXPlxTFZ7MIXxAafgeyeQA2hpkQUCQQCpkCUSbrZ+nd4BYdnxZOSf0//cUT0o6+3kROX7gsXV7zRAWWxojT6DkGVlduDLZM/hjWeHRjWUxKC/jgbzvundAkBckhUSrWJPNQxoFJRXS6l3JKLPWqOSLVKu3ce/6lCrurc66lSsS9eegrhhuZwDAzmNAMhEsGx6a72OAP2WZ5cRAkBd8cT4X2qw4BpePa6YdcPNYZHCqSfvgje9XwbkwGGH1A3pESJlEsxt7BShkKmfRu1+E/AmHJoXIJHHT5M+fKnpAkA+VfyAAviKeCwUSq+5oUa0B+ozEA3frp/40cKQP7k02aamocAQCDRaC1ZlWffeQqYMnYe1/Mjr/SdX/Ut3X0CC";

    @Test
    public void testLimit() throws InterruptedException {
        int threadsCount = 10; // threadsCount个线程同时提交
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

    // 这个请求会路由到story服务
    public void doBusiness(String threadName) throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "alipay.story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.2");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "1");
        bizContent.put("name", "葫芦娃");

        params.put("biz_content", JSON.toJSONString(bizContent));

        String content = AlipaySignature.getSignContent(params);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");

        params.put("sign", sign);

        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

}
