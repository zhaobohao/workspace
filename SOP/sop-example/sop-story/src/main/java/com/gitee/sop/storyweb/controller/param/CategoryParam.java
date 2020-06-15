package com.gitee.sop.storyweb.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryParam {
    @ApiModelProperty(value = "分类名称", example = "娱乐")
    private String categoryName;

    @ApiModelProperty(value = "创建时间", example = "2019-09-25 17:12:52")
    private Date createTime;

    @ApiModelProperty(value = "分类故事")
    private StoryParam storyParam;
}