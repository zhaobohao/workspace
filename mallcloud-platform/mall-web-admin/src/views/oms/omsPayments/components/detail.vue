<template> 
  <el-card class="form-container" shadow="never">
    <el-form  :model="omsPayments" :rules="rules" ref="OmsPaymentsFrom" label-width="150px">



            <el-form-item label="支付类型编码" prop="code">
                <el-select
                        v-model="omsPayments.code"

                        placeholder="请选择计量单位">
                    <el-option
                            v-for="item in unitOptions"
                            :key="item.name"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>


            <el-form-item label="支付类型名称" prop="name">
              <el-input v-model="omsPayments.name" style="width: 370px;"/>
            </el-form-item>

        <el-form-item label="是否线上支付：">
            <el-radio-group v-model="omsPayments.isOnline">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="2">否</el-radio>
            </el-radio-group>
        </el-form-item>

            <el-form-item label="排序" prop="sort">
              <el-input v-model="omsPayments.sort" style="width: 370px;"/>
            </el-form-item>

            <el-form-item label="启用状态" prop="status">

                <el-radio-group v-model="omsPayments.status">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="2">否</el-radio>
                </el-radio-group>
            </el-form-item>

        <el-form-item label="支付参数" prop="params">
            <el-input
                    :autoSize="true"
                    v-model="omsPayments.params"
                    type="textarea"
                    placeholder="请输入参数"></el-input>
        </el-form-item>
        <el-form-item label="支付方式描述" prop="memo">
            <el-input
                    :autoSize="true"
                    v-model="omsPayments.memo"
                    type="textarea"
                    placeholder="请输入参数"></el-input>

        </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('OmsPaymentsFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('OmsPaymentsFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createOmsPayments, getOmsPayments, updateOmsPayments} from  '@/api/oms/omsPayments'
  import SingleUpload from '@/components/Upload/singleUpload'

  const defaultOmsPayments= {
    name: ''
  };
  export default {
    name: 'OmsPaymentsDetail',
    components: {SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
      omsPayments:
      Object.assign({},
    defaultOmsPayments),
          unitOptions:[{
              id: "wechatpay",
              name: "微信支付",
          },
              {
                  id: "alipay",
                  name: "支付宝",
              },
              {
                  id: "offline",
                  name: "线下支付",
              },

              {
                  id: "balancepay",
                  name: "余额支付",
              }],
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
        getOmsPayments(this.$route.query.id).then(response => {
          this.omsPayments = response.data;
      })
        ;
      } else {
        this.omsPayments = Object.assign({},
      defaultOmsPayments)
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
              updateOmsPayments(this.$route.query.id, this.omsPayments).then(response => {
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
              createOmsPayments(this.omsPayments).then(response => {
                if(response.code == 200
            )
              {
                this.$refs[formName].resetFields();
                this.omsPayments = Object.assign({},
              defaultOmsPayments)
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
        this.omsPayments = Object.assign({},
      defaultOmsPayments)
        ;
      }
    }
  }
</script>
<style>
</style>


