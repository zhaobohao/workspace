package com.gitee.easyopen.jwt;

import com.auth0.jwt.interfaces.Claim;

import java.util.Map;

/**
 * @author tanghc
 */
public interface JwtService {

    /**
     * 创建jwt
     * @param data
     *            内容
     * @return 返回jwt
     * @throws Exception
     */
    String createJWT(Map<String, String> data);

    /**
     * 校验JWT字符串
     * 
     * @param token jwt字符串
     * @return 验证通过返回对象
     * @throws Exception
     */
    Map<String, Claim> verfiyJWT(String token);
}
