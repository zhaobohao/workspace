package com.gitee.sop.websiteserver.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 参数	类型	是否必填	最大长度	描述	示例值
 * @author tanghc
 */
@Data
public class DocParameter {
    private String module;
    private String name;
    private String type;
    private String maxLength = "-";
    private boolean required;
    private String description;
    private String example = "";

    @JSONField(name = "x-example")
    private String x_example = "";

    private List<DocParameter> refs;

    public String getParamExample() {
        return StringUtils.isBlank(example) ? x_example : example;
    }
}
