<style lang="less" scoped>
@import "./mofangTem.less";
</style>

<template>
  <div class="mofang" ref="mofang">
    <div
      class="mofang-item"
      v-for="(item,index) in newCombineLists"
      :key="index"
      :style="{width:item.width + 'px',height:item.height + 'px',left:item.left + 'px',top:item.top + 'px'}"
    >
      <img class="mofang-item-img" :src="item.imageUrl" alt>
    </div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      elementLists: [],
      combineLists: [],
      imgPadding: "",
      blockWidth: "",
      blockHeight: "",
      newOptions: {},
      newCombineLists: []
    };
  },
  watch: {
    options: {
      deep: true,
      handler(value) {
        let _this = this;
        _this.init(value);
      }
    }
  },
  mounted() {
    let _this = this;
    this.$nextTick(() => {
      let height = this.$refs.mofang.offsetHeight;
      let width = this.$refs.mofang.offsetWidth;
      _this.blockWidth = 375;
      _this.blockHeight = 375;
    });
    _this.init(_this.options);
  },
  methods: {
    // 初始化
    init(options) {
      let _this = this;
      if (JSON.stringify(options) == "{}") {
        _this.restore();
      } else {
        _this.elementLists = options.elementLists;
        _this.combineLists = options.combineLists;
        _this.imgPadding = options.imgPadding;
        // 处理大小，图片地址
        _this.calcImg();
      }
    },
    calcImg() {
      let _this = this;
      // // 初始化比例值
      let ratio = 375 / 304;
      let newCombineLists = [];
      _this.combineLists.map((v, i) => {
        newCombineLists.push({
          id: v.id,
          isSelect: v.isSelect,
          zIndex: v.zIndex,
          width: v.width * ratio,
          height: v.height * ratio,
          top: v.top * ratio,
          left: v.left * ratio,
          imageUrl: v.imageUrl
        });
      });
      _this.newCombineLists = newCombineLists;
    },
    restore() {
      let _this = this;
      _this.elementLists = [];
      _this.combineLists = [];
      _this.imgPadding = "";
    }
  }
};
</script>
