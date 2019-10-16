package com.gitee.sop.websiteserver.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class DocInfo {
    private String title;
    @JSONField(serialize = false)
    private String serviceId;
    private List<DocModule> docModuleList;
}
