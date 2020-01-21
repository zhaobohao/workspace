<style lang="less" scoped>
@import "./orderTem.less";
</style>

<template>
  <div class="order diyitem">
    <div class="order-title">订单中心</div>
    <div class="order-group" :style="{background:order_bg}">
      <div class="order-item" v-for="(item,index) in colorGroup" :key="index">
        <div class="iconImg">
          <div class="badge" :style="{background:order_badgecolor,color:order_textcolor}">1</div>
          <span :class="['icon iconfont',item.adImg]" :style="{color:order_iconcolor,fontSize:'20px'}"></span>
        </div>
        <div class="iconText" :style="{color:order_textcolor}">{{item.adText}}</div>
      </div>
    </div>
    
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      order_num: "4",
      order_bg: "#fff",
      order_iconcolor: "#fff",
      order_textcolor: "#fff",
      order_badgecolor: "#fff",
      newOptions: {},
      colorGroup: []
    };
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
      _this.init(_this.newOptions);
    }
  },
  created() {
    let _this = this;
    _this.init(_this.options);
  },
  methods: {
    // 初始化
    init(options) {
      let _this = this;
      if (JSON.stringify(options) == "{}") {
        _this.restore();
      } else {
        _this.order_num = options.order_num;
        _this.order_bg = options.order_bg;
        _this.order_iconcolor = options.order_iconcolor;
        _this.order_textcolor = options.order_textcolor;
        _this.order_badgecolor = options.order_badgecolor;
        _this.colorGroup = options.colorGroup;
      }
    },
    // 恢复初始
    restore() {
      let _this = this;
      _this.order_num = "4"
      _this.order_bg = "#fff"
      _this.order_iconcolor = "#fff"
      _this.order_textcolor = "#fff"
      _this.order_badgecolor = "#fff"
      _this.colorGroup = []
      for(let i = 0; i < _this.order_num; i++) {
        let newObj = {
          adImg: "",
          adText:"",
          adLink: ""
        }
        _this.colorGroup.push(newObj)
      }
    },
    // 更换banner
    changeSwiper(oldvalue, value) {
      let _this = this;
      _this.currentId = value;
    }
  }
};
</script>
