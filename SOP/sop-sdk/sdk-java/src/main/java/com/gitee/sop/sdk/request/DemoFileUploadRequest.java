package com.gitee.sop.sdk.request;

import com.gitee.sop.sdk.response.DemoFileUploadResponse;

/**
 * @author tanghc
 */
public class DemoFileUploadRequest extends BaseRequest<DemoFileUploadResponse> {
    @Override
    protected String method() {
        return "demo.file.upload";
    }
}
