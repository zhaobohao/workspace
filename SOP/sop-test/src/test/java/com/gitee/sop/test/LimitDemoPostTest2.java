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
 * 限流测试，根据【路由ID + appKey】限流
 */
public class LimitDemoPostTest2 extends TestBase {

    String url = "http://localhost:8081";
    // 这个appKey会被限流
    String appId = "2019032617262200001";
    // 平台提供的私钥
  String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM4lMCwagCw0Yl3Npabzfma2lxjZjEavg0+OeFCZ9Ss91P5mCLqOab6iVi7KA14Hxw5IAuZPOBeV5/7pJ3B9ElUQxhUA7uGoXQ6h33NM5z6SiWCdYm2pfngKih18AG3RI1L9uyvqEIBa7XtEaduAFor5lokPc1WpdVcTwTfRxgeJAgMBAAECgYAM3XFGL1k0aQiChiUCaEvJKTgAywLgHm/5dRC5JwKP8knqnn+I9P5QcV0jimPvaFjZ4VCdAvCjOC3EUNSvRn7wR2Lb1+BGZZePTdxtHWE2aqJ1W1SvgQTqMsLlPBRPnXo5XH/ng3WEH15ynd5NR035xAluaI0X/y+PsRxE6TlfIQJBAPSYUyXa2yaEqmvIN+ECKALCLLeDdi2YW3Kjahgz0X9V4Y4aTdrHh8y603zXC0Wy8HeOhwGoyciaS8SmjxCMn4UCQQDXweW8xsUreLH8hfVUtyiY/KgUz+R5foJDNXD7TLE9CDoPSHy09qBe99HyVCZg/gNJH4O+tNr6C4916dYaVk01AkBYZ2HOEc8ZmeOaty/zJHtfm9zbqykgi6upwISNINV8Z4bxfHJdO7bKeVANFBBf7a/aFmqXX/EmjxYJioW03o6dAkEAp7ViXJCtJpNU1pNSFZ2hgvmxtSu7zuyVWKSrw8rjYiuI5eRUe13RXsCHgzQB+Ra5exdyEsUGCaL+yosPD73RmQJBALGuM8EQUcBgrpgpeLZ39Ni1DYXYG9aj+u+ar/UL6kI1mCNFgwroO4EVIvXPVxikMxUgiE2tVaBML5nm8VDNJ7s=";

    // 这个appKey不会被限流
    String appId2 = "20190401562373672858288128";
    // 平台提供的私钥
    String privateKey2 = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI6BKjoZ/Azxr6tRPibrWdlrurlwmmMvuW7FAq4m+QyX9DJ1WvNE+KcKHrOqAEkNrthjZfrPlPJOSNi4ORgyt3Idxi8IO4nQ2lE9kxH5AMTvTKn3An6Q6pNrxt6km3HO4JgvEXP7BGwgW3ScjELq20Joz04TD8F+WHCzfFzPDi6pAgMBAAECgYA/zuQ6ieILZbjUDBe5U46yxQMh/6KRoQ/14m81zauckPm+EkA8R3jTSru+lPN1wpO0vqUuLf6ylI0XxT2DcUokOgY07ZdE54pu5XAsyY0eanFwt6C1LrHYpORV3Mp9XuI3fXrxYqVlxLuj1N7MGinXUuW7aZCHaEuSnZ55OL9dkQJBANKOeFiYDISSuIFHSrndSgr+a8E44jS/2/7lE49p5l3WVOFTHh0IZQNTs/IKsKJkUnYDE4W/Ab7NTnWZpXYeM30CQQCtQrtxPEzLl4dtupPOBJJoApj2lq7Q6tIGx178K6wS9Rz3GEvkA7fz1Tpm+nmPZflWZ9mVmEaVuMTMpl3HN/edAkBylyzx8lYltIALg5QskT1hvFNChkW9tYjyMROzIkxIV4Q8WPLzlAT9iYlOOfkld/nU1hnC2VAG2k9P+z2sigU1AkBAl1AptsEqZSMn1RalBy9NdypvQ12IpQIHZOwUNnO/3YEe3P/t0TUSwbs0CMyomOuLOsvy6QHnbypu4Na1HjhBAkAWjtdhuvU15HAa5jMgiUVfQM3YFuz2k3QkRagtZZ33bqnYs4wNxEZqB5t+vEj+8r3fmSN0BpNR1VW71j53Ir0H";

    @Test
    public void testLimit() throws InterruptedException {
        doTest(appId, privateKey);
    }

    @Test
    public void testNotLimit() throws InterruptedException {
        doTest(appId2, privateKey2);
    }

    protected void doTest(String appId, String privateKey) throws InterruptedException {
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
                        doBusiness(Thread.currentThread().getName(), appId, privateKey);
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
    public void doBusiness(String threadName, String appKey, String priKey) throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appKey);
        params.put("method", "alipay.story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "1");
        bizContent.put("name", "葫芦娃");

        params.put("biz_content", JSON.toJSONString(bizContent));

        String content = AlipaySignature.getSignContent(params);
        String sign = AlipaySignature.rsa256Sign(content, priKey, "utf-8");

        params.put("sign", sign);

        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

}
