package com.gitee.easyopen.server.api.param;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.gitee.easyopen.doc.annotation.ApiDocField;

public class UploadParam2 {

    @Size(max = 2, message = "最多只能上传2个文件")
    @NotNull(message = "至少上传一个文件")
    @ApiDocField(description = "上传文件", elementClass = MultipartFile.class)
    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

}
