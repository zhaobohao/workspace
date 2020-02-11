package com.mallplus.common.vo;

/**
 * @Auther: shenzhuan
 * @Date: 2019/4/26 17:52
 * @Description:
 */
public class Rediskey {

    public static final String ARTICLE_VIEWCOUNT_CODE = "ARTICLEVIEWCOUNTCODE_";
    public static final String ARTICLE_VIEWCOUNT_KEY = "ARTICLE_VIEWCOUNT_KEY";

    public static final String STORE_VIEWCOUNT_CODE = "STOREVIEWCOUNTCODE_";
    public static final String STORE_VIEWCOUNT_KEY = "STORE_VIEWCOUNT_KEY";

    public static final String GOODS_VIEWCOUNT_CODE = "GOODSVIEWCOUNTCODE_";
    public static final String GOODS_VIEWCOUNT_KEY = "GOODS_VIEWCOUNT_KEY";
    public static final String KDWL_INFO_CACHE = "KDWL_INFO_CACHE";

    public static String appletBannerKey = "appletBannerKey";
    public static String appletCategoryKey = "appletCategoryKey";
    public static String appletNavIconKey = "appletNavIconKey";
    public static String appletHotProductsKey = "appletHotProductsKey";
    public static String appletNewProductsKey = "appletNewProductsKey";
    public static String appletCateProductsKey = "appletCateProductsKey";
    public static String appletsmsFlashPromotionProductKey = "appletsmsFlashPromotionProductKey";


    public static String allTreesList = "allTreesList:%s";
    public static String menuTreesList = "menuTreesList:%s";
    public static String permissionTreesList = "permissionTreesList:%s";
    public static String allMenuList = "menuList:%s";
    public static String menuList = "menuList:%s";


    public static String HomeContentResult = "HomeContentResult";
    public static String PmsProductResult = "PmsProductResult";
    public static String orderDetailResult = "orderDetailResult";

    public static String PmsProductConsult = "PmsProductConsult";


    public static String GOODSDETAIL = "GOODSDETAIL:%s";

    public static String GOODSHISTORY = "GOODSHISTORY:%s";

    public static String HOMEPAGE = "HomeJsshop";

    public static String HOMEPAGEMOBILE = "HOMEPAGEMOBILE";
    public static String HOMEPAGEmallplus1 = "HomeMallplus1";
    public static String HOMEPAGEmallplus2 = "HomeMallplus2";
    public static String HOMEPAGE2 = "HomeCrmeb";
    public static String HOMEPAGEPC = "HomePc";
    public static String categoryAndChilds = "categoryAndChilds";
    public static String goodsConsult = "goodsConsult";
    public static String categoryAndGoodsList = "categoryAndGoodsList";
    public static String specialcategoryAndGoodsList = "specialcategoryAndGoodsList";
    public static String goodsCategorys = "goodsCategorys";
    public static String orderDetail = "orderDetail";
    public static String getorderstatusnum = "getorderstatusnum";
    /**
     * diypage.setStatus(1);
     * diypage.setType(2);
     */
    public static String EsShopDiypage = "EsShopDiypage12:%s";
    /**
     * 会员
     */
    public static String MEMBER = "MEMBER:%s";
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";

    /**
     * 产生key:如在newsId为2上的咨询点赞后会产生key: LIKE:ENTITY_NEWS:2
     *
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getLikeKey(int entityId, int entityType) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    /**
     * 取消赞:如在newsId为2上的资讯取消点赞后会产生key: DISLIKE:ENTITY_NEWS:2
     *
     * @param entityId
     * @param entityType
     * @return
     */
    public static String getDisLikeKey(int entityId, int entityType) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }


}
