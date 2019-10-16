package com.gitee.sop.storyweb.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author tanghc
 */
@Data
public class FileUploadParam {
    private String remark;

    // 上传文件，字段名称对应表单中的name属性值
    @NotNull(message = "请上传文件1")
    @ApiModelProperty(value = "文件1", required = true)
    private MultipartFile file1;

    @NotNull(message = "请上传文件2")
    @ApiModelProperty(value = "文件2", required = true)
    private MultipartFile file2;
}
