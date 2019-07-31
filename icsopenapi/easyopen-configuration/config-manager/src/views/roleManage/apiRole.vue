<template>
<el-dialog title="角色信息" :visible="apiVisiable" @close="cancel">
  <el-table
    ref="table"
    :data="apiList"
    height="500"
    tooltip-effect="dark"
    style="width: 100%"
    @selection-change="handleSelectionChange"
    @row-click='handleRow'>
    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      label="接口权限"
      >
      <template slot-scope="scope">{{ scope.row.text }}</template>
    </el-table-column>
  </el-table>
  <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="saveConfig">保 存</el-button>
    </div>
</el-dialog>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  export default {
    props:{
      apiVisiable:{
        type:Boolean,
        default:false
      }
    },
    data(){
      return {
        multipleSelection: [],
        apiList:[],
        postApiList:[],
        roleCode:'',
        reLoad:true
      }
    },
    methods:{
       toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleRow(row, event, column){
        this.$refs.table.toggleRowSelection(row)
      },
      saveConfig() {
        var that = this
        console.log(that.multipleSelection)
        that.postApiList = []
        that.multipleSelection.forEach( (val) => {
            that.postApiList.push(val.id)
        })
        ApiUtil.post("role.api.update",{
            apiIds:that.postApiList,
            roleCode:that.roleCode,
            app:that.$route.params.app
        },function(resp){
            if(resp.code=='0'){
            that.$message({
                message: '修改接口权限成功',
                type: 'success'
            });
            that.cancel()
            }
        })
      },
      cancel(){
        this.$emit('closeDialog',false,this.reLoad)
      },
      checked(apiListObj,apiList,roleCode){
        this.apiListObj = apiListObj
        this.apiList = apiList
        this.roleCode = roleCode
        this.curApi = []
        for (var i = 0; i < apiList.length; i++) {
            if (apiListObj.indexOf(apiList[i].id) != -1 ) {
                this.curApi.push(i);      
            }
        }
      },
    },
    updated(){
        if(this.curApi){
            this.curApi.forEach( val => {
            this.$refs.table.toggleRowSelection(this.apiList[val],true);
        })
        }
        
    }
  }
</script>
<style></style>
