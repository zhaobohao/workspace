<template> 
  <el-card class="form-container" shadow="never">
    <el-form  :model="omsCompanyAddress" :rules="rules" ref="OmsCompanyAddressFrom" label-width="150px">



            <el-form-item label="地址名称" prop="addressName">
              <el-input v-model="omsCompanyAddress.addressName" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="默认发货地址：0->否；1->是" prop="sendStatus">
              <el-input v-model="omsCompanyAddress.sendStatus" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="是否默认收货地址：0->否；1->是" prop="receiveStatus">
              <el-input v-model="omsCompanyAddress.receiveStatus" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="收发货人姓名" prop="name">
              <el-input v-model="omsCompanyAddress.name" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="收货人电话" prop="phone">
              <el-input v-model="omsCompanyAddress.phone" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="省/直辖市" prop="province">
              <el-input v-model="omsCompanyAddress.province" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="市" prop="city">
              <el-input v-model="omsCompanyAddress.city" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="区" prop="region">
              <el-input v-model="omsCompanyAddress.region" style="width: 370px;"/>
            </el-form-item>


            <el-form-item label="详细地址" prop="detailAddress">
              <el-input v-model="omsCompanyAddress.detailAddress" style="width: 370px;"/>
            </el-form-item>



      <el-form-item>
        <el-button type="primary" @click="onSubmit('OmsCompanyAddressFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('OmsCompanyAddressFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createOmsCompanyAddress, getOmsCompanyAddress, updateOmsCompanyAddress} from  '@/api/oms/omsCompanyAddress'
  import SingleUpload from '@/components/Upload/singleUpload'

  const defaultOmsCompanyAddress= {
    name: ''
  };
  export default {
    name: 'OmsCompanyAddressDetail',
    components: {SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
      omsCompanyAddress:
      Object.assign({},
    defaultOmsCompanyAddress),
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
        getOmsCompanyAddress(this.$route.query.id).then(response => {
          this.omsCompanyAddress = response.data;
      })
        ;
      } else {
        this.omsCompanyAddress = Object.assign({},
      defaultOmsCompanyAddress)
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
              updateOmsCompanyAddress(this.$route.query.id, this.omsCompanyAddress).then(response => {
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
              createOmsCompanyAddress(this.omsCompanyAddress).then(response => {
                if(response.code == 200
            )
              {
                this.$refs[formName].resetFields();
                this.omsCompanyAddress = Object.assign({},
              defaultOmsCompanyAddress)
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
        this.omsCompanyAddress = Object.assign({},
      defaultOmsCompanyAddress)
        ;
      }
    }
  }
</script>
<style>
</style>


