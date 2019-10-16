package com.gitee.sop.websiteserver.bean;

import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class DocItem {
    private String module;
    private String name;
    private String version;
    private String summary;
    private String description;
    /** 是否多文件上传 */
    private boolean multiple;
    /** http method列表 */
    private Collection<String> httpMethodList;

    private Collection<String> produces;

    private int moduleOrder;

    List<DocParameter> requestParameters;
    List<DocParameter> responseParameters;

    public String getNameVersion() {
        return name + version;
    }

    /**
     * 是否是上传文件请求
     * @return
     */
    public boolean isUploadRequest() {
        boolean upload = false;
        if (requestParameters != null) {
            for (DocParameter requestParameter : requestParameters) {
                String type = requestParameter.getType();
                if ("file".equalsIgnoreCase(type)) {
                    upload = true;
                    break;
                }
            }
        }
        return multiple || upload;
    }
}
