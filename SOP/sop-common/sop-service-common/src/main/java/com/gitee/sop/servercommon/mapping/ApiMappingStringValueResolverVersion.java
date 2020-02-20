package com.gitee.sop.servercommon.mapping;

import org.springframework.util.StringValueResolver;

/**
 * @author tanghc
 */
public class ApiMappingStringValueResolverVersion implements StringValueResolver {

    private static final String SPLIT = "/";

    private String version;

    public ApiMappingStringValueResolverVersion(String version) {
        this.version = version;
    }

    @Override
    public String resolveStringValue(String strVal) {
        if (strVal == null) {
            return null;
        }
        return strVal + SPLIT + version + SPLIT;
    }
}
