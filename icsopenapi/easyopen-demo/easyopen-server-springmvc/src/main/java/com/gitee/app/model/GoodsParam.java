package com.gitee.app.model;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.gitee.easyopen.doc.annotation.ApiDocField;

public class GoodsParam {

    @ApiDocField(description = "商品名称", required = true, example = "iphoneX")
    @NotBlank(message = "商品名称不能为空")
    @Length(min = 3, max = 20, message = "{goods.name.length}=3,20")
    private String goods_name;
    
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

}
