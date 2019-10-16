package com.gitee.sop.test;

import com.gitee.sop.test.taobao.TaobaoSignature;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 模仿淘宝客户端请求接口
 */
public class TaobaoClientPostTest extends TestBase {


    String url = "http://localhost:8081";
    String appId = "taobao_test";
    // 淘宝私钥
    String secret = "G9w0BAQEFAAOCAQ8AMIIBCgKCA";

    /*
参数名称	参数类型	是否必须	参数描述
method	String	是	API接口名称。
app_key	String	是	TOP分配给应用的AppKey。这里要注意正式环境和沙箱环境的AppKey是不同的（包括AppSecret），使用时要注意区分；进入开放平台控制台“应用管理-概览” 和 “应用管理-沙箱环境管理”可分别查看正式环境及沙箱环境的AppKey、AppSecret
session	String	否	用户登录授权成功后，TOP颁发给应用的授权信息，详细介绍请点击这里。当此API文档的标签上注明：“需要授权”，则此参数必传；“不需要授权”，则此参数不需要传；“可选授权”，则此参数为可选。
timestamp	String	是	时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2016-01-01 12:00:00。淘宝API服务端允许客户端请求最大时间误差为10分钟。
format	String	否	响应格式。默认为xml格式，可选值：xml，json。
v	String	是	API协议版本，可选值：2.0。
partner_id	String	否	合作伙伴身份标识。
target_app_key	String	否	被调用的目标AppKey，仅当被调用的API为第三方ISV提供时有效。
simplify	Boolean	否	是否采用精简JSON返回格式，仅当format=json时有效，默认值为：false。
sign_method	String	是	签名的摘要算法，可选值为：hmac，md5。
sign	String	是	API输入参数签名结果，签名算法参照下面的介绍。
     */
    @Test
    public void testPost() throws Exception {

        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_key", appId);
        params.put("method", "alipay.story.get");
        params.put("format", "json");
        params.put("sign_method", "md5");
        params.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("v", "");

        // 业务参数
        params.put("id", "1");
        params.put("name", "葫芦娃");
//        bizContent.put("name", "葫芦娃1234567890葫芦娃1234567890"); // 超出长度

        System.out.println("----------- 请求信息 -----------");
        System.out.println("请求参数：" + buildParamQuery(params));
        System.out.println("商户秘钥：" + secret);
        String content = TaobaoSignature.getSignContent(params);
        System.out.println("待签名内容：" + content);
        String sign = TaobaoSignature.doSign(content, secret, "md5");
        System.out.println("签名(sign)：" + sign);

        params.put("sign", sign);

        System.out.println("----------- 返回结果 -----------");
        String responseData = post(url, params);// 发送请求
        System.out.println(responseData);
    }

}
