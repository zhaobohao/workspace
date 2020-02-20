package com.gitee.sop.servercommon.mapping;

import org.springframework.util.StringValueResolver;

/**
 * @author tanghc
 */
public class ApiMappingStringValueResolverMVC implements StringValueResolver {

    @Override
    public String resolveStringValue(String strVal) {
        return strVal;
    }
}
