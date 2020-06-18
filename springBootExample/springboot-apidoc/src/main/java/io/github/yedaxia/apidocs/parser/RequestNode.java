package io.github.yedaxia.apidocs.parser;

import io.github.yedaxia.apidocs.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * a request node  corresponds to a controller method
 *
 *
 */
public class RequestNode {

    private List<String> method = new ArrayList<>();
    private String url;
    private String methodName; //方法名
    private String description;
    private List<ParamNode> paramNodes = new ArrayList<>();
    private List<HeaderNode> header = new ArrayList<>();
    private Boolean deprecated = Boolean.FALSE;
    private ResponseNode responseNode;
    private ControllerNode controllerNode;
    private String androidCodePath;
    private String iosCodePath;
    private String codeFileUrl;
    private String author;

    public String getCodeFileUrl() {
        return codeFileUrl;
    }

    public void setCodeFileUrl(String codeFileUrl) {
        this.codeFileUrl = codeFileUrl;
    }

    public List<String> getMethod() {
        if (method == null || (method != null && method.size() == 0)) {
            return Arrays.asList(RequestMethod.GET.name(), RequestMethod.POST.name());
        }
        return method;
    }

    public void setMethod(List<String> method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ParamNode> getParamNodes() {
        return paramNodes;
    }

    public void setParamNodes(List<ParamNode> paramNodes) {
        this.paramNodes = paramNodes;
    }

    public List<HeaderNode> getHeader() {
        return header;
    }

    public void setHeader(List<HeaderNode> header) {
        this.header = header;
    }

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }

    public ResponseNode getResponseNode() {
        return responseNode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setResponseNode(ResponseNode responseNode) {
        this.responseNode = responseNode;
    }

    public void addMethod(String method) {
        if (this.method.contains(method)) {
            return;
        }
        this.method.add(method);
    }

    public void addHeaderNode(HeaderNode headerNode) {
        header.add(headerNode);
    }

    public void addParamNode(ParamNode paramNode) {
        paramNodes.add(paramNode);
    }

    public ControllerNode getControllerNode() {
        return controllerNode;
    }

    public void setControllerNode(ControllerNode controllerNode) {
        this.controllerNode = controllerNode;
    }

    public ParamNode getParamNodeByName(String name) {
        for (ParamNode node : paramNodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public String getAndroidCodePath() {
        return androidCodePath;
    }

    public void setAndroidCodePath(String androidCodePath) {
        this.androidCodePath = androidCodePath;
    }

    public String getIosCodePath() {
        return iosCodePath;
    }

    public void setIosCodePath(String iosCodePath) {
        this.iosCodePath = iosCodePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
