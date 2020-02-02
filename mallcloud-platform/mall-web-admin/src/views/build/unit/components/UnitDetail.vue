<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="unit" :rules="rules" ref="unitFrom" label-width="150px">

      <el-form-item label="单元编号：" prop="unitNum">
        <el-input v-model="unit.unitNum"></el-input>
      </el-form-item>

      <el-form-item label="备注：" prop="remark">
        <el-input v-model="unit.remark"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('unitFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('unitFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createUnit, getUnit, updateUnit} from '@/api/build/unit'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultUnit={
    name: ''
  };
  export default {
    name: 'UnitDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        unit:Object.assign({}, defaultUnit),
        rules: {
          unitNum: [
            {required: true, message: '请输入单元编号', trigger: 'blur'},
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
        getUnit(this.$route.query.id).then(response => {
          this.unit = response.data;
        });
      }else{
        this.unit = Object.assign({},defaultUnit);
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
              this.unit.floorId=this.$route.query.cid
              if (this.isEdit) {
                updateUnit(this.$route.query.id, this.unit).then(response => {
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
                createUnit(this.unit).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.unit = Object.assign({},defaultUnit);
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
        this.unit = Object.assign({},defaultUnit);
        this.unit.floorId = Number(this.$route.query.cid);
      }
    }
  }
</script>
<style>
</style>


