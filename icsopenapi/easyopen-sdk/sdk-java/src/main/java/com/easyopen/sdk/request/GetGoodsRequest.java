package com.easyopen.sdk.request;

import com.easyopen.sdk.response.GetGoodsResponse;

public class GetGoodsRequest extends BaseRequest<GetGoodsResponse> {
    @Override
    public String name() {
        return "goods.get";
    }
}
