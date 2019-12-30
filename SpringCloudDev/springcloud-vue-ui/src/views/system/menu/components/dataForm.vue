<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">

    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 1000px; margin-left:10px;">
      基本信息<h1></h1>

      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="菜单名称" prop="name">
            <el-input v-model="temp.name" style="width: 305px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="路由地址" prop="path">
            <el-input v-model="temp.path" style="width: 305px;" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="上级菜单" prop="parentId">
            <el-cascader v-model="parentIds" :show-all-levels="true" :options="parentIdsOptions" :props="props"
              :clearable="true" filterable @change="handleParentsItemChange"></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单图标" prop="source">
            <el-input v-model="temp.source" :readonly="true" placeholder="请选择图标" style="width: 305px;">
              <el-button slot="append" icon="el-icon-setting" @click="handleSelectIconAction">
              </el-button>
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="菜单编号" prop="code">
            <el-input v-model="temp.code" style="width: 305px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单别名" prop="alias">
            <el-input v-model="temp.alias" style="width: 305px;" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="排序" prop="sort">
            <el-input v-model="temp.sort" type="number" style="width: 305px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单类型" prop="remark">
            <el-radio-group v-model="temp.category">
              <el-radio v-for="(item) in categoryOptions" :key="parseFloat(item.dictKey)"
                :label="parseFloat(item.dictKey)" size="small" style="margin-left: 10px;" border>
                {{ item.dictValue }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="temp.remark" type="textarea" style="width: 815px;" />
      </el-form-item>

      <br />
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button v-waves @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
      <el-button v-waves type="primary" @click="dialogStatus==='create'?createData():updateData()">{{
        $t('table.confirm') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    add,
    update,
    getMenuTree
  } from '@/api/system/menu'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/menu'
  // 引入相关utils
  import notify from '@/utils/notify'
  import {
    iteratorTreeData,
    getLoadingOptions
  } from '@/utils/index'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'menu-dataform',
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
      iconDialogVisible: {
        type: Boolean,
        default: false
      },
      categoryOptions: {
        type: Array,
        default: function () {
          return []
        }
      }
    },
    data() {
      // 初始化整个页面用到的数据3f
      return {
        dialogWidth: '1000px',
        dialogFormVisible: false, // form表格对话框是否显示
        dialogStatus: '', // 当前操作的状态，控制form表单的Title,form表单submit的方法
        textMap: {
          update: this.$t('table.edit'),
          create: this.$t('table.add')
        },
        dialogPvVisible: false,
        temp: listQuery().query,
        rules: listQuery().rules,
        // 部门多选择时使用
        parentIds: undefined,
        props: {
          value: 'id',
          label: 'title',
          leaf: 'isLeaf',
          children: 'children',
          checkStrictly: true
        },
        parentIdsOptions: []

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
        getMenuTree(

        ).then(response => {
          this.parentIdsOptions = response.data
        })
      },
      handleParentsItemChange(val) {
        // val为选中项的id数组
        // console.log(this.temp)
        this.temp.parentId = val.pop()
        val.push(this.temp.parentId)
      },
      setDialogWidth() {
        console.log(document.body.clientWidth)
        var val = document.body.clientWidth
        const def = 1000 // 默认宽度
        if (val < def) {
          this.dialogWidth = val + 'px'
        } else {
          this.dialogWidth = def + 'px'
        }
      },
      resetTemp() {
        this.temp = listQuery().query
        this.parentIds = undefined
      },
      // 处理创建按钮
      handleSelectIconAction() {
        this.$emit('update:iconDialogVisible', true)
      },
      handleCreateAction() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
      },
      handleUpdateIcon(icon) {
        this.temp.source = icon
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        // 开始计算几个cascade控件需要显示的值
        this.parentIds = iteratorTreeData(this.parentIdsOptions, this.temp.parentId)
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
      },
      updateData() {
        const loading = this.$loading(getLoadingOptions())
        this.$refs.dataForm.validate((valid) => {
          if (valid) {
            const tempData = Object.assign({}, this.temp)
            // 开始更新数据
            update(tempData).then((response) => {
              loading.close()
              if (response.code === 200) {
                this.$parent.$parent.$parent.$parent.initTreeData()
                for (const v of this.$parent.$parent.$parent.$parent.$refs.listTable.list) {
                  if (v.id === this.temp.id) {
                    const index = this.$parent.$parent.$parent.$parent.$refs.listTable.list.indexOf(v)
                    this.$parent.$parent.$parent.$parent.$refs.listTable.list.splice(index, 1, tempData)
                    break
                  }
                }
                this.dialogFormVisible = false
                notify.successEdit(this)
              } else {
                notify.errorEdit(this)
              }
            })
          } else {
            loading.close()
          }
        })
      },
      createData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            // 开始保存数据
            add(this.temp).then((response) => {
              if (response.code === 200) {
                this.$parent.$parent.$parent.$parent.initTreeData()
                this.$parent.$parent.$parent.$parent.$refs.listTable.list.unshift(response.data)
                this.dialogFormVisible = false
                notify.success(this)
              } else {
                notify.error(this)
              }
            })
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
