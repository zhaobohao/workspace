package com.gitee.easyopen.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;

import javax.validation.constraints.NotBlank;

public class GoodsParam {

    @ApiDocField(description = "商品名称", required = true, example = "iphoneX")
    @NotBlank(message = "商品名称不能为空")
    private String goods_name;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

}
