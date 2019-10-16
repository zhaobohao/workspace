package com.gitee.sop.gatewaycommon.util;

import org.springframework.util.StringUtils;

/**
 * @author tanghc
 */
public class RouteUtil {

    private RouteUtil() {
    }

    private static final String REGEX = "\\#";

    public static final String PROTOCOL_LOAD_BALANCE = "lb://";

    public static String findPath(String uri) {
        // #后面是对应的path
        String[] uriArr = uri.split(REGEX);
        if (uriArr.length == 2) {
            return uriArr[1];
        } else {
            return null;
        }
    }

    /**
     * 将springmvc接口路径转换成SOP方法名
     *
     * @param path springmvc路径，如:/goods/listGoods
     * @return 返回接口方法名，/goods/listGoods ->  goods.listGoods
     */
    public static String buildApiName(String path) {
        char separatorChar = '/';
        path = StringUtils.trimLeadingCharacter(path, separatorChar);
        path = StringUtils.trimTrailingCharacter(path, separatorChar);
        return path.replace(separatorChar, '.');
    }

    public static String getZuulLocation(String uri) {
        if (uri.toLowerCase().startsWith(PROTOCOL_LOAD_BALANCE)) {
            return uri.substring(PROTOCOL_LOAD_BALANCE.length());
        }
        return uri;
    }

}
