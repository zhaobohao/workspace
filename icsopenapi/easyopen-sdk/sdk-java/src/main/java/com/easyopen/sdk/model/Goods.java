package com.easyopen.sdk.model;

import java.math.BigDecimal;

public class Goods {

    private Long id;
    private String goods_name;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods [id=" + id + ", goods_name=" + goods_name + ", price=" + price + "]";
    }

}
