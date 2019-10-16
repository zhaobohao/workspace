package com.gitee.sop.servercommon.manager;

import org.springframework.core.env.Environment;

/**
 * @author tanghc
 */
public class EnvironmentContext {
    private static Environment environment;

    public static Environment getEnvironment() {
        return environment;
    }

    public static void setEnvironment(Environment environment) {
        EnvironmentContext.environment = environment;
    }

    public static String getValue(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

}
