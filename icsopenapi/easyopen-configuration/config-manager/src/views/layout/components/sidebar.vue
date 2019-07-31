<template>
<el-scrollbar class='sidebar-menu' wrapClass="scrollbar-wrapper" style='border-left:5px solid #fff;'>
    <el-menu
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="openState"
      background-color="#343a40"
      text-color="#fff"
      active-text-color="#fff"
      router
    >
    <el-menu-item @click="changeCollapse" v-if='!openState' index="" class='ico-img'>
      <img src='@/assets/logo3.png'/>
    </el-menu-item>
    <el-menu-item @click="changeCollapse" v-else index="">
      <img src='static/favicon.ico' style='width:25px;height:20px;'/>
    </el-menu-item>
      <el-menu-item index="/dashboard">
        <i class="el-icon-location"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu index="1">
        <template slot="title">
          <i class='el-icon-setting'></i>
          <span slot="title">接口配置</span>
        </template>
          <el-submenu v-for="(item) of appList" :index="item.text" :key="item.id">
            
            <span slot="title"><i class='el-icon-mobile-phone'></i>{{item.text}}</span>
            <el-menu-item :index="/enterManage/+item.text">接入方管理</el-menu-item>
            <el-menu-item :index="/roleManage/+item.text">权限管理</el-menu-item>
            <el-menu-item :index="/apiManage/+item.text">接口管理</el-menu-item>
            <el-menu-item :index="/netManage/+item.text">限流设置</el-menu-item>
          </el-submenu>

      </el-submenu>
      <el-submenu index="2">

        <template slot="title">
          <i class='el-icon-menu'></i>
          <span slot="title">系统菜单</span>
        </template>
        <el-menu-item-group class="deepColor">
          <el-menu-item index="/allscope">全局设置</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
</el-scrollbar>
</template>
<style>
  
  .top{
    background: #1f2d3d!important;
  }
  .el-submenu {
    border:0 !important;
  }
  .el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
  }
  .el-menu-vertical-demo{
    height:100%
  }
  .el-menu{
    height: 100%;
  }
  .el-scrollbar__view{
    height: 100%;
  } 
  .sidebar-menu .el-scrollbar__wrap {
    overflow-x: hidden;
  } 
  .is-active{
    background-color: #2189f5!important;
    border-top:1px solid #fff;
    border-bottom:1px solid #fff;
  }
  .sidebar-menu .ico-img{
    height:150px;text-align:center;padding-top:42px;
  }
  .el-menu-item-group__title{
    padding: 0!important;
  }
</style>

<script>
  import ApiUtil from '@/utils/ApiUtil'
  import {mapState} from 'vuex'
  export default {
    data() {
      return {
        isCollapse: false,
        appList:[],
      };
    },
    methods: {
      handleOpen(key, keyPath) {
      },
      handleClose(key, keyPath) {
      },
      changeCollapse() {
        this.isCollapse = !this.isCollapse
      },
      getReq() {
        var that = this
        ApiUtil.post("app.list",{}, function (resp) {
          if(resp.code=='0'){
            that.appList = resp.data[0].children
          }
        })

      },
    },
      mounted() {
        this.getReq()
      },
      computed:{
        ...mapState(['openState'])
      }
    }
</script>
