<!--
    mode:base || diy (固定样式(默认) or 自定义)
   -->
<style lang="less" scoped>
  @import './IconList.less';
</style>

<template>
  <div class="iconPage"  v-if="showIcon">
    <div class="iconContent">
        <i  class="closeBtn" type="ios-close" @click="chooseIcon"></i>
       <!-- <Icon class="closeBtn" type="ios-close" @click="chooseIcon" />-->
        <div class="iconTitle">请选择图标</div>
        <ul class="icon_lists dib-box">
            <li class="dib" v-for="(item,index) in iconLists" :key="index" @click="chooseIcon(item)">
                <span :class="['icon iconfont',item]"></span>
                <div class="name">{{item}}</div>
            </li>
        </ul>
    </div>

  </div>
</template>

<script>
import api from '@/api/request'
export default {
    name: "iconLists",
    props: ["options"],
    data() {
        return {
            mode: "base",
            iconLists:[],
            showIcon:false
        };
    },
    created() {
        this.init()
    },
    methods: {
        init(options) {
            let _this = this;
            if (options) {
                _this.mode = options.mode || "base";
            } else {
                _this.mode = "base";
            }
            // 获取动态css文件
            _this.getCss()

        },
        // 打开图标
        toggleIcon(){
            this.showIcon = !this.showIcon
        },
        // 选择图标
        chooseIcon(e){
            console.log(e)
            this.toggleIcon()
            this.$emit('listenicon',e)
        },
        getCss(){
            //
            api.request('https://at.alicdn.com/t/font_1224020_0mwexi5p3qyl.css','GET').then((res) => {
                let allCss = res.split('}')
                // 匹配所有icon
                this.expcAllcss(allCss)
            })
        },
        expcAllcss(allCss){
            let iconCss = []
            allCss.map((v,i) => {
                // 去空
                v = v.replace(/(^\s*)|(\s*$)/g, "")

                // 去除iconfont
                if(v.includes('before')){
                    let newv = v.split('before')[0]
                    newv = newv.substring(1,newv.length - 1)
                    iconCss.push(newv)
                }

            })
            this.iconLists = iconCss
        }
    },
    watch: {

    }
};

</script>
