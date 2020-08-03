package com.dc3.center.auth.service;

import com.dc3.common.model.User;

/**
 * Token Interface
 *
 * @author pnoker
 */
public interface TokenService {
    /**
     * 生成用户的随机 salt，5分钟失效
     *
     * @param username
     * @return
     */
    String generateSalt(String username);

    /**
     * 生成用户的Token令牌，5小时失效
     *
     * @param user
     * @return
     */
    String generateToken(User user);

    /**
     * 校验用户的Token令牌是否有效
     *
     * @param username
     * @param token
     * @return
     */
    boolean checkTokenValid(String username, String token);

    /**
     * 注销用户的Token令牌
     *
     * @param username
     */
    boolean cancelToken(String username);
}
