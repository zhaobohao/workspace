package com.gitee.easyopen.doc;

/**
 * 保存文档内容
 * @author tanghc
 */
public class ApiDocHolder {
    private static ApiDocBuilder apiDocBuilder;
    
    public static ApiDocBuilder createBuilder() {
    	ApiDocBuilder apiDocBuilder = new ApiDocBuilder();
    	setApiDocBuilder(apiDocBuilder);
    	return apiDocBuilder;
    }

    public static ApiDocBuilder getApiDocBuilder() {
        return apiDocBuilder;
    }

    public static void setApiDocBuilder(ApiDocBuilder apiDocBuilder) {
        ApiDocHolder.apiDocBuilder = apiDocBuilder;
    }

}
