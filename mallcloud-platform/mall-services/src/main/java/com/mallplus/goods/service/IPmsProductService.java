package com.mallplus.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mallplus.common.entity.pms.PmsBrand;
import com.mallplus.common.entity.pms.PmsProduct;
import com.mallplus.common.entity.pms.PmsProductVertifyRecord;
import com.mallplus.common.vo.GoodsDetailResult;
import com.mallplus.common.vo.HomeContentResult;
import com.mallplus.goods.vo.PmsProductParam;
import com.mallplus.goods.vo.PmsProductResult;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
public interface IPmsProductService extends IService<PmsProduct> {



    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    /**
     * 根据商品编号获取更新信息
     */
    PmsProductResult getUpdateInfo(Long id);

    /**
     * 更新商品
     */
    @Transactional
    int update(Long id, PmsProductParam productParam);
    /**
     * 批量修改审核状态
     *
     * @param ids          产品id
     * @param verifyStatus 审核状态
     * @param detail       审核详情
     */
    @Transactional
    int updateVerifyStatus(Long ids, Integer verifyStatus, String detail);

    /**
     * 批量修改商品上架状态
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * 批量修改商品推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 批量修改新品状态
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * 批量删除商品
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * 根据商品名称或者货号模糊查询
     */
    List<PmsProduct> list(String keyword);

    List<PmsProductVertifyRecord> getProductVertifyRecord(Long id);

    /**
     * 获取推荐品牌
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsBrand> getRecommendBrandList(int pageNum, int pageSize);
    /**
     * 获取最新商品
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProduct> getNewProductList(int pageNum, int pageSize);

    /**
     * 获取最热商品列表 按销量倒序
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<PmsProduct> getHotProductList(int pageNum, int pageSize);

    /**
     * 初始化商品到redis
     *
     * @return
     */
    Object initGoodsRedis();
    /**
     * 获取商品详情 优先取redis
     *
     * @param id
     * @return
     */
    GoodsDetailResult getGoodsRedisById(Long id);
    /**
     * 今日添加的商品
     *
     * @param id
     * @return
     */
    Integer countGoodsByToday(Long id);
    /**
     * 拍卖商品详情
     *
     * @param id
     * @return
     */
    Map<String, Object> queryPaiMaigoodsDetail(Long id);
    /**
     * 参与竞价
     *
     * @param goods
     * @return
     */
    Object updatePaiMai(PmsProduct goods);
    HomeContentResult content();
}
