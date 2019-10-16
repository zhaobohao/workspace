package com.gitee.sop.adminserver.api.demo.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class GoodsParam {

    @ApiDocField(description = "商品名称", required = true, example = "iphoneX")
    @NotEmpty(message = "商品名称不能为空")
    @Length(min = 3, max = 20, message = "{goods.name.length}=3,20")
    private String goods_name;
    
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

}
