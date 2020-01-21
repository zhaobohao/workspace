<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="orderSetting"
             ref="orderSettingForm"

             label-width="150px">
      <el-form-item label="APPID：" prop="appid">
        <el-input v-model="orderSetting.appid" class="input-width">
        </el-input>
      </el-form-item>
      <el-form-item label="APPSECRET：" prop="appsecret">
        <el-input v-model="orderSetting.appsecret" class="input-width">
        </el-input>
      </el-form-item>
      <el-form-item label="MCHID：" prop="mchid">
        <el-input v-model="orderSetting.mchid" class="input-width">
        </el-input>
        <span class="note-margin">商户id</span>
      </el-form-item>
      <el-form-item label="paySignKey：" prop="paySignKey">
        <el-input v-model="orderSetting.paySignKey" class="input-width">
        </el-input>
        <span class="note-margin"></span>
      </el-form-item>
      <el-form-item label="notifyurl：" prop="notifyurl">
        <el-input v-model="orderSetting.notifyurl" class="input-width">
        </el-input>
        <span class="note-margin">支付回调地址</span>
      </el-form-item>
            <el-form-item label="templateid1：" prop="templateid1">
              <el-input v-model="orderSetting.templateid1" class="input-width">
              </el-input>
              <span class="note-margin">下单通知模版</span>
            </el-form-item>
<el-form-item label="templateid2：" prop="templateid2">
              <el-input v-model="orderSetting.templateid2" class="input-width">
              </el-input>
              <span class="note-margin">支付成功模版</span>
            </el-form-item>
<el-form-item label="templateid3：" prop="templateid3">
              <el-input v-model="orderSetting.templateid3" class="input-width">
              </el-input>
              <span class="note-margin">取消订单模版</span>
            </el-form-item>
<el-form-item label="templateid4：" prop="templateid4">
              <el-input v-model="orderSetting.templateid4" class="input-width">
              </el-input>
              <span class="note-margin">发货通知模版</span>
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
  import {getOrderSetting,updateOrderSetting} from '@/api/ums/appletSetting';
  const defaultOrderSetting = {
    id: null,
    appid: 0,
    appsecret: 0,
    mchid: 0,
    paySignKey: 0,
    notifyurl:0,
    templateid1:0,
    templateid2:0,
    templateid3:0,
    templateid4:0
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
          console.log(this.orderSetting);
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


