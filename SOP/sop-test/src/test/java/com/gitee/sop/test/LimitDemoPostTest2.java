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
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXJv1pQFqWNA/++OYEV7WYXwexZK/J8LY1OWlP9X0T6wHFOvxNKRvMkJ5544SbgsJpVcvRDPrcxmhPbi/sAhdO4x2PiPKIz9Yni2OtYCCeaiE056B+e1O2jXoLeXbfi9fPivJZkxH/tb4xfLkH3bA8ZAQnQsoXA0SguykMRZntF0TndUfvDrLqwhlR8r5iRdZLB6F8o8qXH6UPDfNEnf/K8wX5T4EB1b8x8QJ7Ua4GcIUqeUxGHdQpzNbJdaQvoi06lgccmL+PHzminkFYON7alj1CjDN833j7QMHdPtS9l7B67fOU/p2LAAkPMtoVBfxQt9aFj7B8rEhGCz02iJIBAgMBAAECggEARqOuIpY0v6WtJBfmR3lGIOOokLrhfJrGTLF8CiZMQha+SRJ7/wOLPlsH9SbjPlopyViTXCuYwbzn2tdABigkBHYXxpDV6CJZjzmRZ+FY3S/0POlTFElGojYUJ3CooWiVfyUMhdg5vSuOq0oCny53woFrf32zPHYGiKdvU5Djku1onbDU0Lw8w+5tguuEZ76kZ/lUcccGy5978FFmYpzY/65RHCpvLiLqYyWTtaNT1aQ/9pw4jX9HO9NfdJ9gYFK8r/2f36ZE4hxluAfeOXQfRC/WhPmiw/ReUhxPznG/WgKaa/OaRtAx3inbQ+JuCND7uuKeRe4osP2jLPHPP6AUwQKBgQDUNu3BkLoKaimjGOjCTAwtp71g1oo+k5/uEInAo7lyEwpV0EuUMwLA/HCqUgR4K9pyYV+Oyb8d6f0+Hz0BMD92I2pqlXrD7xV2WzDvyXM3s63NvorRooKcyfd9i6ccMjAyTR2qfLkxv0hlbBbsPHz4BbU63xhTJp3Ghi0/ey/1HQKBgQC2VsgqC6ykfSidZUNLmQZe3J0p/Qf9VLkfrQ+xaHapOs6AzDU2H2osuysqXTLJHsGfrwVaTs00ER2z8ljTJPBUtNtOLrwNRlvgdnzyVAKHfOgDBGwJgiwpeE9voB1oAV/mXqSaUWNnuwlOIhvQEBwekqNyWvhLqC7nCAIhj3yvNQKBgQCqYbeec56LAhWP903Zwcj9VvG7sESqXUhIkUqoOkuIBTWFFIm54QLTA1tJxDQGb98heoCIWf5x/A3xNI98RsqNBX5JON6qNWjb7/dobitti3t99v/ptDp9u8JTMC7penoryLKK0Ty3bkan95Kn9SC42YxaSghzqkt+uvfVQgiNGQKBgGxU6P2aDAt6VNwWosHSe+d2WWXt8IZBhO9d6dn0f7ORvcjmCqNKTNGgrkewMZEuVcliueJquR47IROdY8qmwqcBAN7Vg2K7r7CPlTKAWTRYMJxCT1Hi5gwJb+CZF3+IeYqsJk2NF2s0w5WJTE70k1BSvQsfIzAIDz2yE1oPHvwVAoGAA6e+xQkVH4fMEph55RJIZ5goI4Y76BSvt2N5OKZKd4HtaV+eIhM3SDsVYRLIm9ZquJHMiZQGyUGnsvrKL6AAVNK7eQZCRDk9KQz+0GKOGqku0nOZjUbAu6A2/vtXAaAuFSFx1rUQVVjFulLexkXR3KcztL1Qu2k5pB6Si0K/uwQ=";

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

        params.put("data", JSON.toJSONString(bizContent));

        String content = AlipaySignature.getSignContent(params);
        String sign = AlipaySignature.rsa256Sign(content, priKey, "utf-8");

        params.put("sign", sign);

        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

}
