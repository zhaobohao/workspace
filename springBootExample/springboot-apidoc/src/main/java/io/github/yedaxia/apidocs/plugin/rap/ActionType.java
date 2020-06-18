package io.github.yedaxia.apidocs.plugin.rap;

/**
 *
 */
enum  ActionType {

    GET("1"),
    POST("2"),
    PUT("3"),
    DELETE("4");

    public final String type;

    ActionType(String type) {
        this.type = type;
    }
}
