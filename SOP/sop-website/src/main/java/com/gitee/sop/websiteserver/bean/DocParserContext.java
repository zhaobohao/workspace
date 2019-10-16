package com.gitee.sop.websiteserver.bean;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * @author tanghc
 */
public class DocParserContext {

    /**
     * 忽略显示
     */
    public static volatile Set<String> ignoreHttpMethods = Sets.newHashSet("put", "options", "head", "put", "delete", "patch");
}
