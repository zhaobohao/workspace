<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="notice" :rules="rules" ref="noticeFrom" label-width="150px">

      <el-form-item label="小区名称：" prop="floorNum">
        <el-form-item :label="cname" readonly="readonly"></el-form-item>
      </el-form-item>
      <el-form-item label="公告标题：" prop="title">
        <el-input v-model="notice.title"></el-input>
      </el-form-item>
      <el-form-item label="公告内容：" prop="content">
        <el-input v-model="notice.content"></el-input>
      </el-form-item>

      <el-form-item label="排序：" prop="sort">
        <el-input v-model="notice.sort"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('noticeFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('noticeFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createNotice, getNotice, updateNotice} from '@/api/build/notice'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultNotice={
    name: ''
  };
  export default {
    name: 'NoticeDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        notice:Object.assign({}, defaultNotice),
        cname:'',
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
      this.cname=this.$route.query.cname;
      if (this.isEdit) {
        getNotice(this.$route.query.id).then(response => {
          this.notice = response.data;
        });
      }else{
        this.notice = Object.assign({},defaultNotice);
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
              this.notice.communityId=this.$route.query.cid
              if (this.isEdit) {
                updateNotice(this.$route.query.id, this.notice).then(response => {
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
                createNotice(this.notice).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.notice = Object.assign({},defaultNotice);
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
        this.notice = Object.assign({},defaultNotice);
      }
    }
  }
</script>
<style>
</style>


