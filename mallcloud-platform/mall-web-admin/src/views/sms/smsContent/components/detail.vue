<template> 
  <el-card class="form-container" shadow="never">
    <el-form  :model="smsContent" :rules="rules" ref="SmsContentFrom" label-width="150px">


            <el-form-item label="内容标题" prop="name">
              <el-input v-model="smsContent.name" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="消息类型" prop="type">
              <el-input v-model="smsContent.type" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="标签说明" prop="meno">
              <el-input v-model="smsContent.meno" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="短信模版CODE" prop="code">
              <el-input v-model="smsContent.code" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="内容模版" prop="remark">
              <el-input v-model="smsContent.remark" style="width: 370px;"/>
            </el-form-item>




      <el-form-item>
        <el-button type="primary" @click="onSubmit('SmsContentFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('SmsContentFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createSmsContent, getSmsContent, updateSmsContent} from  '@/api/sms/smsContent'
  import SingleUpload from '@/components/Upload/singleUpload'

  const defaultSmsContent= {
    name: ''
  };
  export default {
    name: 'SmsContentDetail',
    components: {SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
      smsContent:
      Object.assign({},
    defaultSmsContent),
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
        getSmsContent(this.$route.query.id).then(response => {
          this.smsContent = response.data;
      })
        ;
      } else {
        this.smsContent = Object.assign({},
      defaultSmsContent)
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
              updateSmsContent(this.$route.query.id, this.smsContent).then(response => {
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
              createSmsContent(this.smsContent).then(response => {
                if(response.code == 200
            )
              {
                this.$refs[formName].resetFields();
                this.smsContent = Object.assign({},
              defaultSmsContent)
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
        this.smsContent = Object.assign({},
      defaultSmsContent)
        ;
      }
    }
  }
</script>
<style>
</style>


