package com.gitee.sop.adminserver.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tanghc
 */
@Data
public class GatewayPredicateDefinition {
    public static final String GEN_KEY = "_genkey_";
    /** 断言对应的Name */
    private String name;
    /** 配置的断言规则 */
    private Map<String, String> args = new LinkedHashMap<>();

    public GatewayPredicateDefinition() {
    }

    public GatewayPredicateDefinition(String text) {
        int eqIdx = text.indexOf(61);
        if (eqIdx <= 0) {
            throw new ValidationException("Unable to parse GatewayPredicateDefinition text '" + text + "', must be of the form name=value");
        } else {
            this.setName(text.substring(0, eqIdx));
            String[] args = StringUtils.tokenizeToStringArray(text.substring(eqIdx + 1), ",");

            for(int i = 0; i < args.length; ++i) {
                this.args.put(generateName(i), args[i]);
            }

        }
    }

    public static String generateName(int i) {
        return GEN_KEY + i;
    }
}