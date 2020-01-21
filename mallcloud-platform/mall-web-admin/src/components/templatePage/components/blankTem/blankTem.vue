<style lang="less" scoped>
@import "./blankTem.less";
</style>

<template>
  <div class="search diyitem" :style="{height:udPadding+'px ',background:search_bg}">

  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      udPadding:20,
      search_bg:'#fff'
    };
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
      console.log('选项卡模板',_this.newOptions)
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
        _this.udPadding = options.udPadding
        _this.search_bg = options.search_bg
      }
    },
    // 点击tab
    clickTab(index){
      console.log(index)
      this.current = index
    },
    // 恢复初始
    restore() {
      let _this = this;
      _this.udPadding = 20
      _this.search_bg = '#fff'
    }
  }
};
</script>
