package com.gitee.sop.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.sdk.client.OpenClient;
import com.gitee.sop.sdk.common.CustomDataNameBuilder;
import com.gitee.sop.sdk.common.OpenConfig;
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

public class PabSdkTest extends TestCase {
    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
    /** 开发者私钥 */
    String privateKeyIsv = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM4lMCwagCw0Yl3Npabzfma2lxjZjEavg0+OeFCZ9Ss91P5mCLqOab6iVi7KA14Hxw5IAuZPOBeV5/7pJ3B9ElUQxhUA7uGoXQ6h33NM5z6SiWCdYm2pfngKih18AG3RI1L9uyvqEIBa7XtEaduAFor5lokPc1WpdVcTwTfRxgeJAgMBAAECgYAM3XFGL1k0aQiChiUCaEvJKTgAywLgHm/5dRC5JwKP8knqnn+I9P5QcV0jimPvaFjZ4VCdAvCjOC3EUNSvRn7wR2Lb1+BGZZePTdxtHWE2aqJ1W1SvgQTqMsLlPBRPnXo5XH/ng3WEH15ynd5NR035xAluaI0X/y+PsRxE6TlfIQJBAPSYUyXa2yaEqmvIN+ECKALCLLeDdi2YW3Kjahgz0X9V4Y4aTdrHh8y603zXC0Wy8HeOhwGoyciaS8SmjxCMn4UCQQDXweW8xsUreLH8hfVUtyiY/KgUz+R5foJDNXD7TLE9CDoPSHy09qBe99HyVCZg/gNJH4O+tNr6C4916dYaVk01AkBYZ2HOEc8ZmeOaty/zJHtfm9zbqykgi6upwISNINV8Z4bxfHJdO7bKeVANFBBf7a/aFmqXX/EmjxYJioW03o6dAkEAp7ViXJCtJpNU1pNSFZ2hgvmxtSu7zuyVWKSrw8rjYiuI5eRUe13RXsCHgzQB+Ra5exdyEsUGCaL+yosPD73RmQJBALGuM8EQUcBgrpgpeLZ39Ni1DYXYG9aj+u+ar/UL6kI1mCNFgwroO4EVIvXPVxikMxUgiE2tVaBML5nm8VDNJ7s=";
    /** 开放平台提供的公钥
     * 前往SOP-ADMIN，ISV管理--秘钥管理，生成平台提供的公私钥，然后把【平台公钥】放到这里 */
    String publicKeyPlatform="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOJTAsGoAsNGJdzaWm835mtpcY2YxGr4NPjnhQmfUrPdT+Zgi6jmm+olYuygNeB8cOSALmTzgXlef+6SdwfRJVEMYVAO7hqF0Ood9zTOc+kolgnWJtqX54CoodfABt0SNS/bsr6hCAWu17RGnbgBaK+ZaJD3NVqXVXE8E30cYHiQIDAQAB";
    // 声明一个就行

    OpenClient client = new OpenClient(url, appId, privateKeyIsv, publicKeyPlatform,new OpenConfig().setDataNameBuilder(new CustomDataNameBuilder("data")).setEncrptBizContent(true));

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
