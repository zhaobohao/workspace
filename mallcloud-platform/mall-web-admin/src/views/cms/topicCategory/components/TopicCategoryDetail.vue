<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="topicCategory" :rules="rules" ref="topicCategoryFrom" label-width="150px">

      <el-form-item label="标题：" prop="name">
        <el-input v-model="topicCategory.name"></el-input>
      </el-form-item>
      <el-form-item label="分类图标：" prop="icon">
        <single-upload v-model="topicCategory.icon"></single-upload>
      </el-form-item>

      <el-form-item label="是否显示：">
        <el-radio-group v-model="topicCategory.showStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序：" prop="sort">
        <el-input v-model.number="topicCategory.sort"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('topicCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('topicCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createTopicCategory, getTopicCategory, updateTopicCategory} from '@/api/cms/topicCategory'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultTopicCategory={
    name: ''
  };
  export default {
    name: 'TopicCategoryDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        topicCategory:Object.assign({}, defaultTopicCategory),
        rules: {
          name: [
            {required: true, message: '请输入分类名称', trigger: 'blur'},
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
        getTopicCategory(this.$route.query.id).then(response => {
          this.topicCategory = response.data;
        });
      }else{
        this.topicCategory = Object.assign({},defaultTopicCategory);
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
                updateTopicCategory(this.$route.query.id, this.topicCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createTopicCategory(this.topicCategory).then(response => {
                  this.$refs[formName].resetFields();
                  this.topicCategory = Object.assign({},defaultTopicCategory);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
              this.$router.back();
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
        this.topicCategory = Object.assign({},defaultTopicCategory);
      }
    }
  }
</script>
<style>
</style>


