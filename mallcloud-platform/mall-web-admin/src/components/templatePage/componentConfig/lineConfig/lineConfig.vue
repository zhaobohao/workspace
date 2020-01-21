<style lang="less" scoped>
@import "./lineConfig.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="辅助线" name="content">
        <div class="pannelcontent">
          <div class="colorGroup">
            <div class="pannelItem">
              <div class="tit">线条颜色选择</div>
              <el-color-picker @on-change="changeColor" v-model="search_bg" :editable="false" />
            </div>
          </div>
          <div class="pannelItem" style="align-items:center;">
            <div class="titspe">上下边距</div>
            <div class="slideDiv">
              <el-slider class="slide-content" v-model="udPadding" @on-change="changeContent" :max="48"></el-slider>
              <div class="slide-tips">{{udPadding}}px</div>
            </div>
          </div>
        </div>

      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  props: ["options","editable"],
  data() {
    return {
      search_bg:'#eaeaea',
      udPadding:20,
      newOptions: {},
    };
  },
  created() {
    let _this = this;
    console.log(this.editable)
    _this.init(_this.options);
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
      console.log('选项卡',_this.newOptions)
      _this.init(_this.newOptions);
    },
    editable(){
      console.log(this.editable)
    }
  },
  methods: {
    // 初始化
    init(op) {
      let _this = this;
      let optionsParams = op.options;
      if (JSON.stringify(optionsParams) == "{}") {
        _this.restore();
      } else {
        _this.udPadding = optionsParams.udPadding
        _this.search_bg = optionsParams.search_bg
      }
    },
    //改变颜色
    changeColor(e){
      this.changeForm()
    },

    // 改变内容
    changeContent(e){
      this.changeForm()
    },
    // 恢复初始状态
    restore() {
      let _this = this;
      _this.udPadding = 20
      _this.search_bg = '#eaeaea'
      _this.changeForm();
    },

    //改变值并且给父组件传递事件
    changeForm() {
      let _this = this;
      let changeData;
      changeData = {
        udPadding:_this.udPadding,
        search_bg:_this.search_bg,
      };

      _this.$emit("listenToForm", changeData);
    },
  }
};
</script>
