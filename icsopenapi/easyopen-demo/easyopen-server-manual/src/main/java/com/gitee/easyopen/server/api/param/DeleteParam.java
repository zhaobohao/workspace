package com.gitee.easyopen.server.api.param;

import java.util.List;

import com.gitee.easyopen.doc.annotation.ApiDocField;

public class DeleteParam {

    @ApiDocField(description = "删除的字段")
    private List<Integer> ids;
    
    @ApiDocField(description = "商品名称")
    private String goods_name;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    
}
