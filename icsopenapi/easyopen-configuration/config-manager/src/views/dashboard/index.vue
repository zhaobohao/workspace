<template>
   <!-- <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>欢迎登录配置中心</span>
      </div>
      <div class="component-item">
        <div class="welcome">
          如果是首次登录:<br>
          先设置基本参数（系统菜单-><el-button type="text" @click='goAllScope'>全局设置</el-button>）<br>
          设置app的角色（权限管理->添加角色）<br>
          添加接入方
        </div>
      </div>
  </el-card> -->
  <div class="gif-wrapper">
      <img class="emptyGif" src="https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3">
       <el-card class="box-card">
          <div slot="header" class="clearfix">
            <h3 style="display:inline-block">欢迎登录配置中心</h3>
            <img src='static/smile.png' class='smile-img'/>
          </div>
          <div class="component-item">
            <div class="welcome">
              
              <ul class='info-ul'>
                <li>如果是首次登录:</li>
                <li><i class='el-icon-location'></i> 先设置基本参数(系统菜单-><el-button type="text" @click='goAllScope'>全局设置</el-button>)</li>
                <li><i class='el-icon-location'></i> 设置app的角色(权限管理->添加角色)</li>
                <li><i class='el-icon-location'></i> 添加接入方</li>
              </ul>
            </div>
          </div>
      </el-card>
    </div>
</template>
<script>
import layout from "@/views/layout/index";
export default {
  methods: {
    goAllScope() {
      this.$router.push("/allscope");
    }
  }
};
</script>
<style scoped>
.box-card{
  position:absolute;
  right: 20%;
  top:10%;
  width: 30%;
  border-radius: 30px;
  padding: 30px 20px;
}
.welcome {
  
  font-size: 16px;
  color: #444;
  line-height: 50px;
  
}
.info-ul{
  list-style: none;
  padding: 0
}
.info-ul li:hover .el-icon-location{
  color: #409EFF;
  transform: scale(1.2,1.2);
  /* transition:all 1s; */
}
.emptyGif{
  height: 100%;
  /* float: left; */
}
.gif-wrapper{
  background:rgb(227, 227, 227);
  position: relative
}
.smile-img{
  width:40px;
  height: 40px;
  position: relative;
  top:10px;
}
.smile-img:hover{
  transform: rotate(360deg);
  transition:all 3s;
}
</style>