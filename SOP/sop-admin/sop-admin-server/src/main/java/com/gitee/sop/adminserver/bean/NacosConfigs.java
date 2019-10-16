package com.gitee.sop.adminserver.bean;

/**
 * @author tanghc
 */
public class NacosConfigs {

    public static final String GROUP_CHANNEL = "sop:channel";

    public static final String GROUP_ROUTE = "sop:route";

    public static final String DATA_ID_GRAY = "com.gitee.sop.channel.gray";

    public static final String DATA_ID_IP_BLACKLIST = "com.gitee.sop.channel.ipblacklist";

    public static final String DATA_ID_ISV = "com.gitee.sop.channel.isv";

    public static final String DATA_ID_ROUTE_PERMISSION = "com.gitee.sop.channel.routepermission";

    public static final String DATA_ID_LIMIT_CONFIG = "com.gitee.sop.channel.limitconfig";

    public static final String DATA_ID_ROUTE_CONFIG = "com.gitee.sop.channel.routeconfig";

    private static final String DATA_ID_TPL = "com.gitee.sop.route.%s";

    public static String getRouteDataId(String serviceId) {
        return String.format(DATA_ID_TPL, serviceId);
    }
}
