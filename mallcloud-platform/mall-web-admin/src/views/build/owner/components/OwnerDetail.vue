<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="owner" :rules="rules" ref="ownerFrom" label-width="150px">

      <el-form-item label="类型：">
        <el-radio-group v-model="owner.type">
          <el-radio :label="1">业主本人</el-radio>
          <el-radio :label="2">家庭成员</el-radio>
          <el-radio :label="3">租户</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="业主名称：" prop="name">
        <el-input v-model="owner.name"></el-input>
      </el-form-item>
      <el-form-item label="性别：" prop="sex">
        <el-select
                v-model="owner.sex"

                placeholder="请选择室">
          <el-option
                  v-for="item in genders"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄：" prop="age">
        <el-input v-model="owner.age"></el-input>
      </el-form-item>
      <el-form-item label="联系人手机号：" prop="phone">
        <el-input v-model="owner.phone"></el-input>
      </el-form-item>
      <el-form-item label="备注：" prop="remark">
        <el-input v-model="owner.remark"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit('ownerFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('ownerFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createOwner, getOwner, updateOwner} from '@/api/build/owner'
  import SingleUpload from '@/components/Upload/singleUpload'
  import {formatDate} from '@/utils/date';
  const defaultOwner={
    name: ''
  };
  export default {
    name: 'OwnerDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        owner:Object.assign({}, defaultOwner),
        genders: [
          {
            label: "男"
          },
          {
            label: "女"
          }
        ],
        rules: {
          name: [
            {required: true, message: '请输入业主名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '请输入手机', trigger: 'blur'}
          ],
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ],
        }
      }
    },
    created() {
      if (this.isEdit) {
        getOwner(this.$route.query.id).then(response => {
          this.owner = response.data;
        });
      }else{
        this.owner = Object.assign({},defaultOwner);
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
              this.owner.roomId=this.$route.query.cid
              if (this.isEdit) {
                updateOwner(this.$route.query.id, this.owner).then(response => {
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
                createOwner(this.owner).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.owner = Object.assign({},defaultOwner);
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
        this.owner = Object.assign({},defaultOwner);
        this.owner.unitId = Number(this.$route.query.cid);
      }
    }
  }
</script>
<style>
</style>


