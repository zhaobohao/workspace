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
    String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM4lMCwagCw0Yl3Npabzfma2lxjZjEavg0+OeFCZ9Ss91P5mCLqOab6iVi7KA14Hxw5IAuZPOBeV5/7pJ3B9ElUQxhUA7uGoXQ6h33NM5z6SiWCdYm2pfngKih18AG3RI1L9uyvqEIBa7XtEaduAFor5lokPc1WpdVcTwTfRxgeJAgMBAAECgYAM3XFGL1k0aQiChiUCaEvJKTgAywLgHm/5dRC5JwKP8knqnn+I9P5QcV0jimPvaFjZ4VCdAvCjOC3EUNSvRn7wR2Lb1+BGZZePTdxtHWE2aqJ1W1SvgQTqMsLlPBRPnXo5XH/ng3WEH15ynd5NR035xAluaI0X/y+PsRxE6TlfIQJBAPSYUyXa2yaEqmvIN+ECKALCLLeDdi2YW3Kjahgz0X9V4Y4aTdrHh8y603zXC0Wy8HeOhwGoyciaS8SmjxCMn4UCQQDXweW8xsUreLH8hfVUtyiY/KgUz+R5foJDNXD7TLE9CDoPSHy09qBe99HyVCZg/gNJH4O+tNr6C4916dYaVk01AkBYZ2HOEc8ZmeOaty/zJHtfm9zbqykgi6upwISNINV8Z4bxfHJdO7bKeVANFBBf7a/aFmqXX/EmjxYJioW03o6dAkEAp7ViXJCtJpNU1pNSFZ2hgvmxtSu7zuyVWKSrw8rjYiuI5eRUe13RXsCHgzQB+Ra5exdyEsUGCaL+yosPD73RmQJBALGuM8EQUcBgrpgpeLZ39Ni1DYXYG9aj+u+ar/UL6kI1mCNFgwroO4EVIvXPVxikMxUgiE2tVaBML5nm8VDNJ7s=";

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
    biz_content	    String	是		请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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

        params.put("biz_content", JSON.toJSONString(bizContent));

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
