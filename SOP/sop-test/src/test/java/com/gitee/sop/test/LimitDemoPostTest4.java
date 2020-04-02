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
 * 限流测试，根据【ip】限流
 */
public class LimitDemoPostTest4 extends TestBase {

    String url = "http://localhost:8081";
    String appId = "20190513577548661718777856";
    // 平台提供的私钥
    String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ0aPPcgTZVC14ONBoVpxcsIAip0QghVx/stbt/XjZXnlDTG1yMNM4eMEcTFmwbrj9jlYrPihBVYadfC2uV53xCDRgADu55q3yYTw3MlKb23Ft9T2HBcHvucnFWQXJpIbWnQhkWs1ClttTFNf3vnl14/sN1xIXXjwsuvT3VX75LdAgMBAAECgYB68z/nQDa3q/oykDocS21qujfHtfi/wTKjVylAsdezC+wnab6RRhGf8XUuhGARiGWpn8whcBNjCTC8lVju4vQ5IIx4Hb74vwDDMtNXeqwkLmARLYu2ELibauezSeqom8/J8cR3ho7Hr4VHPTiC8qvePRmu8AvXVQz2T7SOhEjDGQJBAOm8XOivr+atiknLbQhmo508ON3sjoN9VMwK9cmnup+ZPCsurJTHRja0MJQNdOXObUVJ6wJhs1PHWT+vITfXGJ8CQQCsESzxOYTkZaqBUFjbWVf1rSwjOOsylweTuq44YIJkHhwMjHf3kN/UTXbxsBPUGeT7/+2K5UwQ9snUPr0yTBcDAkA0FMezBWqxgNu+g7iA1bYBVCjrskkzHVsmuA56Z4hbBZ71lEnaQOjxSYdFhhYVGsEYXlciSbjWoyXM3e4N7jzLAkB0ejv+H33CTsAZQZalBdnxSQTz4vf0CyDp9BkzuMELnQZHyF79i2i5gqbd/N+vWMgVfq4CtC3F3gnKT54rii6ZAkAMBIvHriT5Zbs1fW+oxBP1rHqdsRvqs1zEyIadvJgKAFwFEisryfdw2mWm3vxQQ22RlOquBiZEDIlyM0z2m9PJ";

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
        params.put("method", "story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "1");
        bizContent.put("name", "葫芦娃");

        params.put("data", JSON.toJSONString(bizContent));

        String content = AlipaySignature.getSignContent(params);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");

        params.put("sign", sign);

        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

}
