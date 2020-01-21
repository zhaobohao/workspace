<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="userRedPacket" :rules="rules" ref="userRedPacketFrom" label-width="150px">
      <el-form-item label="用户红包编号：" prop="id">
        <el-input v-model="userRedPacket.id"></el-input>
      </el-form-item>
      <el-form-item label="红包编号：" prop="redPacketId">
        <el-input v-model="userRedPacket.redPacketId"></el-input>
      </el-form-item>
      <el-form-item label="发红包用户的编号：" prop="adminId">
        <el-input v-model="userRedPacket.adminId"></el-input>
      </el-form-item>
      <el-form-item label="抢红包用户的编号：" prop="userId">
        <el-input v-model="userRedPacket.userId"></el-input>
      </el-form-item>
      <el-form-item label="抢到的红包金额：" prop="amount">
        <el-input v-model="userRedPacket.amount"></el-input>
      </el-form-item>
      <el-form-item label="抢红包时间：" prop="grabTime">
        <el-input v-model="userRedPacket.grabTime"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="note">
        <el-input v-model="userRedPacket.note"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('userRedPacketFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('userRedPacketFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createUserRedPacket, getUserRedPacket, updateUserRedPacket} from '@/api/sms/userRedPacket'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultUserRedPacket={
    name: ''
  };
  export default {
    name: 'UserRedPacketDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        userRedPacket:Object.assign({}, defaultUserRedPacket),
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
        getUserRedPacket(this.$route.query.id).then(response => {
          this.userRedPacket = response.data;
        });
      }else{
        this.userRedPacket = Object.assign({},defaultUserRedPacket);
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
                updateUserRedPacket(this.$route.query.id, this.userRedPacket).then(response => {
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
                createUserRedPacket(this.userRedPacket).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.userRedPacket = Object.assign({},defaultUserRedPacket);
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
        this.userRedPacket = Object.assign({},defaultUserRedPacket);
      }
    }
  }
</script>
<style>
</style>


