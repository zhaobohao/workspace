<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="prefrenceArea" :rules="rules" ref="prefrenceAreaFrom" label-width="150px">

      <el-form-item label="标题：" prop="name">
        <el-input v-model="prefrenceArea.name"></el-input>
      </el-form-item>
      <el-form-item label="子标题：" prop="subTitle">
        <el-input v-model="prefrenceArea.subTitle"></el-input>
      </el-form-item>
      <el-form-item label="展示图片：" prop="pic">
        <el-input v-model="prefrenceArea.pic"></el-input>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input v-model="prefrenceArea.sort"></el-input>
      </el-form-item>
      <el-form-item label="状态：" prop="showStatus">
        <el-input v-model="prefrenceArea.showStatus"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('prefrenceAreaFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('prefrenceAreaFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createPrefrenceArea, getPrefrenceArea, updatePrefrenceArea} from '@/api/cms/prefrenceArea'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultPrefrenceArea={
    name: ''
  };
  export default {
    name: 'PrefrenceAreaDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        prefrenceArea:Object.assign({}, defaultPrefrenceArea),
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
        getPrefrenceArea(this.$route.query.id).then(response => {
          this.prefrenceArea = response.data;
        });
      }else{
        this.prefrenceArea = Object.assign({},defaultPrefrenceArea);
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
                updatePrefrenceArea(this.$route.query.id, this.prefrenceArea).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createPrefrenceArea(this.prefrenceArea).then(response => {
                  this.$refs[formName].resetFields();
                  this.prefrenceArea = Object.assign({},defaultPrefrenceArea);
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
        this.prefrenceArea = Object.assign({},defaultPrefrenceArea);
      }
    }
  }
</script>
<style>
</style>


