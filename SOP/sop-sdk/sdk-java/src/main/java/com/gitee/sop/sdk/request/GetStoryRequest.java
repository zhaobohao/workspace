package com.gitee.sop.sdk.request;

import com.gitee.sop.sdk.response.GetStoryResponse;

public class GetStoryRequest extends BaseRequest<GetStoryResponse> {
    @Override
    protected String method() {
        return "alipay.story.find";
    }

}
