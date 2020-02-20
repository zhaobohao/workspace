package com.gitee.sop.storyweb.controller.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class FileUploadResult {

    private List<FileMeta> files = new ArrayList();
    private String remark;

    @Data
    public static class FileMeta {

        public FileMeta(String filename, long size, String content) {
            this.filename = filename;
            this.size = size;
            this.content = content;
        }

        public FileMeta() {
        }

        @ApiModelProperty(value = "文件名称", example = "1.txt")
        private String filename;
        @ApiModelProperty(value = "文件大小", example = "109")
        private long size;
        @ApiModelProperty(value = "文件内容", example = "啊啊啊")
        private String content;
    }
}
