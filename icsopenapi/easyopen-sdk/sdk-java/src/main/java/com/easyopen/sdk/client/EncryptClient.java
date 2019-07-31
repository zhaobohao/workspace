package com.easyopen.sdk.client;

import com.easyopen.sdk.common.HandshakeParam;
import com.easyopen.sdk.common.RequestForm;
import com.easyopen.sdk.request.BaseRequest;
import com.easyopen.sdk.response.BaseResponse;
import com.easyopen.sdk.util.AESUtil;
import com.easyopen.sdk.util.JsonUtil;
import com.easyopen.sdk.util.MD5Util;
import com.easyopen.sdk.util.RSAUtil;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * 加密模式客户端
 *
 * @author tanghc
 */
public class EncryptClient extends OpenClient {

    private int holdSessionDelaySeconds = 60 * 10;
    private int holdSessionPeriodSeconds = 60 * 10;
    private Timer timer;

    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 随机码
     */
    private volatile String randomKey;

    private String handshakeUrl;

    private EncryptClient(String url, String appKey, String secret) {
        super(url, appKey, secret);
    }

    public EncryptClient(String url, String appKey, String secret, String publicKey) {
        this(url + "/ssl2", appKey, secret);

        this.handshakeUrl = url + "/handshake2";
        this.publicKey = publicKey;

        this.handshake();

        this.initTimer();
    }

    /**
     * 创建定时器防止session失效
     */
    protected void initTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handshake();
            }
        },holdSessionDelaySeconds * 1000, holdSessionPeriodSeconds * 1000); // 延迟10分钟执行，然后每隔10分钟再次执行
    }

    protected synchronized void handshake() {
        String randomKey = this.createRandomKey();
        try {
            // 传递随机数
            HandshakeParam param = this.createHandShakeParam(randomKey);
            String json = this.getOpenRequest().postJsonBody(this.handshakeUrl, JsonUtil.toJSONString(param));
            checkHandshakeResponse(json, randomKey);
            this.randomKey = randomKey;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("握手失败", e);
        }
    }

    protected String createRandomKey() {
        return MD5Util.encrypt16(UUID.randomUUID().toString());
    }

    protected HandshakeParam createHandShakeParam(String randomKey) throws Exception {
        HandshakeParam param = new HandshakeParam();
        String randomKeyEncrypted = RSAUtil.encryptByPublicKey(randomKey, publicKey);
        param.setApp_key(this.getAppKey());
        param.setData(randomKeyEncrypted);
        return param;
    }

    protected void checkHandshakeResponse(String resp, String randomKey) throws Exception {
        StringResponse result = JsonUtil.parseObject(resp, StringResponse.class);
        if (!result.isSuccess()) {
            throw new RuntimeException(result.getMsg());
        }

        String data = result.getData();
        String desStr = RSAUtil.decryptByPublicKey(data, publicKey);

        String content = AESUtil.decryptFromBase64String(desStr, randomKey);
        // 一致
        boolean same = MD5Util.encrypt(randomKey).equals(content);

        if (!same) {
            throw new RuntimeException("传输错误");
        }
    }

    @Override
    protected synchronized String doExecute(String url, RequestForm requestForm, Map<String, String> header) {
        Map<String, Object> form = requestForm.getForm();
        this.encryptData(form);
        this.encryptHeader(header);
        return super.doExecute(url, requestForm, header);
    }

    @Override
    protected <T extends BaseResponse<?>> T parseResponse(String resp, BaseRequest<T> request) {
        try {
            resp = AESUtil.decryptFromBase64String(resp, this.randomKey);
            return super.parseResponse(resp, request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("AES解密失败");
        }
    }

    private void encryptData(Map<String, Object> form) {
        String requestDataName = this.getOpenConfig().getDataName();
        String data = (String) form.get(requestDataName);
        try {
            data = AESUtil.encryptToBase64String(data, randomKey);
            form.put(requestDataName, data);
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败");
        }
    }

    // 加密header
    private void encryptHeader(Map<String, String> header) {
        Set<Map.Entry<String, String>> entrySet = header.entrySet();
        try {
            for (Map.Entry<String, String> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue();

                value = AESUtil.encryptToBase64String(value, randomKey);

                header.put(key, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES加密head失败");
        }

    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setHandshakeUrl(String handshakeUrl) {
        this.handshakeUrl = handshakeUrl;
    }

    public void setHoldSessionDelaySeconds(int holdSessionDelaySeconds) {
        this.holdSessionDelaySeconds = holdSessionDelaySeconds;
    }

    public void setHoldSessionPeriodSeconds(int holdSessionPeriodSeconds) {
        this.holdSessionPeriodSeconds = holdSessionPeriodSeconds;
    }

    private static class StringResponse extends BaseResponse<String> {

    }
}
