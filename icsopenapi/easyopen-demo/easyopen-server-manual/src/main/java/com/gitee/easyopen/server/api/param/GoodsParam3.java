package com.gitee.easyopen.server.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.server.model.GoodsType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class GoodsParam3 {

    @ApiDocField(description = "商品名称", required = true, example = "iphoneX")
    @NotEmpty(message = "商品名称不能为空")
    private String goods_name;

    @ApiDocField(description = "商品类型", enumClass = GoodsType.class, required = true, example = "FOOD")
    @NotBlank(message = "枚举类型不能为空")
    private String goods_type;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }
}
