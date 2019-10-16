package com.gitee.sop.storyweb.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class StoryParam {
    @ApiModelProperty(value = "故事ID", example = "111")
    private int id;

    @NotBlank(message = "name不能为空")
    @Length(max = 20, message = "name长度不能超过20")
    @ApiModelProperty(value = "故事名称", required = true, example = "白雪公主")
    private String name;
}