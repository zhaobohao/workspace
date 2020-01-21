<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="webLog" :rules="rules" ref="webLogFrom" label-width="150px">
      <el-form-item label="编号：" prop="id">
        <el-input v-model="webLog.id"></el-input>
      </el-form-item>
      <el-form-item label="用户编号：" prop="userId">
        <el-input v-model="webLog.userId"></el-input>
      </el-form-item>
      <el-form-item label="用户名：" prop="userName">
        <el-input v-model="webLog.userName"></el-input>
      </el-form-item>
      <el-form-item label="模块名：" prop="serviceName">
        <el-input v-model="webLog.serviceName"></el-input>
      </el-form-item>
      <el-form-item label="记录类名+方法名：" prop="method">
        <el-input v-model="webLog.method"></el-input>
      </el-form-item>
      <el-form-item label="描述：" prop="operationDesc">
        <el-input v-model="webLog.operationDesc"></el-input>
      </el-form-item>
      <el-form-item label="创建时间：" prop="createTime">
        <el-input v-model="webLog.createTime"></el-input>
      </el-form-item>
      <el-form-item label="IP：" prop="ip">
        <el-input v-model="webLog.ip"></el-input>
      </el-form-item>
      <el-form-item label="参数：" prop="params">
        <el-input v-model="webLog.params"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('webLogFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('webLogFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createWebLog, getWebLog, updateWebLog} from '@/api/sys/webLog'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultWebLog={
    name: ''
  };
  export default {
    name: 'WebLogDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        webLog:Object.assign({}, defaultWebLog),
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
        getWebLog(this.$route.query.id).then(response => {
          this.webLog = response.data;
        });
      }else{
        this.webLog = Object.assign({},defaultWebLog);
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
                updateWebLog(this.$route.query.id, this.webLog).then(response => {
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
                createWebLog(this.webLog).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.webLog = Object.assign({},defaultWebLog);
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
        this.webLog = Object.assign({},defaultWebLog);
      }
    }
  }
</script>
<style>
</style>


