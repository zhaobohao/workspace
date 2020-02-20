package com.gitee.sop.servercommon.mapping;

import org.springframework.util.StringValueResolver;

/**
 * @author tanghc
 */
public class ApiMappingStringValueResolver implements StringValueResolver {

    private static final String END = "/";

    @Override
    public String resolveStringValue(String strVal) {
        if (strVal == null) {
            return null;
        }
        return strVal + END;
    }
}
