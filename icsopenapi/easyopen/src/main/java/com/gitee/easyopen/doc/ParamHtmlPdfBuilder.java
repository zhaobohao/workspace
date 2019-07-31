package com.gitee.easyopen.doc;

/**
 * @author tanghc
 */
public class ParamHtmlPdfBuilder extends ParamHtmlBuilder {
    @Override
    protected String buildExampleValue(ApiDocFieldDefinition definition, String nameVersion, ApiDocFieldDefinition parentDefinition) {
        return definition.getExample();
    }
}
