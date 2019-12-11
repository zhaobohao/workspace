<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">

    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 1000px; margin-left:10px;">
      基本信息<h1></h1>

      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="部门名称" prop="deptName">
            <el-input v-model="temp.deptName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="所属租户" prop="tenantId">
            <el-select v-model="temp.tenantId" :placeholder="$t('table.pleaseSelect')" class="filter-item">
              <el-option v-for="item in tenantOptions" :key="parseInt(item.tenantId)" :label="item.tenantName"
                :value="item.tenantId" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="部门全称" prop="fullName">
            <el-input v-model="temp.fullName" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="上级部门" prop="parentId">
            <el-cascader v-model="depParentIds" :show-all-levels="true" :options="departmentOptions" :props="props"
              :change-on-select="true" :clearable="true" filterable @change="handleDepartmentItemChange"></el-cascader>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="排序" prop="sort">
            <el-input v-model="temp.sort" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="temp.remark" />
          </el-form-item>
        </el-col>
      </el-row>

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
    getDeptTree
  } from '@/api/system/dept'
  // 调用trenant对象的selectOption
  import {
    selectOption
  }
  from '@/api/system/tenant'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/dept'
  // 引入相关utils
  import notify from '@/utils/notify'
  import {
    iteratorTreeData,
    deepClone,
    getLoadingOptions
  } from '@/utils/index'
  export default {
    // TODO:本页面的名称
    name: 'dept-dataform',
    components: {

    },
    directives: {
      waves
    },
    filters: {
      // 一些数据转换的函数写在这里，根据key显示value

    },
    props: {},
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
        depParentIds: undefined,
        props: {
          value: 'id',
          label: 'title',
          isLeaf: 'isLeaf',
          children: 'children'
        },
        departmentOptions: [],
        tenantOptions: []
      }
    },
    computed: {

    },
    watch: {

    },
    // 初始化所有的数据
    created() {
      this.initdepartmentOptions()
      this.inittenantOptions()
    },
    mounted() {
      window.onresize = () => {
        return (() => {
          this.setDialogWidth()
        })()
      }
    },
    methods: {
      initdepartmentOptions() {
        getDeptTree(
          '000000'
        ).then(response => {
          this.departmentOptions = response.data
        })
      },
      inittenantOptions() {
        selectOption().then(response => {
          this.tenantOptions = response.data
        })
      },
      handleDepartmentItemChange(val) {
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
        this.depParentIds = undefined
      },
      handleCreateAction() {
        this.resetTemp()
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        // 开始计算几个cascade控件需要显示的值
        this.depParentIds = iteratorTreeData(this.departmentOptions, this.temp.parentId)
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
