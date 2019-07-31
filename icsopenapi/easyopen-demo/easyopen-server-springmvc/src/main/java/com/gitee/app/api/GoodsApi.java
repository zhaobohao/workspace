package com.gitee.app.api;

import com.gitee.app.model.Goods;
import com.gitee.app.model.GoodsParam;
import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 业务类
 * 
 * @author tanghc
 *
 */
@ApiService
@ApiDoc("商品模块")
public class GoodsApi {

    @Api(name = "goods.get")
    @ApiDocMethod(description = "获取商品")
    public Goods getGoods(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("苹果iPhoneX");
        goods.setPrice(new BigDecimal(8000));
        return goods;
    }

    @Api(name = "goods.list", version = "2.0")
    @ApiDocMethod(description = "获取商品列表", results = {
            @ApiDocField(description = "商品列表", name = "list", elementClass = Goods.class) })
    public List<Goods> listGoods(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("iPhoneX");
        goods.setPrice(new BigDecimal(8000));

        Goods goods2 = new Goods();
        goods2.setId(2L);
        goods2.setGoods_name("三星");
        goods2.setPrice(new BigDecimal(7000));
        return Arrays.asList(goods, goods2);
    }

}
