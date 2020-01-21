<style lang="less" scoped>
@import "./bannerTem.less";
</style>

<template>
  <div class="banner diyitem" :style="{height:dot_position == 'dot_inside' ? '210px' : '240px'}">
    <el-carousel
      class="banneritem"
      v-model="options.bannerActive"
      dots="none"
      @on-change="changeSwiper"
    >
      <el-carousel-item v-for="(item,index) in bannerLists" :key="index">
        <div class="carousel">
          <img v-if="item.adImg" :src="item.adImg" alt>
          <img v-else src="../../static/img/default_onegoods.jpg" alt>
        </div>
      </el-carousel-item>
    </el-carousel>
    <div class="dots" v-if="dot_isshow == 'dot_show'">
      <div
        :class="[dot_type]"
        :style="{background:index == currentId ? dot_color : 'rgba(0,0,0,0.2)',margin:'0px 6px'}"
        v-for="(item,index) in bannerLists"
        :key="index"
      ></div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      dot_isshow: "dot_show",
      dot_type: "dot_rectangle",
      dot_position: "dot_inside",
      dot_color: "#fff",
      currentId: 0,
      bannerLists: [
        {
          adImg: "",
          adLink: ""
        }
      ]
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
        _this.dot_isshow = options.dot_isshow;
        _this.dot_type = options.dot_type;
        _this.dot_position = options.dot_position;
        _this.dot_color = options.dot_color;
        _this.bannerLists = options.colorGroup;
      }
    },
    // 恢复初始
    restore() {
      let _this = this;
      _this.dot_isshow = "dot_show";
      _this.dot_type = "dot_rectangle";
      _this.dot_position = "dot_inside";
      _this.dot_color = "#fff";
      _this.bannerLists = [
        {
          adImg: "",
          adLink: ""
        }
      ];
    },
    // 更换banner
    changeSwiper(oldvalue, value) {
      let _this = this;
      _this.currentId = value;
    }
  }
};
</script>
