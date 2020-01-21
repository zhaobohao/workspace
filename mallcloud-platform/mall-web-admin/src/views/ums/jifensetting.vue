<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="orderSetting"
             ref="orderSettingForm"

             label-width="150px">

            <el-form-item label="一元抵扣的积分：" prop="deductionPerAmount">
              <el-input v-model="orderSetting.deductionPerAmount" class="input-width">
              </el-input>
              <span class="note-margin">每一元需要抵扣的积分数量</span>
            </el-form-item>
<el-form-item label="每笔订单最高抵百分比：" prop="maxPercentPerOrder">
              <el-input v-model="orderSetting.maxPercentPerOrder" class="input-width">
              </el-input>
              <span class="note-margin">每笔订单最高抵用百分比</span>
            </el-form-item>
            <el-form-item label="1元兑换的积分：" prop="useUnit">
              <el-input v-model="orderSetting.useUnit" class="input-width">
              </el-input>
              <span class="note-margin">充值的时候，1元兑换的积分</span>
            </el-form-item>
            <el-form-item label="是否可以和优惠券同用：" prop="couponStatus">

                <el-radio-group v-model="orderSetting.couponStatus">
                    <el-radio :label="1">可以</el-radio>
                    <el-radio :label="0">不可以</el-radio>
                </el-radio-group>

            </el-form-item>

        <el-form-item label="登录送积分：" prop="login">
            <el-input v-model="orderSetting.login" class="input-width">
            </el-input>
            <span class="note-margin">登录送积分</span>
        </el-form-item>
        <el-form-item label="注册送积分：" prop="register">
            <el-input v-model="orderSetting.register" class="input-width">
            </el-input>
            <span class="note-margin">注册送积分</span>
        </el-form-item>
        <el-form-item label="签到送积分：" prop="sign">
            <el-input v-model="orderSetting.sign" class="input-width">
            </el-input>
            <span class="note-margin">签到送积分</span>
        </el-form-item>
        <el-form-item label="下单送积分：" prop="orders">
            <el-input v-model="orderSetting.orders" class="input-width">
            </el-input>
            <span class="note-margin">下单 送积分比例</span>
        </el-form-item>

      <el-form-item>
        <el-button
          @click="confirm('orderSettingForm')"
          type="primary">提交</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {getOrderSetting,updateOrderSetting} from '@/api/ums/jifenSetting';
  const defaultOrderSetting = {
    id: null,
    appid: 0,
    appsecret: 0,
    mchid: 0,
    paySignKey: 0,
    notifyUrl:0,
    templateId1:0,
    templateId2:0,
    templateId3:0,
    templateId4:0
  };
  const checkTime = (rule, value, callback) => {

  };
  export default {
    name: 'orderSetting',
    data() {
      return {
        orderSetting: Object.assign({}, defaultOrderSetting)

      }
    },
    created(){
      this.getDetail();
    },
    methods:{
      confirm(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否要提交修改?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              updateOrderSetting(1,this.orderSetting).then(response=>{
                this.$message({
                  type: 'success',
                  message: '提交成功!',
                  duration:1000
                });
              })
            });
          } else {
            this.$message({
              message: '提交参数不合法',
              type: 'warning'
            });
            return false;
          }
        });
      },
      getDetail(){
        getOrderSetting(1).then(response=>{
          this.orderSetting=response.data;
        })
      }
    }
  }
</script>
<style scoped>
  .input-width {
    width: 50%;
  }

  .note-margin {
    margin-left: 15px;
  }
</style>


