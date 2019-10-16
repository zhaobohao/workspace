package com.gitee.sop.adminserver.bean;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tanghc
 */
@Data
public class GatewayFilterDefinition {
    /** Filter Name */
    private String name;
    /** 对应的路由规则 */
    private Map<String, String> args = new LinkedHashMap<>();
}