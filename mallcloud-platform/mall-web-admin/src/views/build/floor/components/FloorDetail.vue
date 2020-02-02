<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="floor" :rules="rules" ref="floorFrom" label-width="150px">
      <!--<el-form-item label="小区：" prop="categoryId" c>
        <el-select
                v-model="floor.communityId"
                @change="handlecateChange"
                placeholder="请选择小区">
          <el-option
                  v-for="item in cateOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item label="小区名称：" prop="floorNum">
        <el-form-item :label="cname" readonly="readonly"></el-form-item>
      </el-form-item>
      <el-form-item label="楼编号：" prop="floorNum">
        <el-input v-model="floor.floorNum"></el-input>
      </el-form-item>
      <el-form-item label="小区楼名称：" prop="name">
        <el-input v-model="floor.name"></el-input>
      </el-form-item>
      <el-form-item label="楼层：" prop="layerCount">
        <el-input v-model="floor.layerCount"></el-input>
      </el-form-item>

      <el-form-item label="是否有电梯：">
        <el-radio-group v-model="floor.lift">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注：" prop="remark">
        <el-input v-model="floor.remark"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit('floorFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('floorFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createFloor, getFloor, updateFloor} from '@/api/build/floor'
  import {fetchList, deleteSubjectCategory} from '@/api/build/community'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultFloor={
    name: ''
  };
  export default {
    name: 'FloorDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        floor:Object.assign({}, defaultFloor),
        cateOptions:null,
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
        getFloor(this.$route.query.id).then(response => {
          this.floor = response.data;
        });
      }else{
        this.floor = Object.assign({},defaultFloor);
      }
     // this.getCateList();
    },
    methods: {
      handlecateChange1(val) {
        let brandName = '';
        for (let i = 0; i < this.cateOptions.length; i++) {
          if (this.cateOptions[i].value === val) {
            brandName = this.cateOptions[i].label;
            break;
          }
        }

        this.categoryName = brandName;
      },
      getCateList() {
        fetchList({pageNum: 1, pageSize: 100}).then(response => {
          this.cateOptions = [];
          let brandList = response.data.records;
          for (let i = 0; i < brandList.length; i++) {
            this.cateOptions.push({label: brandList[i].name, value: brandList[i].id});
          }
        });
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.floor.communityId=this.$route.query.cid
              if (this.isEdit) {
                updateFloor(this.$route.query.id, this.floor).then(response => {
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
                createFloor(this.floor).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.floor = Object.assign({},defaultFloor);
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
        this.floor = Object.assign({},defaultFloor);
        this.floor.communityId = Number(this.$route.query.cid);
      }
    }
  }
</script>
<style>
</style>


