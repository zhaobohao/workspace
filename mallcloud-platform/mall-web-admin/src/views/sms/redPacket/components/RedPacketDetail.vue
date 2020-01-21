<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="redPacket" :rules="rules" ref="redPacketFrom" label-width="150px">

      <el-form-item label="红包类型：" prop="type">
        <el-radio-group v-model="redPacket.type">
          <el-radio :label="1">随机红包</el-radio>
          <el-radio :label="2">等额红包</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="红包金额：" prop="amount">
        <el-input v-model="redPacket.amount"></el-input>
      </el-form-item>
      <el-form-item label="红包总数：" prop="total">
        <el-input v-model="redPacket.total"></el-input>
      </el-form-item>
      <el-form-item label="单个红包的金额：" prop="unitAmount">
        <el-input v-model="redPacket.unitAmount"></el-input>
      </el-form-item>

      <el-form-item label="备注：" prop="note">
        <el-input v-model="redPacket.note"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('redPacketFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('redPacketFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createRedPacket, getRedPacket, updateRedPacket} from '@/api/sms/redPacket'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultRedPacket={
    name: ''
  };
  export default {
    name: 'RedPacketDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        redPacket:Object.assign({}, defaultRedPacket),
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
        getRedPacket(this.$route.query.id).then(response => {
          this.redPacket = response.data;
        });
      }else{
        this.redPacket = Object.assign({},defaultRedPacket);
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
                updateRedPacket(this.$route.query.id, this.redPacket).then(response => {
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
                createRedPacket(this.redPacket).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.redPacket = Object.assign({},defaultRedPacket);
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
        this.redPacket = Object.assign({},defaultRedPacket);
      }
    }
  }
</script>
<style>
</style>


