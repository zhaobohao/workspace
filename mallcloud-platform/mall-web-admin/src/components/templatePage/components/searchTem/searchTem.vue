<style lang="less" scoped>
@import "./searchTem.less";
</style>

<template>
  <div class="search diyitem" :style="{padding:udPadding+'px '+lrPadding+'px',background:search_img ? 'url('+search_img+')' : '#fff',backgroundSize:'cover',backgroundPosition:'center'}">
    <div class="search-key" v-if="key_show == '1' && searchkeys.length == 0" :style="{opacity:0,justifyContent:search_keyalign == '1' ? 'flex-start' : search_keyalign == '2' ? 'center' : 'flex-end'}">
      <div class="search-item">新品</div>
      <div class="search-item">热销</div>
    </div>
    <div class="search-key" v-if="key_show == '1' && searchkeys.length > 0" :style="{opacity:0,justifyContent:search_keyalign == '1' ? 'flex-start' : search_keyalign == '2' ? 'center' : 'flex-end'}">
      <div class="search-item" v-for="(item,index) in searchkeys" :key="index">{{item}}</div>
    </div>
    <div
      class="searchContent"
      :style="{
          border:'1px solid '+search_border,
          background:search_img ? 'rgba(0,0,0,0)' : search_bg,
          borderRadius:search_style == '1'
          ?
          '0px'
          :
          search_style == '2'
            ?
            '6px'
            :
            '50px'
          }"
      >
      <i  class="iconsearch" :style="{color:search_icon}" type="ios-search-outline"></i>
     <!-- <Icon class="iconsearch" :style="{color:search_icon}" type="ios-search-outline" />-->
      <div
        class="searchBorder"
        :style="{
          color:textColor,
          textAlign:
          serach_align == '1'
          ?
          'left'
          :
            serach_align == '2'
            ?
            'center'
            :
            'right',
          padding:
          serach_align == '1'
          ?
          '0px 0px 0px 30px'
          :
            serach_align == '2'
            ?
            '0px'
            :
            '0px 6px 0px 0px'

        }"
      >{{search_tips ? search_tips : '请输入关键字进行搜索'}}</div>
    </div>
    <div class="search-key" v-if="key_show == '1' && searchkeys.length == 0" :style="{justifyContent:search_keyalign == '1' ? 'flex-start' : search_keyalign == '2' ? 'center' : 'flex-end'}">
      <div class="search-item">新品</div>
      <div class="search-item">热销</div>
    </div>
    <div class="search-key" v-if="key_show == '1' && searchkeys.length > 0" :style="{justifyContent:search_keyalign == '1' ? 'flex-start' : search_keyalign == '2' ? 'center' : 'flex-end'}">
      <div class="search-item" v-for="(item,index) in searchkeys" :key="index">{{item}}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      udPadding:6,
      lrPadding:3,
      search_bg:'#fff',
      search_border:'#f1f1f1',
      search_text:'#666',
      search_icon:'#666',
      textColor:'#666',
      search_keyalign:'1',
      search_key:'',
      key_show:'1',
      serach_align:'1',
      search_style:'1',
      search_img:'',
      search_tips:'',
      newOptions: {},
      searchkeys:[]
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
        _this.lrPadding = options.lrPadding
        _this.search_bg = options.search_bg
        _this.search_border = options.search_border
        _this.search_text = options.search_text
        _this.search_icon = options.search_icon
        _this.textColor = options.textColor
        _this.search_keyalign = options.search_keyalign
        _this.search_key = options.search_key
        _this.key_show = options.key_show
        _this.serach_align = options.serach_align
        _this.search_style = options.search_style
        _this.search_img = options.search_img
        _this.search_tips = options.search_tips

        let keys = _this.search_key || _this.search_key.includes(';') ? _this.search_key.split(';') : []
        console.log(keys)
        _this.searchkeys = keys
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
      _this.udPadding = 6
      _this.lrPadding = 3
      _this.search_bg = '#fff'
      _this.search_border = '#f1f1f1'
      _this.search_text = '#666'
      _this.search_icon = '#666'
      _this.textColor = '#666'
      _this.search_keyalign = '1'
      _this.search_key = ''
      _this.key_show = '1'
      _this.serach_align = '1'
      _this.search_style = '1'
      _this.search_img = ''
      _this.search_tips = ''
    }
  }
};
</script>
