package com.gitee.sop.story.api.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author tanghc
 */
@Data
public class Story {
    private int id;

    @NotBlank(message = "name不能为空")
    @Length(max = 20, message = "name长度不能超过20")
    private String name;
}
