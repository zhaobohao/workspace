<template> 
  <el-card class="form-container" shadow="never">
    <el-form  :model="smsDiypageTemplateCategory" :rules="rules" ref="SmsDiypageTemplateCategoryFrom" label-width="150px">
            <el-form-item label="分类名" prop="name">
              <el-input v-model="smsDiypageTemplateCategory.name" style="width: 370px;"/>
            </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('SmsDiypageTemplateCategoryFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('SmsDiypageTemplateCategoryFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createSmsDiypageTemplateCategory, getSmsDiypageTemplateCategory, updateSmsDiypageTemplateCategory} from  '@/api/sms/smsDiypageTemplateCategory'
  import SingleUpload from '@/components/Upload/singleUpload'

  const defaultSmsDiypageTemplateCategory= {
    name: ''
  };
  export default {
    name: 'SmsDiypageTemplateCategoryDetail',
    components: {SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
      smsDiypageTemplateCategory:
      Object.assign({},
    defaultSmsDiypageTemplateCategory),
      rules: {
        name: [
          {required: true, message: '请输入品牌名称', trigger: 'blur'},
          {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
        ],
                logo
      :
        [
          {required: true, message: '请输入品牌logo', trigger: 'blur'}
        ],
                sort
      :
        [
          {type: 'number', message: '排序必须为数字'}
        ],
      }
    }
    },
    created() {
      if (this.isEdit) {
        getSmsDiypageTemplateCategory(this.$route.query.id).then(response => {
          this.smsDiypageTemplateCategory = response.data;
      })
        ;
      } else {
        this.smsDiypageTemplateCategory = Object.assign({},
      defaultSmsDiypageTemplateCategory)
        ;
      }
    },
    methods: {
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if(valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              if(this.isEdit
          )
            {
              updateSmsDiypageTemplateCategory(this.$route.query.id, this.smsDiypageTemplateCategory).then(response => {
                if(response.code == 200
            )
              {
                this.$refs[formName].resetFields();
                this.$message({
                  message: '修改成功',
                  type: 'success',
                  duration: 1000
                });
                this.$router.back();
              }
            else
              {
                this.$message({
                  message: response.msg,
                  type: 'error',
                  duration: 1000
                });
              }

            })
              ;
            }
          else
            {
              createSmsDiypageTemplateCategory(this.smsDiypageTemplateCategory).then(response => {
                if(response.code == 200
            )
              {
                this.$refs[formName].resetFields();
                this.smsDiypageTemplateCategory = Object.assign({},
              defaultSmsDiypageTemplateCategory)
                ;
                this.$message({
                  message: '提交成功',
                  type: 'success',
                  duration: 1000
                });
                this.$router.back();
              }
            else
              {
                this.$message({
                  message: response.msg,
                  type: 'error',
                  duration: 1000
                });
              }

            })
              ;
            }
          })
            ;

          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration: 1000
            });
        return false;
      }
      })
        ;
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.smsDiypageTemplateCategory = Object.assign({},
      defaultSmsDiypageTemplateCategory)
        ;
      }
    }
  }
</script>
<style>
</style>


