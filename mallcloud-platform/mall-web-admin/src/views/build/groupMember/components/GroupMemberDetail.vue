<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="groupMember" :rules="rules" ref="groupMemberFrom" label-width="150px">
      <el-form-item label="：" prop="id">
        <el-input v-model="groupMember.id"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="groupId">
        <el-input v-model="groupMember.groupId"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="memberId">
        <el-input v-model="groupMember.memberId"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="createTime">
        <el-input v-model="groupMember.createTime"></el-input>
      </el-form-item>
      <el-form-item label="发起人：" prop="applyId">
        <el-input v-model="groupMember.applyId"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="name">
        <el-input v-model="groupMember.name"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="goodsId">
        <el-input v-model="groupMember.goodsId"></el-input>
      </el-form-item>
      <el-form-item label="状态：" prop="status">
        <el-input v-model="groupMember.status"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="orderId">
        <el-input v-model="groupMember.orderId"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="exipreTime">
        <el-input v-model="groupMember.exipreTime"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('groupMemberFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('groupMemberFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createGroupMember, getGroupMember, updateGroupMember} from '@/api/build/groupMember'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultGroupMember={
    name: ''
  };
  export default {
    name: 'GroupMemberDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        groupMember:Object.assign({}, defaultGroupMember),
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
        getGroupMember(this.$route.query.id).then(response => {
          this.groupMember = response.data;
        });
      }else{
        this.groupMember = Object.assign({},defaultGroupMember);
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
                updateGroupMember(this.$route.query.id, this.groupMember).then(response => {
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
                createGroupMember(this.groupMember).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.groupMember = Object.assign({},defaultGroupMember);
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
        this.groupMember = Object.assign({},defaultGroupMember);
      }
    }
  }
</script>
<style>
</style>


