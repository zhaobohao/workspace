package com.gitee.sop.test.pab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.test.Client;
import com.gitee.sop.test.HttpTool;
import com.gitee.sop.test.ParamNames;
import com.gitee.sop.test.TestBase;
import org.junit.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 涵盖所有测试情况，发版前运行这个类，确保功能没有问题。
 *
 * @author tanghc
 */
public class AllInOneTest extends TestBase {

    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
 static String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM4lMCwagCw0Yl3Npabzfma2lxjZjEavg0+OeFCZ9Ss91P5mCLqOab6iVi7KA14Hxw5IAuZPOBeV5/7pJ3B9ElUQxhUA7uGoXQ6h33NM5z6SiWCdYm2pfngKih18AG3RI1L9uyvqEIBa7XtEaduAFor5lokPc1WpdVcTwTfRxgeJAgMBAAECgYAM3XFGL1k0aQiChiUCaEvJKTgAywLgHm/5dRC5JwKP8knqnn+I9P5QcV0jimPvaFjZ4VCdAvCjOC3EUNSvRn7wR2Lb1+BGZZePTdxtHWE2aqJ1W1SvgQTqMsLlPBRPnXo5XH/ng3WEH15ynd5NR035xAluaI0X/y+PsRxE6TlfIQJBAPSYUyXa2yaEqmvIN+ECKALCLLeDdi2YW3Kjahgz0X9V4Y4aTdrHh8y603zXC0Wy8HeOhwGoyciaS8SmjxCMn4UCQQDXweW8xsUreLH8hfVUtyiY/KgUz+R5foJDNXD7TLE9CDoPSHy09qBe99HyVCZg/gNJH4O+tNr6C4916dYaVk01AkBYZ2HOEc8ZmeOaty/zJHtfm9zbqykgi6upwISNINV8Z4bxfHJdO7bKeVANFBBf7a/aFmqXX/EmjxYJioW03o6dAkEAp7ViXJCtJpNU1pNSFZ2hgvmxtSu7zuyVWKSrw8rjYiuI5eRUe13RXsCHgzQB+Ra5exdyEsUGCaL+yosPD73RmQJBALGuM8EQUcBgrpgpeLZ39Ni1DYXYG9aj+u+ar/UL6kI1mCNFgwroO4EVIvXPVxikMxUgiE2tVaBML5nm8VDNJ7s=";
   static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOJTAsGoAsNGJdzaWm835mtpcY2YxGr4NPjnhQmfUrPdT+Zgi6jmm+olYuygNeB8cOSALmTzgXlef+6SdwfRJVEMYVAO7hqF0Ood9zTOc+kolgnWJtqX54CoodfABt0SNS/bsr6hCAWu17RGnbgBaK+ZaJD3NVqXVXE8E30cYHiQIDAQAB";
    private Client client = new Client(url, appId, privateKey,publicKey, AllInOneTest::assertResult);

    /**
     * 以get方式提交
     */
    public void testGet() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("alipay.story.get")
                .version("1.0")
                .bizContent(new BizContent().add("id", "1").add("name", "葫芦娃"))
                .httpMethod(HttpTool.HTTPMethod.POST);

