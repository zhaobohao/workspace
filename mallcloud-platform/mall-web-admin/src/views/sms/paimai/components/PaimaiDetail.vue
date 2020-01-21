<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="group" :rules="rules" ref="groupFrom" label-width="150px">

      <el-form-item label="竞拍名称：" prop="name">
        <el-input v-model="group.name"></el-input>
      </el-form-item>
      <el-form-item label="商品：" prop="goodId">
        <el-select
          v-model="group.goodId"
          @change="handleBrandChange"
          placeholder="请选择商品">
          <el-option
            v-for="item in goodsOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="开始时间：" prop="startTime">
        <el-date-picker
          class="input-width"
          v-model="group.startTime"
          value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          :picker-options="pickerOptions1"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间：" prop="endTime">
        <el-date-picker
          class="input-width"
          v-model="group.endTime"
          value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          :picker-options="pickerOptions1"
          placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否展示：">
        <el-radio-group v-model="group.status">
          <el-radio :label="0">展示</el-radio>
          <el-radio :label="1">不展示</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="起拍价：" prop="num">
        <el-input v-model="group.price"></el-input>
      </el-form-item>
      <el-form-item label="加价的倍数：" prop="spellingNumber">
        <el-input v-model="group.addprice"></el-input>
      </el-form-item>
      <el-form-item label="保证金：" prop="spellingNumber">
        <el-input v-model="group.proprice"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('groupFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('groupFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createPaimai, getPaimai, updatePaimai} from '@/api/sms/paimai'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {
    fetchList
  } from '@/api/product'
  const defaultPaimai={
    name: ''
  };
  export default {
    name: 'PaimaiDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        //日期选择器配置
        pickerOptions1: {
          disabledDate(time) {
            return time.getTime() < Date.now();
          }
        },
        group:Object.assign({}, defaultPaimai),
        goodsOptions:null,
        goodsName:'',
        originPrice:'',
        rules: {
          name: [
            {required: true, message: '请输入竞拍名称', trigger: 'blur'},
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
      this.getGoodsList();
      if (this.isEdit) {
        getPaimai(this.$route.query.id).then(response => {
          this.group = response.data;
        });
      }else{
        this.group = Object.assign({},defaultPaimai);
      }
    },
    methods: {
      getGoodsList() {
        fetchList({pageNum: 1, pageSize: 1000}).then(response => {
          this.goodsOptions = [];
        let brandList = response.data.records;
        for (let i = 0; i < brandList.length; i++) {
          this.goodsOptions.push({label: brandList[i].price+','+brandList[i].name, value: brandList[i].id});
        }
      });
      },
      handleBrandChange(val) {
        let brandName = '';
        let originPrice = '';
        for (let i = 0; i < this.goodsOptions.length; i++) {
          if (this.goodsOptions[i].value === val) {
            originPrice = this.goodsOptions[i].label.split(",")[0];
            brandName = this.goodsOptions[i].label.split(",")[1];
            break;
          }
        }
        this.originPrice =originPrice;
        this.goodsName = brandName;
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.group.goodsName=this.goodsName
            this.group.originPrice=this.originPrice
              if (this.isEdit) {
                updatePaimai(this.$route.query.id, this.group).then(response => {
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
                createPaimai(this.group).then(response => {

                  if(response.code==200){
                  this.$refs[formName].resetFields();
                  this.group = Object.assign({},defaultPaimai);
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
        this.group = Object.assign({},defaultPaimai);
      }
    }
  }
</script>
<style>
</style>


