<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="area" :rules="rules" ref="areaFrom" label-width="150px">
      <el-form-item label="：" prop="id">
        <el-input v-model="area.id"></el-input>
      </el-form-item>
      <el-form-item label="：" prop="pid">
        <el-input v-model="area.pid"></el-input>
      </el-form-item>
      <el-form-item label="层级：" prop="deep">
        <el-input v-model="area.deep"></el-input>
      </el-form-item>
      <el-form-item label="名称：" prop="name">
        <el-input v-model="area.name"></el-input>
      </el-form-item>
      <el-form-item label="拼音前缀：" prop="pinyinPrefix">
        <el-input v-model="area.pinyinPrefix"></el-input>
      </el-form-item>
      <el-form-item label="拼音：" prop="pinyin">
        <el-input v-model="area.pinyin"></el-input>
      </el-form-item>
      <el-form-item label="备注名：" prop="extId">
        <el-input v-model="area.extId"></el-input>
      </el-form-item>
      <el-form-item label="备注名：" prop="extName">
        <el-input v-model="area.extName"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('areaFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('areaFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createArea, getArea, updateArea} from '@/api/area'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultArea={
    name: ''
  };
  export default {
    name: 'AreaDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        area:Object.assign({}, defaultArea),
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
        getArea(this.$route.query.id).then(response => {
          this.area = response.data;
        });
      }else{
        this.area = Object.assign({},defaultArea);
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
                updateArea(this.$route.query.id, this.area).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createArea(this.area).then(response => {
                  this.$refs[formName].resetFields();
                  this.area = Object.assign({},defaultArea);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration:1000
                  });
              this.$router.back();
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
        this.area = Object.assign({},defaultArea);
      }
    }
  }
</script>
<style>
</style>


