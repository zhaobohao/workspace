<style lang="less" scoped>
@import "./banner.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="内容" name="content">
        <div class="pannelcontent">
          <div class="pannelItem">
            <div class="tit">轮播按钮</div>
            <el-radio-group v-model="dot_isshow" @on-change="changeShow">
              <el-radio label="dot_show">
                <span>显示</span>
              </el-radio>
              <el-radio label="dot_hide">
                <span>不显示</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="tit">按钮形状</div>
            <el-radio-group v-model="dot_type" @on-change="changeType">
              <el-radio label="dot_rectangle">
                <span>长方形</span>
              </el-radio>
              <el-radio label="dot_square">
                <span>正方形</span>
              </el-radio>
              <el-radio label="dot_circle">
                <span>圆形</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="tit">按钮位置</div>
            <el-radio-group v-model="dot_position" @on-change="changePosition">
              <el-radio label="dot_inside">
                <span>轮播图内</span>
              </el-radio>
              <el-radio label="dot_outside">
                <span>轮播图外</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="tit">按钮颜色</div>
            <el-color-picker @on-change="changeColor" placement="bottom-start" v-model="dot_color" :editable="false"/>
          </div>
          <div class="pannelItem">
            <div class="tit">上传图片</div>
            <div class="colorGroup">
              <draggable v-model="colorGroup" v-bind="dragOptions" handle=".handle" @change="changeDisplay">
                <transition-group type="transition">
                  <div class="colorItem" v-for="(item,index) in colorGroup" :key="index">
                    <i  class="iconfont icon-drag handle"
                        size="24"
                        style="position:absolute;top:0px;right:25px;"></i>
                    <i type="ios-trash img-trash"
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
              <span class="colortips">添加广告图片</span>
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
      dot_isshow: "dot_show",
      dot_type: "dot_rectangle",
      dot_position: "dot_inside",
      dot_color: "#fff",
      newOptions: {},
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
    console.log(this.editable)
    _this.init(_this.options);
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
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
        _this.dot_isshow = optionsParams.dot_isshow;
        _this.dot_type = optionsParams.dot_type;
        _this.dot_position = optionsParams.dot_position;
        _this.dot_color = optionsParams.dot_color;
        _this.colorGroup = optionsParams.colorGroup;
      }
    },
    // 恢复初始状态
    restore() {
      let _this = this;
      _this.dot_isshow = "dot_show";
      _this.dot_type = "dot_rectangle";
      _this.dot_position = "dot_inside";
      _this.dot_color = "#fff";
      _this.colorGroup = [
        {
          adImg: "",
          adLink: ""
        }
      ];
      _this.changeForm();
    },
    // 改变轮播按钮是否显示
    changeShow(value) {
      let _this = this;
      _this.dot_isshow = value;
      _this.changeForm();
    },
    // 改变轮播形状
    changeType(value) {
      let _this = this;
      _this.dot_type = value;
      _this.changeForm();
    },
    // 改变轮播位置
    changePosition(value) {
      let _this = this;
      _this.dot_position = value;
      _this.changeForm();
    },
    // 改变轮播按钮颜色
    changeColor(value) {
      let _this = this;
      console.log(value)
      _this.dot_color = value;
      _this.changeForm();
    },

    //改变值并且给父组件传递事件
    changeForm(index) {
      let _this = this;
      console.log(index);
      let changeData;
      if (index) {
        changeData = {
          dot_isshow: _this.dot_isshow,
          dot_type: _this.dot_type,
          dot_position: _this.dot_position,
          dot_color: _this.dot_color,
          colorGroup: _this.colorGroup,
          addIndex: index - 1
        };
      } else {
        changeData = {
          dot_isshow: _this.dot_isshow,
          dot_type: _this.dot_type,
          dot_position: _this.dot_position,
          dot_color: _this.dot_color,
          colorGroup: _this.colorGroup
        };
      }

      _this.$emit("listenToForm", changeData);
    },

    //删除广告图片
    deleteColor(index) {
      let _this = this;
      _this.colorGroup.splice(index, 1);
      _this.changeForm();
    },

    // 添加广告图片

    addAd() {
      let _this = this;
      // 判断数量
      let length = _this.colorGroup.length;
      if (length >= 10) {
        _this.$Message.error("不能超过10个");
        return false;
      }
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
        id: "banner"
      };
      _this.$emit("listenToOpenImgSelect", changeData);
    },

    // 点击显示链接的选择弹框
    showLinkSelect(index) {
      let _this = this;
      _this.changeForm(index + 1);
      let changeData = {
        mode: "diy",
        id: "banner",
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
