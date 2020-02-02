<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="typePrice" :rules="rules" ref="typePriceFrom" label-width="150px">

      <el-form-item label="小区名称：" prop="floorNum">
        <el-form-item :label="cname" readonly="readonly"></el-form-item>
      </el-form-item>

      <el-form-item label="名称：" prop="name">
        <el-input v-model="typePrice.name"></el-input>
      </el-form-item>
      <el-form-item label="单价：" prop="price">
        <el-input v-model="typePrice.price"></el-input>
      </el-form-item>
      <el-form-item label="单位：" prop="remark">
        <el-input v-model="typePrice.remark"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('typePriceFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('typePriceFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createTypePrice, getTypePrice, updateTypePrice} from '@/api/build/typePrice'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultTypePrice={
    name: ''
  };
  export default {
    name: 'TypePriceDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        typePrice:Object.assign({}, defaultTypePrice),
        cname:'',
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
      this.cname=this.$route.query.cname;
      if (this.isEdit) {
        getTypePrice(this.$route.query.id).then(response => {
          this.typePrice = response.data;
        });
      }else{
        this.typePrice = Object.assign({},defaultTypePrice);
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
              this.typePrice.communityId=this.$route.query.cid
              if (this.isEdit) {
                updateTypePrice(this.$route.query.id, this.typePrice).then(response => {
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
                createTypePrice(this.typePrice).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.typePrice = Object.assign({},defaultTypePrice);
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
        this.typePrice = Object.assign({},defaultTypePrice);
      }
    }
  }
</script>
<style>
</style>


