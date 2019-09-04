

package com.spring.web.core.util;

import java.util.UUID;

/**
 * @author geekidea
 * @date 2018-11-08
 */
public class UUIDUtil {

    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
    
}
