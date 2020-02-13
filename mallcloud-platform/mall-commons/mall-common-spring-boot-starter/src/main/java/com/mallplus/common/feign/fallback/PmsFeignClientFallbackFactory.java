package com.mallplus.common.feign.fallback;

import com.mallplus.common.entity.pms.PmsGifts;

import com.mallplus.common.entity.pms.PmsProductConsult;
import com.mallplus.common.entity.pms.PmsSkuStock;
import com.mallplus.common.feign.PmsFeignClient;
import com.mallplus.common.entity.pms.PmsProduct;
import com.mallplus.common.vo.PromotionProduct;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * userService降级工场
 *
 * @author mall
 * @date 2019/1/18
 */
@Slf4j
@Component
public class PmsFeignClientFallbackFactory implements FallbackFactory<PmsFeignClient> {
    @Override
    public PmsFeignClient create(Throwable throwable) {
        return new PmsFeignClient() {

            @Override
            public PmsProduct selectById(Long id) {
                log.error("通过id查询商品异常:{}", id, throwable);
                return new PmsProduct();
            }

            @Override
            public PmsSkuStock selectSkuById(Long id) {
                return null;
            }

            @Override
            public List<PromotionProduct> getPromotionProductList(List<Long> productIdList) {
                return null;
            }

            @Override
            public void updateSkuById(PmsSkuStock skuStock) {

            }

            @Override
            public void updateGoodsById(PmsProduct goods) {

            }

            @Override
            public PmsGifts getGiftById(Long goodsId) {
                return null;
            }

            @Override
            public void updateGiftById(PmsGifts newGoods) {

            }

            @Override
            public List<PmsProduct> listGoodsByIds(List<Long> ids) {
                return null;
            }

            @Override
            public void saveProductConsult(PmsProductConsult productConsult) {

            }

            @Override
            public PmsProduct selectPmsJiFenById(Long goodsId) {
                return null;
            }
        };
    }
}
