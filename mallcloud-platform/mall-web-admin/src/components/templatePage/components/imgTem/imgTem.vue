<style lang="less" scoped>
@import "./imgTem.less";
</style>

<template>
  <div class="imgContent diyitem" :style="{background:backColor}">
    <div class="imgGroup" v-for="(item,index) in bannerLists" :key="index" :style="{boxSizing:'border-box',paddingTop:udPadding+'px',paddingBottom:udPadding+'px',paddingLeft:lrPadding+'px',paddingRight:lrPadding+'px'}">
      <img v-if="item.adImg" :src="item.adImg" alt>
      <img v-else src="../../static/img/default_onegoods.jpg" alt>
    </div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      currentId: 0,
      bannerLists: [
        {
          adImg: "",
          adLink: ""
        }
      ],
      udPadding:0,
      lrPadding:0,
      backColor:'#fff'
    };
  },
  watch: {
    options() {
      let _this = this;
      console.log(_this.options);
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
      console.log(options)
      let _this = this;
      if (JSON.stringify(options) == "{}") {
        _this.restore();
      } else {
        _this.bannerLists = options.colorGroup
        _this.udPadding = options.udPadding || 0
        _this.lrPadding = options.lrPadding || 0
        _this.backColor = options.backColor || '#fff'
      }
    },
    // 恢复初始
    restore() {
      let _this = this;
      _this.bannerLists = [
        {
          adImg: "",
          adLink: ""
        }
      ]
      _this.udPadding = 0
      _this.lrPadding = 0
      _this.backColor = '#fff'
    }
  }
};
</script>
