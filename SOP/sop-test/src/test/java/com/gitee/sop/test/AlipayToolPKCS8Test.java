package com.gitee.sop.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用支付宝工具生成签名，这里演示的是PKCS8（java）
 */
public class AlipayToolPKCS8Test extends TestBase {

    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
    // 平台提供的私钥
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXJv1pQFqWNA/++OYEV7WYXwexZK/J8LY1OWlP9X0T6wHFOvxNKRvMkJ5544SbgsJpVcvRDPrcxmhPbi/sAhdO4x2PiPKIz9Yni2OtYCCeaiE056B+e1O2jXoLeXbfi9fPivJZkxH/tb4xfLkH3bA8ZAQnQsoXA0SguykMRZntF0TndUfvDrLqwhlR8r5iRdZLB6F8o8qXH6UPDfNEnf/K8wX5T4EB1b8x8QJ7Ua4GcIUqeUxGHdQpzNbJdaQvoi06lgccmL+PHzminkFYON7alj1CjDN833j7QMHdPtS9l7B67fOU/p2LAAkPMtoVBfxQt9aFj7B8rEhGCz02iJIBAgMBAAECggEARqOuIpY0v6WtJBfmR3lGIOOokLrhfJrGTLF8CiZMQha+SRJ7/wOLPlsH9SbjPlopyViTXCuYwbzn2tdABigkBHYXxpDV6CJZjzmRZ+FY3S/0POlTFElGojYUJ3CooWiVfyUMhdg5vSuOq0oCny53woFrf32zPHYGiKdvU5Djku1onbDU0Lw8w+5tguuEZ76kZ/lUcccGy5978FFmYpzY/65RHCpvLiLqYyWTtaNT1aQ/9pw4jX9HO9NfdJ9gYFK8r/2f36ZE4hxluAfeOXQfRC/WhPmiw/ReUhxPznG/WgKaa/OaRtAx3inbQ+JuCND7uuKeRe4osP2jLPHPP6AUwQKBgQDUNu3BkLoKaimjGOjCTAwtp71g1oo+k5/uEInAo7lyEwpV0EuUMwLA/HCqUgR4K9pyYV+Oyb8d6f0+Hz0BMD92I2pqlXrD7xV2WzDvyXM3s63NvorRooKcyfd9i6ccMjAyTR2qfLkxv0hlbBbsPHz4BbU63xhTJp3Ghi0/ey/1HQKBgQC2VsgqC6ykfSidZUNLmQZe3J0p/Qf9VLkfrQ+xaHapOs6AzDU2H2osuysqXTLJHsGfrwVaTs00ER2z8ljTJPBUtNtOLrwNRlvgdnzyVAKHfOgDBGwJgiwpeE9voB1oAV/mXqSaUWNnuwlOIhvQEBwekqNyWvhLqC7nCAIhj3yvNQKBgQCqYbeec56LAhWP903Zwcj9VvG7sESqXUhIkUqoOkuIBTWFFIm54QLTA1tJxDQGb98heoCIWf5x/A3xNI98RsqNBX5JON6qNWjb7/dobitti3t99v/ptDp9u8JTMC7penoryLKK0Ty3bkan95Kn9SC42YxaSghzqkt+uvfVQgiNGQKBgGxU6P2aDAt6VNwWosHSe+d2WWXt8IZBhO9d6dn0f7ORvcjmCqNKTNGgrkewMZEuVcliueJquR47IROdY8qmwqcBAN7Vg2K7r7CPlTKAWTRYMJxCT1Hi5gwJb+CZF3+IeYqsJk2NF2s0w5WJTE70k1BSvQsfIzAIDz2yE1oPHvwVAoGAA6e+xQkVH4fMEph55RJIZ5goI4Y76BSvt2N5OKZKd4HtaV+eIhM3SDsVYRLIm9ZquJHMiZQGyUGnsvrKL6AAVNK7eQZCRDk9KQz+0GKOGqku0nOZjUbAu6A2/vtXAaAuFSFx1rUQVVjFulLexkXR3KcztL1Qu2k5pB6Si0K/uwQ=";

    // 公共请求参数
    Map<String, String> params = new HashMap<String, String>();

    {
        params.put("app_id", appId);
        params.put("method", "alipay.story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        // 手动改下这里的时间，改成当前时间
        params.put("timestamp", "2019-12-28 11:23:29");
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "1");
        bizContent.put("name", "葫芦娃");

        params.put("data", JSON.toJSONString(bizContent));
    }

    /**
     * 第一步：生成请求参数。
     * 生成后的字符串放到支付宝工具【请求参数】中，将上面的privateKey放到【商户应用私钥】中
     * 点击【开始签名】
     */
    @Test
    public void testFirst() {
        System.out.println("请求参数：");
        System.out.println(buildParamQuery(params));
    }

    /**
     * 第二步：将生成到签名放到sign变量中，然后运行本方法
     */
    @Test
    public void testSecond() {
        String sign = "Wt80mV3ojQ4++19yUhG1pjElGc5rWRJ0udoPv6yHjDG9h0Ai8/sQx9XaWBfiPR3YU6ZzTznr1gEYeXpJg3X9ptLjkMG3xx5NRkCMTYi9eZ40c8RU13O/mQDDr8can59jYgG3PUCTBSpymBnZsTxAzCuho3p26vm64napu+XlKiWEU1yQz46Ga8hW9EBRFFBWvwSsXqp0ZUuddXIj4nhuRr/miSsGgtGXByRMgOh+FLivZnIZJmjP1fS3Veq4Qp2Pv/V+7r+3DBBclos2OF72O1lrALWA8qrHhyjWyFPuNJv6b7PA0vKUenthSPeKH1qJTmMIqlokRrrrD5Ubt1pDJg==";
        // 这里用支付宝工具生成
        params.put("sign", sign);
        System.out.println("----------- 返回结果 -----------");
        String responseData = get(url, params);// 发送请求
        System.out.println(responseData);
    }

}
