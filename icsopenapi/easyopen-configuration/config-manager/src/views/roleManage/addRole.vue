<template>
  <el-dialog title="角色信息" :visible="addVisiable" @close="cancel">
    <el-form :model="form" status-icon :rules="rules" ref="form" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
      <el-form-item label="角色码" prop="roleCode">
        <el-input type="text" v-model="form.roleCode" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="角色描述" prop="description">
        <el-input type="text" v-model="form.description" auto-complete="off"></el-input>
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
    props:{
      addVisiable:{
        type:Boolean,
        default:false
      },
    },
    data(){
      return {
        form: {
          roleCode:'',
          description:'',
        },
        reLoad:false,
        rules: {
          roleCode: [
            { required: true, message: '请填写角色码', trigger: 'change' }
          ],
          description: [
            { required: true, message: '请填写角色描述', trigger: 'change' }
          ]
        },
      }
    },
    methods:{
      saveConfig() {
        var that = this
        this.$refs.form.validate((valid) => {
          if (valid) {
            ApiUtil.post("role.add",that.form,function(resp){
              if(resp.code=='0'){
                that.$message({
                  message: '添加成功',
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
      cancel(data){
        if(data==true){
          this.reLoad = data
        }else{
          this.reLoad = false
        }
        this.$refs.form.resetFields();
        this.$emit('closeDialog',false,this.reLoad)
      },
    }
  }
</script>
<style></style>
