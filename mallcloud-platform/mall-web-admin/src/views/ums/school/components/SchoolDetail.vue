<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="school" :rules="rules" ref="schoolFrom" label-width="150px">

      <el-form-item label="精度：" prop="longitude">
        <el-input v-model="school.longitude"></el-input>
      </el-form-item>
      <el-form-item label="纬度：" prop="latitude">
        <el-input v-model="school.latitude"></el-input>
      </el-form-item>
      <el-form-item label="名称：" prop="name">
        <el-input v-model="school.name"></el-input>
      </el-form-item>
      <el-form-item label="省：" prop="province">
        <el-input v-model="school.province"></el-input>
      </el-form-item>
      <el-form-item label="市：" prop="city">
        <el-input v-model="school.city"></el-input>
      </el-form-item>
      <el-form-item label="区：" prop="region">
        <el-input v-model="school.region"></el-input>
      </el-form-item>
      <el-form-item label="地址：" prop="address">
        <el-input v-model="school.address"></el-input>
      </el-form-item>
      <el-form-item label="1热门：" prop="hot">
        <el-input v-model="school.hot"></el-input>
      </el-form-item>
      <el-form-item label="211：" prop="is211">
        <el-input v-model="school.is211"></el-input>
      </el-form-item>
      <el-form-item label="985：" prop="is985">
        <el-input v-model="school.is985"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('schoolFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('schoolFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createSchool, getSchool, updateSchool} from '@/api/ums/school'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultSchool={
    name: ''
  };
  export default {
    name: 'SchoolDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        school:Object.assign({}, defaultSchool),
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
        getSchool(this.$route.query.id).then(response => {
          this.school = response.data;
        });
      }else{
        this.school = Object.assign({},defaultSchool);
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
                updateSchool(this.$route.query.id, this.school).then(response => {
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
                createSchool(this.school).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.school = Object.assign({},defaultSchool);
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
        this.school = Object.assign({},defaultSchool);
      }
    }
  }
</script>
<style>
</style>


