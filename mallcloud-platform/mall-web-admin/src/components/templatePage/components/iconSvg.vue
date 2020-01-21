<template>
  <svg class="svg-icon" :svgid="svgid" viewBox="0 0 1024 1024" width="25px" height="25px">
    <path v-for="(item,index) in content" :key="index" :d="item.d" :fill="item.fill"></path>
  </svg>
</template>

<script>
import svgs from "../svg";
export default {
  props: ["svgid"],
  data() {
    return {
      content: []
    };
  },
  created() {
    let _this = this;
    _this.init();
  },
  methods: {
    // 初始化
    init() {
      let _this = this;
      _this.makeSvgs();
    },
    // 加载svgs图标
    makeSvgs() {
      let _this = this;
      let result = svgs.find(t => t.id == _this.svgid);
      _this.content = result.content;
    }
  }
};
</script>
