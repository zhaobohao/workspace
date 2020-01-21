<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="memberLevel" :rules="rules" ref="memberLevelFrom" label-width="150px">

      <el-form-item label="名称：" prop="name">
        <el-input v-model="memberLevel.name"></el-input>
      </el-form-item>
      <el-form-item label="成长值：" prop="growthPoint">
        <el-input v-model="memberLevel.growthPoint"></el-input>
      </el-form-item>

      <el-form-item label="免运费标准：" prop="freeFreightPoint">
        <el-input v-model="memberLevel.freeFreightPoint"></el-input>
      </el-form-item>
      <el-form-item label="每次评价获取的成长值：" prop="commentGrowthPoint">
        <el-input v-model="memberLevel.commentGrowthPoint"></el-input>
      </el-form-item>
      <el-form-item label="是否有免邮特权：" prop="priviledgeFreeFreight">
        <el-input v-model="memberLevel.priviledgeFreeFreight"></el-input>
      </el-form-item>
      <el-form-item label="是否有签到特权：" prop="priviledgeSignIn">
        <el-input v-model="memberLevel.priviledgeSignIn"></el-input>
      </el-form-item>
      <el-form-item label="是否有评论获奖励特权：" prop="priviledgeComment">
        <el-input v-model="memberLevel.priviledgeComment"></el-input>
      </el-form-item>
      <el-form-item label="是否有专享活动特权：" prop="priviledgePromotion">
        <el-input v-model="memberLevel.priviledgePromotion"></el-input>
      </el-form-item>
      <el-form-item label="是否有会员价格特权：" prop="priviledgeMemberPrice">
        <el-input v-model="memberLevel.priviledgeMemberPrice"></el-input>
      </el-form-item>
      <el-form-item label="是否有生日特权：" prop="priviledgeBirthday">
        <el-input v-model="memberLevel.priviledgeBirthday"></el-input>
      </el-form-item>
      <el-form-item label="可发文章数：" prop="articlecount">
        <el-input v-model="memberLevel.articlecount"></el-input>
      </el-form-item>
      <el-form-item label="可发商品数：" prop="goodscount">
        <el-input v-model="memberLevel.goodscount"></el-input>
      </el-form-item>
      <el-form-item label="成为会员的价格：" prop="price">
        <el-input v-model="memberLevel.price"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="note">
        <el-input v-model="memberLevel.note"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('memberLevelFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('memberLevelFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createMemberLevel, getMemberLevel, updateMemberLevel} from '@/api/memberLevel'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultMemberLevel={
    name: ''
  };
  export default {
    name: 'MemberLevelDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        memberLevel:Object.assign({}, defaultMemberLevel),
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
        getMemberLevel(this.$route.query.id).then(response => {
          this.memberLevel = response.data;
        });
      }else{
        this.memberLevel = Object.assign({},defaultMemberLevel);
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
                updateMemberLevel(this.$route.query.id, this.memberLevel).then(response => {
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
                createMemberLevel(this.memberLevel).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.memberLevel = Object.assign({},defaultMemberLevel);
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
        this.memberLevel = Object.assign({},defaultMemberLevel);
      }
    }
  }
</script>
<style>
</style>


