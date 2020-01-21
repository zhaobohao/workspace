<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="permissionCategory" :rules="rules" ref="permissionCategoryFrom" label-width="150px">
      <el-form-item label="：" prop="id" style="display: none">
        <el-input v-model="permissionCategory.id"></el-input>
      </el-form-item>
      <el-form-item label="类型名称：" prop="name">
        <el-input v-model="permissionCategory.name"></el-input>
      </el-form-item>
      <el-form-item label="类型图标：" prop="icon">
        <el-input v-model="permissionCategory.icon"></el-input>
      </el-form-item>
      <el-form-item label="类型数量：" prop="typeCount">
        <el-input v-model="permissionCategory.typeCount"></el-input>
      </el-form-item>
      <el-form-item label="状态：" prop="showStatus">
        <el-input v-model="permissionCategory.showStatus"></el-input>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input v-model="permissionCategory.sort"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('permissionCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('permissionCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createPermissionCategory, getPermissionCategory, updatePermissionCategory} from '@/api/sys/permissionCategory'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultPermissionCategory={
    name: ''
  };
  export default {
    name: 'PermissionCategoryDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        permissionCategory:Object.assign({}, defaultPermissionCategory),
        rules: {
          name: [
            {required: true, message: '请输入类型名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ]
        }
      }
    },
    created() {
      if (this.isEdit) {
        getPermissionCategory(this.$route.query.id).then(response => {
          this.permissionCategory = response.data;
        });
      }else{
        this.permissionCategory = Object.assign({},defaultPermissionCategory);
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
                updatePermissionCategory(this.$route.query.id, this.permissionCategory).then(response => {
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
                createPermissionCategory(this.permissionCategory).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.permissionCategory = Object.assign({},defaultPermissionCategory);
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
        this.permissionCategory = Object.assign({},defaultPermissionCategory);
      }
    }
  }
</script>
<style>
</style>


