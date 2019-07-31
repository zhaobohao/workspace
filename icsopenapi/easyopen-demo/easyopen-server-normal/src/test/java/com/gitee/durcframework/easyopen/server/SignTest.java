package com.gitee.durcframework.easyopen.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.verify.DefaultMd5Verifier;

import junit.framework.TestCase;

public class SignTest extends TestCase {

    private DefaultMd5Verifier signer = new DefaultMd5Verifier();
    {
        ApiConfig apiConfig = new ApiConfig();
        ApiContext.setApiConfig(apiConfig);
    }

    @Test
    public void testPost() throws IOException {
        String appKey = "test";
        String secret = "123456";
        /*
         * {
    "access_token" = "";
    "app_key" = test;
    data = "%7B%22pageSize%22:%2210%22,%22pageIndex%22:%221%22%7D";
    format = json;
    name = "nologin.activity.page";
    sign = C1AD013FD8CD4E114C3E4604813E8AD8;
    timestamp = "2018-03-21 14:38:09";
    version = "1.0.0";
}
         */
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", "nologin.activity.page");
        param.put("app_key", appKey);
        param.put("data", "%7B%22pageSize%22:%2210%22,%22pageIndex%22:%221%22%7D");
        param.put("timestamp", "2018-03-21 14:38:09");
        param.put("format", "json");
        param.put("access_token", "");
        param.put("version", "1.0.0");

        String sign = signer.buildSign(param, secret);

        System.out.println(sign);
    }
}
