package com.gitee.sop.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.sdk.client.OpenClient;
import com.gitee.sop.sdk.common.UploadFile;
import com.gitee.sop.sdk.model.DemoFileUploadModel;
import com.gitee.sop.sdk.model.GetStoryModel;
import com.gitee.sop.sdk.request.CommonRequest;
import com.gitee.sop.sdk.request.DemoFileUploadRequest;
import com.gitee.sop.sdk.request.GetStoryRequest;
import com.gitee.sop.sdk.response.CommonResponse;
import com.gitee.sop.sdk.response.DemoFileUploadResponse;
import com.gitee.sop.sdk.response.GetStoryResponse;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SdkTest extends TestCase {
    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
    /** 开发者私钥 */
    String privateKeyIsv = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXJv1pQFqWNA/++OYEV7WYXwexZK/J8LY1OWlP9X0T6wHFOvxNKRvMkJ5544SbgsJpVcvRDPrcxmhPbi/sAhdO4x2PiPKIz9Yni2OtYCCeaiE056B+e1O2jXoLeXbfi9fPivJZkxH/tb4xfLkH3bA8ZAQnQsoXA0SguykMRZntF0TndUfvDrLqwhlR8r5iRdZLB6F8o8qXH6UPDfNEnf/K8wX5T4EB1b8x8QJ7Ua4GcIUqeUxGHdQpzNbJdaQvoi06lgccmL+PHzminkFYON7alj1CjDN833j7QMHdPtS9l7B67fOU/p2LAAkPMtoVBfxQt9aFj7B8rEhGCz02iJIBAgMBAAECggEARqOuIpY0v6WtJBfmR3lGIOOokLrhfJrGTLF8CiZMQha+SRJ7/wOLPlsH9SbjPlopyViTXCuYwbzn2tdABigkBHYXxpDV6CJZjzmRZ+FY3S/0POlTFElGojYUJ3CooWiVfyUMhdg5vSuOq0oCny53woFrf32zPHYGiKdvU5Djku1onbDU0Lw8w+5tguuEZ76kZ/lUcccGy5978FFmYpzY/65RHCpvLiLqYyWTtaNT1aQ/9pw4jX9HO9NfdJ9gYFK8r/2f36ZE4hxluAfeOXQfRC/WhPmiw/ReUhxPznG/WgKaa/OaRtAx3inbQ+JuCND7uuKeRe4osP2jLPHPP6AUwQKBgQDUNu3BkLoKaimjGOjCTAwtp71g1oo+k5/uEInAo7lyEwpV0EuUMwLA/HCqUgR4K9pyYV+Oyb8d6f0+Hz0BMD92I2pqlXrD7xV2WzDvyXM3s63NvorRooKcyfd9i6ccMjAyTR2qfLkxv0hlbBbsPHz4BbU63xhTJp3Ghi0/ey/1HQKBgQC2VsgqC6ykfSidZUNLmQZe3J0p/Qf9VLkfrQ+xaHapOs6AzDU2H2osuysqXTLJHsGfrwVaTs00ER2z8ljTJPBUtNtOLrwNRlvgdnzyVAKHfOgDBGwJgiwpeE9voB1oAV/mXqSaUWNnuwlOIhvQEBwekqNyWvhLqC7nCAIhj3yvNQKBgQCqYbeec56LAhWP903Zwcj9VvG7sESqXUhIkUqoOkuIBTWFFIm54QLTA1tJxDQGb98heoCIWf5x/A3xNI98RsqNBX5JON6qNWjb7/dobitti3t99v/ptDp9u8JTMC7penoryLKK0Ty3bkan95Kn9SC42YxaSghzqkt+uvfVQgiNGQKBgGxU6P2aDAt6VNwWosHSe+d2WWXt8IZBhO9d6dn0f7ORvcjmCqNKTNGgrkewMZEuVcliueJquR47IROdY8qmwqcBAN7Vg2K7r7CPlTKAWTRYMJxCT1Hi5gwJb+CZF3+IeYqsJk2NF2s0w5WJTE70k1BSvQsfIzAIDz2yE1oPHvwVAoGAA6e+xQkVH4fMEph55RJIZ5goI4Y76BSvt2N5OKZKd4HtaV+eIhM3SDsVYRLIm9ZquJHMiZQGyUGnsvrKL6AAVNK7eQZCRDk9KQz+0GKOGqku0nOZjUbAu6A2/vtXAaAuFSFx1rUQVVjFulLexkXR3KcztL1Qu2k5pB6Si0K/uwQ=";
    /** 开放平台提供的公钥
     * 前往SOP-ADMIN，ISV管理--秘钥管理，生成平台提供的公私钥，然后把【平台公钥】放到这里 */
    String publicKeyPlatform = "";

    // 声明一个就行
    OpenClient client = new OpenClient(url, appId, privateKeyIsv, publicKeyPlatform);

    // 标准用法
    @Test
    public void testGet() {
        // 创建请求对象
        GetStoryRequest request = new GetStoryRequest();
        // 请求参数
        GetStoryModel model = new GetStoryModel();
        model.setName("白雪公主");
        request.setBizModel(model);

        // 发送请求
        GetStoryResponse response = client.execute(request);

        if (response.isSuccess()) {
            // 返回结果
            System.out.println(String.format("成功！response:%s\n响应原始内容:%s",
                    JSON.toJSONString(response), response.getBody()));
        } else {
            System.out.println("错误，subCode:" + response.getSubCode() + ", subMsg:" + response.getSubMsg());
        }
    }


    // 懒人版，如果不想添加Request,Response,Model。可以用这种方式，返回全部是String，后续自己处理json
    @Test
    public void testLazy() {
        // 创建请求对象
        CommonRequest request = new CommonRequest("alipay.story.find");
        // 请求参数
        Map<String, Object> bizModel = new HashMap<>();
        bizModel.put("name", "白雪公主");
        request.setBizModel(bizModel);

        // 发送请求
        CommonResponse response = client.execute(request);

        if (response.isSuccess()) {
            // 返回结果
            String body = response.getBody();
            JSONObject jsonObject = JSON.parseObject(body);
            System.out.println(jsonObject);
        } else {
            System.out.println("错误，subCode:" + response.getSubCode() + ", subMsg:" + response.getSubMsg());
        }
    }

    // 文件上传
    @Test
    public void testUpload() throws IOException {
        DemoFileUploadRequest request = new DemoFileUploadRequest();

        DemoFileUploadModel model = new DemoFileUploadModel();
        model.setRemark("上传文件参数");
        request.setBizModel(model);

        String root = System.getProperty("user.dir");
        System.out.println(root);
        // 这里演示将resources下的两个文件上传到服务器
        request.addFile(new UploadFile("file1", new File(root + "/src/main/resources/file1.txt")));
        request.addFile(new UploadFile("file2", new File(root + "/src/main/resources/file2.txt")));

        DemoFileUploadResponse response = client.execute(request);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            List<DemoFileUploadResponse.FileMeta> responseFiles = response.getFiles();
            System.out.println("您上传的文件信息：");
            responseFiles.forEach(System.out::println);
        } else {
            System.out.println(JSON.toJSONString(response));
        }
        System.out.println("--------------------");
    }

}
