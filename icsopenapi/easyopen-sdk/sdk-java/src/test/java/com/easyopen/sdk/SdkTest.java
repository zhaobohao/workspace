package com.easyopen.sdk;

import com.easyopen.sdk.client.EncryptClient;
import com.easyopen.sdk.client.OpenHttp;
import com.easyopen.sdk.common.OpenConfig;
import com.easyopen.sdk.common.UploadFile;
import com.easyopen.sdk.model.Goods;
import com.easyopen.sdk.param.GoodsParam;
import com.easyopen.sdk.request.CommonRequest;
import com.easyopen.sdk.request.FileUploadRequest;
import com.easyopen.sdk.request.GetGoodsRequest;
import com.easyopen.sdk.request.HelloRequest;
import com.easyopen.sdk.response.CommonResponse;
import com.easyopen.sdk.response.FileUploadResponse;
import com.easyopen.sdk.response.GetGoodsResponse;
import com.easyopen.sdk.response.HelloResponse;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SdkTest extends BaseTest {

    OpenHttp openHttp = new OpenHttp(new OpenConfig());

    @Test
    public void testHello() throws Exception {
        HelloRequest request = new HelloRequest();
//        request.setVersion("1.0");
        HelloResponse result = client.execute(request); // 发送请求
        if (result.isSuccess()) {
            String resp = result.getData();
            System.out.println(resp); // 返回hello world
        } else {
            throw new RuntimeException(result.getMsg());
        }
    }

    // 标准用法
    @Test
    public void testGet() throws Exception {
        // 创建请求对象
        GetGoodsRequest request = new GetGoodsRequest();
        // 请求参数
        GoodsParam param = new GoodsParam();
        param.setGoods_name("iphone6");
        request.setParam(param);

        // 发送请求
        GetGoodsResponse response = client.execute(request);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            // 返回结果
            Goods goods = response.getData();
            System.out.println(goods);
        } else {
            System.out.println("errorCode:" + response.getCode() + ",errorMsg:" + response.getMsg());
        }
        System.out.println("--------------------");
    }


    // 先浏览器访问http://localhost:8080/go_oauth2 获取accessToken
    @Test
    public void testGetToken() throws Exception {
        String accessToken = "a8498f31d9f1e2a73e03b41db8cddf3d";

        GoodsParam param = new GoodsParam();
        param.setGoods_name("iphone6");
        GetGoodsRequest request = new GetGoodsRequest();
        request.setName("user.goods.get");
        request.setAccess_token(accessToken);
        request.setParam(param);

        GetGoodsResponse response = client.execute(request);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            Goods goods = response.getData();
            System.out.println(goods);
        } else {
            System.out.println("errorCode:" + response.getCode() + ",errorMsg:" + response.getMsg());
        }
        System.out.println("--------------------");
    }

    @Test
    public void testGetWithJwt() throws Exception {
        String jwt = openHttp.get("http://localhost:8080/jwtLogin", null);
        System.out.println("jwt:" + jwt);

        GetGoodsRequest request = new GetGoodsRequest();
        request.setName("userjwt.goods.get");
        GoodsParam param = new GoodsParam();
        param.setGoods_name("iphone6");

        request.setParam(param);

        GetGoodsResponse response = client.execute(request, jwt);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            Goods goods = response.getData();
            System.out.println(goods);
        } else {
            throw new RuntimeException(response.getMsg());
        }
        System.out.println("--------------------");
    }

    /**
     * 上传文件，读取本地文件
     *
     * @throws IOException
     */
    @Test
    public void testUpload() throws IOException {
        FileUploadRequest request = new FileUploadRequest();

        GoodsParam param = new GoodsParam();
        param.setGoods_name("iphone6");
        request.setParam(param);

        List<UploadFile> files = new ArrayList<>();
        String path = this.getClass().getResource("").getPath();
        files.add(new UploadFile("headImg", new File(path + "BaseTest.class")));
        files.add(new UploadFile("idcardImg", new File(path + "SdkTest.class")));
        request.setFiles(files);

        FileUploadResponse response = client.execute(request);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            String data = response.getData();
            System.out.println(data);
        } else {
            System.out.println("errorCode:" + response.getCode() + ",errorMsg:" + response.getMsg());
        }
        System.out.println("--------------------");
    }

    // 公私钥加密传输
    @Test
    public void testSsl() {
        // 公钥
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbNQvVpS25TzGN7EOK4N/YMmq+sgCL/8y2mHn9F4RPJonEu19GAgISVzPiY8Q9C9i+t9H9KvcjBy+EODdhl2SWpfI76U+ArIHMeSSrfOo322rKOzGAippa+Qh4fb99Al4sLZy739b2Lh+WdYGzbqClnlPBe4M01CVPPEHYjmqoRQIDAQAB";

        EncryptClient encryptClient = new EncryptClient(url, appKey, secret, publicKey);

        // 创建请求对象
        GetGoodsRequest request = new GetGoodsRequest();
        // 请求参数
        GoodsParam param = new GoodsParam();
        param.setGoods_name("iphone6");
        request.setParam(param);

        // 发送请求
        GetGoodsResponse response = encryptClient.execute(request);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            // 返回结果
            Goods goods = response.getData();
            System.out.println(goods);
        } else {
            System.out.println("errorCode:" + response.getCode() + ",errorMsg:" + response.getMsg());
        }
        System.out.println("--------------------");
    }

    // 懒人版，如果不想添加Request,Response,Param。可以用这种方式，返回全部是String，后续自己处理json
    @Test
    public void testLazy() {
        // 创建请求对象
        CommonRequest request = new CommonRequest("goods.get");
        // 请求参数
        Map<String, Object> param = new HashMap<>();
        param.put("goods_name", "iphone6");
        request.setParam(param);

        // 发送请求
        CommonResponse response = client.execute(request);

        System.out.println("--------------------");
        if (response.isSuccess()) {
            // 返回结果
            Map<String, Object> goods = response.getData();
            System.out.println(goods.get("goods_name"));
        } else {
            System.out.println("errorCode:" + response.getCode() + ",errorMsg:" + response.getMsg());
        }
        System.out.println("--------------------");
    }

}
