<template> 
  <el-card class="form-container" shadow="never">
    <el-form  :model="sysTest" :rules="rules" ref="SysTestFrom" label-width="150px">


            <el-form-item label="编号" prop="id">
              <el-input v-model="sysTest.id" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="名称" prop="name">
              <el-input v-model="sysTest.name" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="创建时间" prop="createTime">
              <el-input v-model="sysTest.createTime" style="width: 370px;"/>
            </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit('SysTestFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('SysTestFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createSysTest, getSysTest, updateSysTest} from  '@/api/sys/sysTest'
  import SingleUpload from '@/components/Upload/singleUpload'

  const defaultSysTest= {
    name: ''
  };
  export default {
    name: 'SysTestDetail',
    components: {SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
      sysTest:
      Object.assign({},
    defaultSysTest),
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
        getSysTest(this.$route.query.id).then(response => {
          this.sysTest = response.data;
      })
        ;
      } else {
        this.sysTest = Object.assign({},
      defaultSysTest)
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
              updateSysTest(this.$route.query.id, this.sysTest).then(response => {
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
              createSysTest(this.sysTest).then(response => {
                if(response.code == 200
            )
              {
                this.$refs[formName].resetFields();
                this.sysTest = Object.assign({},
              defaultSysTest)
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
        this.sysTest = Object.assign({},
      defaultSysTest)
        ;
      }
    }
  }
</script>
<style>
</style>


