package com.gitee.sop.test;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.test.alipay.AlipayApiException;
import com.gitee.sop.test.alipay.AlipaySignature;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 模仿支付宝客户端请求接口
 */
public class AlipayClientPostTest extends TestBase {

    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
    // 平台提供的私钥
    String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXJv1pQFqWNA/++OYEV7WYXwexZK/J8LY1OWlP9X0T6wHFOvxNKRvMkJ5544SbgsJpVcvRDPrcxmhPbi/sAhdO4x2PiPKIz9Yni2OtYCCeaiE056B+e1O2jXoLeXbfi9fPivJZkxH/tb4xfLkH3bA8ZAQnQsoXA0SguykMRZntF0TndUfvDrLqwhlR8r5iRdZLB6F8o8qXH6UPDfNEnf/K8wX5T4EB1b8x8QJ7Ua4GcIUqeUxGHdQpzNbJdaQvoi06lgccmL+PHzminkFYON7alj1CjDN833j7QMHdPtS9l7B67fOU/p2LAAkPMtoVBfxQt9aFj7B8rEhGCz02iJIBAgMBAAECggEARqOuIpY0v6WtJBfmR3lGIOOokLrhfJrGTLF8CiZMQha+SRJ7/wOLPlsH9SbjPlopyViTXCuYwbzn2tdABigkBHYXxpDV6CJZjzmRZ+FY3S/0POlTFElGojYUJ3CooWiVfyUMhdg5vSuOq0oCny53woFrf32zPHYGiKdvU5Djku1onbDU0Lw8w+5tguuEZ76kZ/lUcccGy5978FFmYpzY/65RHCpvLiLqYyWTtaNT1aQ/9pw4jX9HO9NfdJ9gYFK8r/2f36ZE4hxluAfeOXQfRC/WhPmiw/ReUhxPznG/WgKaa/OaRtAx3inbQ+JuCND7uuKeRe4osP2jLPHPP6AUwQKBgQDUNu3BkLoKaimjGOjCTAwtp71g1oo+k5/uEInAo7lyEwpV0EuUMwLA/HCqUgR4K9pyYV+Oyb8d6f0+Hz0BMD92I2pqlXrD7xV2WzDvyXM3s63NvorRooKcyfd9i6ccMjAyTR2qfLkxv0hlbBbsPHz4BbU63xhTJp3Ghi0/ey/1HQKBgQC2VsgqC6ykfSidZUNLmQZe3J0p/Qf9VLkfrQ+xaHapOs6AzDU2H2osuysqXTLJHsGfrwVaTs00ER2z8ljTJPBUtNtOLrwNRlvgdnzyVAKHfOgDBGwJgiwpeE9voB1oAV/mXqSaUWNnuwlOIhvQEBwekqNyWvhLqC7nCAIhj3yvNQKBgQCqYbeec56LAhWP903Zwcj9VvG7sESqXUhIkUqoOkuIBTWFFIm54QLTA1tJxDQGb98heoCIWf5x/A3xNI98RsqNBX5JON6qNWjb7/dobitti3t99v/ptDp9u8JTMC7penoryLKK0Ty3bkan95Kn9SC42YxaSghzqkt+uvfVQgiNGQKBgGxU6P2aDAt6VNwWosHSe+d2WWXt8IZBhO9d6dn0f7ORvcjmCqNKTNGgrkewMZEuVcliueJquR47IROdY8qmwqcBAN7Vg2K7r7CPlTKAWTRYMJxCT1Hi5gwJb+CZF3+IeYqsJk2NF2s0w5WJTE70k1BSvQsfIzAIDz2yE1oPHvwVAoGAA6e+xQkVH4fMEph55RJIZ5goI4Y76BSvt2N5OKZKd4HtaV+eIhM3SDsVYRLIm9ZquJHMiZQGyUGnsvrKL6AAVNK7eQZCRDk9KQz+0GKOGqku0nOZjUbAu6A2/vtXAaAuFSFx1rUQVVjFulLexkXR3KcztL1Qu2k5pB6Si0K/uwQ=";

    /**
    参数	            类型	    是否必填	    最大长度	    描述	            示例值
    app_id	        String	是	        32	    支付宝分配给开发者的应用ID	2014072300007148
    method	        String	是	        128	    接口名称	alipay.trade.fastpay.refund.query
    format	        String	否	        40	    仅支持JSON	JSON
    charset	        String	是	        10	    请求使用的编码格式，如utf-8,gbk,gb2312等	utf-8
    sign_type	    String	是	        10	    商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2	RSA2
    sign	        String	是	        344	    商户请求参数的签名串，详见签名	详见示例
    timestamp	    String	是	        19	    发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"	2014-07-24 03:07:50
    version	        String	是	        3	    调用的接口版本，固定为：1.0	1.0
    app_auth_token	String	否	        40	    详见应用授权概述
     data	    String	是		请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
     */
    // 这个请求会路由到story服务
    @Test
    public void testGet() throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
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

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);
        System.out.println("URL参数：" + buildUrlQuery(params));

        System.out.println("----------- 返回结果 -----------");
        String responseData = get(url, params);// 发送请求
        System.out.println(responseData);
    }


    // 这个请求会路由到book服务
    @Test
    public void testPostBook() throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "alipay.book.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "1");
        bizContent.put("name", "葫芦娃");
//        bizContent.put("name", "葫芦娃1234567890葫芦娃1234567890"); // 超出长度

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

    // 这个请求会路由到book服务，然后再调用story服务
    // gateway -> book-service -> story-service
    @Test
    public void testPostBook2() throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "alipay.book.story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "1");
        bizContent.put("name", "葫芦娃");
//        bizContent.put("name", "葫芦娃1234567890葫芦娃1234567890"); // 超出长度

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

    // 测试dubbo服务，book会调用story提供的服务。参见：DemoConsumerController.java
    @Test
    public void testDubboDemo() throws Exception {
        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "dubbo.story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "222");

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

    // 忽略验证,不校验签名，只需传接口名、版本号、业务参数
    @Test
    public void testIgnore() {
        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("method", "story.get");
        params.put("version", "2.1");
        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "222");
        bizContent.put("name", "忽略验证name");

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 返回结果 -----------");
        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

    @Test
    public void testStoryget() throws AlipayApiException {
        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "story.get");
        params.put("version", "2.2");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("id", "222");
        bizContent.put("name", "忽略验证22");

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

    @Test
    public void testGetStory2() throws AlipayApiException {
        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "getStory2");
        params.put("version", "1.0");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("name", "Jim");
        bizContent.put("age", "2");

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = get(url, params);// 发送请求
        System.out.println(responseData);
    }

    @Test
    public void testGetJson() throws AlipayApiException {
        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "getJson");
        params.put("version", "1.0");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 业务参数
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("name", "Jim");
        bizContent.put("age", "2");

        params.put("data", JSON.toJSONString(bizContent));

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + privateKey);
        String content = AlipaySignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = AlipaySignature.rsa256Sign(content, privateKey, "utf-8");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = get(url, params);// 发送请求
        System.out.println(responseData);
    }

}
