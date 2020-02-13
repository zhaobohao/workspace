package com.mallplus.goods.controller;


import com.mallplus.common.entity.pms.PmsGifts;
import com.mallplus.common.entity.pms.PmsProduct;
import com.mallplus.common.entity.pms.PmsSkuStock;
import com.mallplus.common.redis.template.RedisRepository;
import com.mallplus.common.redis.template.RedisUtil;
import com.mallplus.goods.mapper.PmsGiftsMapper;
import com.mallplus.goods.mapper.PmsProductCategoryMapper;
import com.mallplus.goods.mapper.PmsProductMapper;
import com.mallplus.goods.mapper.PmsSkuStockMapper;
import com.mallplus.goods.service.*;
import com.mallplus.goods.vo.PromotionProduct;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/2 15:02
 * @Description:
 */
@Slf4j
@RestController
@Api(tags = "NotAuthRpcPmsController", description = "商品关系管理")
public class NotAuthRpcPmsController {

    @Autowired
    private IPmsProductConsultService pmsProductConsultService;
    @Resource
    private IPmsProductService pmsProductService;
    @Resource
    private IPmsProductAttributeCategoryService productAttributeCategoryService;
    @Resource
    private IPmsProductCategoryService productCategoryService;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private IPmsBrandService IPmsBrandService;
    @Resource
    private RedisRepository redisRepository;
    @Resource
    private PmsProductCategoryMapper categoryMapper;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private IPmsFavoriteService favoriteService;
    @Resource
    private PmsSkuStockMapper skuStockMapper;
    @Resource
    private PmsGiftsMapper giftsMapper;

    @GetMapping(value = "/notAuth/rpc/PmsProduct/id", params = "id")
    PmsProduct selectById(Long id) {
        return productMapper.selectById(id);
    }

    @GetMapping(value = "/notAuth/rpc/PmsJifenProduct/id", params = "id")
    PmsProduct selectPmsJiFenById(Long id) {
        PmsGifts pg = giftsMapper.selectById(id);
        PmsProduct pp = new PmsProduct();
        pp.setId(pg.getId());
        pp.setName(pg.getTitle());
        pp.setPic(pg.getIcon());
        pp.setPrice(pg.getPrice());
        pp.setStock(pg.getStock());
        pp.setProductCategoryId(pg.getCategoryId());
        return pp;
    }

    @GetMapping(value = "/notAuth/rpc/PmsSkuStock/id", params = "id")
    PmsSkuStock selectSkuById(Long id) {
        return skuStockMapper.selectById(id);
    }

    @GetMapping(value = "/notAuth/rpc/getPromotionProductList")
    List<PromotionProduct> getPromotionProductList(List<Long> productIdList) {
        return null;
    }

    @PostMapping(value = "/notAuth/rpc/updateSkuById")
    void updateSkuById(PmsSkuStock skuStock) {
        skuStockMapper.updateById(skuStock);
    }


}
