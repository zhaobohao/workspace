<style lang="less" scoped>
@import "./orderConfig.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="订单中心设置" name="content">
        <div class="pannelcontent">
          <div class="pannelItem">
            <div class="tit">每行数量</div>
            <el-radio-group v-model="order_num" @on-change="changeNum">
              <el-radio label="3">
                <span>3个</span>
              </el-radio>
              <el-radio label="4">
                <span>4个</span>
              </el-radio>
              <el-radio label="5">
                <span>5个</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="tit">背景色</div>
            <el-color-picker @on-change="changeColor" v-model="order_bg" :editable="false"/>
          </div>
          <div class="pannelItem">
            <div class="tit">图标颜色</div>
            <el-color-picker @on-change="changeColor" v-model="order_iconcolor" :editable="false"/>
          </div>
          <div class="pannelItem">
            <div class="tit">文字颜色</div>
            <el-color-picker @on-change="changeColor" v-model="order_textcolor" :editable="false"/>
          </div>
          <div class="pannelItem">
            <div class="tit">角标颜色</div>
            <el-color-picker @on-change="changeColor" v-model="order_badgecolor" :editable="false"/>
          </div>

          <div class="pannelItemspe">
            <div class="pannelcontent">
              <div class="colorGroup">
                <div class="colorItem" v-for="(item,index) in colorGroup" :key="index">
                  <div class="colorImg">
                    <span v-if="item.adImg" :class="['icon iconfont colorIcon',item.adImg]" :src="item.adImg"></span>
                    <img v-else class="colorIcon" src="../../static/img/default_onegoods.jpg" alt>
                    <el-button class="chooseIcon" @click="showIconSelect(index)" :disabled="editable != 'enable'">选择图标</el-button>
                  </div>

                  <div class="colorInput">
                    <el-input v-model="item.adText" class="itemInput" :maxlength="10" placeholder="请输入标题"></el-input>
                    <el-input v-model="item.adLink" class="itemInput" disabled placeholder="请选择或填写小程序路径">
                      <el-button slot="append" @click="showLinkSelect(index)" :disabled="editable != 'enable'">选择跳转</el-button>
                    </el-input>
                  </div>
                </div>
              </div>
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
      order_num: "4",
      order_bg: "#fff",
      order_iconcolor: "#fff",
      order_textcolor: "#fff",
      order_badgecolor: "#fff",
      newOptions: {},
      colorGroup: []
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
        _this.order_num = optionsParams.order_num || '4';
        _this.order_bg = optionsParams.order_bg || '';
        _this.order_iconcolor = optionsParams.order_iconcolor || '#fff';
        _this.order_textcolor = optionsParams.order_textcolor || '#fff';
        _this.order_badgecolor = optionsParams.order_badgecolor || '#fff';
        _this.colorGroup = optionsParams.colorGroup || [];
      }
    },

    changeValue(e){
      console.log(e.srcElement.value)
    },
    // 改变数量
    changeNum(e){
      let _this = this
      _this.colorGroup = []
      for(let i = 0; i < _this.order_num; i++) {
        let newObj = {
          adImg: "",
          adText:"",
          adLink: ""
        }
        _this.colorGroup.push(newObj)
      }
      _this.changeForm();
    },

    // 改变颜色
    changeColor(){
      this.changeForm()
    },
    // 恢复初始状态
    restore() {
      let _this = this;
      _this.order_num = "4"
      _this.order_bg = "#fff"
      _this.order_iconcolor = "#fff"
      _this.order_textcolor = "#fff"
      _this.order_badgecolor = "#fff"
      _this.colorGroup = []
      for(let i = 0; i < _this.order_num; i++) {
        let newObj = {
          adImg: "",
          adText:"",
          adLink: ""
        }
        _this.colorGroup.push(newObj)
      }
      _this.changeForm();
    },

    //改变值并且给父组件传递事件
    changeForm(index) {
      let _this = this;
      console.log(index);
      let changeData;
      if (index) {
        changeData = {
          order_num : _this.order_num,
          order_bg : _this.order_bg,
          order_iconcolor : _this.order_iconcolor,
          order_textcolor : _this.order_textcolor,
          order_badgecolor : _this.order_badgecolor,
          colorGroup: _this.colorGroup,
          addIndex: index - 1
        };
      } else {
        changeData = {
          order_num : _this.order_num,
          order_bg : _this.order_bg,
          order_iconcolor : _this.order_iconcolor,
          order_textcolor : _this.order_textcolor,
          order_badgecolor : _this.order_badgecolor,
          colorGroup : _this.colorGroup
        };
      }

      _this.$emit("listenToForm", changeData);
    },

    // 点击显示图片的选择弹框
    showIconSelect(index) {
      let _this = this;
      _this.changeForm(index + 1);
      let changeData = {
        openImg: true,
        mode: "diy",
        type: "image",
        id: "banner"
      };
      _this.$emit("listenToOpenIconSelect", changeData);
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
    }
  }
};
</script>
