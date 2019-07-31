package com.gitee.durcframework.easyopen;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.verify.DefaultMd5Verifier;

import junit.framework.TestCase;

public class PostTest extends TestCase {
    
    private DefaultMd5Verifier signer = new DefaultMd5Verifier();
    {
        ApiConfig apiConfig = new ApiConfig();
        ApiContext.setApiConfig(apiConfig);
    }

    @Test
    public void testPost() throws IOException {
        String appKey = "test";
        String secret = "123456";
        // 业务参数
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put("goodsName", "iphoneX");

        String json = JSON.toJSONString(jsonMap);
        json = URLEncoder.encode(json, "utf-8");

        // 系统参数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", "goods.get");
        param.put("app_key", appKey);
        param.put("data", json);
        param.put("timestamp", getTime());
        param.put("version", "");

        String sign = signer.buildSign(param, secret);

        param.put("sign", sign);

        System.out.println("=====请求数据=====");
        String postJson = JSON.toJSONString(param);
        System.out.println(postJson);
        // String resp = HttpUtil.post(postJson); // 发送请求
    }

    public String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
