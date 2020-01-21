<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="adminLog" :rules="rules" ref="adminLogFrom" label-width="150px">
      <el-form-item label="编号：" prop="id">
        <el-input v-model="adminLog.id"></el-input>
      </el-form-item>
      <el-form-item label="用户编号：" prop="userId">
        <el-input v-model="adminLog.userId"></el-input>
      </el-form-item>
      <el-form-item label="用户名：" prop="userName">
        <el-input v-model="adminLog.userName"></el-input>
      </el-form-item>
      <el-form-item label="模块名：" prop="serviceName">
        <el-input v-model="adminLog.serviceName"></el-input>
      </el-form-item>
      <el-form-item label="记录类名+方法名：" prop="method">
        <el-input v-model="adminLog.method"></el-input>
      </el-form-item>
      <el-form-item label="描述：" prop="operationDesc">
        <el-input v-model="adminLog.operationDesc"></el-input>
      </el-form-item>
      <el-form-item label="创建时间：" prop="createTime">
        <el-input v-model="adminLog.createTime"></el-input>
      </el-form-item>
      <el-form-item label="IP：" prop="ip">
        <el-input v-model="adminLog.ip"></el-input>
      </el-form-item>
      <el-form-item label="参数：" prop="params">
        <el-input v-model="adminLog.params"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('adminLogFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('adminLogFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createAdminLog, getAdminLog, updateAdminLog} from '@/api/sys/adminLog'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultAdminLog={
    name: ''
  };
  export default {
    name: 'AdminLogDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        adminLog:Object.assign({}, defaultAdminLog),
        rules: {
          name: [
            {required: true, message: '请输入品牌名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入品牌logo', trigger: 'blur'}
          ],
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ],
        }
      }
    },
    created() {
      if (this.isEdit) {
        getAdminLog(this.$route.query.id).then(response => {
          this.adminLog = response.data;
        });
      }else{
        this.adminLog = Object.assign({},defaultAdminLog);
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if (this.isEdit) {
                updateAdminLog(this.$route.query.id, this.adminLog).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.$message({
                          message: '修改成功',
                          type: 'success',
                          duration:1000
                      });
                      this.$router.back();
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });
                  }

                });
              } else {
                createAdminLog(this.adminLog).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.adminLog = Object.assign({},defaultAdminLog);
                      this.$message({
                          message: '提交成功',
                          type: 'success',
                          duration:1000
                      });
                      this.$router.back();
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });
                  }

                });
              }
            });

          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.adminLog = Object.assign({},defaultAdminLog);
      }
    }
  }
</script>
<style>
</style>


