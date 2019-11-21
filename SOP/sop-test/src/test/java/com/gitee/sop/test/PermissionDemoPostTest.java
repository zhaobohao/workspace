package com.gitee.sop.test;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.test.alipay.AlipaySignature;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试是否有权限访问，可在sop-admin中设置权限
 */
public class PermissionDemoPostTest extends TestBase {

    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
    // 平台提供的私钥
  String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM4lMCwagCw0Yl3Npabzfma2lxjZjEavg0+OeFCZ9Ss91P5mCLqOab6iVi7KA14Hxw5IAuZPOBeV5/7pJ3B9ElUQxhUA7uGoXQ6h33NM5z6SiWCdYm2pfngKih18AG3RI1L9uyvqEIBa7XtEaduAFor5lokPc1WpdVcTwTfRxgeJAgMBAAECgYAM3XFGL1k0aQiChiUCaEvJKTgAywLgHm/5dRC5JwKP8knqnn+I9P5QcV0jimPvaFjZ4VCdAvCjOC3EUNSvRn7wR2Lb1+BGZZePTdxtHWE2aqJ1W1SvgQTqMsLlPBRPnXo5XH/ng3WEH15ynd5NR035xAluaI0X/y+PsRxE6TlfIQJBAPSYUyXa2yaEqmvIN+ECKALCLLeDdi2YW3Kjahgz0X9V4Y4aTdrHh8y603zXC0Wy8HeOhwGoyciaS8SmjxCMn4UCQQDXweW8xsUreLH8hfVUtyiY/KgUz+R5foJDNXD7TLE9CDoPSHy09qBe99HyVCZg/gNJH4O+tNr6C4916dYaVk01AkBYZ2HOEc8ZmeOaty/zJHtfm9zbqykgi6upwISNINV8Z4bxfHJdO7bKeVANFBBf7a/aFmqXX/EmjxYJioW03o6dAkEAp7ViXJCtJpNU1pNSFZ2hgvmxtSu7zuyVWKSrw8rjYiuI5eRUe13RXsCHgzQB+Ra5exdyEsUGCaL+yosPD73RmQJBALGuM8EQUcBgrpgpeLZ39Ni1DYXYG9aj+u+ar/UL6kI1mCNFgwroO4EVIvXPVxikMxUgiE2tVaBML5nm8VDNJ7s=";

    @Test
    public void testPost() throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", appId);
        params.put("method", "permission.story.get");
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("version", "1.0");

        // 业务参数
        Map<String, String> bizContent = new HashMap<>();

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

}
