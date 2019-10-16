package com.gitee.sop.story.api.result;

import lombok.Data;

import java.io.Serializable;

/**
 * dubbo返回结果
 * @author tanghc
 */
@Data
public class DemoResult implements Serializable {
    private int id;
    private String name;
}
