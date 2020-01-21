<style lang="less" scoped>
@import "./listTem.less";
</style>

<template>
  <div class="list diyitem">
    <div class="list-group">
      <div class="list-item" v-for="(item,index) in colorGroup" :key="index">
        <div class="iconImg">
          <span :class="['icon iconfont',item.adImg]" :style="{fontSize:'20px'}"></span>
        </div>
        <div class="iconText">{{item.adText}}</div>
        <span class="icon iconfont icon-right"></span>
      </div>
    </div>
    
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
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
        _this.colorGroup = options.colorGroup;
      }
    },
    // 恢复初始
    restore() {
      let _this = this;
      _this.colorGroup = []
      for(let i = 0; i < 1; i++) {
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
