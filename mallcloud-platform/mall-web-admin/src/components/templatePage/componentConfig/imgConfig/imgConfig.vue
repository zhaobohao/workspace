<style lang="less" scoped>
@import "./imgConfig.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="内容" name="content">
        <div class="pannelcontent">
          <div class="pannelItem">
            <div class="pannelItem" style="border-bottom:0px;display:flex;flex-direction:row;align-items:center;">
              <div class="tit" style="margin-right:30px;margin-bottom:0px;">背景色</div>
              <el-color-picker v-model="backColor" @on-change="changeBackColor" :editable="false" />
            </div>
            <div class="pannelItem" style="border-bottom:0px">
              <div class="tit">上下边距</div>
              <div class="list-group slide">
                <el-slider class="slide-content" @on-change="changeSlide" v-model="udPadding" :max="48"></el-slider>
                <div class="slide-tips">{{udPadding}}PX</div>
              </div>
            </div>
            <div class="pannelItem" style="border-bottom:0px">
              <div class="tit">左右边距</div>
              <div class="list-group slide">
                <el-slider class="slide-content" @on-change="changeSlide" v-model="lrPadding" :max="48"></el-slider>
                <div class="slide-tips">{{lrPadding}}PX</div>
              </div>
            </div>
            <div class="tit">上传图片</div>
            <div class="colorGroup">
              <draggable v-model="colorGroup" v-bind="dragOptions" handle=".handle" @change="changeDisplay">
                <transition-group type="transition">
                  <div class="colorItem" v-for="(item,index) in colorGroup" :key="index">
                    <i  class="iconfont icon-drag handle"
                        size="24"
                        style="position:absolute;top:0px;right:25px;"></i>
                    <i  type="ios-trash img-trash"
                        size="24"
                        @click="deleteColor(index)"
                        v-if="index != 0"></i>
                   <!-- <Icon
                      class="iconfont icon-drag handle"
                      size="24"
                      style="position:absolute;top:0px;right:25px;"
                    />
                    <Icon
                      type="ios-trash img-trash"
                      size="24"
                      @click="deleteColor(index)"
                      v-if="index != 0"
                    />-->
                    <img v-if="item.adImg" class="colorIcon" :src="item.adImg" alt>
                    <img v-else class="colorIcon" src="../../static/img/default_onegoods.jpg" alt>
                    <div class="colorInput">
                      <el-input v-model="item.adImg" class="itemInput" disabled placeholder="请选择图片">
                        <el-button slot="append" @click="showImgSelect(index)" :disabled="editable != 'enable'">选择图片</el-button>
                      </el-input>
                      <el-input v-model="item.adLink" class="itemInput" disabled placeholder="请选择或填写小程序路径">
                        <el-button slot="append" @click="showLinkSelect(index)" :disabled="editable != 'enable'">选择跳转</el-button>
                      </el-input>
                    </div>
                  </div>
                </transition-group>
              </draggable>
            </div>

            <div class="colorAdd" @click="addAd" v-if="editable == 'enable'">
              +
              <span class="colortips">添加图片</span>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import draggable from "vuedraggable";
export default {
  props: ["options","editable"],
  components:{
    draggable
  },
  data() {
    return {
      newOptions: {},
      udPadding:0,
      lrPadding:0,
      backColor:'#fff',
      colorGroup: [
        {
          adImg: "",
          adLink: ""
        }
      ]
    };
  },
  computed: {
    dragOptions() {
      return {
        animation: 200,
        group: "description",
        disabled: false,
        ghostClass: "ghost"
      };
    }
  },
  created() {
    let _this = this;
    _this.init(_this.options);
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
      _this.init(_this.newOptions);
    }
  },
  methods: {
    // 初始化
    init(op) {
      let _this = this;
      let optionsParams = op.options;
      console.log(optionsParams);
      if (JSON.stringify(optionsParams) == "{}") {
        _this.restore();
      } else {
        _this.colorGroup = optionsParams.colorGroup;
        _this.udPadding = optionsParams.udPadding || 0
        _this.lrPadding = optionsParams.lrPadding || 0
        _this.backColor = optionsParams.backColor || '#fff'
      }
    },
    // 恢复初始状态
    restore() {
      let _this = this;
      _this.colorGroup = [
        {
          adImg: "",
          adLink: ""
        }
      ]
      _this.udPadding = 0
      _this.lrPadding = 0
      _this.backColor = '#fff'
      _this.changeForm();
    },

    // 改变背景色
    changeBackColor(e){
      this.changeForm();
    },

    //改变边距
    changeSlide(e){
      console.log(e)
      this.changeForm();
    },

    //改变值并且给父组件传递事件
    changeForm(index) {
      let _this = this;
      console.log(index);
      let changeData;
      if (index) {
        changeData = {
          colorGroup: _this.colorGroup,
          udPadding:_this.udPadding,
          lrPadding:_this.lrPadding,
          backColor:_this.backColor,
          addIndex: index - 1
        };
      } else {
        changeData = {
          udPadding:_this.udPadding,
          lrPadding:_this.lrPadding,
          backColor:_this.backColor,
          colorGroup: _this.colorGroup
        };
      }

      _this.$emit("listenToForm", changeData);
    },

    //删除图片
    deleteColor(index) {
      let _this = this;
      _this.colorGroup.splice(index, 1);
      _this.changeForm();
    },

    // 添加图片

    addAd() {
      let _this = this;
      // 判断数量
      _this.colorGroup.push({
        adImg: "",
        adLink: ""
      });
      _this.changeForm();
    },

    // 点击显示图片的选择弹框
    showImgSelect(index) {
      let _this = this;
      _this.changeForm(index + 1);
      let changeData = {
        openImg: true,
        mode: "diy",
        type: "image",
        id: "img"
      };
      _this.$emit("listenToOpenImgSelect", changeData);
    },

    // 点击显示链接的选择弹框
    showLinkSelect(index) {
      let _this = this;
      _this.changeForm(index + 1);
      let changeData = {
        mode: "diy",
        id: "img",
        link: _this.colorGroup[index].adLink
      };
      _this.$emit("listenToOpenLinkSelect", changeData);
    },

    // 更换位置
    changeDisplay(e){
      this.changeForm();
    }
  }
};
</script>
