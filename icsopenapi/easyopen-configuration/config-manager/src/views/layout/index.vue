<template>
  <el-container>
      <sidebar></sidebar>
    <el-container>
      <el-header style="border-bottom: solid 1px #e6e6e6;height: 51px;">
        <topbar></topbar>
      </el-header>
      <el-main>
        <div class='main-box'>
            <router-view />
        </div>
        <footerbar></footerbar>
      </el-main>
      
    </el-container>
  </el-container>
</template>

<script>
  import sidebar from '@/views/layout/components/sidebar'
  import topbar from "@/views/layout/components/topbar"
  import footerbar from "@/views/layout/components/footer"
  export default {
    components:{
      topbar,
      sidebar,
      footerbar
    }
  }
</script>

<style scoped>
  .el-container{
    height: 100%;
  }
  .el-main{
    background-color: #f4f6f9;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    padding: 20px;
    box-sizing: border-box;
  }
  .main-box{
    box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
    background: #fff;
    padding: 20px;
    box-sizing: border-box;
    border-radius: 5px;
  }

</style>
