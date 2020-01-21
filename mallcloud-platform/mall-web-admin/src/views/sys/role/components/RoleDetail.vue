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
        <div slot="header" class="clearfix">
          <el-tooltip class="item" effect="dark" content="选择指定角色分配菜单" placement="top">
            <span class="role-span">菜单分配</span>
          </el-tooltip>

        </div>
        <el-tree
                ref="menu"
                :data="menus"
                :default-checked-keys="menuIds"
                :props="defaultProps"
                check-strictly
                show-checkbox
                accordion
                show-el-checkbox
                node-key="id"/>
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
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        currentId: 0, menuLoading: false, showButton: false,
        delLoading: false, menus: [], menuIds: [],
        //所有专题列表
        subjectList: [],
        //专题左右标题
        subjectTitles: ['待选择', '已选择'],
        menuIds:null,
        roleMenus:null,
        rules: {
          name: [
            {required: true, message: '请输入角色明', trigger: 'blur'}
          ],
        }
      }
    },
    created() {
    //  this.getMenuList();
      this.getMenus();

      if (this.isEdit) {
        rolePermission(this.$route.query.id).then(res => {
          if (this.$route.query.id) {

            // 清空菜单的选中
            //  this.$refs.menu.setCheckedKeys([])
            // 保存当前的角色id
            this.currentId = this.$route.query.id
            console.log(this.roleMenus)
            // 初始化
            let menuIds = []
            // 菜单数据需要特殊处理
            if (res.data && res.data.length>0){
              res.data.forEach(function(data, index) {
                menuIds.push(data.permissionId)
              })
            }
            this.menuIds = menuIds;
          }
        });
        getRole(this.$route.query.id).then(response => {
          this.role = response.data;
        });

      }else{
        this.role = Object.assign({},defaultRole);
      }
    },

    methods: {
      getMenus() {
        treeList().then(res => {
          this.menus = res.data
        })
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

              // 得到半选的父节点数据，保存起来
              this.$refs.menu.getHalfCheckedNodes().forEach(function(data, index) {
                serviceIds += data.id + ',';
              })
              // 得到已选中的 key 值
              this.$refs.menu.getCheckedKeys().forEach(function(data, index) {
                serviceIds += data + ',';
              })

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


