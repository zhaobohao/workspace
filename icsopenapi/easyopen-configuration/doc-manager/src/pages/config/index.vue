<template>
<div>
    <h2 class="title">配置项</h2>
    <table>
        <tr>
            <th>参数</th>
            <th>值</th>
        </tr>
        <tr>
            <td>HTTP请求地址</td>
            <td><el-input v-model="url"></el-input></td>
        </tr>
        <tr>
            <td>app_key</td>
            <td><el-input v-model="app_key"></el-input></td>
        </tr>
        <tr>
            <td>secret</td>
            <td><el-input v-model="secret"></el-input></td>
        </tr>
        <tr>
            <td>access_token</td>
            <td><el-input v-model="access_token"></el-input></td>
        </tr>
        <tr>
            <td>jwt</td>
            <td><el-input v-model="jwt"></el-input></td>
        </tr>
        
    </table>
    <el-button size='small' type="info" class='test-btn' style="margin-top:10px;" @click='save'><i class="el-icon-success margin-right-5"></i>保存</el-button>
</div>
</template>
<script>
import router from '@/router'
import {mapState} from 'vuex'
export default {
    data() {
      return {
        url:'',
        app_key:'test',
        secret:'123456',
        access_token:'',
        jwt:'',
      }
    },
    
    methods:{
        save(){
            sessionStorage.setItem('url',this.url)
            sessionStorage.setItem('app_key',this.app_key)
            sessionStorage.setItem('secret',this.secret)
            sessionStorage.setItem('access_token',this.access_token)
            sessionStorage.setItem('easyconf_jwt',this.jwt)
            this.$message({
                message: '保存成功',
                type: 'success'
            });
            this.getReq()
        },
        getReq(){
            if(this.appInfo.needPassword==true){
                if(sessionStorage.jwt==''){
                    router.push('/login')
                }
            }
            if(sessionStorage.getItem('url')){
                this.url = sessionStorage.getItem('url')
            }else{
                if(this.appInfo.docUrl){
                    let urlList = this.appInfo.docUrl.split('/')
                    urlList.pop()
                    let urlNow = urlList.join('/')
                    sessionStorage.setItem('url',urlNow)
                }
                this.url = sessionStorage.getItem('url')
            }
            
            this.app_key = sessionStorage.getItem('app_key') || this.app_key,
            this.secret = sessionStorage.getItem('secret') ||this.secret,
            this.access_token = sessionStorage.getItem('access_token') || this.access_token,
            this.jwt = sessionStorage.getItem('easyconf_jwt') || this.jwt
        },
    },
    mounted(){
        this.getReq()
    },
    computed:{
        ...mapState(['appInfo'])
    },
    watch:{
        appInfo(){
            this.getReq()
        }
    }
}
</script>
<style scoped>
.el-input input{height: 30px;line-height: 30px;}
table{
    padding: 10px 20px;
}
td, th{
    padding: 5px 10px;
    min-height: 41px;
}
tr{
    height: 41px;
}
table, td, th
  {
    text-align: left;
    border:1px solid #e9eaec;
    font-size: 12px;
  }
</style>


