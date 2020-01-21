<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="memberReport" :rules="rules" ref="memberReportFrom" label-width="150px">

      <el-form-item label="举报类型：" prop="reportType">
        <el-input v-model="memberReport.reportType"></el-input>
      </el-form-item>
      <el-form-item label="举报人：" prop="reportMemberName">
        <el-input v-model="memberReport.reportMemberName"></el-input>
      </el-form-item>
      <el-form-item label="创建时间：" prop="createTime">
        <el-input v-model="memberReport.createTime"></el-input>
      </el-form-item>
      <el-form-item label="举报对象：" prop="reportObject">
        <el-input v-model="memberReport.reportObject"></el-input>
      </el-form-item>
      <el-form-item label="举报状态：" prop="reportStatus">
        <el-input v-model="memberReport.reportStatus"></el-input>
      </el-form-item>
      <el-form-item label="处理结果：" prop="handleStatus">
        <el-input v-model="memberReport.handleStatus"></el-input>
      </el-form-item>
      <el-form-item label="内容：" prop="note">
        <el-input v-model="memberReport.note"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('memberReportFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('memberReportFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createMemberReport, getMemberReport, updateMemberReport} from '@/api/cms/memberReport'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultMemberReport={
    name: ''
  };
  export default {
    name: 'MemberReportDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        memberReport:Object.assign({}, defaultMemberReport),
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
        getMemberReport(this.$route.query.id).then(response => {
          this.memberReport = response.data;
        });
      }else{
        this.memberReport = Object.assign({},defaultMemberReport);
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
                updateMemberReport(this.$route.query.id, this.memberReport).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createMemberReport(this.memberReport).then(response => {
                  this.$refs[formName].resetFields();
                  this.memberReport = Object.assign({},defaultMemberReport);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
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
        this.memberReport = Object.assign({},defaultMemberReport);
      }
    }
  }
</script>
<style>
</style>


