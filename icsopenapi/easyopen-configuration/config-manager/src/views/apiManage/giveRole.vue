<template>
  <el-dialog title="接口授权" :visible="RoleVisiable" @close="cancel">
    <el-form status-ico label-position="left" label-width="100px" style=' margin-left:50px;'>
      <el-form-item label="角色 :" prop="roleCode">
        <el-checkbox-group v-model="roleCodes">
          <el-checkbox :label="item.id" name="type" v-for="item of roleList" :key="item.id">{{item.text}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="saveConfig">添 加</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  export default {
    props:['RoleVisiable'],

    data(){
      return {
        roleList:[],
        roleCodes:[],
        apiId:'',
        reLoad:false
      }
    },
    methods:{
      receive(roleCodes,roleList,apiId){
          this.roleList = roleList
          this.roleCodes = roleCodes
          this.apiId = apiId
      },
      saveConfig() {
        var that = this
        ApiUtil.post("api.role.update",{
            roleCodes:that.roleCodes,
            apiId:that.apiId,
            app:that.$route.params.app
        },function(resp){
            if(resp.code=='0'){
            that.$message({
                message: '授权成功',
                type: 'success'
            });
            that.cancel(true)
            } 
        })
      },
      cancel(data){
        if(data==true){
          this.reLoad = data
        }else{
          this.reLoad = false
        }
        this.$emit('closeDialog',false,this.reLoad)
      },
    },
  }
</script>
<style></style>
