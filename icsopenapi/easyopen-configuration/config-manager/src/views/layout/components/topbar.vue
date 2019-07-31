<template>
  <el-menu class="navbar" mode="horizontal" style='box-shadow: 0 1px 3px 0 rgba(0,0,0,.12), 0 0 3px 0 rgba(0,0,0,.04);padding:0 20px'>
    <div class="collapseDiv">
     <hamburger class="hamburger-container"></hamburger>
     <breadcrumb class="breadcrumb-container"></breadcrumb>
    </div>
    <!-- <div class="right-menu">
      <el-dropdown class="avatar-container right-menu-item" trigger="hover">
        <div class="avatar-wrapper">
          <img class="user-avatar" :src="'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80'">
          <i class="el-icon-caret-bottom"></i>
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <span @click="logout" style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div> -->
    <div class="right-menu">
      <el-button @click="logout" class='login-out' type="text">退出<i class="el-icon-d-arrow-right"></i></el-button>
    </div>
  </el-menu>
</template>

<script>
  import Breadcrumb from '@/components/Breadcrumb'
  import ApiUtil from '@/utils/ApiUtil'
  import Hamburger from '@/components/Hamburger'
  
  export default {
    components: {
      Breadcrumb,
      Hamburger
    },
    methods: {
      logout(){
        ApiUtil.logout();
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss">
  .right-menu{
    .login-out{
      display:block;
      cursor:pointer;
      position:relative;
      top:3px;
      font-size:18px;
    }
  }
  .router-name{
    color: #666;
    text-decoration: none;
  }
  .el-header{
    padding:0!important;
  }
  .collapseDiv{
    display:inline-block;
  }
  .collapseDiv,.hamburger-container{
    outline:none;
  }
  .navbar {
    height: 50px;
    line-height: 50px;
    border-radius: 0px !important;
    position: relative;
    z-index: 99;
    .hamburger-container {
      line-height: 58px;
      height: 50px;
      float: left;
      padding: 0 10px;
    }
    .breadcrumb-container{
      float: left;
    }
  
    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }
    .right-menu {
      float: right;
      height: 100%;
      &:focus{
        outline: none;
      }
      .right-menu-item {
        display: inline-block;
        margin: 0 8px;
      }
      .screenfull {
        height: 20px;
      }
      .avatar-container {
        height: 50px;
        margin-right: 30px;
        .avatar-wrapper {
          cursor: pointer;
          margin-top: 5px;
          position: relative;
          .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }
          .el-icon-caret-bottom {
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>
