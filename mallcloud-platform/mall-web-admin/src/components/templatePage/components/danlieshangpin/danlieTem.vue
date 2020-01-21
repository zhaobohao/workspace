<style lang="less" scoped>
  @import './danlieTem.less';
</style>
<template>
  <div class="danlieshangpin diyitem" :options="options">
    <div class="danitem" v-if="colorGroup.length == 0">
      <div class="d-img">
        <img src="../../static/img/default_onegoods.jpg" alt="">
      </div>
      <div class="d-detail">
        <div class="name line-hide" v-if="goods.includes('goods_title')">这里是商品标题</div>
        <div class="subtitle line-hide" v-if="goods.includes('goods_subtitle')">
          这里是商品副标题
        </div>
        <div style="display: flex;">
          <div style="flex: 1 1 0%;">
            <div class="price"><span class="text" v-if="goods.includes('goods_price')">￥<span>20.00</span></span><span class="productprice" v-if="goods.includes('origin_price')">￥0.00</span></div>
            <div class="sold" v-if="goods.includes('goods_sale')"><span>已售0</span></div>
          </div>
          <span class="buy buybtn1" v-if="cart == 'cart_show'">
            购买
          </span>
        </div>
      </div>
    </div>
    <div v-else class="danitem" v-for="(item,index) in colorGroup" :key="index">
      <div class="d-img">
        <img v-if="item.pic" :src="item.pic" alt="">
        <img v-else src="../../static/img/default_onegoods.jpg" alt="">
      </div>
      <div class="d-detail">
        <div class="name line-hide" v-if="goods.includes('goods_title')">{{item.title || '这里是商品标题'}}</div>
        <div class="subtitle line-hide" v-if="goods.includes('goods_subtitle')">
          {{item.subTitle || '这里是商品副标题'}}
        </div>
        <div style="display: flex;">
          <div style="flex: 1 1 0%;">
            <div class="price"><span class="text" v-if="goods.includes('goods_price')">￥<span>{{item.price || '20.00'}}</span></span><span class="productprice" v-if="goods.includes('origin_price')">￥0.00</span></div>
            <div class="sold" v-if="goods.includes('goods_sale')"><span>已售{{item.sale || 0}}</span></div>
          </div>
          <span class="buy buybtn1" v-if="cart == 'cart_show'">
            购买
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props:['options'],
    data() {
      return {
        goods:['goods_title','goods_price','goods_subtitle'],
        cart:'cart_show',
        colorGroup:[]
      }
    },
    created() {
      let _this = this
      _this.init(_this.options)
    },
    watch:{
        options:{
          handler(oldValue,newValue){
            let _this = this
            _this.newOptions = _this.options
            console.log('更新的tem数据',_this.newOptions)
            _this.init(_this.newOptions)
          },
          deep:true
        }
    },
    methods: {
      // 初始化
      init(options) {
        let _this = this
        if(JSON.stringify(options) == "{}"){
          _this.restore()
        }else{
          _this.cart = options.cart
          _this.goods = options.goods
          _this.colorGroup = options.colorGroup
        }
      },
      // 恢复初始状态
      restore(){
        let _this = this
        _this.cart = 'cart_show'
        _this.goods = ['goods_title','goods_price','goods_subtitle']
        _this.colorGroup = []
      }
    }
  }

</script>
