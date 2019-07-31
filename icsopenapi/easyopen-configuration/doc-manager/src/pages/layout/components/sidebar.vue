<template>
    <el-menu
      :default-active="$route.path"
      class="el-menu-vertical-demo"
      active-text-color='#265467'
      :unique-opened='true'
      router>
      <div id="search">
          <el-select v-model="value8" filterable placeholder="请选择或搜索" size="small" @change="change">
              <el-option
              v-for="(item,index) in options"
              :key="item.value+index"
              :label="item.label"
              :value="item.value"
              >
              </el-option>
          </el-select>
      </div>
      <el-menu-item index="/config">
        <i class="el-icon-setting"></i>
        <span slot="title" style="font-size:15px;">配置项</span>
      </el-menu-item>
      <el-submenu v-for='(item,index) of resData' :index="item.name"  :key='item.name+index'>
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>{{item.name}}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item v-for='(child,index) of item.moduleItems' :index="/getModule/+child.orderName"  :key='child.orderName+index'>{{child.description}}{{child.version}}</el-menu-item>
        </el-menu-item-group>
      </el-submenu>
      <!-- <el-submenu index="10">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>附录</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="1-1">签名算法</el-menu-item>
          <el-menu-item index="1-2">请求实例</el-menu-item>
        </el-menu-item-group>
      </el-submenu> -->
    </el-menu>
</template>

<script>
import {mapState} from 'vuex'
import api from '@/axios/api.js'
export default {
    data(){
      return {
        options: [],
        value8: ''
      }
    },
    mounted(){
      this.getReq()
    },
    methods:{
    change(){
        this.$router.push('/getModule/'+this.value8)
    },
      search(){
          if(this.value8==''){
              this.$message({
                  type:'warning',
                  message:'请选择接口'
              })
              return 
          }
          this.$router.push('/getModule/'+this.value8)
      },
      getReq(){
        this.resData.forEach(element => {
          this.moduleName = element.name
        });
        let resUrl = JSON.parse(localStorage.getItem('appInfo'))
        if(!resUrl){
            return
        }
        api.post(resUrl.indexUrl).then((res)=>{
            
            let that = this
            that.options = []
            res.apiModules.forEach(item=>{
                item.moduleItems.forEach(val=>{
                    that.options.push({value:val.orderName,label:val.description+val.version})
                })
            })
        }).catch((error)=>{})
      }
    },
    computed:{
      ...mapState(['resData']),
    },
    watch: {
            '$route' (to, from) {
                this.getReq()
            }
        }
}
</script>
<style>
    .el-aside{
        border:1px solid #e0e1e3;
        border-radius: 5px;
        width: 300px!important;
        background: #fff;
        padding-top: 15px;
        padding-bottom: 20px;
    }
    .el-menu{border-right:none!important;}
    .el-scrollbar__wrap {
        overflow-x: hidden;
    } 
    .el-menu-item-group__title{
        display: none;
    }
    .el-submenu .el-menu-item{color: #265467;font-size: 13px;}
    .el-submenu__title{
        font-size: 15px;
        color:#444853;
    }
    .el-menu-item.is-active,.el-menu-item:hover{
        background: #f8f8f8!important;
    }
    .menubar li:hover,.el-submenu__title:hover{
        background: #f8f8f8!important;
    }
    .el-submenu .el-menu-item{height: 32px;line-height: 32px;}
    .el-menu .el-menu-item,.el-submenu__title{height: 40px!important;line-height: 40px!important;}
    .el-menu-item{height: 40px;line-height: 40px;}
    .el-menu-item-group .is-active{border-left:3px solid #fe7300;
    }
    .el-menu-item,.el-submenu__title{border-left:3px solid transparent}
    .el-menu-vertical-demo li:first-child.is-active{border-left:3px solid #fe7300;}
    .el-submenu__title,.el-menu-item{text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
    .el-submenu__title span{width: 140px;display: inline-block;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
    .is-opened .el-submenu__title{color: #fe7300}
    .is-opened .el-icon-location{color: #fe7300}
    #search{padding: 0 20px;box-sizing: border-box;margin-bottom: 15px;}
    #search .el-select{width: 100%;}
</style>
