package com.gitee.sop.sopauth.auth;

import java.io.Serializable;

/**
 * 如果要使用oauth2功能，自己的用户类需要实现这个对象
 *
 * @author tanghc
 */
public interface OpenUser extends Serializable {
    /**
     * 返回用户id
     * @return
     */
    String getUserId();
    /**
     * 返回用户名
     *
     * @return 返回用户名
     */
    String getUsername();
}
