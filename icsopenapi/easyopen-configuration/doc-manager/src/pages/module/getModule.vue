<template>
<div>
    <h2 class="title">{{moduleItem.description}}</h2>
    <a href='javascript:void(0)' @click.prevent="custormAnchor('test-btn')" class="el-icon-location mao" title='快速定位'></a><div class="address"><span class="method-area">接口名：</span> <span class="url">{{moduleItem.name}}</span><span class="method-area" style="float:right">版本：{{moduleItem.version}}</span></div>
    
    <h2 class="title">请求参数</h2>
    <form ref="form">
    <table>
        <tr>
            <th width='15%'>名称</th>
            <th qidth='10%'>类型</th>
            <th width='10%'>必须</th>
            <th>示例值</th>
            <th width='15%'>描述</th>
        </tr>
        <tr v-for='item of moduleItem.paramDefinitions' :key='item.name'>
            <td>{{item.name}}</td>
            <td>{{item.dataType}}</td>
            <td v-if="item.required=='true'">是</td>
            <td v-else>否</td>
            <td>
                <el-input class='create-input' v-if="item.dataType==='array' && item.name!=='files'" v-model="item.example[index]" v-for="(item1,index) of item.example" :key='item1.name'></el-input>
                <el-input class='create-input' v-if="item.dataType==='array' && item.name==='files'" type='file' v-model="item.example[index]" v-for="(item1,index) of item.example" :key='item1.name' :name="index==0?item.name:'upload_file_'+(index-1)*2"></el-input>
                <el-input v-model="item.example" v-if="item.dataType!=='array' && item.dataType!=='file' && item.elements.length==0" :name='item.name'></el-input>
                <span v-if="item.dataType==='array' && item.elements.length==0" class="jisuan-wrap"><el-button type="info" plain class="jisuan" @click='delInput(item)'>-</el-button><el-button class="jisuan" @click='addInput(item)' type="info" plain>+</el-button></span>
                <el-input v-if="item.dataType==='file'" type="file"  v-model="item.example" :name="item.name"></el-input>
                <table-params :data='item.elements'></table-params>
            </td>
            <td>{{item.description}}</td>
        </tr>
    </table>
    </form>
    <h2 class="title">返回结果</h2>
    <table >
        <tr>
            <th>名称</th>
            <th>类型</th>
            <th>描述</th>
        </tr>
        <tr>
            <td>{{resAllData.code_name}}</td>
            <td>string</td>
            <td>{{resAllData.code_description}}</td>
        </tr>
        <tr>
            <td>{{resAllData.msg_name}}</td>
            <td>string</td>
            <td>{{resAllData.msg_description}}</td>
        </tr>
        <tr v-if="moduleItem.apiDocReturnDefinition!=null">
            <td>{{resAllData.data_name}}</td>
            <td>{{moduleItem.apiDocReturnDefinition.dataType}}</td>
            <td>{{moduleItem.apiDocReturnDefinition.description}}。示例值：{{moduleItem.apiDocReturnDefinition.example}}
                <table-layout :data='moduleItem.resultDefinitions'></table-layout>
            </td>
        </tr>
        <tr v-else>
            <td>{{resAllData.data_name}}</td>
            <td>object</td>
            <td>{{resAllData.data_description}}
                <table-layout :data='moduleItem.resultDefinitions'></table-layout>
            </td>
        </tr>
        
    </table>
    <el-button :disabled="disabled" id='test-btn' size='small' type="info" class='test-btn' style="margin-top:10px;" @click='test'>
        <i class="el-icon-success margin-right-5" v-if='loading'></i>
        <i v-else class="el-icon-loading margin-right-5"></i>测试
    </el-button>
    <div v-if="flag" class="result" style=""><h2 class="title">请求数据</h2> 
    <div class="curl code-area"><pre><code class="json hljs">{{requestData}}</code></pre>
    </div>
     <div> <h2 class="title">返回结果<el-tooltip class="item" effect="light" content="点击复制" placement="right-end">
                    <el-button class="copy" 
                        v-clipboard:copy="resBodyString"
                        v-clipboard:success="onCopy"
                        v-clipboard:error="onError">
                            一键复制
                    </el-button>
                </el-tooltip></h2> 
        <div class="response-body code-area">
            <pre><code class="json hljs">{{resBody}}
          </code></pre>
        </div>
    </div>
