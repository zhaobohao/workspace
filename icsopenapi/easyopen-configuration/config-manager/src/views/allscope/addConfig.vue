<template>
  <el-dialog title="配置信息" :visible="addVisiable" @close="cancel">
    <el-form :model="form" status-icon :rules="rules" ref="form" label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
      <el-form-item label="配置域" prop="keyName">
        <el-input type="text" v-model="form.keyName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="配置项" prop="fieldName">
        <el-input type="text" v-model="form.fieldName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="配置值" prop="fieldValue">
        <el-input v-model="form.fieldValue"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input v-model="form.remark"></el-input>
      </el-form-item>
      <!-- <el-form-item>
        <el-button type="primary" @click="saveConfig">添 加</el-button>
        <el-button @click="cancel">取 消</el-button>
      </el-form-item> -->
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
          keyName:'',// 配置域
          fieldName:'',// 配置项
          fieldValue:'',// 配置值
          remark:'', // 描述
        },
        reLoad:false,
        rules: {
          keyName: [
            { required: true, message: '请填写配置域', trigger: 'change' }
          ],
          fieldName: [
            { required: true, message: '请填写配置项', trigger: 'change' }
          ],
          fieldValue: [
            { required: true, message: '请填写配置值', trigger: 'change' }
          ],
          remark: [
            { required: true, message: '请填写描述', trigger: 'change' }
          ]
        },
      }
    },
    methods:{
      saveConfig() {
        var that = this
        this.$refs.form.validate((valid) => {
          if (valid) {
            ApiUtil.post("global.config.add",that.form,function(resp){
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
