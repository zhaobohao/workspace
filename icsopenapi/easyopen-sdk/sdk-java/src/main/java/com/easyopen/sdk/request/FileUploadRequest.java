package com.easyopen.sdk.request;

import com.easyopen.sdk.response.FileUploadResponse;

public class FileUploadRequest extends BaseRequest<FileUploadResponse> {
    @Override
    public String name() {
        return "file.upload";
    }
}