</div>
</div>
</template>
<script>
import TableLayout from '@/components/table-layout'
import TableParams from '@/components/table-params'
import api from '@/axios/api.js'
import {mapState,mapMutations} from 'vuex'
import ApiUtil from '@/utils/ApiUtil'
import {sdk,requestData} from '@/utils/sdk'
export default {
    components:{TableLayout,TableParams},
    data() {
      return {
        loading:true,
        flag:false,
        objList:[],
        resBody:'',
        disabled:false,
        moduleItem:{},
        form:{},
        tableForm:null,
        resBodyString:'',
        
      }
    },
    methods:{
        onCopy: function (e) {
        this.$message({
          type:'success',
          message:'复制成功'
        })
            console.log('你刚刚复制: ' + e.text)
        },
        onError: function (e) {
            console.log('无法复制文本！')
        },
        generateForm(obj){
            var that = this
            
            obj.forEach((item)=>{
                if(item.dataType=='file' || item.name=='files'){
                    that.tableForm = that.$refs.form
                }
                if(item.dataType!='file' && item.name!='files'){
                    // that.tableForm = null
                    if(item.elements.length!=0){
                        that.form[item.name] = {}
                        item.elements.forEach((val)=>{
                            that.form[item.name][val.name] = val.example
                        })
                    }else{
                        that.form[item.name] = item.example
                    }
                }
            }) 
        },
        test(){
            var that = this;
            that.loading=false;
            that.disabled = true;
            that.form = {}
            console.log('that.moduleItem.paramDefinitions',that.moduleItem.paramDefinitions)
            that.generateForm(that.moduleItem.paramDefinitions)
            
            setTimeout(function(){
                ApiUtil.post(that.moduleItem.name, that.form,that.moduleItem.version,that.tableForm,function (res) {
                    that.flag=true
                    that.loading=true
                    // if(typeof res.data === 'string'){
                    //     res.data = JSON.parse(res.data)
                    // }
                    that.resBody = res
                    that.resBodyString = JSON.stringify(res)
                    that.disabled = false
                    that.requestData = requestData
                })   
            },200)
        },
        custormAnchor(anchorName) {
            // 找到锚点
            let anchorElement = document.getElementById(anchorName);
            // 如果对应id的锚点存在，就跳转到锚点
            if(anchorElement) { anchorElement.scrollIntoView(); }
        },
        getReq(){
            console.log('resData',this.resData)
            this.resData.forEach(element => {
                element.moduleItems.forEach(value=>{
                    if(this.$route.params.orderName==value.orderName){
                        this.moduleItem = value
                        this.moduleItem.paramDefinitions.forEach(item=>{
                            if(item.dataType==='array'){
                                item.example = []
                            }
                            if(item.dataType==='file'){
                                item.example = ''
                            }

                        })
                    }
                })
            });
        },
        addInput(item){
            item.example.push('')
        },
        delInput(item){
            item.example.pop()
        }
    },
    created(){
        this.getReq()
    },
    computed:{
      ...mapState(['resData','resAllData'])
    },
    watch: {
        '$route' (to, from) {
            this.flag=false,
            this.loading=true
            this.disabled=false
            this.getReq()
        },
        resData(){
            this.getReq()
        }
    }
}
</script>
<style scoped>
table, td, th
  {
    text-align: left;
    border:1px solid #e9eaec;
    padding: 10px 20px;
    font-size: 12px;
  }
.jisuan-wrap{display: flex;flex-direction: row;text-align: center;}
.jisuan{display: block;box-sizing: border-box;cursor: pointer;width: 100%}
.address{
    width: 100%;
    border: 1px solid #e0e1e3;
    border-radius: 2px;
    background: #fcfcfc;
    margin-bottom: 10px;
}
.method-area{
    color: #86a555;
    background: #f5f5f5;
    text-align: center;
    display: inline-block;
    padding: 6px;
    font-size: 18px;
    line-height: 24px;
}
.url{
    color: #5d5d67;
    font-size: 14px;
    line-height: 24px;
    display: inline-block;
    margin-left: 4px;
}
.res-box{
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    background: #f0f0f0;
    border: 1px solid #e0e1e3;
    font-size: 12px;
    color:#444;
}
.res-box-content{margin-left:20px;}
.res-box-content p{padding-left: 20px;line-height: 14px;margin:5px;}
.res-box-content p span{color:#800;margin-left: 5px;}
.result .hljs {
    border: 1px solid #e0e1e3;
    padding-left: 10px;
    word-wrap:break-word
}
.hljs, .hljs-subst {
    color: #444;
    font-size: 13px;
}
.hljs {
    display: block;
    overflow-x: auto;
    padding: .5em;
    background: #f0f0f0;
    font-size: 13px;
    line-height: 16px;
    font-family: 'Courier New', Courier, monospace;
    color:#222;
}
.mao{text-decoration: none;color:#86a555;font-size: 30px;margin-bottom: 10px;}
.mao:hover{transform: scale(1.2);transition:all .1s;}
td,th{padding: 5px;min-height: 41px;}
tr{height: 41px;}
pre{padding: 0;margin: 0;}
.el-input input{height: 30px;line-height: 30px;}
.example-table{margin-bottom: 10px;}
.create-input{margin-bottom: 5px;}
.innerTable table{padding: 0!important;}
.el-input{display: block;}
.result .copy{float:right;font-size: 14px;padding:8px 10px;color: #657180;}
.result .copy:hover{background: #fff;color: #999;border: 1px solid #ccc;}
</style>


