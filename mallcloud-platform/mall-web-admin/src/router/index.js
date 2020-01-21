import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '../views/layout/Layout'

Vue.use(Router)

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  {path: '/acceptStore', component: () => import('@/views/login/acceptStore'), hidden: true},
  {path: '/login', component: () => import('@/views/login/index'), hidden: true},
  {path: '/404', component: () => import('@/views/404'), hidden: true},
  {
    path: '/store',
    component: () => import('@/views/home/store'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/home/index'),
      meta: {title: '首页', icon: 'home'}
    }]
  },
  {
    path: '/@/components/templatePage',
    name: 'templateComponents',
    meta: {
      title: '组件测试',
      showflg: true,
      showrole: true,
      roles: "-2",
      level1: 'common',
    },
    component: () =>
        import ('@/components/templatePage'),
  },
  {
    path: '/pms',
    component: Layout,
    redirect: '/pms/product',
    name: 'pms',
    meta: {title: '商品', icon: 'product'},
    children: [{
      path: 'product',
      name: 'product',
      component: () => import('@/views/pms/product/index'),
      meta: {title: '商品列表', icon: 'product-list'}
    },
      {
        path: 'addProduct',
        name: 'addProduct',
        component: () => import('@/views/pms/product/add'),
        meta: {title: '添加商品', icon: 'product-add'}
      },
      {
        path: 'updateProduct',
        name: 'updateProduct',
        component: () => import('@/views/pms/product/update'),
        meta: {title: '修改商品', icon: 'product-add'},
        hidden: true
      },
      {
        path: 'productRecycle',
        name: 'productRecycle',
        component: () => import('@/views/pms/product/index'),
        meta: {title: '商品回收站', icon: 'product-recycle'},
        hidden: true
      },
      {
        path: 'productComment',
        name: 'productComment',
        component: () => import('@/views/pms/product/index'),
        meta: {title: '商品评价', icon: 'product-comment'},
        hidden: true
      },
      {
        path: 'productCate',
        name: 'productCate',
        component: () => import('@/views/pms/productCate/index'),
        meta: {title: '商品分类', icon: 'product-cate'}
      },
      {
        path: 'addProductCate',
        name: 'addProductCate',
        component: () => import('@/views/pms/productCate/add'),
        meta: {title: '添加商品分类'},
        hidden: true
      },
      {
        path: 'updateProductCate',
        name: 'updateProductCate',
        component: () => import('@/views/pms/productCate/update'),
        meta: {title: '修改商品分类'},
        hidden: true
      },
      {
        path: 'productAttr',
        name: 'productAttr',
        component: () => import('@/views/pms/productAttr/index'),
        meta: {title: '商品类型', icon: 'product-attr'}
      },
      {
        path: 'productAttrList',
        name: 'productAttrList',
        component: () => import('@/views/pms/productAttr/productAttrList'),
        meta: {title: '商品属性列表'},
        hidden: true
      },
      {
        path: 'addProductAttr',
        name: 'addProductAttr',
        component: () => import('@/views/pms/productAttr/addProductAttr'),
        meta: {title: '添加商品属性'},
        hidden: true
      },
      {
        path: 'updateProductAttr',
        name: 'updateProductAttr',
        component: () => import('@/views/pms/productAttr/updateProductAttr'),
        meta: {title: '修改商品属性'},
        hidden: true
      },
      {
        path: 'brand',
        name: 'brand',
        component: () => import('@/views/pms/brand/index'),
        meta: {title: '品牌管理', icon: 'product-brand'}
      },
      {
        path: 'addBrand',
        name: 'addBrand',
        component: () => import('@/views/pms/brand/add'),
        meta: {title: '添加品牌'},
        hidden: true
      },
      {
        path: 'updateBrand',
        name: 'updateBrand',
        component: () => import('@/views/pms/brand/update'),
        meta: {title: '编辑品牌'},
        hidden: true
      },
      //小程序首页分类图标管理
      {
        path: 'smallNaviconCategory',
        name: 'smallNaviconCategory',
        component: () => import('@/views/pms/smallNaviconCategory/index'),
        meta: {title: '小程序首页nav管理列表}'}
      },
      {
        path: 'addSmallNaviconCategory',
        name: 'addSmallNaviconCategory',
        component: () => import('@/views/pms/smallNaviconCategory/add'),
        meta: {title: '添加小程序首页nav管理', icon: 'smallNaviconCategory-add'}
      },
      {
        path: 'updateSmallNaviconCategory',
        name: 'updateSmallNaviconCategory',
        component: () => import('@/views/pms/smallNaviconCategory/update'),
        meta: {title: '修改小程序首页nav管理', icon: 'smallNaviconCategory-update'},
        hidden: true
      },
      {
        path: 'PmsGifts',
        name: 'PmsGifts',
        component: () => import('@/views/pms/Gifts/index'),
        meta: {title: '赠品管理', icon: 'product-list'}
      },

      {
        path: 'addPmsGifts',
        name: 'addPmsGifts',
        component: () => import('@/views/pms/Gifts/add'),
        meta: {title: '添加赠品'},
        hidden: true
      },
      {
        path: 'updatePmsGifts',
        name: 'updatePmsGifts',
        component: () => import('@/views/pms/Gifts/update'),
        meta: {title: '编辑赠品'},
        hidden: true
      },
      {
        path: 'PmsGiftsCategory',
        name: 'PmsGiftsCategory',
        component: () => import('@/views/pms/GiftsCategory/index'),
        meta: {title: '赠品分类', icon: 'product-list'}
      },

      {
        path: 'addPmsGiftsCategory',
        name: 'addPmsGiftsCategory',
        component: () => import('@/views/pms/GiftsCategory/add'),
        meta: {title: '添加赠品分类'},
        hidden: true
      },
      {
        path: 'updatePmsGiftsCategory',
        name: 'updatePmsGiftsCategory',
        component: () => import('@/views/pms/GiftsCategory/update'),
        meta: {title: '编辑赠品分类'},
        hidden: true
      },
      {
        path: 'ProductConsult',
        name: 'ProductConsult',
        component: () => import('@/views/pms/productConsult/index'),
        meta: {title: '商品评论'},
        hidden: true
      }

    ]
  },
  {
    path: '/oms',
    component: Layout,
    redirect: '/oms/order',
    name: 'oms',
    meta: {title: '订单', icon: 'order'},
    children: [
      {
        path: 'omsCompanyAddress',
        name: 'omsCompanyAddress',
        component: () => import('@/views/oms/omsCompanyAddress/index'),
        meta: {title: '发货地址列表', icon: 'product-list'}
      },

      {
        path: 'addOmsCompanyAddress',
        name: 'addOmsCompanyAddress',
        component: () => import('@/views/oms/omsCompanyAddress/add'),
        meta: {title: '添加发货地址'},
        hidden: true
      },
      {
        path: 'updateOmsCompanyAddress',
        name: 'updateOmsCompanyAddress',
        component: () => import('@/views/oms/omsCompanyAddress/update'),
        meta: {title: '编辑发货地址'},
        hidden: true
      },

      {
        path: 'omsPayments',
        name: 'omsPayments',
        component: () => import('@/views/oms/omsPayments/index'),
        meta: {title: '支付配置列表', icon: 'product-list'}
      },

      {
        path: 'addOmsPayments',
        name: 'addOmsPayments',
        component: () => import('@/views/oms/omsPayments/add'),
        meta: {title: '添加支付配置'},
        hidden: true
      },
      {
        path: 'updateOmsPayments',
        name: 'updateOmsPayments',
        component: () => import('@/views/oms/omsPayments/update'),
        meta: {title: '编辑支付配置'},
        hidden: true
      },
      {
        path: 'order',
        name: 'order',
        component: () => import('@/views/oms/order/index'),
        meta: {title: '订单列表', icon: 'product-list'}
      },
      {
        path: 'orderDetail',
        name: 'orderDetail',
        component: () => import('@/views/oms/order/orderDetail'),
        meta: {title: '订单详情'},
        hidden: true
      },
      {
        path: 'deliverOrderList',
        name: 'deliverOrderList',
        component: () => import('@/views/oms/order/deliverOrderList'),
        meta: {title: '发货列表'},
        hidden: true
      },
      {
        path: 'orderSetting',
        name: 'orderSetting',
        component: () => import('@/views/oms/order/setting'),
        meta: {title: '订单设置', icon: 'order-setting'}
      },
      {
        path: 'returnApply',
        name: 'returnApply',
        component: () => import('@/views/oms/apply/index'),
        meta: {title: '退货申请处理', icon: 'order-return'}
      },
      {
        path: 'returnReason',
        name: 'returnReason',
        component: () => import('@/views/oms/apply/reason'),
        meta: {title: '退货原因设置', icon: 'order-return-reason'}
      },
      {
        path: 'returnApplyDetail',
        name: 'returnApplyDetail',
        component: () => import('@/views/oms/apply/applyDetail'),
        meta: {title: '退货原因详情'},
        hidden: true
      },
      {
        path: 'OrderProductConsult',
        name: 'OrderProductConsult',
        component: () => import('@/views/oms/orderConsult/index'),
        meta: {title: '订单评论'},
        hidden: true
      },
      {
        path: 'FeightTemplate',
        name: 'FeightTemplate',
        component: () => import('@/views/pms/feightTemplate/index'),
        meta: {title: '运费模版', icon: 'product-list'}
      },

      {
        path: 'addFeightTemplate',
        name: 'addFeightTemplate',
        component: () => import('@/views/pms/feightTemplate/add'),
        meta: {title: '添加运费模版'},
        hidden: true
      },
      {
        path: 'updateFeightTemplate',
        name: 'updateFeightTemplate',
        component: () => import('@/views/pms/feightTemplate/update'),
        meta: {title: '编辑运费模版'},
        hidden: true
      }

    ]
  },
  {
    path: '/marking',
    component: Layout,
    redirect: '/marking/smsDiyPage',
    name: 'marking',
    meta: {title: '运营', icon: 'sms'},
    children: [
      {
        path: 'smsContentMsg',
        name: 'smsContentMsg',
        component: () => import('@/views/sms/smsContentMsg/index'),
        meta: {title: '短信记录列表', icon: 'product-list'}
      },
      {
        path: 'smsContent',
        name: 'smsContent',
        component: () => import('@/views/sms/smsContent/index'),
        meta: {title: '短信模版列表', icon: 'product-list'}
      },

      {
        path: 'addSmsContent',
        name: 'addSmsContent',
        component: () => import('@/views/sms/smsContent/add'),
        meta: {title: '添加短信模版'},
        hidden: true
      },
      {
        path: 'updateSmsContent',
        name: 'updateSmsContent',
        component: () => import('@/views/sms/smsContent/update'),
        meta: {title: '编辑短信模版'},
        hidden: true
      },
      {
        path: 'smsDiyPage',
        name: 'smsDiyPage',
        component: () => import('@/views/sms/page/index'),
        meta: {title: '添加页面模版', icon: 'sms-flash'}
      },
      {
        path: 'smsDiypageTemplateCategory',
        name: 'smsDiypageTemplateCategory',
        component: () => import('@/views/sms/smsDiypageTemplateCategory/index'),
        meta: {title: '页面模版列表', icon: 'product-list'}
      },

      {
        path: 'addSmsDiypageTemplateCategory',
        name: 'addSmsDiypageTemplateCategory',
        component: () => import('@/views/sms/smsDiypageTemplateCategory/add'),
        meta: {title: '添加页面模版'},
        hidden: true
      },
      {
        path: 'updateSmsDiypageTemplateCategory',
        name: 'updateSmsDiypageTemplateCategory',
        component: () => import('@/views/sms/smsDiypageTemplateCategory/update'),
        meta: {title: '编辑页面模版'},
        hidden: true
      },
      {
        path: 'homeBrand',
        name: 'homeBrand',
        component: () => import('@/views/sms/brand/index'),
        meta: {title: '品牌推荐', icon: 'product-brand'}
      },
      {
        path: 'homeNew',
        name: 'homeNew',
        component: () => import('@/views/sms/new/index'),
        meta: {title: '新品推荐', icon: 'sms-new'}
      },
      {
        path: 'homeHot',
        name: 'homeHot',
        component: () => import('@/views/sms/hot/index'),
        meta: {title: '人气推荐', icon: 'sms-hot'}
      },
      {
        path: 'homeSubject',
        name: 'homeSubject',
        component: () => import('@/views/sms/subject/index'),
        meta: {title: '专题推荐', icon: 'sms-subject'}
      },
      {
        path: 'homeAdvertise',
        name: 'homeAdvertise',
        component: () => import('@/views/sms/advertise/index'),
        meta: {title: '广告列表', icon: 'sms-ad'}
      },
      {
        path: 'addHomeAdvertise',
        name: 'addHomeAdvertise',
        component: () => import('@/views/sms/advertise/add'),
        meta: {title: '添加广告'},
        hidden: true
      },
      {
        path: 'updateHomeAdvertise',
        name: 'updateHomeAdvertise',
        component: () => import('@/views/sms/advertise/update'),
        meta: {title: '编辑广告'},
        hidden: true
      },

      ]
  },
  {
    path: '/sms',
    component: Layout,
    redirect: '/sms/coupon',
    name: 'sms',
    meta: {title: '营销', icon: 'sms'},
    children: [


      {
        path: 'flashPromotion',
        name: 'flashPromotion',
        component: () => import('@/views/sms/flash/index'),
        meta: {title: '秒杀活动列表', icon: 'sms-flash'}
      },
      {
        path: 'flashSession',
        name: 'flashSession',
        component: () => import('@/views/sms/flash/sessionList'),
        meta: {title: '秒杀时间段列表'},
        hidden: true
      },
      {
        path: 'selectSession',
        name: 'selectSession',
        component: () => import('@/views/sms/flash/selectSessionList'),
        meta: {title: '秒杀时间段选择'},
        hidden: true
      },
      {
        path: 'flashProductRelation',
        name: 'flashProductRelation',
        component: () => import('@/views/sms/flash/productRelationList'),
        meta: {title: '秒杀商品列表'},
        hidden: true
      },
      {
        path: 'coupon',
        name: 'coupon',
        component: () => import('@/views/sms/coupon/index'),
        meta: {title: '优惠券列表', icon: 'sms-coupon'}
      },
      {
        path: 'addCoupon',
        name: 'addCoupon',
        component: () => import('@/views/sms/coupon/add'),
        meta: {title: '添加优惠券'},
        hidden: true
      },
      {
        path: 'updateCoupon',
        name: 'updateCoupon',
        component: () => import('@/views/sms/coupon/update'),
        meta: {title: '修改优惠券'},
        hidden: true
      },
      {
        path: 'couponHistory',
        name: 'couponHistory',
        component: () => import('@/views/sms/coupon/history'),
        meta: {title: '优惠券领取详情'},
        hidden: true
      },

      {
        path: 'group',
        name: 'group',
        component: () => import('@/views/sms/group/index'),
        meta: {title: '拼团列表', icon: 'product-list'}
      },
      {
        path: 'groupHistory',
        name: 'groupHistory',
        component: () => import('@/views/sms/group/detail'),
        meta: {title: '拼团详情'},
        hidden: true
      },
      {
        path: 'addGroup',
        name: 'addGroup',
        component: () => import('@/views/sms/group/add'),
        meta: {title: '添加拼团'},
        hidden: true
      },
      {
        path: 'updateGroup',
        name: 'updateGroup',
        component: () => import('@/views/sms/group/update'),
        meta: {title: '编辑拼团'},
        hidden: true
      },
      {
        path: 'redPacket',
        name: 'redPacket',
        component: () => import('@/views/sms/redPacket/index'),
        meta: {title: '红包列表', icon: 'product-list'}
      },

      {
        path: 'addRedPacket',
        name: 'addRedPacket',
        component: () => import('@/views/sms/redPacket/add'),
        meta: {title: '添加红包'},
        hidden: true
      },
      {
        path: 'updateRedPacket',
        name: 'updateRedPacket',
        component: () => import('@/views/sms/redPacket/update'),
        meta: {title: '编辑红包'},
        hidden: true
      },
      {
        path: 'basicMark',
        name: 'basicMark',
        component: () => import('@/views/sms/basicMark/index'),
        meta: {title: '基本营销', icon: 'product-list'}
      },

      {
        path: 'addBasicMark',
        name: 'addBasicMark',
        component: () => import('@/views/sms/basicMark/add'),
        meta: {title: '添加基本营销'},
        hidden: true
      },
      {
        path: 'updateBasicMark',
        name: 'updateBasicMark',
        component: () => import('@/views/sms/basicMark/update'),
        meta: {title: '编辑基本营销'},
        hidden: true
      },
      {
        path: 'smsBasicGifts',
        name: 'smsBasicGifts',
        component: () => import('@/views/sms/SmsBasicGifts/index'),
        meta: {title: '赠品营销', icon: 'product-list'}
      },

      {
        path: 'addSmsBasicGifts',
        name: 'addSmsBasicGifts',
        component: () => import('@/views/sms/SmsBasicGifts/add'),
        meta: {title: '添加赠品营销'},
        hidden: true
      },
      {
        path: 'updateSmsBasicGifts',
        name: 'updateSmsBasicGiftsk',
        component: () => import('@/views/sms/SmsBasicGifts/update'),
        meta: {title: '编辑赠品营销'},
        hidden: true
      }
      ,
      {
        path: 'groupActivity',
        name: 'groupActivity',
        component: () => import('@/views/sms/groupActivity/index'),
        meta: {title: '团购管理', icon: 'product-list'}
      },

      {
        path: 'addgroupActivity',
        name: 'addgroupActivity',
        component: () => import('@/views/sms/groupActivity/add'),
        meta: {title: '添加团购'},
        hidden: true
      },
      {
        path: 'updategroupActivity',
        name: 'updategroupActivity',
        component: () => import('@/views/sms/groupActivity/update'),
        meta: {title: '编辑团购'},
        hidden: true
      },{
        path: 'smsBargainConfig',
        name: 'smsBargainConfig',
        component: () => import('@/views/sms/smsBargainConfig/index'),
        meta: {title: '砍价商品列表', icon: 'product-list'}
      },

      {
        path: 'addSmsBargainConfig',
        name: 'addSmsBargainConfig',
        component: () => import('@/views/sms/smsBargainConfig/add'),
        meta: {title: '添加砍价商品'},
        hidden: true
      },
      {
        path: 'updateSmsBargainConfig',
        name: 'updateSmsBargainConfig',
        component: () => import('@/views/sms/smsBargainConfig/update'),
        meta: {title: '编辑砍价商品'},
        hidden: true
      }, {
        path: 'smsDraw',
        name: 'smsDraw',
        component: () => import('@/views/sms/draw/index'),
        meta: {title: '抽奖活动', icon: 'product-list'}
      }, {
        path: 'addsmsDraw',
        name: 'addsmsDraw',
        component: () => import('@/views/sms/draw/add'),
        meta: {title: '添加抽奖'},
        hidden: true
      },
      {
        path: 'updatesmsDraw',
        name: 'updatesmsDraw',
        component: () => import('@/views/sms/draw/update'),
        meta: {title: '编辑抽奖'},
        hidden: true
      }, {
        path: 'smsPaimai',
        name: 'smsPaimai',
        component: () => import('@/views/sms/paimai/index'),
        meta: {title: '竞拍活动', icon: 'product-list'}
      }, {
        path: 'addsmsPaimai',
        name: 'addsmsPaimai',
        component: () => import('@/views/sms/paimai/add'),
        meta: {title: '添加竞拍'},
        hidden: true
      },
      {
        path: 'updatesmsPaimai',
        name: 'updatesmsPaimai',
        component: () => import('@/views/sms/paimai/update'),
        meta: {title: '编辑竞拍'},
        hidden: true
      }
    ]
  },


  {
    path: '/jifen',
    component: Layout,
    redirect: '/jifen/jifenCoupon',
    name: 'jifen',
    meta: {title: '积分商城', icon: 'home'},
    children: [
      {
        path: 'jifenSetting',
        name: 'jifenSetting',
        component: () => import('@/views/ums/jifensetting'),
        meta: {title: '积分设置', icon: 'order-setting'}
      },
      {
        path: 'jifenCoupon',
        name: 'jifenCoupon',
        component: () => import('@/views/jifen/jifenCoupon/index'),
        meta: {title: '积分券列表', icon: 'product-list'}
      },

      {
        path: 'addJifenCoupon',
        name: 'addJifenCoupon',
        component: () => import('@/views/jifen/jifenCoupon/add'),
        meta: {title: '添加积分券'},
        hidden: true
      },
      {
        path: 'updateJifenCoupon',
        name: 'updateJifenCoupon',
        component: () => import('@/views/jifen/jifenCoupon/update'),
        meta: {title: '编辑积分券'},
        hidden: true
      },
      {
        path: 'jifenDonateRule',
        name: 'jifenDonateRule',
        component: () => import('@/views/jifen/jifenDonateRule/index'),
        meta: {title: '积分赠送规则列表', icon: 'product-list'}
      },

      {
        path: 'addJifenDonateRule',
        name: 'addJifenDonateRule',
        component: () => import('@/views/jifen/jifenDonateRule/add'),
        meta: {title: '添加积分赠送规则'},
        hidden: true
      },
      {
        path: 'updateJifenDonateRule',
        name: 'updateJifenDonateRule',
        component: () => import('@/views/jifen/jifenDonateRule/update'),
        meta: {title: '编辑积分赠送规则'},
        hidden: true
      },
      {
        path: 'jifenSignRule',
        name: 'jifenSignRule',
        component: () => import('@/views/jifen/jifenSignRule/index'),
        meta: {title: '积分签到规则列表', icon: 'product-list'}
      },

      {
        path: 'addJifenSignRule',
        name: 'addJifenSignRule',
        component: () => import('@/views/jifen/jifenSignRule/add'),
        meta: {title: '添加积分签到规则'},
        hidden: true
      },
      {
        path: 'updateJifenSignRule',
        name: 'updateJifenSignRule',
        component: () => import('@/views/jifen/jifenSignRule/update'),
        meta: {title: '编辑积分签到规则'},
        hidden: true
      },


    ]
  },
  {
    path: '/fenxiao',
    component: Layout,
    redirect: '/fenxiao/fenxiaoChecks',
    name: 'fenxiao',
    meta: {title: '分销商城', icon: 'home'},
    children: [
      {
        path: 'fenxiaoChecks',
        name: 'fenxiaoChecks',
        component: () => import('@/views/fenxiao/fenxiaoChecks/index'),
        meta: {title: '分销审核列表', icon: 'product-list'}
      },

      {
        path: 'addFenxiaoChecks',
        name: 'addFenxiaoChecks',
        component: () => import('@/views/fenxiao/fenxiaoChecks/add'),
        meta: {title: '添加分销审核'},
        hidden: true
      },
      {
        path: 'updateFenxiaoChecks',
        name: 'updateFenxiaoChecks',
        component: () => import('@/views/fenxiao/fenxiaoChecks/update'),
        meta: {title: '编辑分销审核'},
        hidden: true
      },
      {
        path: 'fenxiaoConfig',
        name: 'fenxiaoConfig',
        component: () => import('@/views/fenxiao/fenxiaoConfig/index'),
        meta: {title: '分销配置列表', icon: 'product-list'}
      },

      {
        path: 'addFenxiaoConfig',
        name: 'addFenxiaoConfig',
        component: () => import('@/views/fenxiao/fenxiaoConfig/add'),
        meta: {title: '添加分销配置'},
        hidden: true
      },
      {
        path: 'updateFenxiaoConfig',
        name: 'updateFenxiaoConfig',
        component: () => import('@/views/fenxiao/fenxiaoConfig/update'),
        meta: {title: '编辑分销配置'},
        hidden: true
      },
      {
        path: 'fenxiaoRecords',
        name: 'fenxiaoRecords',
        component: () => import('@/views/fenxiao/fenxiaoRecords/index'),
        meta: {title: '分销记录列表', icon: 'product-list'}
      },

      {
        path: 'addFenxiaoRecords',
        name: 'addFenxiaoRecords',
        component: () => import('@/views/fenxiao/fenxiaoRecords/add'),
        meta: {title: '添加分销记录'},
        hidden: true
      },
      {
        path: 'updateFenxiaoRecords',
        name: 'updateFenxiaoRecords',
        component: () => import('@/views/fenxiao/fenxiaoRecords/update'),
        meta: {title: '编辑分销记录'},
        hidden: true
      },

      {
        path: 'fenxiaoUserRelate',
        name: 'fenxiaoUserRelate',
        component: () => import('@/views/fenxiao/fenxiaoUserRelate/index'),
        meta: {title: '分销关系列表', icon: 'product-list'}
      },

      {
        path: 'addFenxiaoUserRelate',
        name: 'addFenxiaoUserRelate',
        component: () => import('@/views/fenxiao/fenxiaoUserRelate/add'),
        meta: {title: '添加分销关系'},
        hidden: true
      },
      {
        path: 'updateFenxiaoUserRelate',
        name: 'updateFenxiaoUserRelate',
        component: () => import('@/views/fenxiao/fenxiaoUserRelate/update'),
        meta: {title: '编辑分销关系'},
        hidden: true
      },

    ]
  },
  {
    path: '/ums',
    component: Layout,
    redirect: '/ums/member',
    name: 'ums',
    meta: {title: '会员', icon: 'home'},
    children: [
      {
        path: 'appletSetting',
        name: 'appletSetting',
        component: () => import('@/views/ums/setting'),
        meta: {title: '登录支付设置', icon: 'order-setting'}
      },

      {
        path: 'member',
        name: 'member',
        component: () => import('@/views/ums/member/index'),
        meta: {title: '会员列表', icon: 'product-list'}
      },

      {
        path: 'addMember',
        name: 'addMember',
        component: () => import('@/views/ums/member/add'),
        meta: {title: '添加会员'},
        hidden: true
      },
      {
        path: 'updateMember',
        name: 'updateMember',
        component: () => import('@/views/ums/member/update'),
        meta: {title: '编辑会员'},
        hidden: true
      }, {
        path: 'memberLevel',
        name: 'memberLevel',
        component: () => import('@/views/ums/memberLevel/index'),
        meta: {title: '会员等级列表', icon: 'product-list'}
      },

      {
        path: 'addMemberLevel',
        name: 'addMemberLevel',
        component: () => import('@/views/ums/memberLevel/add'),
        meta: {title: '添加会员等级'},
        hidden: true
      },
      {
        path: 'updateMemberLevel',
        name: 'updateMemberLevel',
        component: () => import('@/views/ums/memberLevel/update'),
        meta: {title: '编辑会员等级'},
        hidden: true
      }, {
        path: 'school',
        name: 'school',
        component: () => import('@/views/ums/school/index'),
        meta: {title: '学校列表', icon: 'product-list'}
      },

      {
        path: 'addSchool',
        name: 'addSchool',
        component: () => import('@/views/ums/school/add'),
        meta: {title: '添加学校'},
        hidden: true
      },
      {
        path: 'updateSchool',
        name: 'updateSchool',
        component: () => import('@/views/ums/school/update'),
        meta: {title: '编辑学校'},
        hidden: true
      }, {
        path: 'memberBlanceLog',
        name: 'memberBlanceLog',
        component: () => import('@/views/ums/memberBlanceLog/index'),
        meta: {title: '余额日志记录', icon: 'product-list'}
      }, {
        path: 'memberIntegration',
        name: 'memberIntegration',
        component: () => import('@/views/ums/memberIntegration/index'),
        meta: {title: '积分日志记录', icon: 'product-list'}
      }


    ]
  },

  {
    path: '/build',
    component: Layout,
    redirect: '/build/community',
    name: 'build',
    meta: {title: '物业管理', icon: 'home'},
    children: [
      {
        path: 'companyCommunity',
        name: 'companyCommunity',
        component: () => import('@/views/build/wuyeCompany/companyCommunity'),
        meta: {title: '绑定小区', icon: 'product-list'}
      },
      {
        path: 'community',
        name: 'community',
        component: () => import('@/views/build/community/index'),
        meta: {title: '小区列表', icon: 'product-list'}
      },

      {
        path: 'addCommunity',
        name: 'addCommunity',
        component: () => import('@/views/build/community/add'),
        meta: {title: '添加小区'},
        hidden: true
      },
      {
        path: 'updateCommunity',
        name: 'updateCommunity',
        component: () => import('@/views/build/community/update'),
        meta: {title: '编辑小区'},
        hidden: true
      },
      {
        path: 'room',
        name: 'room',
        component: () => import('@/views/build/room/index'),
        meta: {title: '房间列表', icon: 'product-list'}
      },

      {
        path: 'addRoom',
        name: 'addRoom',
        component: () => import('@/views/build/room/add'),
        meta: {title: '添加房间'},
        hidden: true
      },
      {
        path: 'updateRoom',
        name: 'updateRoom',
        component: () => import('@/views/build/room/update'),
        meta: {title: '编辑房间'},
        hidden: true
      },
      {
        path: 'floor',
        name: 'floor',
        component: () => import('@/views/build/floor/index'),
        meta: {title: '楼栋列表', icon: 'product-list'}
      },

      {
        path: 'addFloor',
        name: 'addFloor',
        component: () => import('@/views/build/floor/add'),
        meta: {title: '添加楼栋'},
        hidden: true
      },
      {
        path: 'updateFloor',
        name: 'updateFloor',
        component: () => import('@/views/build/floor/update'),
        meta: {title: '编辑楼栋'},
        hidden: true
      },
      {
        path: 'unit',
        name: 'unit',
        component: () => import('@/views/build/unit/index'),
        meta: {title: '单元列表', icon: 'product-list'}
      },

      {
        path: 'addUnit',
        name: 'addUnit',
        component: () => import('@/views/build/unit/add'),
        meta: {title: '添加单元'},
        hidden: true
      },
      {
        path: 'updateUnit',
        name: 'updateUnit',
        component: () => import('@/views/build/unit/update'),
        meta: {title: '编辑单元'},
        hidden: true
      },
      {
        path: 'owner',
        name: 'owner',
        component: () => import('@/views/build/owner/index'),
        meta: {title: '业主列表', icon: 'product-list'}
      },

      {
        path: 'addOwner',
        name: 'addOwner',
        component: () => import('@/views/build/owner/add'),
        meta: {title: '添加业主列表'},
        hidden: true
      },
      {
        path: 'updateOwner',
        name: 'updateOwner',
        component: () => import('@/views/build/owner/update'),
        meta: {title: '编辑业主列表'},
        hidden: true
      },


        {
          path: 'adv',
          name: 'adv',
          component: () => import('@/views/build/adv/index'),
          meta: {title: '社区广告', icon: 'product-list'}
        },

  {
    path: 'addAdv',
    name: 'addAdv',
    component: () => import('@/views/build/adv/add'),
    meta: {title: '添加社区广告'},
    hidden: true
  },
  {
    path: 'updateAdv',
    name: 'updateAdv',
    component: () => import('@/views/build/adv/update'),
    meta: {title: '编辑社区广告'},
    hidden: true
  },
  {
    path: 'buildGroup',
    name: 'buildGroup',
    component: () => import('@/views/build/group/index'),
    meta: {title: '社区团购', icon: 'product-list'}
  },

  {
    path: 'addBuildGroup',
    name: 'addBuildGroup',
    component: () => import('@/views/build/group/add'),
    meta: {title: '添加社区团购'},
    hidden: true
  },
  {
    path: 'updateBuildGroup',
    name: 'updateBuildGroup',
    component: () => import('@/views/build/group/update'),
    meta: {title: '编辑社区团购'},
    hidden: true
  },
      {
        path: 'buildGroupHistory',
        name: 'buildGroupHistory',
        component: () => import('@/views/build/group/detail'),
        meta: {title: '拼团详情'},
        hidden: true
      },
  {
    path: 'notice',
    name: 'notice',
    component: () => import('@/views/build/notice/index'),
    meta: {title: '社区公告', icon: 'product-list'}
  },

  {
    path: 'addNotice',
    name: 'addNotice',
    component: () => import('@/views/build/notice/add'),
    meta: {title: '添加社区公告'},
    hidden: true
  },
  {
    path: 'updateNotice',
    name: 'updateNotice',
    component: () => import('@/views/build/notice/update'),
    meta: {title: '编辑社区公告'},
    hidden: true
  },
  {
    path: 'repair',
    name: 'repair',
    component: () => import('@/views/build/repair/index'),
    meta: {title: '社区报修', icon: 'product-list'}
  },

  {
    path: 'addRepair',
    name: 'addRepair',
    component: () => import('@/views/build/repair/add'),
    meta: {title: '添加社区报修'},
    hidden: true
  },
  {
    path: 'updateRepair',
    name: 'updateRepair',
    component: () => import('@/views/build/repair/update'),
    meta: {title: '编辑社区报修'},
    hidden: true
  },
  {
    path: 'typePrice',
    name: 'typePrice',
    component: () => import('@/views/build/typePrice/index'),
    meta: {title: '费用类型', icon: 'product-list'}
  },

  {
    path: 'addTypePrice',
    name: 'addTypePrice',
    component: () => import('@/views/build/typePrice/add'),
    meta: {title: '添加费用类型'},
    hidden: true
  },
  {
    path: 'updateTypePrice',
    name: 'updateTypePrice',
    component: () => import('@/views/build/typePrice/update'),
    meta: {title: '编辑费用类型'},
    hidden: true
  },
  {
    path: 'wuyeCompany',
    name: 'wuyeCompany',
    component: () => import('@/views/build/wuyeCompany/index'),
    meta: {title: '物业公司', icon: 'product-list'}
  },

  {
    path: 'addWuyeCompany',
    name: 'addWuyeCompany',
    component: () => import('@/views/build/wuyeCompany/add'),
    meta: {title: '添加物业公司'},
    hidden: true
  },
  {
    path: 'updateWuyeCompany',
    name: 'updateWuyeCompany',
    component: () => import('@/views/build/wuyeCompany/update'),
    meta: {title: '编辑物业公司'},
    hidden: true
  },
  {
    path: 'wuyePrice',
    name: 'wuyePrice',
    component: () => import('@/views/build/wuyePrice/index'),
    meta: {title: '缴费记录', icon: 'product-list'}
  },

  {
    path: 'addWuyePrice',
    name: 'addWuyePrice',
    component: () => import('@/views/build/wuyePrice/add'),
    meta: {title: '添加缴费记录'},
    hidden: true
  },
  {
    path: 'updateWuyePrice',
    name: 'updateWuyePrice',
    component: () => import('@/views/build/wuyePrice/update'),
    meta: {title: '编辑缴费记录'},
    hidden: true
  },
    ]
  },
  {
    path: '/tool',
    component: Layout,
    redirect: '/tool/email',
    name: 'tool',
    meta: {title: '工具调试', icon: 'home'},
    children: [
      {
        path: 'webLog',
        name: 'webLog',
        component: () => import('@/views/sys/webLog/index'),
        meta: {title: '前台日志列表', icon: 'product-list'}
      }, {
        path: 'adminLog',
        name: 'adminLog',
        component: () => import('@/views/sys/adminLog/index'),
        meta: {title: '后台日志列表', icon: 'product-list'}
      }, {
        path: 'adminLogStatic',
        name: 'adminLogStatic',
        component: () => import('@/views/sys/adminLog/adminLogStatic'),
        meta: {title: '后台日志列表', icon: 'product-list'}
      },
      {
        path: 'webLogStatic',
        name: 'webLogStatic',
        component: () => import('@/views/sys/webLog/webLogStatic'),
        meta: {title: '后台日志列表', icon: 'product-list'}
      },
      {
        path: 'alipay',
        name: 'alipay',
        component: () =>
            import('@/views/sys/aliPay/index'),
        meta:
            {
              title: '支付宝测试',
              icon: 'dict-update'
            },
        hidden: true
      },
      {
        path: 'email',
        name: 'email',
        component: () =>
            import('@/views/sys/email/index'),
        meta:
            {
              title: '邮件测试',
              icon: 'dict-update'
            },
        hidden: true
      },
    ]
  },


  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/admin',
    name: 'sys',
    meta: {title: '系统管理', icon: 'home'},
    children: [


      {
        path: 'admin',
        name: 'admin',
        component: () => import('@/views/sys/admin/index'),
        meta: {title: '用户列表', icon: 'product-list'}
      },
      {
        path: 'userCommunity',
        name: 'userCommunity',
        component: () => import('@/views/sys/admin/userCommunity'),
        meta: {title: '绑定小区', icon: 'product-list'}
      },
      {
        path: 'gen',
        name: 'gen',
        component: () => import('@/views/sys/gen/index'),
        meta: {title: '代码生成', icon: 'product-list'}
      }, {
        path: 'permissionCategory',
        name: 'permissionCategory',
        component: () => import('@/views/sys/permissionCategory/index'),
        meta: {title: '菜单类别列表}'}
      },
      {
        path: 'addPermissionCategory',
        name: 'addPermissionCategory',
        component: () => import('@/views/sys/permissionCategory/add'),
        meta: {title: '添加菜单类别', icon: 'permissionCategory-add'},
        hidden: true
      },
      {
        path: 'updatePermissionCategory',
        name: 'updatePermissionCategory',
        component: () => import('@/views/sys/permissionCategory/update'),
        meta: {title: '修改菜单类别', icon: 'permissionCategory-update'},
        hidden: true
      },
      {
        path: 'addAdmin',
        name: 'addAdmin',
        component: () => import('@/views/sys/admin/add'),
        meta: {title: '添加用户'},
        hidden: true
      },
      {
        path: 'updateAdmin',
        name: 'updateAdmin',
        component: () => import('@/views/sys/admin/update'),
        meta: {title: '编辑用户'},
        hidden: true
      }, {
        path: 'role',
        name: 'role',
        component: () => import('@/views/sys/role/index'),
        meta: {title: '角色列表', icon: 'product-list'}
      },
      {
        path: 'addRole',
        name: 'addRole',
        component: () => import('@/views/sys/role/add'),
        meta: {title: '添加角色'},
        hidden: true
      },
      {
        path: 'updateRole',
        name: 'updateRole',
        component: () => import('@/views/sys/role/update'),
        meta: {title: '编辑角色'},
        hidden: true
      }, {
        path: 'permission',
        name: 'permission',
        component: () => import('@/views/sys/permission/index'),
        meta: {title: '权限列表', icon: 'product-list'}
      },
      {
        path: 'addPermission',
        name: 'addPermission',
        component: () => import('@/views/sys/permission/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updatePermission',
        name: 'updatePermission',
        component: () => import('@/views/sys/permission/update'),
        meta: {title: '编辑权限'},
        hidden: true
      },
      {
        path: 'area',
        name: 'area',
        component: () => import('@/views/sys/area/index'),
        meta: {title: '权限列表', icon: 'product-list'}
      },
      {
        path: 'addArea',
        name: 'addArea',
        component: () => import('@/views/sys/area/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updateArea',
        name: 'updateArea',
        component: () => import('@/views/sys/area/update'),
        meta: {title: '编辑权限'},
        hidden: true
      },
      {
        path: 'dict',
        name: 'dict',
        component: () => import('@/views/sys/dict/index'),
        meta:
            {
              title: '系统配置信息表列表}'
            }
      },
      {
        path: 'addDict',
        name: 'addDict',
        component: () => import('@/views/sys/dict/add'),
        meta: {
          title: '添加系统配置信息表',
          icon: 'dict-add'
        }
      },
      {
        path: 'updateDict',
        name: 'updateDict',
        component: () =>
            import('@/views/sys/dict/update'),
        meta:
            {
              title: '修改系统配置信息表',
              icon: 'dict-update'
            },
        hidden: true
      }, {
        path: 'job',
        name: 'job',
        component: () => import('@/views/sys/job/index'),
        meta:
            {
              title: '定时任务息表列表'
            }
      }, {
        path: 'joblog',
        name: 'joblog',
        component: () => import('@/views/sys/job/joblog'),
        meta:
            {
              title: '定时任务息表列表'
            }
      },
      {
        path: 'addJob',
        name: 'addJob',
        component: () => import('@/views/sys/job/add'),
        meta: {
          title: '添加定时任务',
          icon: 'dict-add'
        }
      },
      {
        path: 'updateJob',
        name: 'updateJob',
        component: () =>
            import('@/views/sys/job/update'),
        meta:
            {
              title: '修改定时任务',
              icon: 'dict-update'
            },
        hidden: true
      },

    ]
  },
  {
    path: '/cms',
    component: Layout,
    redirect: '/cms/help',
    name: 'cms',
    meta: {title: '内容管理', icon: 'home'},
    children: [
      {
        path: 'help',
        name: 'help',
        component: () => import('@/views/cms/help/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }, {
        path: 'addHelp',
        name: 'addHelp',
        component: () => import('@/views/cms/help/add'),
        meta: {title: '添加帮助'},
        hidden: true
      },
      {
        path: 'updateHelp',
        name: 'updateHelp',
        component: () => import('@/views/cms/help/update'),
        meta: {title: '编辑帮助'},
        hidden: true
      },
      {
        path: 'subject',
        name: 'subject',
        component: () => import('@/views/cms/subject/index'),
        meta: {title: '专题列表', icon: 'product-list'}
      }, {
        path: 'addSubject',
        name: 'addSubject',
        component: () => import('@/views/cms/subject/add'),
        meta: {title: '添加专题'},
        hidden: true
      },
      {
        path: 'updateSubject',
        name: 'updateSubject',
        component: () => import('@/views/cms/subject/update'),
        meta: {title: '编辑专题'},
        hidden: true
      }
      ,
      {
        path: 'helpCategory',
        name: 'helpCategory',
        component: () => import('@/views/cms/helpCategory/index'),
        meta: {title: '专题分类列表', icon: 'product-list'}
      }, {
        path: 'addHelpCategory',
        name: 'addHelpCategory',
        component: () => import('@/views/cms/helpCategory/add'),
        meta: {title: '添加专题分类'},
        hidden: true
      },
      {
        path: 'updateHelpCategory',
        name: 'updateHelpCategory',
        component: () => import('@/views/cms/helpCategory/update'),
        meta: {title: '编辑专题分类'},
        hidden: true
      }
      ,
      {
        path: 'memberReport',
        name: 'memberReport',
        component: () => import('@/views/cms/memberReport/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }, {
        path: 'addMemberReport',
        name: 'addMemberReport',
        component: () => import('@/views/cms/memberReport/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updateMemberReport',
        name: 'updateMemberReport',
        component: () => import('@/views/cms/memberReport/update'),
        meta: {title: '编辑权限'},
        hidden: true
      }
      ,
      {
        path: 'prefrenceArea',
        name: 'prefrenceArea',
        component: () => import('@/views/cms/prefrenceArea/index'),
        meta: {title: '偏爱专区列表', icon: 'product-list'}
      }, {
        path: 'addPrefrenceArea',
        name: 'addPrefrenceArea',
        component: () => import('@/views/cms/prefrenceArea/add'),
        meta: {title: '添加偏爱专区'},
        hidden: true
      },
      {
        path: 'updatePrefrenceArea',
        name: 'updatePrefrenceArea',
        component: () => import('@/views/cms/prefrenceArea/update'),
        meta: {title: '编辑偏爱专区'},
        hidden: true
      }

      ,
      {
        path: 'subjectCategory',
        name: 'subjectCategory',
        component: () => import('@/views/cms/subjectCategory/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }, {
        path: 'addSubjectCategory',
        name: 'addSubjectCategory',
        component: () => import('@/views/cms/subjectCategory/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updateSubjectCategory',
        name: 'updateSubjectCategory',
        component: () => import('@/views/cms/subjectCategory/update'),
        meta: {title: '编辑权限'},
        hidden: true
      }

      ,
      {
        path: 'subjectComment',
        name: 'subjectComment',
        component: () => import('@/views/cms/subjectComment/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }, {
        path: 'addSubjectComment',
        name: 'addSubjectComment',
        component: () => import('@/views/cms/subjectComment/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updateSubjectComment',
        name: 'updateSubjectComment',
        component: () => import('@/views/cms/subjectComment/update'),
        meta: {title: '编辑权限'},
        hidden: true
      }

      , {
        path: 'topic',
        name: 'topic',
        component: () => import('@/views/cms/topic/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }, {
        path: 'addTopic',
        name: 'addTopic',
        component: () => import('@/views/cms/topic/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updateTopic',
        name: 'updateTopic',
        component: () => import('@/views/cms/topic/update'),
        meta: {title: '编辑权限'},
        hidden: true
      }

      , {
        path: 'topicCategory',
        name: 'topicCategory',
        component: () => import('@/views/cms/topicCategory/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }, {
        path: 'addTopicCategory',
        name: 'addTopicCategory',
        component: () => import('@/views/cms/topicCategory/add'),
        meta: {title: '添加权限'},
        hidden: true
      },
      {
        path: 'updateTopicCategory',
        name: 'updateTopicCategory',
        component: () => import('@/views/cms/topicCategory/update'),
        meta: {title: '编辑权限'},
        hidden: true
      }

      , {
        path: 'topicComment',
        name: 'topicComment',
        component: () => import('@/views/cms/topicComment/index'),
        meta: {title: '帮助列表', icon: 'product-list'}
      }

    ]
  },

  {path: '*', redirect: '/404', hidden: true}
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({y: 0}),
  routes: constantRouterMap
})

