<template>
<div>
    <header class="fixed noborder" id="git-header-nav">
        <div class="ui container">
            <div class="ui menu">
                <div class="item gitosc-logo">
                    <img class="logo-img" height="28" src="static/logo.png">
                    <span class="developer">邦德</span>
                    <div class="right-menu" v-if="$route.path!='/login'">
                        <el-button @click='flashApp' type="text" v-bind:class="{ 'el-icon-refresh': active1,'el-icon-loading':active2,'fresh-icon': true }" title="刷新应用列表"></el-button>
                        <el-dropdown class="avatar-container right-menu-item" trigger="hover">
                            <div class="avatar-wrapper">
                                
                                <el-badge :value="appNum" class="item1">{{appInfo.app}}</el-badge>
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item v-for='item of res' :key="item.app">
                                    <span @click="login(item)" style="display:block;"><div class="app-name">{{item.app}}</div><div class="address-name">{{item.docUrl}}</div></span>
                                </el-dropdown-item>
                                <router-link to="/login"><el-dropdown-item divided><span class="app-name">退出</span></el-dropdown-item></router-link>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>
                </div>
            </div>
        </div>
       
    </header>    
     <el-dialog title="提示" :visible.sync="dialogFormVisible" :before-close="cancel">
            <el-form :rules='rules' ref="form" :model="setData" >
                <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" :type="type">
                    <i
                        :class="{'el-icon-view':true,'el-input__icon':true,'edit':edit}"
                        slot="suffix"
                        @click="handleIconClick">
                    </i>
                </el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click='cancel'>取 消</el-button>
                <el-button type="primary" @click="confirmHandle">确 定</el-button>
            </div>
        </el-dialog>
        </div>
</template>

<script>
import api from '@/axios/api'
import axios from 'axios'
import {mapState,mapMutations} from 'vuex'
export default {
    data() {
        return {
            dialogVisible: false,
            res:[],
            appNum:0,
            dialogFormVisible:false,
            form:{
                password:'',
            },
            itemObj:{},
            active1:true,
            active2:false,
            rules: {
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ],
            },
            edit:false,
            type:'password'
            
        };
    },
    methods: {
         handleIconClick(){
            if(this.type=='password'){
                this.type = 'text'
                this.edit = true
            }else{
                this.type = 'password'
                this.edit = false
            }
            
        },
        flashApp(){
            this.active2 = true
            setTimeout(()=>{
                 api.post('listDocUrl').then((res)=>{
                    this.res = res
                    this.appNum = res.length
                    this.active2 = false
                    this.$message.success('刷新成功')
                }).catch((e)=>{
                    this.active2 = false
                    this.$message.error('刷新失败')
            })
            },500)
           
        },
        getReq(){
            api.post('listDocUrl').then((res)=>{
                this.res = res
                this.appNum = res.length
            }).catch(()=>{
                let res = JSON.parse(localStorage.getItem('urlObj'))
                res.forEach(item=>{
                    item.loading = this.loading
                    item.password = ''
                    item.edit = false
                    item.type = 'password'
                })
                this.res = res
                this.appNum = res.length
            })
        },
        ...mapMutations(['setRes','setAppInfo']),
        cancel(){
            this.dialogFormVisible = false
            this.$refs.form.resetFields();
        },
        confirmHandle(){
            var that = this
            that.$refs.form.validate((valid) => {
            if (valid) {
                axios.post(that.itemObj.docUrl+'/pwd?password='+ that.form.password).then((res)=>{
                    if(res.data.code==0){
                        that.dialogFormVisible = false
                        that.$refs.form.resetFields();
                        sessionStorage.setItem('jwt',res.data.jwt)
                        that.formatUrl(that.itemObj)
                        that.setAppInfo(that.itemObj)
                        that.$router.push('/') 
                        that.setRes()
                    }else{
                        that.$message.error(res.data.msg)
                        return false
                    }
                })
             }
            })
        },
        login(item){
            this.itemObj = item
            var that = this
            if(item.needPassword==true){
                that.dialogFormVisible = true
            }
            else{
                axios.post(item.docUrl).then((res)=>{
                    that.formatUrl(item)
                    that.setAppInfo(item)
                    that.$router.push('/')
                    that.setRes()
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
    computed:{
        ...mapState(['appInfo']),
        setData(){
            return this.form
        }
    },
    mounted(){
        Array.prototype.insert = function (index, item) {
                this.splice(index, 0, item);
        }; 
        this.getReq()
    }
}
</script>

<style scoped>
    a{text-decoration: none;}
    .fresh-icon{position: relative;;top:4px;right:1px;font-size: 20px;}
    .item1 {
        margin-right: 15px;
        line-height: 43px;
    }
    .item{height: 100%;position: relative;right: 10px;}
    .ui.container {
        width: 1200px;
        height: 100%;
        margin: 0 auto;
    }
    span.developer {
        color: white;
        font-size: 20px;
        font-weight: bold;
        position: relative;
        top: 4px;
        left: 5px;
        line-height: 12px;
    }
    #git-header-nav {
        top: 0;
        left: 0;
        width: 100%;
        height: 46px;
        z-index: 100;
        -webkit-box-shadow: none;
        box-shadow: none;
        position: fixed;
        background: #303643 !important;
        -webkit-transition: -webkit-box-shadow .3s;
        transition: -webkit-box-shadow .3s;
        transition: box-shadow .3s;
        transition: box-shadow .3s, -webkit-box-shadow .3s;
    }
    .logo-img{
        position: relative;
        top:11px;
    }
    .right-menu{float:right;color:#fff;height: 100%;}
    .right-menu div{height: 100%;}
    .avatar-wrapper{color:#fff;font-size: 16px;font-weight: bold;cursor: pointer;}
    .app-name{margin-right:10px;display: inline;color:#222}
    .address-name{display: inline;color: #52a7ea;}
    .el-input__icon{cursor: pointer;}
    .edit{color:#409EFF;}
</style>
