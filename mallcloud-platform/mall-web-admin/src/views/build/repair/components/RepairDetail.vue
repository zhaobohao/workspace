<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="repair" :rules="rules" ref="repairFrom" label-width="150px">
      <el-form-item label="编号：" prop="id">
        <el-input v-model="repair.id"></el-input>
      </el-form-item>
      <el-form-item label="房屋编号：" prop="roomId">
        <el-input v-model="repair.roomId"></el-input>
      </el-form-item>
      <el-form-item label="创建时间：" prop="createTime">
        <el-input v-model="repair.createTime"></el-input>
      </el-form-item>
      <el-form-item label="解决时间：" prop="solveTime">
        <el-input v-model="repair.solveTime"></el-input>
      </el-form-item>
      <el-form-item label="报修原因：" prop="resons">
        <el-input v-model="repair.resons"></el-input>
      </el-form-item>
      <el-form-item label="报修者电话：" prop="phone">
        <el-input v-model="repair.phone"></el-input>
      </el-form-item>
      <el-form-item label="解决记录：" prop="solveResons">
        <el-input v-model="repair.solveResons"></el-input>
      </el-form-item>
      <el-form-item label="房屋具体位置：" prop="roomDesc">
        <el-input v-model="repair.roomDesc"></el-input>
      </el-form-item>
      <el-form-item label="价格：" prop="price">
        <el-input v-model="repair.price"></el-input>
      </el-form-item>
      <el-form-item label="1 申请 2 处理中 3 已处理：" prop="status">
        <el-input v-model="repair.status"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('repairFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('repairFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createRepair, getRepair, updateRepair} from '@/api/build/repair'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultRepair={
    name: ''
  };
  export default {
    name: 'RepairDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        repair:Object.assign({}, defaultRepair),
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
        getRepair(this.$route.query.id).then(response => {
          this.repair = response.data;
        });
      }else{
        this.repair = Object.assign({},defaultRepair);
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
                updateRepair(this.$route.query.id, this.repair).then(response => {
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
                createRepair(this.repair).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.repair = Object.assign({},defaultRepair);
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
        this.repair = Object.assign({},defaultRepair);
      }
    }
  }
</script>
<style>
</style>


