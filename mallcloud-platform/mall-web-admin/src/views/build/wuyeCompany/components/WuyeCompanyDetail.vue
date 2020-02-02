<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="wuyeCompany" :rules="rules" ref="wuyeCompanyFrom" label-width="150px">

      <el-form-item label="名称：" prop="name">
        <el-input v-model="wuyeCompany.name"></el-input>
      </el-form-item>
      <el-form-item label="联系方式：" prop="phone">
        <el-input v-model="wuyeCompany.phone"></el-input>
      </el-form-item>
      <!--<el-form-item label="状态：">
        <el-radio-group v-model="wuyeCompany.status">
          <el-radio :label="1">申请</el-radio>
          <el-radio :label="2">审核失败</el-radio>
          <el-radio :label="3">审核成功</el-radio>
        </el-radio-group>
      </el-form-item>-->
      <el-form-item label="照片：" prop="pic">
        <single-upload v-model="wuyeCompany.pic"></single-upload>
      </el-form-item>
      <el-form-item label="位置：" prop="address">
        <el-input v-model="wuyeCompany.address"></el-input>
      </el-form-item>

     <!-- <el-form-item label="审核内容：" prop="statusDesc">
        <el-input v-model="wuyeCompany.statusDesc"></el-input>
      </el-form-item>-->

      <el-form-item>
        <el-button type="primary" @click="onSubmit('wuyeCompanyFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('wuyeCompanyFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createWuyeCompany, getWuyeCompany, updateWuyeCompany} from '@/api/build/wuyeCompany'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultWuyeCompany={
    name: ''
  };
  export default {
    name: 'WuyeCompanyDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        wuyeCompany:Object.assign({}, defaultWuyeCompany),
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
        getWuyeCompany(this.$route.query.id).then(response => {
          this.wuyeCompany = response.data;
        });
      }else{
        this.wuyeCompany = Object.assign({},defaultWuyeCompany);
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
                updateWuyeCompany(this.$route.query.id, this.wuyeCompany).then(response => {
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
                createWuyeCompany(this.wuyeCompany).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.wuyeCompany = Object.assign({},defaultWuyeCompany);
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
        this.wuyeCompany = Object.assign({},defaultWuyeCompany);
      }
    }
  }
</script>
<style>
</style>


