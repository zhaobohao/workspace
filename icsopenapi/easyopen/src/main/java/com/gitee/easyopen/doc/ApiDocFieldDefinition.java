package com.gitee.easyopen.doc;

import java.util.Collections;
import java.util.List;

/**
 * 文档参数字段信息
 * 
 * @author tanghc
 *
 */
public class ApiDocFieldDefinition {
    
    private static ParamHtmlBuilder paramHtmlBuilder = new ParamHtmlBuilder();
    private static ParamHtmlBuilder paramHtmlPdfBuilder = new ParamHtmlPdfBuilder();
    private static ParamMarkdownHtmlBuilder paramMarkdownHtmlBuilder = new ParamMarkdownHtmlBuilder();
    private static ResultHtmlBuilder resultHtmlBuilder = new ResultHtmlBuilder();

    private String name;
    private String dataType;
    private String required;
    private String example;
    private String description;
    private Class<?> elementClass;

    private List<ApiDocFieldDefinition> elements = Collections.emptyList();

    private boolean rootData;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public List<ApiDocFieldDefinition> getElements() {
        return elements;
    }

    public void setElements(List<ApiDocFieldDefinition> elements) {
        this.elements = elements;
    }
    
    public String getResultHtml() {
        return resultHtmlBuilder.buildHtml(this);
    }
    
    public String getParamHtml(String nameVersion) {
        return paramHtmlBuilder.buildHtml(this, nameVersion);
    }

    public String getParamMarkdownHtml() {
        return paramMarkdownHtmlBuilder.buildHtml(this);
    }

    public String getParamHtmlPdf(String nameVersion) {
        return paramHtmlPdfBuilder.buildHtml(this, nameVersion);
    }

    public Class<?> getElementClass() {
        return elementClass;
    }

    public void setElementClass(Class<?> elementClass) {
        this.elementClass = elementClass;
    }

    public boolean isRootData() {
        return rootData;
    }

    public void setRootData(boolean rootData) {
        this.rootData = rootData;
    }

    @Override
    public String toString() {
        return "ApiDocFieldDefinition{" +
                "name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", required='" + required + '\'' +
                ", example='" + example + '\'' +
                ", description='" + description + '\'' +
                ", elementClass=" + elementClass +
                ", elements=" + elements +
                ", rootData=" + rootData +
                '}';
    }
}
