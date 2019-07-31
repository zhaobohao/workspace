<template>
  <el-dialog title="角色信息" :visible="editVisiable" @close="cancel">
    <el-form :model="form" status-icon :rules="rules" ref="form" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
      <el-form-item label="角色码" prop="roleCode">
        <el-input type="text" v-model="form.roleCode" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="角色描述" prop="description">
        <el-input type="text" v-model="form.description"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="saveConfig">修 改</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  export default {
    props:{
      editVisiable:{
        type:Boolean,
        default:false
      }
    },
    data(){
      return {
        form: {},
        reLoad:false,
        rules: {
          roleCode: [
            { required: true, message: '请填写角色码', trigger: 'blur' }
          ],
          description: [
            { required: true, message: '请填写角色描述', trigger: 'blur' }
          ]
        },
      }
    },
    methods:{
      saveConfig() {
        var that = this
        this.$refs.form.validate((valid) => {
          if (valid) {
            ApiUtil.post("role.update",that.form,function(resp){
              if(resp.code=='0'){
                that.$message({
                  message: '修改成功',
                  type: 'success'
                });
                that.cancel(true)
              }
            })
          } else {
            console.log('请填写完整');
            return false;
          }
        });
      },
      received(formObj){
        this.form.roleCode = formObj.roleCode
        this.form.description = formObj.description
        this.form.id = formObj.id
      },
      cancel(data){
        if(data==true){
          this.reLoad = data
        }else{
          this.reLoad = false
        }
        // this.form.roleCode = []
        this.$refs.form.clearValidate();
        this.$emit('closeDialog',false,this.reLoad)
      },
    },
  }
</script>
<style></style>
