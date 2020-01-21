<style lang="less" scoped>
@import "./tabConfig.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="选项卡设置" name="content">
        <div class="pannelcontent">
          <div class="colorGroup">
            <div class="pannelItem">
              <div class="tit">背景色</div>
              <el-color-picker @on-change="changeColor" v-model="tab_bg" :editable="false"/>
            </div>
            <div class="pannelItem">
              <div class="tit">文字颜色</div>
              <el-color-picker @on-change="changeColor" v-model="tab_text" :editable="false"/>
            </div>
            <div class="pannelItem">
              <div class="tit">激活背景色</div>
              <el-color-picker @on-change="changeColor" v-model="tab_activebg" :editable="false"/>
            </div>
            <div class="pannelItem">
              <div class="tit">激活文字颜色</div>
              <el-color-picker @on-change="changeColor" v-model="tab_activetext" :editable="false"/>
            </div>
          </div>


          <div class="pannelItem">
            <div class="colorGroup">
              <div class="colorItem" v-for="(item,index) in colorGroup" :key="index">
                <i   type="ios-trash img-trash"
                     size="24"
                     @click="deleteColor(index,cindex)"
                     v-if="index != 0"></i>
                <!--<Icon
                  type="ios-trash img-trash"
                  size="24"
                  @click="deleteColor(index,cindex)"
                  v-if="index != 0"
                />-->
                <div class="colorItemContent">
                  <div class="itemTitle">选项卡文字</div>
                  <div class="itemContent">
                    <el-input v-model="item.tabs" @on-change="changeInput" class="itemInput" placeholder="请输入选项卡文字"></el-input>
                  </div>
                </div>
                <div class="colorItemContent">
                  <div class="itemTitle">内容图片</div>
                  <div class="contentGroup">
                    <div class="itemContent" v-for="(citem,cindex) in item.tabContent" :key="cindex">
                     <!-- <Icon
                        type="ios-trash img-trash"
                        size="24"
                        @click="deleteColor(index,cindex)"
                        v-if="index != 0"
                      />-->
                      <i   type="ios-trash img-trash"
                           size="24"
                           @click="deleteColor(index,cindex)"
                           v-if="index != 0"></i>
                      <img v-if="citem.tabImg" class="colorIcon" :src="citem.tabImg" alt>
                      <img v-else class="colorIcon" src="../../static/img/default_onegoods.jpg" alt>
                      <div class="colorInput">
                        <el-input v-model="citem.tabImg" class="itemInput" disabled placeholder="请选择图片">
                          <el-button slot="append" @click="showImgSelect(index,cindex)" :disabled="editable != 'enable'">选择图片</el-button>
                        </el-input>
                        <el-input v-model="citem.tabLink" class="itemInput" disabled placeholder="请选择或填写小程序路径">
                          <el-button slot="append" @click="showLinkSelect(index,cindex)" :disabled="editable != 'enable'">选择跳转</el-button>
                        </el-input>
                      </div>
                    </div>
                    <div class="colorAdd" @click="addContent(index)" v-if="editable == 'enable'">
                      +
                      <span class="colortips">添加内容</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="colorAdd" @click="addAd" v-if="editable == 'enable'">
              +
              <span class="colortips">添加选项卡</span>
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
      tab_bg:'#fff',
      tab_text:'#666',
      tab_activebg:'#fff',
      tab_activetext:'#FF0000',
      newOptions: {},
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
        _this.tab_bg = optionsParams.tab_bg;
        _this.tab_text = optionsParams.tab_text;
        _this.tab_activebg = optionsParams.tab_activebg;
        _this.tab_activetext = optionsParams.tab_activetext;
        _this.colorGroup = optionsParams.colorGroup;
      }
    },
    // 添加内容
    addContent(index){
      let _this = this
      let length = _this.colorGroup[index].tabContent.length;
      if (length >= 10) {
        _this.$Message.error("不能超过10个");
        return false;
      }
      _this.colorGroup[index].tabContent.push({
        tabImg:'',
        tabLink:''
      })

      _this.changeForm()
    },
    //改变颜色
    changeColor(e){
      this.changeForm()
    },

    // 改变内容
    changeInput(e){
      console.log(e.srcElement.value)
      this.changeForm()
    },
    // 恢复初始状态
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
      _this.changeForm();
    },

    //改变值并且给父组件传递事件
    changeForm(index,cindex) {
      let _this = this;
      console.log(index,cindex);
      let changeData;
      if (index && cindex) {
        changeData = {
          tab_bg:_this.tab_bg,
          tab_text:_this.tab_text,
          tab_activebg:_this.tab_activebg,
          tab_activetext:_this.tab_activetext,
          colorGroup: _this.colorGroup,
          addIndex: index - 1,
          addChildIndex:cindex - 1
        };
      } else {
        changeData = {
          tab_bg:_this.tab_bg,
          tab_text:_this.tab_text,
          tab_activebg:_this.tab_activebg,
          tab_activetext:_this.tab_activetext,
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
      if (length >= 6) {
        _this.$Message.error("不能超过6个");
        return false;
      }
      _this.colorGroup.push({
        tabs: "",
        tabContent: [
          {
            tabImg:'',
            tabLink:''
          }
        ]
      });
      _this.changeForm();
    },

    // 点击显示图片的选择弹框
    showImgSelect(index,cindex) {
      let _this = this;
      _this.changeForm(index + 1,cindex + 1);
      let changeData = {
        openImg: true,
        mode: "diy",
        type: "image",
        id: "tabs"
      };
      _this.$emit("listenToOpenImgSelect", changeData);
    },

    // 点击显示链接的选择弹框
    showLinkSelect(index,cindex) {
      let _this = this;
      _this.changeForm(index + 1,cindex + 1);
      let changeData = {
        mode: "diy",
        id: "tabs",
        link: _this.colorGroup[index].tabContent[cindex].tabLink
      };
      _this.$emit("listenToOpenLinkSelect", changeData);
    }
  }
};
</script>
