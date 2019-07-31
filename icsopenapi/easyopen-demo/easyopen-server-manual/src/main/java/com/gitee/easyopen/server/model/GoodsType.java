package com.gitee.easyopen.server.model;

import com.gitee.easyopen.doc.IEnum;

/**
 * 商品类型
 *
 * @author tanghc
 */
public enum GoodsType implements IEnum {
    ELECTRIC("家电"),
    CLOTH("衣服"),
    FOOD("食物"),
    ;

    private String desc;

    GoodsType(String desc) {
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return desc;
    }
}
