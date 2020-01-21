<style lang="less" scoped>
    @import './default.less';
</style>
<template :options="options">
    <div class="d-config">
        <el-tabs value="content" class="d-tab" style="width:100%;">
            <el-tab-pane :label="editable == 'disable' ? '模板设置' : '页面设置'" name="content" style="width:100%;">
                <div class="pannelcontent">
                    <div class="pannelItem" v-if="editable == 'disable'">
                      <div class="tit">模板类型</div>
                      <div class="inputItem">
                        <el-select v-model="mbtype">
                            <el-option value="1">商品详情模板</el-option>
                        </el-select>
                      </div>
                    </div>
                    <div class="pannelItem">
                        <div class="tit">{{editable == 'disable' ? '模板名称':'页面名称'}}</div>
                        <div class="inputItem">
                            <el-input v-model="pageName" @input="changePageName"/>
                            <div class="inputTips">页面名称仅供后台查找</div>
                        </div>
                    </div>
                    <div class="pannelItem" v-if="editable == 'enable'">
                        <div class="tit">页面标题</div>
                        <div class="inputItem">
                            <el-input v-model="pageTitle" @input="changePageTitle"/>
                            <div class="inputTips">显示在小程序顶部</div>
                        </div>
                    </div>
                    <div class="pannelItem" style="border-bottom:0px;display:flex;flex-direction:row;align-items:center;">
                        <div class="tit" style="margin-right:30px;margin-bottom:0px;">页面背景色</div>
                        <el-color-picker v-model="backColor" @on-change="changeBackColor" :editable="false" />
                    </div>

                    <div class="pannelItem">
                        <div class="tit">页面背景图</div>
                        <div class="pannelClose">
                          <el-input v-model="backImg" class="itemInput" disabled placeholder="请选择图片">
                            <el-button slot="append" @click="showImgSelect">选择图片</el-button>
                          </el-input>
                            <i  v-if="backImg" type="ios-close-circle" class="icon-pannelClose" @click="hideImg"></i>
                         <!-- <Icon v-if="backImg" type="ios-close-circle" class="icon-pannelClose" @click="hideImg" />-->
                        </div>
                    </div>

                    <div class="pannelItem" v-if="editable == 'enable'" style="border-bottom:0px;display:flex;flex-direction:row;align-items:center;">
                        <div class="tit" style="margin-right:30px;margin-bottom:0px;">标题栏背景色</div>
                        <el-color-picker v-model="tabBackColor" @on-change="changeTabBackColor" :editable="false" />
                    </div>

                    <div class="pannelItem" v-if="editable == 'enable'">
                        <div class="tit">标题栏文字</div>
                        <el-radio-group v-model="tabColor" @on-change="changeTabColor">
                            <el-radio label="white">
                                <span>白色</span>
                            </el-radio>
                            <el-radio label="black">
                                <span>黑色</span>
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
    props: ['options','editable'],
    data() {
      return {
        pageName:'',
        pageTitle:'',
        backColor:'#fff',
        backImg:'',
        tabBackColor:'#000',
        tabColor:'white',
        mbtype:'1'
      }
    },
    created() {
      let _this = this
      console.log('初始化:' + _this.options)
      _this.init(_this.options)
    },
    watch: {
      options() {
        let _this = this
        _this.newOptions = _this.options
        console.log('监听变化:', _this.newOptions)
        _this.init(_this.newOptions)
      }
    },
    methods: {
      // 初始化
      init(op) {
        let _this = this
        let optionsParams = op
        if (JSON.stringify(optionsParams) == "{}") {
          _this.restore()
        } else {
            _this.pageName = optionsParams.pageName,
            _this.pageTitle = optionsParams.pageTitle,
            _this.backColor = optionsParams.backColor,
            _this.backImg = optionsParams.backImg,
            _this.tabBackColor = optionsParams.tabBackColor,
            _this.tabColor = optionsParams.tabColor
        }
      },
      // 清楚图片
      hideImg(){
        this.backImg = ''
        this.changeForm()
      },
      // 恢复初始状态
      restore() {
        let _this = this
        _this.pageName = '',
        _this.pageTitle = '',
        _this.backColor = '#fff',
        _this.backImg = '',
        _this.tabBackColor = '#000',
        _this.tabColor = 'white'
        _this.changeForm()
      },
      // 页面名称
      changePageName(e) {

        let _this = this
        _this.pageName = e
        _this.changeForm()
      },
      // 页面标题
      changePageTitle(e){
        let _this = this
        _this.pageTitle = e
        _this.changeForm()
      },
      // 改变标题栏文字颜色
      changeTabColor(value) {
        let _this = this
        _this.tabColor = value
        _this.changeForm()
      },
      // 改变背景颜色
      changeBackColor(value) {
        let _this = this
        _this.backColor = value
        _this.changeForm()
      },

      // 改变标题栏背景颜色
      changeTabBackColor(value) {
        let _this = this
        _this.tabBackColor = value
        _this.changeForm()
      },

      //改变值并且给父组件传递事件
      changeForm() {
        let _this = this
        let changeData
        changeData = {
            pageName:_this.pageName,
            pageTitle:_this.pageTitle,
            backColor:_this.backColor,
            backImg:_this.backImg,
            tabBackColor:_this.tabBackColor,
            tabColor:_this.tabColor
        }
          console.log(changeData)
        _this.$emit('listenToOption', changeData)
      },

      // 点击显示图片的选择弹框
      showImgSelect() {
        let _this = this
        _this.changeForm()
        let changeData = {
          openImg: true,
          mode: 'diy',
          type: 'image',
          id: 'options'
        }
        _this.$emit('listenToOpenImgSelect', changeData)
      },
    }
  }

</script>