        client.execute(requestBuilder);
    }

    /**
     * 以表单方式提交(application/x-www-form-urlencoded)
     */
    public void testPostForm() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("alipay.story.get")
                .version("1.0")
                .bizContent(new BizContent().add("id", "1").add("name", "葫芦娃"))
                .httpMethod(HttpTool.HTTPMethod.POST);

        client.execute(requestBuilder);
    }

    /**
     * 以json方式提交(application/json)
     */
    public void testPostJSON() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("alipay.story.get")
                .version("1.0")
                // 以json方式提交
                .postJson(true)
                .bizContent(new BizContent().add("id", "1").add("name", "葫芦娃"))
                .httpMethod(HttpTool.HTTPMethod.POST);

        client.execute(requestBuilder);
    }

    /**
     * 测试feign。gateway -> book-service(consumer) -> story-service(provider)
     */
    public void testFeign() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("alipay.book.story.get")
                .version("1.0")
                .bizContent(new BizContent())
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);
    }

    /**
     * 测试dubbo服务，book会调用story提供的服务。参见：DemoConsumerController.java
     */
    public void testDubbo() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("dubbo.story.get")
                .version("1.0")
                .bizContent(new BizContent().add("id", "222"))
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);

    }

    /**
     * 忽略验证,不校验签名，只需传接口名、版本号、业务参BaseExecutorAdapter数
     */
    public void testIgnoreSign() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("story.get")
                .version("2.1")
                .ignoreSign(true)
                .bizContent(new BizContent().add("id", "222").add("name", "忽略222"))
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);
    }

    /**
     * OpenContext参数绑定
     */
    public void testOpenContext() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("story.get")
                .version("2.2")
                .bizContent(new BizContent().add("id", "222").add("name", "openContext"))
                .httpMethod(HttpTool.HTTPMethod.POST)
                .callback((requestInfo, responseData) -> {
                    System.out.println(responseData);
                    JSONObject jsonObject = JSON.parseObject(responseData);
                    String name = jsonObject.getJSONObject("data").getString("name");
                    Assert.assertEquals(name, "appId:" + appId + ", openContext");
                });

        client.execute(requestBuilder);
    }

    /**
     * 其它参数绑定
     */
    public void testOtherParam() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("story.get")
                .version("2.3")
                .bizContent(new BizContent().add("id", "222").add("name", "request param"))
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);
    }

    /**
     * JSR-303参数校验
     */
    public void testJSR303() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("goods.add")
                .version("1.0")
                .bizContent(new BizContent().add("goods_name", "iphone6").add("goods_remark", "iphone6").add("goods_comment", "1"))
                .httpMethod(HttpTool.HTTPMethod.POST)
                .callback((requestInfo, responseData) -> {
                    System.out.println(responseData);
                    JSONObject jsonObject = JSON.parseObject(responseData);
                    String sub_msg = jsonObject.getJSONObject("data").getString("sub_msg");
                    Assert.assertEquals(sub_msg, "商品评论长度必须在3和20之间");
                });

        client.execute(requestBuilder);
    }

    /**
     * 测试是否有权限访问，可在sop-admin中设置权限
     */
    public void testPermission() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("permission.story.get")
                .version("1.0")
                .bizContent(new BizContent())
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);
    }

    /**
     * 演示将接口名版本号跟在url后面，规则:http://host:port/{method}/{version}/
     */
    public void testRestful() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .url("http://localhost:8081/alipay.story.get/1.0/")
                .bizContent(new BizContent().add("name", "name111"))
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);
    }

    /**
     * 演示文件上传
     */
    public void testFile() {
        String root = System.getProperty("user.dir");
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("demo.file.upload")
                .version("1.0")
                .bizContent(new BizContent().add("remark", "test file upload"))
                // 添加文件
                .addFile("file1", new File(root + "/src/main/resources/file1.txt"))
                .addFile("file2", new File(root + "/src/main/resources/file2.txt"))
                .callback((requestInfo, responseData) -> {
                    System.out.println(responseData);
                    JSONObject jsonObject = JSON.parseObject(responseData);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Assert.assertEquals(data.getString("code"), "10000");
                    Assert.assertEquals(data.getJSONArray("files").size(), 2);
                })
                ;

        client.execute(requestBuilder);
    }

    /**
     * 演示文件上传2
     */
    public void testFile2() {
        String root = System.getProperty("user.dir");
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("demo.file.upload2")
                .version("1.0")
                .bizContent(new BizContent().add("remark", "test file upload"))
                // 添加文件
                .addFile("file1", new File(root + "/src/main/resources/file1.txt"))
                .addFile("file2", new File(root + "/src/main/resources/file2.txt"))
                .callback((requestInfo, responseData) -> {
                    JSONObject jsonObject = JSON.parseObject(responseData);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Assert.assertEquals(data.getString("code"), "10000");
                    Assert.assertEquals(data.getJSONArray("files").size(), 2);
                })
                ;

        client.execute(requestBuilder);
    }

    /**
     * 验证中文乱码问题
     */
    public void testString() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("story.string.get")
                .version("1.0")
                .bizContent(new BizContent().add("name", "name111"))
                .httpMethod(HttpTool.HTTPMethod.GET)
                .callback((requestInfo, responseData) -> {
                    JSONObject jsonObject = JSON.parseObject(responseData);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Assert.assertEquals("海底小纵队", data.getString("name"));
                });

        client.execute(requestBuilder);
    }

    /**
     * 限流测试，根据路由id限流
     *
     * @throws InterruptedException
     */
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
                        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                                .method("alipay.story.get")
                                .version("1.2")
                                .bizContent(new BizContent().add("id", "1").add("name", "葫芦娃"))
                                .httpMethod(HttpTool.HTTPMethod.GET);

                        client.execute(requestBuilder);
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

    public void testToken() {
        Client.RequestBuilder requestBuilder = new Client.RequestBuilder()
                .method("story.token.get")
                .version("1.0")
                .bizContent(new BizContent().add("id", "1").add("name", "葫芦娃"))
                .appAuthToken("asdfasdfadsf")
                .httpMethod(HttpTool.HTTPMethod.GET);

        client.execute(requestBuilder);
    }

    class BizContent extends HashMap<String, String> {
        public BizContent add(String key, String value) {
            this.put(key, value);
            return this;
        }
    }

    public static void assertResult(Client.RequestInfo requestInfo, String responseData) {
        System.out.println(responseData);
        String method = requestInfo.getMethod();
        if (method == null) {
            return;
        }
        String node = "data";
        JSONObject jsonObject=JSON.parseObject(responseData);

        try {
            Boolean isCheckSign=Boolean.FALSE;
            Boolean isDecrypt=Boolean.FALSE;
            if(jsonObject.containsKey("sign")){
                isCheckSign=Boolean.TRUE;
                isDecrypt=Boolean.TRUE;
            jsonObject.put(node,JSON.parseObject(PabSignature.checkClientSignAndDecryptBySignType(jsonObject,AllInOneTest.publicKey,AllInOneTest.privateKey,isCheckSign,isDecrypt,jsonObject.getString(ParamNames.SIGN_TYPE_NAME))));
            }
            System.out.println(jsonObject.toJSONString());
        } catch (PabApiException e) {
            e.printStackTrace();
        }
        String code = Optional.ofNullable(jsonObject).map(json -> json.getString("code")).orElse("20000");
        Assert.assertEquals("10000", code);
    }


}
