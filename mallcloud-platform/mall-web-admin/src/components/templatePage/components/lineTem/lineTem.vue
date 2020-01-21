<style lang="less" scoped>
@import "./lineTem.less";
</style>

<template>
  <div class="search diyitem" :style="{padding:udPadding+'px 0px'}">
    <div class="line" :style="{width:'100%',height:'1px',background:search_bg}"></div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      udPadding:20,
      search_bg:'#eaeaea'
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
      _this.search_bg = '#eaeaea'
    }
  }
};
</script>
