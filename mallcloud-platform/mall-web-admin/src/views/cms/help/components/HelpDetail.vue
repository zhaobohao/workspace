<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="help" :rules="rules" ref="helpFrom" label-width="150px">

      <el-form-item label="类别：" prop="categoryId">
        <el-select
          v-model="help.categoryId"
          @change="handlecateChange"
          placeholder="请选择分类">
          <el-option
            v-for="item in cateOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="标题：" prop="title">
        <el-input v-model="help.title"></el-input>
      </el-form-item>
      <el-form-item label="图片：" prop="icon">
        <single-upload v-model="help.icon"></single-upload>
      </el-form-item>
      <el-form-item label="是否显示：">
        <el-radio-group v-model="help.showStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="内容：" prop="content">
        <el-tabs v-model="activeHtmlName" type="card">
          <el-tab-pane label="电脑端详情" name="pc">
            <tinymce :width="595" :height="300" v-model="help.content"></tinymce>
          </el-tab-pane>
          <el-tab-pane label="移动端详情" name="mobile">
            <tinymce :width="595" :height="300" v-model="help.content"></tinymce>
          </el-tab-pane>
        </el-tabs>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('helpFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('helpFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createHelp, getHelp, updateHelp} from '@/api/cms/help'
  import {fetchList, deleteSubjectCategory} from '@/api/cms/helpCategory'
  import SingleUpload from '@/components/Upload/singleUpload'
  import Tinymce from '@/components/Tinymce'
  import MultiUpload from '@/components/Upload/multiUpload'
  const defaultHelp={
    name: ''
  };
  export default {
    name: 'HelpDetail',
   components:{SingleUpload, MultiUpload, Tinymce},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        help:Object.assign({}, defaultHelp),
        categoryName:'',
        album_pics:null,
        cateOptions:null,
        activeHtmlName: 'pc',
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
        getHelp(this.$route.query.id).then(response => {
          this.help = response.data;
      });
      }else{
        this.help = Object.assign({},defaultHelp);
      }
      this.getCateList();
    },
    methods: {
      getCateList() {
        fetchList({pageNum: 1, pageSize: 100}).then(response => {
          this.cateOptions = [];
        let brandList = response.data.records;
        for (let i = 0; i < brandList.length; i++) {
          this.cateOptions.push({label: brandList[i].name, value: brandList[i].id});
        }
      });
      },
      handlecateChange(val) {
              let brandName = '';
              for (let i = 0; i < this.cateOptions.length; i++) {
                if (this.cateOptions[i].value === val) {
                  brandName = this.cateOptions[i].label;
                  break;
                }
              }

              this.categoryName = brandName;
            },

      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.help.categoryName=this.categoryName
              if (this.isEdit) {
              updateHelp(this.$route.query.id, this.help).then(response => {
                this.$refs[formName].resetFields();
              this.$message({
                message: '修改成功',
                type: 'success',
                duration:1000
              });
              this.$router.back();
            });
            } else {
              createHelp(this.help).then(response => {
                this.$refs[formName].resetFields();
              this.help = Object.assign({},defaultHelp);
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
        this.help = Object.assign({},defaultHelp);
      }
    }
  }
</script>
<style>
</style>


