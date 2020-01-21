<template> 
  <div style="margin-top: 50px">
    <el-form :model="role" :rules="rules" ref="roleFrom"
             label-width="120px" style="width: 680px"
             size="small">
      <el-form-item label="角色名称：" prop="name">
        <el-input v-model="role.name"></el-input>
      </el-form-item>
      <el-form-item label="角色描述：">
        <el-input
          placeholder="请输入内容"
          type="textarea"
          v-model="role.description"
          :autosize="true"></el-input>
      </el-form-item>
      <el-form-item label="关联权限：">
        <el-transfer
          style="display: inline-block"
          filterable
          :filter-method="filterMethod"
          filter-placeholder="请输入权限名称"
          v-model="selectSubject"
          :titles="subjectTitles"
          :data="subjectList">
        </el-transfer>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmit('roleFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('roleFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import {createRole, getRole, updateRole,rolePermission} from '@/api/role'
  import {treeList} from '@/api/permission'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultRole={
    name: ''
  };
  export default {
    name: 'RoleDetail',
    components:{SingleUpload},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        role:Object.assign({}, defaultRole),
        //所有专题列表
        subjectList: [],
        //专题左右标题
        subjectTitles: ['待选择', '已选择'],
        menuIds:null,
        rules: {
          name: [
            {required: true, message: '请输入角色明', trigger: 'blur'}
          ],
        }
      }
    },
    created() {
      this.getMenuList();
      if (this.isEdit) {
        rolePermission(this.$route.query.id).then(res => {
          this.menuIds=res.data;
         });
        getRole(this.$route.query.id).then(response => {
          this.role = response.data;
        });
      }else{
        this.role = Object.assign({},defaultRole);
      }
    },
    computed:{
      //选中的专题
      selectSubject:{
        get:function () {
          let subjects =[];
          if(this.menuIds==null||this.menuIds.length<=0){
            return subjects;
          }
          for(let i=0;i<this.menuIds.length;i++){
            subjects.push(this.menuIds[i].permissionId);
          }
          return subjects;
        },
        set:function (newValue) {
          this.menuIds=[];
          for(let i=0;i<newValue.length;i++){
            this.menuIds.push({permissionId:newValue[i]});
          }
        }
      },
    },
    methods: {
      getMenuList() {
        treeList().then(response => {
          let list = response.data;
        for (let i = 0; i < list.length; i++) {
          this.subjectList.push({
            label: list[i].name,
            key: list[i].id
          });
          if(list[i].children){
            let ch1 = list[i].children;
            for (let i = 0; i < ch1.length; i++) {
              this.subjectList.push({
                label: '--'+ch1[i].name,
                key: ch1[i].id
              });
              if(ch1[i].children){
                let ch2 = ch1[i].children;
                for (let i = 0; i < ch2.length; i++) {
                  this.subjectList.push({
                    label: '----'+ch2[i].name,
                    key: ch2[i].id
                  });
                }
              }
            }
          }

        }

      });
      },

      filterMethod(query, item) {
        return item.label.indexOf(query) > -1;
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              let serviceIds='';
              for(let i=0;i<this.menuIds.length;i++){
                serviceIds += this.menuIds[i].permissionId + ',';
              }
              if (serviceIds.endsWith(',')) {
                serviceIds = serviceIds.substr(0, serviceIds.length - 1)
              }
              this.role.menuIds = serviceIds;
              if (this.isEdit) {
                updateRole(this.$route.query.id, this.role).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createRole(this.role).then(response => {
                  this.$refs[formName].resetFields();
                  this.role = Object.assign({},defaultRole);
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
        this.role = Object.assign({},defaultRole);
      }
    }
  }
</script>
<style scoped>

</style>


