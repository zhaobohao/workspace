<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="adv"
             :rules="rules"
             ref="advFrom"
             label-width="150px"
             size="small">
      <el-form-item label="小区名称：" prop="floorNum">
        <el-form-item :label="cname" readonly="readonly"></el-form-item>
      </el-form-item>

      <el-form-item label="广告名称：" prop="name">
        <el-input v-model="adv.name" class="input-width"></el-input>
      </el-form-item>

      <el-form-item label="开始时间：" prop="startTime">
        <el-date-picker
                type="datetime"
                placeholder="选择日期"
                v-model="adv.startTime"></el-date-picker>
      </el-form-item>
      <el-form-item label="到期时间：" prop="endTime">
        <el-date-picker
                type="datetime"
                placeholder="选择日期"
                v-model="adv.endTime"></el-date-picker>
      </el-form-item>
      <el-form-item label="上线/下线：">
        <el-radio-group v-model="adv.status">
          <el-radio :label="0">下线</el-radio>
          <el-radio :label="1">上线</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="广告图片：">
        <single-upload v-model="adv.pic"></single-upload>
      </el-form-item>
      <el-form-item label="排序：">
        <el-input v-model="adv.sort" class="input-width"></el-input>
      </el-form-item>

      <el-form-item label="广告备注：">
        <el-input
                class="input-width"
                type="textarea"
                :rows="5"
                placeholder="请输入内容"
                v-model="adv.note">
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('advFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('advFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createAdv, getAdv, updateAdv} from '@/api/build/adv'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultAdv={
    name: ''
  };
  export default {
    name: 'AdvDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        adv:Object.assign({}, defaultAdv),
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
        getAdv(this.$route.query.id).then(response => {
          this.adv = response.data;
        });
      }else{
        this.adv = Object.assign({},defaultAdv);
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
              this.adv.communityId=this.$route.query.cid
              if (this.isEdit) {
                updateAdv(this.$route.query.id, this.adv).then(response => {
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
                createAdv(this.adv).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.adv = Object.assign({},defaultAdv);
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
        this.adv = Object.assign({},defaultAdv);
      }
    }
  }
</script>
<style>
</style>


