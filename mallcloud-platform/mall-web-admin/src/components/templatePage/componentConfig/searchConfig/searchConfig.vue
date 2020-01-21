<style lang="less" scoped>
@import "./searchConfig.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="选项卡设置" name="content">
        <div class="pannelcontent">
          <div class="colorGroup">
            <div class="pannelItem">
              <div class="tit">输入框背景色</div>
              <el-color-picker @on-change="changeColor" v-model="search_bg" :editable="false"/>
            </div>
            <div class="pannelItem">
              <div class="tit">输入框框体色</div>
              <el-color-picker @on-change="changeColor" v-model="search_border" :editable="false"/>
            </div>
            <div class="pannelItem">
              <div class="tit">文字颜色</div>
              <el-color-picker @on-change="changeColor" v-model="search_text" :editable="false"/>
            </div>
            <div class="pannelItem">
              <div class="tit">图标颜色</div>
              <el-color-picker @on-change="changeColor" v-model="search_icon" :editable="false"/>
            </div>
          </div>
          <div class="pannelItem">
            <div class="tit">输入框背景图</div>
            <div class="pannelDiv" style="position:relative">
              <el-input v-model="search_img" class="itemInput" disabled placeholder="请选择图片">
                <el-button slot="append" @click="showImgSelect">选择图片</el-button>
                <el-button slot="append" @click="clearImg">重置</el-button>
              </el-input>
              <div class="linetips" style="position:absolute;top:2px;width:1px;height:30px;right:50px;background:#dcdee2;"></div>
              <div class="tipsicon" style="margin-top:6px;font-size:12px;color:#666;">设置输入框背景图，将不再显示输入框背景色</div>
            </div>
          </div>
          <div class="pannelItem" style="align-items:center;">
            <div class="titspe">提示文字</div>
            <el-input v-model="search_tips" class="itemInput" @input="changeContent" placeholder="请输入关键字进行搜索">
            </el-input>
          </div>
          <div class="pannelItem" style="align-items:center;">
            <div class="titspe">提示文字颜色</div>
            <el-color-picker v-model="textColor" @on-change="changeColor" :editable="false" />
          </div>
          <div class="pannelItem" style="align-items:center;">
            <div class="titspe">上下边距</div>
            <div class="slideDiv">
              <el-slider class="slide-content" v-model="udPadding" @on-change="changeContent" :max="48"></el-slider>
              <div class="slide-tips">{{udPadding}}px</div>
            </div>
          </div>
          <div class="pannelItem" style="align-items:center;">
            <div class="titspe">左右边距</div>
            <div class="slideDiv">
              <el-slider class="slide-content" v-model="lrPadding" @on-change="changeContent" :max="48"></el-slider>
              <div class="slide-tips">{{lrPadding}}px</div>
            </div>
          </div>
          <div class="pannelItem">
            <div class="titspe">搜索框样式</div>
            <el-radio-group v-model="search_style" @on-change="changeContent">
              <el-radio label="1">
                <span>方形</span>
              </el-radio>
              <el-radio label="2">
                <span>圆角</span>
              </el-radio>
              <el-radio label="3">
                <span>圆弧</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="titspe">文字对齐</div>
            <el-radio-group v-model="serach_align" @on-change="changeContent">
              <el-radio label="1">
                <span>居左</span>
              </el-radio>
              <el-radio label="2">
                <span>居中</span>
              </el-radio>
              <el-radio label="3">
                <span>居右</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="titspe">推荐关键词</div>
            <el-radio-group v-model="key_show" @on-change="changeContent">
              <el-radio label="1">
                <span>显示</span>
              </el-radio>
              <el-radio label="2">
                <span>隐藏</span>
              </el-radio>
            </el-radio-group>
          </div>
          <div class="pannelItem">
            <div class="tit">输入关键词</div>
            <div class="pannelDiv">
              <el-input v-model="search_key" class="itemInput" @on-change="changeContent" placeholder="新品;热销">
              </el-input>
              <div class="tipsicon" style="margin-top:6px;font-size:12px;color:#666;">多个关键词用半角“;"隔开</div>
            </div>
          </div>
          <div class="pannelItem">
            <div class="titspe">关键词对齐</div>
            <el-radio-group v-model="search_keyalign" @on-change="changeContent">
              <el-radio label="1">
                <span>居左</span>
              </el-radio>
              <el-radio label="2">
                <span>居中</span>
              </el-radio>
              <el-radio label="3">
                <span>居右</span>
              </el-radio>
            </el-radio-group>
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
        _this.lrPadding = optionsParams.lrPadding
        _this.search_bg = optionsParams.search_bg
        _this.search_border = optionsParams.search_border
        _this.search_text = optionsParams.search_text
        _this.search_icon = optionsParams.search_icon
        _this.textColor = optionsParams.textColor
        _this.search_keyalign = optionsParams.search_keyalign
        _this.search_key = optionsParams.search_key
        _this.key_show = optionsParams.key_show
        _this.serach_align = optionsParams.serach_align
        _this.search_style = optionsParams.search_style
        _this.search_img = optionsParams.search_img
        _this.search_tips = optionsParams.search_tips
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
    changeContent(e){
      this.changeForm()
    },
    // 恢复初始状态
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
      _this.changeForm();
    },

    //改变值并且给父组件传递事件
    changeForm() {
      let _this = this;
      let changeData;
      changeData = {
        udPadding:_this.udPadding,
        lrPadding:_this.lrPadding,
        search_bg:_this.search_bg,
        search_border:_this.search_border,
        search_text:_this.search_text,
        search_icon:_this.search_icon,
        textColor:_this.textColor,
        search_keyalign:_this.search_keyalign,
        search_key:_this.search_key,
        key_show:_this.key_show,
        serach_align:_this.serach_align,
        search_style:_this.search_style,
        search_img:_this.search_img,
        search_tips:_this.search_tips
      };

      _this.$emit("listenToForm", changeData);
    },

    // 点击显示图片的选择弹框
    showImgSelect() {
      let _this = this;
      _this.changeForm();
      let changeData = {
        openImg: true,
        mode: "diy",
        type: "image",
        id: "search"
      };
      _this.$emit("listenToOpenImgSelect", changeData);
    },

    clearImg(){
      this.search_img = ''
      this.changeForm()
    }
  }
};
</script>
