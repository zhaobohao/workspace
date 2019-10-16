package com.gitee.sop.servercommon.mapping;

import org.springframework.util.StringUtils;

/**
 * @author tanghc
 */
public class RouteUtil {

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

}
