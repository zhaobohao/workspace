<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="group" :rules="rules" ref="groupFrom" label-width="150px">
      <el-form-item label="商品：" prop="goodsId">
        <el-select
                v-model="group.goodsId"
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

      <el-form-item label="拼团价格：" prop="groupPrice">
        <el-input v-model="group.groupPrice"></el-input>
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

      <el-form-item label="拼团总人数：" prop="maxPeople">
        <el-input v-model="group.maxPeople"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('groupFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('groupFrom')">重置</el-button>
      </el-form-item>
    </el-form>

  </el-card>
</template>
<script>
  import {createGroup, getGroup, updateGroup} from '@/api/build/group'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {
    fetchList
  } from '@/api/product'
  import {formatDate} from '@/utils/date';
  const defaultGroup={
    name: ''
  };
  export default {
    name: 'GroupDetail',
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
        group:Object.assign({}, defaultGroup),
        goodsOptions:null,
        goodsName:'',
        originPrice:'',
        rules: {
          groupPrice: [
            {required: true, message: '请输入拼团价格', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          maxPeople: [
            {required: true, message: '请输入总人数', trigger: 'blur'}
          ],
          startTime: [
            {required: true, message: '请输入开始时间', trigger: 'blur'}
          ],
          endTime: [
            {required: true, message: '请输入结束时间', trigger: 'blur'}
          ],
        }
      }
    },
    created() {
      this.getGoodsList();
      if (this.isEdit) {
        getGroup(this.$route.query.id).then(response => {
          this.group = response.data;
        });
      }else{
        this.group = Object.assign({},defaultGroup);
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
                updateGroup(this.$route.query.id, this.group).then(response => {
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
                createGroup(this.group).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.group = Object.assign({},defaultGroup);
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
        this.group = Object.assign({},defaultGroup);
      }
    }
  }
</script>
<style>
</style>


