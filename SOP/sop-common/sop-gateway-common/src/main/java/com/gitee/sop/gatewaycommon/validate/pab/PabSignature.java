package com.gitee.sop.gatewaycommon.validate.pab;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.validate.SignConfig;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zhaobo
 */
public class PabSignature {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, Object> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = String.valueOf(sortedParams.get(key));
            if (StringUtils.areNotEmpty(key, value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * rsa内容签名
     *
     * @param content
     * @param publicKey
     * @param charset
     * @return
     */
    public static String rsaSignBySignType(String content, String publicKey, String charset,
                                           String signType) {

        if (PabConstants.SIGN_TYPE_RSA.equals(signType)) {

            return rsaSign(content, publicKey, charset);
        } else if (PabConstants.SIGN_TYPE_RSA2.equals(signType)) {

            return rsa256Sign(content, publicKey, charset);
        } else {
            throw ErrorEnum.ISV_INVALID_SIGNATURE_TYPE.getErrorMeta().getException();
//            throw new pabApiException("Sign Type is Not Support : signType=" + signType);
        }

    }

    /**
     * sha256WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     */
    public static String rsa256Sign(String content, String privateKey,
                                    String charset) {

        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(PabConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(privateKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(PabConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(e);
//            throw new pabApiException("RSAcontent = " + content + "; charset = " + charset, e);
        }

    }

    /**
     * sha1WithRsa 加签
     *
     * @param content
     * @param publicKey
     * @param charset
     * @return
     */
    public static String rsaSign(String content, String publicKey,
                                           String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(PabConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(PabConstants.SIGN_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE_TYPE.getErrorMeta().getException(ie);
        } catch (Exception e) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(e);
        }
    }

    public static String rsaSignBySignType(Map<String, Object> params, String publicKey,
                                           String charset, String signType) {
        String signContent = getSignContent(params);

        return rsaSignBySignType(signContent, publicKey, charset, signType);

    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm,
                                                    InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }

        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        byte[] encodedKey = StreamUtil.readText(ins, "UTF-8").getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public static String getSignCheckContentV1(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");
        params.remove("sign_type");

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = SignConfig.wrapVal(params.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static String getSignCheckContentV2(Map<String, ?> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = SignConfig.wrapVal(params.get(key));
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey,
                                     String charset) {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey,
                                     String charset, String signType) {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheckBySignType(content, sign, publicKey, charset, signType);
    }

    public static boolean rsaCheckV2(Map<String, String> params, String publicKey,
                                     String charset) {
        String sign = params.get("sign");
        String content = getSignCheckContentV2(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }

    public static boolean rsaCheckV2BySignType(Map<String, ?> params, String publicKey,
                                               String charset, String signType) {
        String sign = String.valueOf(params.get("sign"));
        String content = getSignCheckContentV2(params);

        return rsaCheckBySignType(content, sign, publicKey, charset, signType);
    }

    public static boolean rsaCheckBySignType(String content, String sign, String publicKey, String charset,
                                             String signType) {

        if (PabConstants.SIGN_TYPE_RSA.equals(signType)) {

            return rsaCheckContent(content, sign, publicKey, charset);

        } else if (PabConstants.SIGN_TYPE_RSA2.equals(signType)) {

            return rsa256CheckContent(content, sign, publicKey, charset);

        } else {
            throw ErrorEnum.ISV_INVALID_SIGNATURE_TYPE.getErrorMeta().getException();
//            throw new pabApiException("Sign Type is Not Support : signType=" + signType);
        }

    }

    public static boolean rsa256CheckContent(String content, String sign, String publicKey,
                                             String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA",
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(PabConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(e);
//            throw new pabApiException(
//                    "RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static boolean rsaCheckContent(String content, String sign, String publicKey,
                                          String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA",
                    new ByteArrayInputStream(publicKey.getBytes()));

            java.security.Signature signature = java.security.Signature
                    .getInstance(PabConstants.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(e);
//            throw new pabApiException(
//                    "RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm,
                                                 InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    /**
     * 验签并解密,使用RSA
     *
     * @param params
     * @param cusPublicKey 对方的公钥
     * @param pabPrivateKey   自己的私钥
     * @param isCheckSign     是否验签
     * @param isDecrypt       是否解密
     * @return 解密后明文，验签失败则异常抛出
     */
    public static String checkSignAndDecrypt(Map<String, String> params, String cusPublicKey,
                                             String pabPrivateKey, boolean isCheckSign,
                                             boolean isDecrypt) {
        String charset = params.get(ParamNames.CHARSET_NAME);
        String bizContent = params.get(ParamNames.BIZ_CONTENT_NAME);
        if (isCheckSign) {
            if (!rsaCheckV2(params, cusPublicKey, charset)) {
                throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException();
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, pabPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     * 验签并解密
     *
     * @param params
     * @param cusPublicKey 对方的公钥
     * @param pabPrivateKey   自己的私钥
     * @param isCheckSign     是否验签
     * @param isDecrypt       是否解密
     * @param signType      验签名类型
     * @return 解密后明文，验签失败则异常抛出
     */
    public static String checkSignAndDecryptBySignType(Map<String, Object> params, String cusPublicKey,
                                             String pabPrivateKey, boolean isCheckSign,
                                             boolean isDecrypt, String signType) {
        String charset =(String) params.get(ParamNames.CHARSET_NAME);
        String bizContent = (String) params.get(ParamNames.BIZ_CONTENT_NAME);
        if (isCheckSign) {
            if (!rsaCheckV2BySignType(params, cusPublicKey, charset, signType)) {
                throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException();
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, pabPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     * 加密并签名<br>
     * <b>目前适用于公众号,使用RSA</b>
     *
     * @param bizContent      待加密、签名内容
     * @param cusPublicKey 对方的公钥
     * @param pabPrivateKey   自己的私钥
     * @param charset         字符集，如UTF-8, GBK, GB2312
     * @param isEncrypt       是否加密，true-加密  false-不加密
     * @param isSign          是否签名，true-签名  false-不签名
     * @return 加密、签名后xml内容字符串
     */
    public static String encryptAndSign(String bizContent, String cusPublicKey,
                                        String pabPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign) {
        JSONObject  finalData= JSONObject.parseObject(bizContent);
        if (StringUtils.isEmpty(charset)) {
            charset = PabConstants.CHARSET_GBK;
        }
        if (isEncrypt) {// 加密
            String data=finalData.getString(ParamNames.BIZ_CONTENT_NAME);
            String encrypted = rsaEncrypt(data, cusPublicKey, charset);
            finalData.put(ParamNames.BIZ_CONTENT_NAME,encrypted);
            finalData.put(ParamNames.ENCRYPTION_TYPE_NAME,"RSA");
            if (isSign) {
                finalData.put(ParamNames.SIGN_TYPE_NAME,"RSA");
                String sign = rsaSign(getSignCheckContentV2(finalData), pabPrivateKey, charset);
                finalData.put(ParamNames.SIGN_NAME,sign);
            }
        } else if (isSign) {// 不加密，但需要签名
            finalData.put(ParamNames.SIGN_TYPE_NAME,"RSA");
            String sign = rsaSign(getSignCheckContentV2(finalData), pabPrivateKey, charset);
            finalData.put(ParamNames.SIGN_NAME,sign);
        } else {// 不加密，不加签
           ;
        }
        return finalData.toJSONString();
    }

    /**
     * 加密并签名<br>
     * <b>目前适用于公众号</b>
     *
     * @param bizContent      待加密、签名内容
     * @param cusPublicKey 对方的公钥
     * @param pabPrivateKey   自己的私钥
     * @param charset         字符集，如UTF-8, GBK, GB2312
     * @param isEncrypt       是否加密，true-加密  false-不加密
     * @param isSign          是否签名，true-签名  false-不签名
     * @param signType        签名类型  RSA   RSA2
     * @return 加密、签名后xml内容字符串
     */
    public static String encryptAndSignBySignType(String bizContent, String cusPublicKey,
                                        String pabPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign, String signType) {
        JSONObject  finalData= JSONObject.parseObject(bizContent);
        if (StringUtils.isEmpty(charset)) {
            charset = PabConstants.CHARSET_GBK;
        }
        if (isEncrypt) {// 加密
            String data=finalData.getString(ParamNames.BIZ_CONTENT_NAME);
            String encrypted = rsaEncrypt(data, cusPublicKey, charset);
            finalData.put(ParamNames.BIZ_CONTENT_NAME,encrypted);
            finalData.put(ParamNames.ENCRYPTION_TYPE_NAME,"RSA");
            if (isSign) {
                finalData.put(ParamNames.SIGN_TYPE_NAME,"RSA");
                String sign = rsaSignBySignType(getSignCheckContentV2(finalData), pabPrivateKey, charset,signType);
                finalData.put(ParamNames.SIGN_NAME,sign);
            }
        } else if (isSign) {// 不加密，但需要签名
            finalData.put(ParamNames.SIGN_TYPE_NAME,"RSA");
            String sign = rsaSignBySignType(getSignCheckContentV2(finalData), pabPrivateKey, charset,signType);
            finalData.put(ParamNames.SIGN_NAME,sign);
        } else {// 不加密，不加签
            ;
        }
        return finalData.toJSONString();
    }

    /**
     * 公钥加密
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @return 密文内容
     */
    public static String rsaEncrypt(String content, String publicKey,
                                    String charset) {
        try {
            PublicKey pubKey = getPublicKeyFromX509(PabConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance(PabConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = StringUtils.isEmpty(charset) ? content.getBytes()
                    : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();

            return StringUtils.isEmpty(charset) ? new String(encryptedData)
                    : new String(encryptedData, charset);
        } catch (Exception e) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(e);
//            throw new pabApiException("EncryptContent = " + content + ",charset = " + charset,
//                    e);
        }
    }

    /**
     * 私钥解密
     *
     * @param content    待解密内容
     * @param privateKey 私钥
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
     */
    public static String rsaDecrypt(String content, String privateKey,
                                    String charset) {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8(PabConstants.SIGN_TYPE_RSA,
                    new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance(PabConstants.SIGN_TYPE_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset)
                    ? Base64.decodeBase64(content.getBytes())
                    : Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return StringUtils.isEmpty(charset) ? new String(decryptedData)
                    : new String(decryptedData, charset);
        } catch (Exception e) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE.getErrorMeta().getException(e);
//            throw new pabApiException("EncodeContent = " + content + ",charset = " + charset, e);
        }
    }


}
