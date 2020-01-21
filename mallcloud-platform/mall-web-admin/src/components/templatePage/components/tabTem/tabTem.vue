<style lang="less" scoped>
@import "./tabTem.less";
</style>

<template>
  <div class="tabs diyitem" style="min-height:100px;">
    <div class="tabs-tab" v-if="colorGroup.length > 1 || colorGroup[0].tabs">
      <div class="tab-item" :style="{color:index == current ? tab_activetext : tab_text,background:index == current ? tab_activebg : tab_bg}" @click.stop="clickTab(index)" v-for="(item,index) in colorGroup" :key="index">{{item.tabs}}</div>
    </div>
    <div class="tabs-content" v-if="current == index" v-for="(item,index) in colorGroup" :key="index">
      <div class="tabsimg" v-for="(citem,cindex) in item.tabContent" :key="cindex">
        <img class="tabimg" :src="citem.tabImg" alt="">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      tab_bg:'#fff',
      tab_text:'#666',
      tab_activebg:'#fff',
      tab_activetext:'#FF0000',
      newOptions: {},
      current:0,
      colorGroup: [
        {
          tabs: "",
          tabContent: [
            {
              tabImg:'',
              tabLink:''
            }
          ]
        }
      ]
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
        _this.tab_bg = options.tab_bg;
        _this.tab_text = options.tab_text;
        _this.tab_activebg = options.tab_activebg;
        _this.tab_activetext = options.tab_activetext;
        _this.colorGroup = options.colorGroup;
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
      _this.tab_bg = '#fff',
      _this.tab_text = '#666',
      _this.tab_activebg = '#fff',
      _this.tab_activetext = '#FF0000',
      _this.colorGroup = [
        {
          tabs: "",
          tabContent: [
            {
              tabImg:'',
              tabLink:''
            }
          ]
        }
      ]
    }
  }
};
</script>
