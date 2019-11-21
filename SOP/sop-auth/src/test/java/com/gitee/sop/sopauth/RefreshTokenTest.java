package com.gitee.sop.sopauth;

import com.gitee.sop.sdk.client.OpenClient;
import com.gitee.sop.sdk.model.OpenAuthTokenAppModel;
import com.gitee.sop.sdk.request.OpenAuthTokenAppRequest;
import com.gitee.sop.sdk.response.OpenAuthTokenAppResponse;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanghc
 */
@Slf4j
public class RefreshTokenTest extends TestCase {
    String url = "http://localhost:8081";
    String appId = "2019032617262200001";
    // 平台提供的私钥
  String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM4lMCwagCw0Yl3Npabzfma2lxjZjEavg0+OeFCZ9Ss91P5mCLqOab6iVi7KA14Hxw5IAuZPOBeV5/7pJ3B9ElUQxhUA7uGoXQ6h33NM5z6SiWCdYm2pfngKih18AG3RI1L9uyvqEIBa7XtEaduAFor5lokPc1WpdVcTwTfRxgeJAgMBAAECgYAM3XFGL1k0aQiChiUCaEvJKTgAywLgHm/5dRC5JwKP8knqnn+I9P5QcV0jimPvaFjZ4VCdAvCjOC3EUNSvRn7wR2Lb1+BGZZePTdxtHWE2aqJ1W1SvgQTqMsLlPBRPnXo5XH/ng3WEH15ynd5NR035xAluaI0X/y+PsRxE6TlfIQJBAPSYUyXa2yaEqmvIN+ECKALCLLeDdi2YW3Kjahgz0X9V4Y4aTdrHh8y603zXC0Wy8HeOhwGoyciaS8SmjxCMn4UCQQDXweW8xsUreLH8hfVUtyiY/KgUz+R5foJDNXD7TLE9CDoPSHy09qBe99HyVCZg/gNJH4O+tNr6C4916dYaVk01AkBYZ2HOEc8ZmeOaty/zJHtfm9zbqykgi6upwISNINV8Z4bxfHJdO7bKeVANFBBf7a/aFmqXX/EmjxYJioW03o6dAkEAp7ViXJCtJpNU1pNSFZ2hgvmxtSu7zuyVWKSrw8rjYiuI5eRUe13RXsCHgzQB+Ra5exdyEsUGCaL+yosPD73RmQJBALGuM8EQUcBgrpgpeLZ39Ni1DYXYG9aj+u+ar/UL6kI1mCNFgwroO4EVIvXPVxikMxUgiE2tVaBML5nm8VDNJ7s=";

    OpenClient openClient = new OpenClient(url, appId, privateKey);

    /**
     * 根据refreshToken换取token
     */
    public void testRefreshToken() {
        OpenAuthTokenAppRequest request = new OpenAuthTokenAppRequest();
        OpenAuthTokenAppModel model = new OpenAuthTokenAppModel();
        model.setGrant_type("refresh_token");
        model.setRefresh_token("c9e4003c06fe59066eed73d64ea074ca");
        request.setBizModel(model);

        OpenAuthTokenAppResponse response = openClient.execute(request);
        if (response.isSuccess()) {
            // 成功拿到token，开发者在这里保存token信息
            // 后续使用token进行接口访问
            log.info("换取token成功，body:{}", response.getBody());
        }
    }

}
