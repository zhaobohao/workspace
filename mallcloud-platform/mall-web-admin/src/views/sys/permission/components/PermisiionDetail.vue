<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="permission" :rules="rules" ref="permissionFrom" label-width="150px">
      <el-form-item label="类别：" prop="name">
      <el-radio-group v-model="permission.type" @change="getPermissionCategoryList">
        <el-radio :label="0" >目录</el-radio>
        <el-radio :label="1"  >菜单</el-radio>
        <el-radio :label="2" >按钮</el-radio>
      </el-radio-group>
      </el-form-item>
      <el-form-item label="权限名：" prop="name">
        <el-input v-model="permission.name"></el-input>
      </el-form-item>
      <el-form-item label="权限值：">
        <el-input v-model="permission.value"></el-input>
      </el-form-item>
      <el-form-item label="菜单url：">
        <el-input v-model="permission.uri"></el-input>
      </el-form-item>
      <el-form-item label="上级菜单：" prop="type" v-if="permission.type>0">
        <el-select
          v-model="permission.pid"

          placeholder="请选择上级菜单">
          <el-option
            v-for="item in permissionCategoryOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="菜单排序：">
        <el-input v-model="permission.sort"></el-input>
      </el-form-item>
      <el-form-item label="菜单图像：" prop="logo">
        <el-popover
                placement="bottom-start"
                width="450"
                trigger="click"
                @show="$refs['iconSelect'].reset()">
          <IconSelect ref="iconSelect" @selected="selected" />
          <el-input slot="reference" v-model="permission.icon" style="width: 450px;" placeholder="点击选择图标" readonly>
            <svg-icon v-if="permission.icon" slot="prefix" :icon-class="permission.icon" class="el-input__icon" style="height: 32px;width: 16px;" />
            <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
          </el-input>
        </el-popover>
      </el-form-item>

      <el-form-item label="是否显示：">
        <el-radio-group v-model="permission.status">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="0">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('permissionFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('permissionFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createPermission, getPermission, updatePermission,fetchList} from '@/api/permission'
  import IconSelect from '@/components/IconSelect'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultPermission={
    name: ''
  };
  export default {
    name: 'PermissionDetail',
    components:{SingleUpload,IconSelect},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        listQuery: {
          type: null
        },
        selectPermissionCategoryValue:[],
        permissionCategoryOptions: [],
        typeName:'',
        permission:Object.assign({}, defaultPermission),
        rules: {
          name: [
            {required: true, message: '请输入菜单名', trigger: 'blur'}
          ],
          type: [{required: true, message: '请选择菜单类型', trigger: 'blur'}]
        }
      }
    },

    created() {
      if (this.isEdit) {
        getPermission(this.$route.query.id).then(response => {
          this.permission = response.data;
          this.getPermissionCategoryList();
        });
      }else{
        this.permission = Object.assign({},defaultPermission);

      }
    //  this.getPermissionCategoryList();
    },
    methods: {

      handleTypeChange(val) {
        let typeId = 0;
        for (let i = 0; i < this.permissionCategoryOptions.length; i++) {
          if (this.permissionCategoryOptions[i].value === val) {
            typeId = this.permissionCategoryOptions[i].value;
            break;
          }
        }
        this.permission.type = typeId;
      },
      getPermissionCategoryList(){
        this.permissionCategoryOptions.length=0;
        if (this.permission.type){
          this.listQuery.type=this.permission.type-1;
        }

        fetchList(this.listQuery).then(response => {
          let list = response.data;
          for (let i = 0; i < list.length; i++) {
            this.permissionCategoryOptions.push({label: list[i].name, value: list[i].id});
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
                updatePermission(this.$route.query.id, this.permission).then(response => {
                  this.$refs[formName].resetFields();
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration:1000
                  });
                  this.$router.back();
                });
              } else {
                createPermission(this.permission).then(response => {
                  this.$refs[formName].resetFields();
                  this.permission = Object.assign({},defaultPermission);
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
      selected(name) {
        this.permission.icon = name
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.permission = Object.assign({},defaultPermission);
      }
    }
  }
</script>
<style>
</style>


