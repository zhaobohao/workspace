package com.gitee.easyopen.server.api;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.ext.hystrix.annotation.ApiHystrix;
import com.gitee.easyopen.server.api.param.GoodsParam;
import com.gitee.easyopen.server.api.result.Goods;

import java.math.BigDecimal;

/**
 * 业务类
 *
 * @author tanghc
 */
@ApiService
@ApiDoc("商品模块")
public class GoodsApi {

    @Api(name = "goods.get")
    @ApiDocMethod(description="获取商品")
    @ApiHystrix(executionTimeoutInMilliseconds = 500, errorCode = "333", errorMsg = "超时了。")
    Goods getGoods(GoodsParam param) {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoods_name("苹果iPhoneX");
        goods.setPrice(new BigDecimal(8000));
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return goods;
    }

}
