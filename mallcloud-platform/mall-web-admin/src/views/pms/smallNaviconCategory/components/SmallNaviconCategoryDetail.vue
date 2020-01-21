<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="smallNaviconCategory" :rules="rules" ref="smallNaviconCategoryFrom" label-width="150px">
      <el-form-item label="小程序首页分类ID：" prop="id" style="display: none">
        <el-input v-model="smallNaviconCategory.id"></el-input>
      </el-form-item>
      <el-form-item label="分类名称：" prop="title">
        <el-input v-model="smallNaviconCategory.title"></el-input>
      </el-form-item>
      <el-form-item label="分类图标：" prop="icon">
        <single-upload v-model="smallNaviconCategory.icon"></single-upload>
      </el-form-item>
      <el-form-item label="跳转页面：" prop="summary">
        <el-input v-model="smallNaviconCategory.summary"></el-input>
      </el-form-item>
      <el-form-item label="跳转类型：" prop="content">
        <el-input v-model="smallNaviconCategory.content"></el-input>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input v-model="smallNaviconCategory.sort"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('smallNaviconCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('smallNaviconCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createSmallNaviconCategory, getSmallNaviconCategory, updateSmallNaviconCategory} from '@/api/pms/smallNaviconCategory'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultSmallNaviconCategory={
    name: ''
  };
  export default {
    name: 'SmallNaviconCategoryDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        smallNaviconCategory:Object.assign({}, defaultSmallNaviconCategory),
        rules: {
          name: [
            {required: true, message: '请输入品牌名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入品牌logo', trigger: 'blur'}
          ],
        }
      }
    },
    created() {
      if (this.isEdit) {
        getSmallNaviconCategory(this.$route.query.id).then(response => {
          this.smallNaviconCategory = response.data;
        });
      }else{
        this.smallNaviconCategory = Object.assign({},defaultSmallNaviconCategory);
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
                updateSmallNaviconCategory(this.$route.query.id, this.smallNaviconCategory).then(response => {
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
                createSmallNaviconCategory(this.smallNaviconCategory).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.smallNaviconCategory = Object.assign({},defaultSmallNaviconCategory);
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
        this.smallNaviconCategory = Object.assign({},defaultSmallNaviconCategory);
      }
    }
  }
</script>
<style>
</style>


