<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="gifts" :rules="rules" ref="giftsFrom" label-width="150px">

      <el-form-item label="类别：" prop="categoryId">
        <el-select
          v-model="gifts.categoryId"
          placeholder="请选择分类">
          <el-option
            v-for="item in cateOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="名称：" prop="title">
        <el-input v-model="gifts.title"></el-input>
      </el-form-item>

      <el-form-item label="价格：" prop="price">
        <el-input v-model="gifts.price"></el-input>
      </el-form-item>
       <el-form-item label="库存：" prop="stock">
              <el-input v-model="gifts.stock"></el-input>
            </el-form-item>
       <el-form-item label="类型：">
              <el-radio-group v-model="gifts.type">
                <el-radio :label="1">赠品</el-radio>
                <el-radio :label="2">抽奖赠品</el-radio>
                  <el-radio :label="3">积分赠品</el-radio>
              </el-radio-group>
        </el-form-item>
      <el-form-item label="图片：" prop="icon">
        <single-upload v-model="gifts.icon"></single-upload>
      </el-form-item>
      <el-form-item label="是否显示：">
        <el-radio-group v-model="gifts.showStatus">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="内容：" prop="content">
        <el-tabs v-model="activeHtmlName" type="card">
          <el-tab-pane label="电脑端详情" name="pc">
            <tinymce :width="595" :height="300" v-model="gifts.content"></tinymce>
          </el-tab-pane>
          <el-tab-pane label="移动端详情" name="mobile">
            <tinymce :width="595" :height="300" v-model="gifts.content"></tinymce>
          </el-tab-pane>
        </el-tabs>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('giftsFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('giftsFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createGifts, getGifts, updateGifts} from '@/api/pms/Gifts'
  import {fetchList} from '@/api/pms/GiftsCategory'
  import SingleUpload from '@/components/Upload/singleUpload'
  import Tinymce from '@/components/Tinymce'
  import MultiUpload from '@/components/Upload/multiUpload'
  const defaultGifts={
    name: ''
  };
  export default {
    name: 'GiftsDetail',
   components:{SingleUpload, MultiUpload, Tinymce},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        gifts:Object.assign({}, defaultGifts),
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
        getGifts(this.$route.query.id).then(response => {
          this.gifts = response.data;
      });
      }else{
        this.gifts = Object.assign({},defaultGifts);
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

      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.gifts.categoryName=this.categoryName
              if (this.isEdit) {
              updateGifts(this.$route.query.id, this.gifts).then(response => {
                this.$refs[formName].resetFields();
              this.$message({
                message: '修改成功',
                type: 'success',
                duration:1000
              });
              this.$router.back();
            });
            } else {
              createGifts(this.gifts).then(response => {
                this.$refs[formName].resetFields();
              this.gifts = Object.assign({},defaultGifts);
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
        this.gifts = Object.assign({},defaultGifts);
      }
    }
  }
</script>
<style>
</style>


