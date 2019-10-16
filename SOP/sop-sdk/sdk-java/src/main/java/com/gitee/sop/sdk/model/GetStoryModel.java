package com.gitee.sop.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GetStoryModel {

    @JSONField(name = "name")
    private String name;
}
