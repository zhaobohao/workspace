<template>
    <div v-loading="loading"
    element-loading-text="拼命加载中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)" style="height:100%">
        <top-common></top-common>
        <div class="main2">
        <el-container class="main">
            <el-header><top></top></el-header>
            <el-container>
                <el-aside><sidebar></sidebar></el-aside>
                <el-container>
                    <el-main ><router-view></router-view></el-main>
                </el-container>
            </el-container>
        </el-container>
        </div>
    </div>
</template>

<script>
import api from '@/axios/api.js'
import Top from '@/pages/layout/components/top'
import Sidebar from '@/pages/layout/components/sidebar'
import Foot from '@/pages/layout/components/foot'
import TopCommon from '@/pages/layout/components/topCommon'
import {mapMutations,mapState} from 'vuex'
    export default {
        components:{
            Top,
            Sidebar,
            Foot,
            TopCommon
        },
        methods:{
            getReq(){
                this.setRes()
            },
            ...mapMutations(['setRes'])
        },
        created(){
            this.getReq()
        },
        computed:{
            ...mapState(['loading'])
        }
        
    }
</script>

<style>
    .main{
        top: 0;
        left: 50%;
        width: 1200px;
        display: table;
        overflow: hidden;
        table-layout: fixed;
        margin: 0 auto;
        padding-bottom: 20px;
        margin-bottom: 50px;
    }
    .el-header{padding: 0;}
    
    
    
    
    .el-main{
        border:1px solid #e0e1e3;
        border-radius: 5px;
        margin-left: 14px;
        background: #fff;
    }
    
    .main2{
        width: 100%;
        height: 100%;
        overflow-y: scroll;
    }
</style>
