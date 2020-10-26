package org.springclouddev.crm.core.exception;


import org.springclouddev.crm.core.entity.UserExtraInfo;

/**
 * 用户尚未登陆
 */
public class NoLoginException extends RuntimeException {

    private UserExtraInfo info;

    public NoLoginException() {
        this(null);
    }

    public NoLoginException(UserExtraInfo info) {
        super("请先登录！", null, false, false);
        this.info = info;
    }

    public UserExtraInfo getInfo() {
        return info;
    }
}
