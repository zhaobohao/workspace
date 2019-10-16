package com.gitee.sop.adminserver.common;

/**
 * @author tanghc
 */
public enum ChannelOperation {
    /**
     * 限流推送路由配置-修改
     */
    LIMIT_CONFIG_UPDATE("update"),

    /**
     * 路由信息更新
     */
    ROUTE_CONFIG_UPDATE("update"),

    /**
     * isv信息修改
     */
    ISV_INFO_UPDATE("update"),

    /**
     * 黑名单消息类型：添加
     */
    BLACKLIST_ADD("add"),
    /**
     * 黑名单消息类型：删除
     */
    BLACKLIST_DELETE("delete"),

    /**
     * 路由权限配置更新
     */
    ROUTE_PERMISSION_UPDATE("update"),
    /**
     * 路由权限加载
     */
    ROUTE_PERMISSION_RELOAD("reload"),

    /**
     * 灰度发布设置
     */
    GRAY_USER_KEY_SET("set"),
    /**
     * 灰度发布-开启
     */
    GRAY_USER_KEY_OPEN("open"),
    /**
     * 灰度发布-关闭
     */
    GRAY_USER_KEY_CLOSE("close"),

    ;

    private String operation;

    ChannelOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
