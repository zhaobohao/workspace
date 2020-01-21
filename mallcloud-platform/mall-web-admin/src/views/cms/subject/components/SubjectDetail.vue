<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="subject" :rules="rules" ref="subjectFrom" label-width="150px">

      <el-form-item label="分类：" prop="categoryId">
        <el-select
          v-model="subject.categoryId"
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
        <el-input v-model="subject.title"></el-input>
      </el-form-item>
      <el-form-item label="专题主图：" prop="pic">
        <single-upload v-model="subject.pic"></single-upload>
      </el-form-item>

      <el-form-item label="推荐：" prop="recommendStatus">
        <el-radio-group v-model="subject.recommendStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>


      <el-form-item label="画册：">
        <multi-upload v-model="selectProductPics"></multi-upload>
      </el-form-item>


      <el-form-item label="是否显示：">
        <el-radio-group v-model="subject.showStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="描述：" prop="description">
        <el-input
          :autoSize="true"
          v-model="subject.description"
          type="textarea"
          placeholder="请输入描述"></el-input>
      </el-form-item>

      <el-form-item label="内容：" prop="content">
        <el-tabs v-model="activeHtmlName" type="card">
          <el-tab-pane label="电脑端详情" name="pc">
            <tinymce :width="595" :height="300" v-model="subject.content"></tinymce>
          </el-tab-pane>
          <el-tab-pane label="移动端详情" name="mobile">
            <tinymce :width="595" :height="300" v-model="subject.content"></tinymce>
          </el-tab-pane>
        </el-tabs>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('subjectFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('subjectFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createSubject, getSubject, updateSubject} from '@/api/cms/subject'
  import {fetchList, deleteSubjectCategory} from '@/api/cms/subjectCategory'
  import SingleUpload from '@/components/Upload/singleUpload'
  import Tinymce from '@/components/Tinymce'
  import MultiUpload from '@/components/Upload/multiUpload'
  const defaultSubject={
    name: ''
  };
  export default {
    name: 'SubjectDetail',
    components:{SingleUpload, MultiUpload, Tinymce},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        subject:Object.assign({}, defaultSubject),
        categoryName:'',
          albumPics:null,
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
    computed: {

      //商品的主图和画册图片
      selectProductPics:{
        get:function () {
          let pics=[];
          if(this.albumPics===undefined||this.albumPics==null||this.albumPics===''){
            return pics;
          }
          let albumPics = this.albumPics.split(',');
          for(let i=0;i<albumPics.length;i++){
            pics.push(albumPics[i]);
          }
          return pics;
        },
        set:function (newValue) {
          if (newValue == null || newValue.length === 0) {
            this.albumPics = null;
          } else {
            this.albumPics = '';
            if (newValue.length > 0) {
              for (let i = 0; i < newValue.length; i++) {
                this.albumPics += newValue[i];
                if (i !== newValue.length - 1) {
                  this.albumPics += ',';
                }
              }
            }
          }
        }
      }
    },
    created() {
      if (this.isEdit) {
        getSubject(this.$route.query.id).then(response => {
          this.subject = response.data;
            this.albumPics = response.data.albumPics;

        });
      }else{
        this.subject = Object.assign({},defaultSubject);
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
              this.subject.categoryName=this.categoryName;
              this.subject.albumPics=this.albumPics;
                console.log(this.albumPics);
              console.log(this.subject.albumPics);
              if (this.isEdit) {
                updateSubject(this.$route.query.id, this.subject).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createSubject(this.subject).then(response => {
                  this.$refs[formName].resetFields();
                  this.subject = Object.assign({},defaultSubject);
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
        this.subject = Object.assign({},defaultSubject);
      },
      handleBrandChange(val) {

      }
    }
  }
</script>
<style>
</style>


