<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="admin" :rules="loginRules" ref="adminFrom" label-width="150px">
      <el-form-item label="用户昵称：" prop="nickName">
        <el-input v-model="admin.nickName"></el-input>
      </el-form-item>
      <el-form-item label="用户名：" prop="username">
        <el-input v-model="admin.username"></el-input>
      </el-form-item>

      <el-form-item label="邮箱：" prop="email">
        <el-input v-model="admin.email"></el-input>
      </el-form-item>
      <el-form-item label="图像：" prop="icon">
        <single-upload v-model="admin.icon"></single-upload>
      </el-form-item>
      <el-form-item label="角色：" prop="roleIds">
        <el-checkbox-group v-model="allRoles1">
          <el-checkbox v-for="(item,index) in allRoles" :key="index"   :checked="item.checked" :label="item.id">{{item.name}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="备注：">
        <el-input
          placeholder="请输入内容"
          type="textarea"
          v-model="admin.note"
          :autosize="true"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('adminFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('adminFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createAdmin, getAdmin, updateAdmin,userRoles,userRoleCheck} from '@/api/admin'
  import {isvalidUsername} from '@/utils/validate';
  import {fetchList} from '@/api/role'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultAdmin={
    username: '',
    roleIds:'',

  };
  export default {
    name: 'AdminDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)) {
          callback(new Error('请输入正确的用户名'))
        } else {
          callback()
        }
      };
      const validatePass = (rule, value, callback) => {
        if (value.length < 3) {
          callback(new Error('密码不能小于3位'))
        } else {
          callback()
        }
      };
      return {
        admin:Object.assign({}, defaultAdmin),
        allRoles:null,
        userRoles:null,
        roleIds:null,
        listQuery: {
          pageNum: 1,
          pageSize: 100
        },
        loginRules: {
          username: [{required: true, trigger: 'blur', message: '请输入用户名',}]
        }

        }

    },
    created() {
      if (this.isEdit) {
        this.listQuery.adminId=this.$route.query.id;
        userRoleCheck(this.listQuery).then(res => {
          if(res.code==200){
          this.allRoles =  res.data
        }
      });

        getAdmin(this.$route.query.id).then(response => {
          this.admin = response.data;
      });
      }else{
        fetchList(this.listQuery).then(response => {
          this.allRoles = response.data.records;
      });
        this.admin = Object.assign({},defaultAdmin);
      };


    },
    computed: {
      //选中的服务保证
      allRoles1: {
        get() {
          let list = [];
          if (this.roleIds === undefined || this.roleIds == null || this.roleIds === '') return list;
          let ids = this.roleIds.split(',');
          for (let i = 0; i < ids.length; i++) {
            list.push(Number(ids[i]));
          }
          return list;
        },
        set(newValue) {
          let serviceIds = '';
          if (newValue != null && newValue.length > 0) {
            for (let i = 0; i < newValue.length; i++) {
              serviceIds += newValue[i] + ',';
            }
            if (serviceIds.endsWith(',')) {
              serviceIds = serviceIds.substr(0, serviceIds.length - 1)
            }
            this.roleIds = serviceIds;
          } else {
            this.roleIds = null;
          }
        }
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
              this.admin.roleIds = this.roleIds;
              if (this.isEdit) {
                updateAdmin(this.$route.query.id, this.admin).then(response => {
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
                createAdmin(this.admin).then(response => {
                  if(response.code==200)
                {
                  this.$refs[formName].resetFields();
                  this.admin = Object.assign({}, defaultAdmin);
                  this.$message({
                    message: '提交成功',
                    type: 'success',
                    duration: 1000
                  });
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
        this.admin = Object.assign({},defaultAdmin);
      }
    }
  }
</script>
<style>
</style>


