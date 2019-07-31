<template>
    <div class="login-wrapper" 
    v-loading.fullscreen.lock="fullscreenLoading" 
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
        <top-common></top-common>
        <div class="content-box" >
            <div class="login-header"><i class="el-icon-service icon"></i>应用列表</div>
            <div class='app-box' v-for='item of res' :key="item.app" >
                    <el-tag style="width:200px;text-align:center">{{item.app}}</el-tag>
                    <el-tag type='info' style="width:300px;text-align:center">{{item.docUrl}}</el-tag>
                    <el-input v-if='item.needPassword' :type="item.type" v-model="item.password" auto-complete="off" size='small'>
                        <i
                            :class="{'el-icon-view':true,'el-input__icon':true,'edit':item.edit}"
                            slot="suffix"
                            @click="handleIconClick(item)">
                        </i>
                    </el-input>
                    <el-button class="text" @click='login(item)' size='small' :loading="item.loading">跳转</el-button>
            </div>
        </div>
    </div>
</template>

<script>
    import TopCommon from '@/pages/layout/components/topCommon' 
    import api from '@/axios/api.js'
    import ApiUtil from '@/utils/ApiUtil'
    import axios from 'axios'
    import {mapMutations} from 'vuex'
    export default {
    data(){
        return {
            res:[],
            resItem:{},
            fullscreenLoading:true,
        }
    },
    components:{
        TopCommon,
    },
    methods:{
        handleIconClick(item){
            if(item.type=='password'){
                item.type = 'text'
                item.edit = true
            }else{
                item.type = 'password'
                item.edit = false
            }
            
        },
        getReq(){
            api.post('listDocUrl').then((res)=>{
                this.fullscreenLoading = false
                localStorage.setItem('urlObj',JSON.stringify(res))
                res.forEach(item=>{
                    item.loading = this.loading
                    item.password = ''
                    item.edit = false
                    item.type = 'password'
                })
                this.res = res
            }).catch(()=>{
                this.fullscreenLoading = false
                let res = JSON.parse(localStorage.getItem('urlObj'))
                res.forEach(item=>{
                    item.loading = this.loading
                    item.password = ''
                    item.edit = false
                    item.type = 'password'
                })
                this.res = res
            })
        },
        ...mapMutations(['setAppInfo']),
        login(item){
            var that = this
            item.loading = true
            if(item.needPassword==true){
                if(item.password==''){
                    that.$message.error('密码不能为空')
                    item.loading = false
                    return false
                }
                axios.post(item.docUrl+'/pwd?password='+ item.password).then((res)=>{
                    if(res.data.code==0){
                        item.loading = false
                        sessionStorage.setItem('jwt',res.data.jwt)
                        that.formatUrl(item)
                        that.setAppInfo(item)
                        that.$router.push('/')  
                    }else{
                        item.loading = false
                        that.$message.error(res.data.msg)
                    }
            })
            }else{
                axios.post(item.docUrl).then((res)=>{
                    item.loading = false
                    that.formatUrl(item)
                    that.setAppInfo(item)
                    that.$router.push('/')
                }).catch(()=>{
                    item.loading = false
                })
            }
            
            
        },
        formatUrl(item){
            let url = item.docUrl
            let urlArr = url.split('/')
            urlArr.insert(urlArr.length-1,'json')
            item['indexUrl'] = urlArr.join('/')
            let curUrl = url.split('/')
            curUrl.pop()
            sessionStorage.setItem('url',curUrl.join('/'))
        }
    },
    mounted(){
        Array.prototype.insert = function (index, item) {
                this.splice(index, 0, item);
        }; 
        this.getReq()
    },
}
</script>

<style scoped>
    .login-wrapper{height: 100%;}
    .content-box{width: 1200px;margin: 0 auto;border: 1px solid #eee;background: #fff;
    padding: 20px;position:relative;top:90px;box-sizing: border-box;border-radius: 5px;box-shadow: 0px 0px 0px rgba(219,31,5,1), 0px 7px 21px rgba(0,0,0,.7);padding-bottom: 50px;}
    .login-header{font-size: 20px;margin-bottom: 30px;}
    .login-header .icon{margin-right: 5px;}
    .app-box{margin-bottom: 10px;border-bottom:1px dashed #ccc;padding: 10px 0;}
    .el-input{width: 20%;}
    .el-tag{line-height: 31px;}
    .el-input,.el-button{position: relative;bottom:1px;}
    .el-input__icon{cursor: pointer;}
    .edit{color:#409EFF;}
</style>
