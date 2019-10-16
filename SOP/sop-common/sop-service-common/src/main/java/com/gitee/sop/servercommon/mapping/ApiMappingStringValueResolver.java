package com.gitee.sop.servercommon.mapping;

import org.springframework.util.StringValueResolver;

/**
 * @author tanghc
 */
public class ApiMappingStringValueResolver implements StringValueResolver {

    private static final String END_CHAR = "/";

    @Override
    public String resolveStringValue(String strVal) {
        if (strVal != null && !strVal.endsWith(END_CHAR)) {
            return strVal + END_CHAR;
        }
        return null;
    }
}
