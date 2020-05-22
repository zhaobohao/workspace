<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" title="角色权限配置" :visible.sync="permissionFormVisible">
    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="height:700px;width: 1000px; margin-left:10px;">
      <el-cascader-panel v-model="temp.menuIds" :show-all-levels="true" :options="menuOptions" :props="props"
        :clearable="true" filterable style="width: 605px;" @change="handleMenuItemChange">
      </el-cascader-panel>
      <br />
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button v-waves @click="permissionFormVisible = false">{{ $t('table.cancel') }}</el-button>
      <el-button v-waves type="primary" @click="updateData()">{{
        $t('table.confirm') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    grant,
    getRoleId,
    grantTree
  } from '@/api/permission/role'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求

  // 引入相关utils
  import notify from '@/utils/notify'
  import {
    getLoadingOptions
  } from '@/utils/index'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'setPermission',
    components: {

    },
    directives: {
      waves,
      elDragDialog
    },
    filters: {
      // 一些数据转换的函数写在这里，根据key显示value

    },
    props: {

    },
    data() {
      // 初始化整个页面用到的数据3f
      return {
        dialogWidth: '1000px',
        permissionFormVisible: false, // form表格对话框是否显示
        dialogStatus: '', // 当前操作的状态，控制form表单的Title,form表单submit的方法
        textMap: {
          update: this.$t('table.edit'),
          create: this.$t('table.add')
        },
        temp: {
          menuIds: undefined,
          roleIds: undefined,
          reqmenuIds: undefined
        },
        rules: {},
        // 部门多选择时使用
        props: {
          value: 'id',
          label: 'title',
          leaf: 'isLeaf',
          children: 'children',
          multiple: true,
          emitPath: false,
          checkStrictly: false
        },
        menuOptions: []
      }
    },
    computed: {

    },
    watch: {

    },
    // 初始化所有的数据
    created() {
      this.initTreeOptions()
    },
    mounted() {
      window.onresize = () => {
        return (() => {
          this.setDialogWidth()
        })()
      }
    },
    methods: {
      initTreeOptions() {
        grantTree().then(response => {
          this.menuOptions = response.data
        })
      },
      handleMenuItemChange(val) {
        // val为选中项的id数组
        // console.log(val)
        this.temp.reqmenuIds = val.join()
      },
      setDialogWidth() {
        var val = document.body.clientWidth
        const def = 1000 // 默认宽度
        if (val < def) {
          this.dialogWidth = val + 'px'
        } else {
          this.dialogWidth = def + 'px'
        }
      },
      setPermission(roleId) {
        const loading = this.$loading(getLoadingOptions())
        this.temp.roleIds = roleId
        getRoleId(roleId).then((response) => {
          loading.close()
          if (response.code === 200) {
            this.temp.menuIds = response.data
            this.temp.reqmenuIds = this.temp.menuIds.join()
            // console.log(this.temp.menuIds)
          } else {
            notify.error(this)
          }
        })

        this.dialogStatus = 'update'
        this.permissionFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
        loading.close()
      },
      updateData() {
        const loading = this.$loading(getLoadingOptions())
        this.$refs.dataForm.validate((valid) => {
          if (valid) {
            const tempData = Object.assign({}, this.temp)
            // 开始更新数据
            grant(tempData.roleIds, tempData.reqmenuIds).then((response) => {
              loading.close()
              if (response.code === 200) {
                this.permissionFormVisible = false
                notify.successEdit(this)
              } else {
                notify.errorEdit(this)
              }
            })
          } else {
            loading.close()
          }
        })
      }
    }
  }

</script>
<style lang="scss" scoped>
  .el-form-item__label {
    color: rgba(0, 0, 0, 0.85);
    font-weight: 500;
  }

  .el-table th>.cell {
    color: rgba(0, 0, 0, 0.85);
    font-weight: 500;
  }

  .el-table th,
  .el-table tr {
    background-color: #fafafa;
  }

</style>
<style lang="scss">
  .el-cascader-menu__wrap {
    height: 504px;
  }

</style>
