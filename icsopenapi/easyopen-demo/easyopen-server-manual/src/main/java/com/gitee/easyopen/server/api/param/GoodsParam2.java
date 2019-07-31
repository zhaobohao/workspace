package com.gitee.easyopen.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;

public class GoodsParam2 extends GoodsParam {

    @ApiDocField(description="商品价格")
    private double goodsPrice;

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    
}
