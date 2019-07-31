<template>
    <div class="ui secondary pointing menu" id="api-doc-menu">
        <span class="active item" href="/api/v5/swagger">API 文档</span>
        
    </div>
</template>

<script>
import {mapState} from 'vuex'
import api from '@/axios/api.js'
export default {
    data() {
      return {
        options: [],
        value8: ''
      }
    },
    mounted() {
        this.getReq()
    },
    
    methods: {
        getReq(){
        let resUrl = JSON.parse(localStorage.getItem('appInfo')) || {}
        if(JSON.stringify(resUrl)!='{}'){
            api.post(resUrl.indexUrl).then((res)=>{
                let that = this
                that.options = []
                res.apiModules.forEach(item=>{
                    item.moduleItems.forEach(val=>{
                        that.options.push({value:val.orderName,label:val.description+val.version})
                    })
                })
            }).catch((error)=>{
                })
            }
        },
    }
}
</script>
<style>
.el-header{
    padding: 0!important;
    margin-top: 70px!important;
}
#api-doc-menu {
    border-bottom: 1px solid rgba(0,0,0,0.1);
    padding-bottom: 1px;
    width: 100%;
    margin: 20px auto;
}
#api-doc-menu span.item.active {
    color: #fe7300 !important;
    border-bottom: 2px solid #fe7300 !important;
    text-decoration: none;
}
#api-doc-menu span.item {
    font-size: 18px;
    padding-left: 0;
    padding-right: 0px;
    margin-right: 35px;
}
</style>
