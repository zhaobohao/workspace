package com.gitee.easyopen.template;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.AppSecretManager;
import com.gitee.easyopen.Encrypter;
import com.gitee.easyopen.LocalAppSecretManager;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.bean.HandshakeParam;
import com.gitee.easyopen.bean.Secret;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.util.RequestUtil;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
public class HandshakeTemplate extends AbstractTemplate {

    public HandshakeTemplate(ApiConfig apiConfig) {
        super(apiConfig);
    }

    public Object handshake(HttpServletRequest request, HttpServletResponse response) {
        try {
            String randomKeyEncrypted = RequestUtil.getText(request);
            Encrypter encrypter = this.apiConfig.getEncrypter();
            String privateKey = this.apiConfig.getPrivateKey();
            if(StringUtils.isEmpty(privateKey)) {
                logger.error("未设置正确的私钥");
                throw Errors.ERROR_SSL.getException();
            }
            // 得到客户端传来的随机码
            String randomKey = encrypter.rsaDecryptByPrivateKey(randomKeyEncrypted, privateKey);
            request.getSession().setAttribute(Consts.RANDOM_KEY_NAME, randomKey);
            // 再用随机码进行加密,返回给客户端
            // 客户端如果能解开,说明两边对接成功
            // 后续的数据传输都通过这个随机码进行加解密操作
            String retContent = "0";
            String aesStr = encrypter.aesEncryptToHex(retContent, randomKey);

            String data = encrypter.rsaEncryptByPrivateKey(aesStr, privateKey);

            ApiResult result = new ApiResult();
            result.setCode(Errors.SUCCESS.getCode());
            result.setData(data);

            return result;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            ApiResult result = new ApiResult();
            result.setCode(Errors.ERROR_SSL.getCode());
            result.setMsg("交互错误");
            return result;
        }
    }

    /**
     * 新版本的握手交互
     * @param request
     * @param response
     * @return 返回握手结果
     * @throws Throwable
     */
    public Object handshake2(HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = RequestUtil.getText(request);
            HandshakeParam handshakeParam = JSON.parseObject(json, HandshakeParam.class);
            String privateKey = null;
            AppSecretManager appSecretManager = this.apiConfig.getAppSecretManager();
            if(appSecretManager instanceof LocalAppSecretManager) {
                LocalAppSecretManager localAppSecretManager = (LocalAppSecretManager)appSecretManager;
                Secret secret = localAppSecretManager.getSecretInfo(handshakeParam.getApp_key());
                if(secret == null) {
                    return ApiResult.error(Errors.ERROR_APP_ID);
                }
                privateKey = secret.getPriKey();
            } else {
                privateKey = this.apiConfig.getPrivateKey();
            }

            if(StringUtils.isEmpty(privateKey)) {
                logger.error("未设置正确的私钥");
                throw Errors.ERROR_SSL.getException();
            }
            Encrypter encrypter = this.apiConfig.getEncrypter();
            // 得到客户端传来的随机码
            String randomKeyEncrypted = handshakeParam.getData();
            String randomKey = encrypter.rsaDecryptByPrivateKeyNew(randomKeyEncrypted, privateKey);
            request.getSession().setAttribute(Consts.RANDOM_KEY_NAME, randomKey);
            // 再用随机码进行加密,返回给客户端
            // 客户端如果能解开,说明两边对接成功
            // 后续的数据传输都通过这个随机码进行加解密操作
            String retContent = encrypter.md5(randomKey);
            String aesStr = encrypter.aesEncryptToBase64String(retContent, randomKey);

            String data = encrypter.rsaEncryptByPrivateKeyNew(aesStr, privateKey);

            return ApiResult.success(data);
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiResult.error(Errors.ERROR_SSL);
        }
    }
}
