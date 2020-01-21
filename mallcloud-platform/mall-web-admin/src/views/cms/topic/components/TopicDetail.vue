<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="topic" :rules="rules" ref="topicFrom" label-width="150px">

      <el-form-item label="所属分类：" prop="categoryId">
        <el-select
          v-model="topic.categoryId"
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
      <el-form-item label="标题：" prop="name">
        <el-input v-model="topic.name"></el-input>
      </el-form-item>

      <el-form-item label="开始时间：" prop="startTime">
        <el-date-picker
          class="input-width"
          v-model="topic.startTime"
          value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          :picker-options="pickerOptions1"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间：" prop="endTime">
        <el-date-picker
          class="input-width"
          v-model="topic.endTime"
          value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss"
          type="datetime"
          :picker-options="pickerOptions1"
          placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="奖品名称：" prop="awardName">
        <el-input v-model="topic.awardName"></el-input>
      </el-form-item>
      <el-form-item label="参与方式：" prop="attendType">
        <el-input v-model="topic.attendType"></el-input>
      </el-form-item>
      <el-form-item label="内容：" prop="content">
        <el-tabs v-model="activeHtmlName" type="card">
          <el-tab-pane label="电脑端详情" name="pc">
            <tinymce :width="595" :height="300" v-model="topic.content"></tinymce>
          </el-tab-pane>
          <el-tab-pane label="移动端详情" name="mobile">
            <tinymce :width="595" :height="300" v-model="topic.content"></tinymce>
          </el-tab-pane>
        </el-tabs>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('topicFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('topicFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createTopic, getTopic, updateTopic} from '@/api/cms/topic'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {fetchList, deleteSubjectCategory} from '@/api/cms/topicCategory'
  import Tinymce from '@/components/Tinymce'
  const defaultTopic={
    name: ''
  };
  export default {
    name: 'TopicDetail',
    components:{SingleUpload,Tinymce},
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
        cateOptions:null,
        activeHtmlName: 'pc',
        topic:Object.assign({}, defaultTopic),
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
        getTopic(this.$route.query.id).then(response => {
          this.topic = response.data;
        });
      }else{
        this.topic = Object.assign({},defaultTopic);
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
              if (this.isEdit) {
                updateTopic(this.$route.query.id, this.topic).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createTopic(this.topic).then(response => {
                  this.$refs[formName].resetFields();
                  this.topic = Object.assign({},defaultTopic);
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
        this.topic = Object.assign({},defaultTopic);
      }
    }
  }
</script>
<style>
</style>


