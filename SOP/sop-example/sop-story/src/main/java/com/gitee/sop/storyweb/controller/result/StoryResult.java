package com.gitee.sop.storyweb.controller.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author tanghc
 */
@Data
public class StoryResult {
    @ApiModelProperty(value = "故事ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "故事名称", example = "海底小纵队")
    private String name;

    @ApiModelProperty(value = "创建时间", example = "2019-04-14 19:02:12")
    private Date gmt_create;
}
