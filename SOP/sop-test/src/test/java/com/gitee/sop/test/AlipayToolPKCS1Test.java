package com.gitee.sop.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用支付宝工具生成签名，这里演示的是PKCS1（非java）
 */
public class AlipayToolPKCS1Test extends TestBase {

    String url = "http://localhost:8081";
    String appId = "201904035630907729292csharp";
    // 平台提供的私钥
    String privateKey = "MIIEowIBAAKCAQEA5+OvJxeSzf44NxQ/cl7Ii+BzPg2k6sRcvH4ffOtU5Dzq1/oEvg02nxIhmwOHBZmjbmuUu0aLsfglUTAwqfXftfAKZidshsgj9NNh0/kxk0avRZ1UoljWGz/FxVZA0ogbxxhohPZ9jWcD+eBQcIwF2DtHfAJqWWZrYFnCMeHD8mPzxo2kwXSvDzi0vf9I2tKiYvNG26a9FqeYtPOoi81sdS3+70HOMdxP8ejXtyfnKpKz7Dx506LCIRS5moWS3Q5eTLV3NGX/1CSJ8wpQA2DAQTjVhX5eVu7Yqz12t8W+sjWM/tHUR6cgwYYR10p7tSCeCPzkigjGxKm4cYXWtATQJQIDAQABAoIBAHFDsgrrJca+NKEan77ycwx3jnKx4WrWjOF4zVKL9AQjiSYDNgvKknJyPb3kpC/lEoHdxGERHSzJoxib7DkoIqRQYhPxj73pxj5QfYk3P7LLJNNg/LTrpXDb3nL8JV9wIflGf87qQvstZTDJEyFWE4jBs7Hr0BxovWvri8InnzkmERJ1cbGJgNHe1Y3Zo2tw0yaHxQCxLuajP+notRZhD9bEp7uKeI0w9AvlW6k8m/7y10F0BK/TlyW8rQiEC391yOiRYoMcUh4hd2Q9bMx3jngZgX8PXIvZZcup4/pvWlv1alwhB2tsnLdazP62r1MO80vLyLunzGO+7WwCjEYlVaECgYEA+lQRFmbhKaPuAuXMtY31Fbga8nedka5TjnEV7+/kX+yowE2OlNujF+ZG8UTddTxAGv56yVNi/mjRlgD74j8z0eOsgvOq9mwbCrgLhLo51H9O/wAxtb+hBKtC5l50pBr4gER6d8W6EQNTSGojnMIaLXTkAZ5Qf6Z8e2HFVdOn0X0CgYEA7SSrTokwzukt5KldNu5ukyyd+C3D1i6orbg6qD73EP9CfNMfGSBn7dDv9wMSJH01+Ty+RgTROgtjGRDbMJWnfbdt/61NePr9ar5sb6Nbsf7/I0w7cZF5dsaFYgzaOfQYquzXPbLQHkpMT64bqpv/Mwy4F2lFvaYWY5fA4pC2uckCgYEAg75Ym9ybJaoTqky8ttQ2Jy8UZ4VSVQhVC0My02sCWwWXLlXi8y7An+Rec73Ve0yxREOn5WrQT6pkmzh7V/ABWrYi5WxODpCIjtSbo0fLBa3Wqle00b0/hdCITetqIa/cFs1zUrOqICgK3bKWeXqiAkhhcwSZwwSgwOKM04Wn7ZUCgYBvhHX2mbdVJfyJ8kc+hMOE/E9RHRxiBVEXWHJlGi8PVCqNDq8qHr4g7Mdbzprig+s0yKblwHAvrpkseWvKHiZEjVTyDipHgShY4TGXEigVvUd37uppTrLi8xpYcJjS9gH/px7VCdiq1d+q/MJP6coJ1KphgATm2UrgDMYNBWaYWQKBgEHRxrmER7btUF60/YgcqPHFc8RpYQB2ZZE0kyKGDqk2Data1XYUY6vsPAU28yRLAaWr/D2H17iyLkxP80VLm6QhifxCadv90Q/Wl1DFfOJQMW6avyQ0so6G0wFq/LJxaFK4iLXQn1RJnmTp6BYiJMmK2BhFbRzw8ssMoF6ad2rr";

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

        params.put("biz_content", JSON.toJSONString(bizContent));
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
        String sign = "sOK1Kwoq6OmjFoneb6LRgUpX+/uQ9D9gsV1Cgi6aw/ErUWLYqMkESuJ148uV1rk5ms8ok9n4T4LpM7aJEYLo9IguW5eVCQ+ePe3ea6gjEQIZm2fiymfSdasSrrBDN/oLqhFwWRGzTQ5TWAgxpNCLH2zKMc+Z5ir7W6xeA0CcEmhZYXC3fB4MSnPcCbHj4V0LLAoQZZQ3voHpdh+6OYNzMKcrwJTTbukI6LbVPopO44ZanV11ehytKLJB71wGJlvFIMK+N3Gph01JKFvfzOlMmndGiDodOjgSz+aQBIjlcyCea/PXSzr2fa5efvWDX/5QayhWkWQ3/1ocBMj7/d4pMA==";
        // 这里用支付宝工具生成
        params.put("sign", sign);
        System.out.println("----------- 返回结果 -----------");
        String responseData = get(url, params);// 发送请求
        System.out.println(responseData);
    }

}
