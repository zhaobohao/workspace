<style lang="less" scoped>
@import "./videoTem.less";
</style>

<template>
  <div class="video diyitem">
    <div class="videoContent" :style="{height:video_style == 1 ? '211px' : video_style == 2 ? '281px' : '375px'}">
      <div v-if="video_link" class="videoImg">
        <video :poster="video_img" :src="video_link" style="width:100%;height:100%;"></video>
      </div>
      <div v-else>
        <img class="videoImg" src="@/assets/images/default_video.png" alt="">
      </div>
      
      
      <img class="videoplay" src="@/assets/images/video_play.png" alt="">
    </div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      video_type:'1',
      video_style:'1',
      video_img:'',
      video_link:'',
      newOptions: {}
    };
  },
  watch: {
    options:{
      handler(){
        let _this = this;
        _this.newOptions = _this.options;
        console.log('选项卡模板',_this.newOptions)
        _this.init(_this.newOptions);
      },
      deep:true
    }
  },
  created() {
    let _this = this;
    _this.init(_this.options);
  },
  methods: {
    // 初始化
    init(options) {
      console.log('123123',options)
      let _this = this;
      if (JSON.stringify(options) == "{}") {
        console.log('12')
        _this.restore();
      } else {
        console.log('123',options)
        _this.video_type = options.video_type
        _this.video_style = options.video_style
        _this.video_img = options.video_img
        _this.video_link = options.video_link
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
      _this.video_type = '1'
      _this.video_style = '1'
      _this.video_img = ''
      _this.video_link = ''
    }
  }
};
</script>
