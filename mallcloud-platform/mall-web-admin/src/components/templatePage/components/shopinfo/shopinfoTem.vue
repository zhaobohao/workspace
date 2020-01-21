<style lang="less" scoped>
  @import './shopinfoTem.less';

</style>
<template>
  <div class="shangpinxiangqing diyitem" :options="options">
    <div class="goodsDetail goodsDetail">
      <div class="rel goods-img"><img alt="" src="../../static/img/normal-detail-img1.png">
      </div>
      <div class="goods-msg">
        <div class="goods-info">
          <!---->
          <div class="flex price-msg">
            <div class="flex">
              <div class="goods-price es-style-text">
                ￥<span>88</span></div>
              <!---->
            </div>
            <div class="right-mon">预计佣金
              <span class="es-style-text">￥12.4</span></div>
          </div>
          <div class="title">商品标题
            <span class="iconfont icon-fenxiang1 pull-right" style="font-weight: normal;"></span></div>
          <div class="subtitle">
            商品副标题
          </div>
          <div class="express flex">
            <!---->
            <div>
              快递：￥10-20
            </div>
            <div>销量：22</div>
            <div>库存：22</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: ['options'],
    data() {
      return {
        goods: ['goods_title', 'goods_price', 'goods_subtitle'],
        cart: 'cart_show',
      }
    },
    created() {
      let _this = this
      _this.init(_this.options)
    },
    watch: {
      options() {
        let _this = this
        _this.newOptions = _this.options
        _this.init(_this.newOptions)
      }
    },
    methods: {
      // 初始化
      init(options) {
        let _this = this
        console.log(options)
        if (JSON.stringify(options) == "{}") {
          _this.restore()
        } else {
          _this.cart = options.cart
          _this.goods = options.goods
        }
      },
      // 恢复初始状态
      restore() {
        let _this = this
        _this.cart = 'cart_show'
        _this.goods = ['goods_title', 'goods_price', 'goods_subtitle']
      }
    }
  }

</script>
