package com.gitee.sop.sdk.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanghc
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DemoFileUploadResponse extends BaseResponse {

    private List<FileMeta> files = new ArrayList();

    @Data
    public static class FileMeta {

        public FileMeta(String filename, long size, String content) {
            this.filename = filename;
            this.size = size;
            this.content = content;
        }

        public FileMeta() {
        }

        private String filename;
        private long size;
        private String content;
    }
}
