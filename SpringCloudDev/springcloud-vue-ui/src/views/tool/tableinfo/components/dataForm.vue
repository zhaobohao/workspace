<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">

    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 1000px; margin-left:10px;">
      基本信息<h1></h1>
      <el-row type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="名称" prop="name">
            <el-input v-model="temp.name" style="width: 305px;" />
          </el-form-item>
        </el-col>
        <el-col v-if="temp.category===2" :span="12">
          <el-form-item label="字段类型" prop="typeKey">
            <el-select v-model="temp.typeKey" :placeholder="$t('table.pleaseSelect')" class="filter-item"
              style="width: 305px;">
              <el-option v-for="item in typeKeyOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="temp.category===2" type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="字段长度" prop="typeValue">
            <el-input v-model="temp.typeValue" type="NUMBER" style="width: 305px;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否为空" prop="isEmpty">
            <el-radio-group v-model="temp.isEmpty">
              <el-radio v-for="(item) in yesnoOptions" :key="item.dictKey" :label="item.dictKey" size="small"
                style="margin-left: 10px;" border>
                {{ item.dictValue }}
              </el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="temp.category===2" type="flex" class="row-bg">
        <el-col :span="12">
          <el-form-item label="默认值" prop="defaultValue">
            <el-input v-model="temp.defaultValue" style="width: 305px;" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="注释说明" prop="comment">
        <el-input v-model="temp.comment" type="textarea" autosize style="width: 825px;" />
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
    update
  } from '@/api/tool/tableinfo'

  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/tableinfo'
  // 引入相关utils
  import notify from '@/utils/notify'
  import {
    getLoadingOptions
  } from '@/utils/index'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'tableinfo-dataform',
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
      yesnoOptions: {
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
        roleParentIds: undefined,
        deptParentIds: undefined,
        props: {
          value: 'id',
          label: 'title',
          leaf: 'isLeaf',
          children: 'children',
          multiple: true,
          emitPath: false,
          checkStrictly: true
        },
        typeKeyOptions: [{
            label: 'VARCHAR2',
            value: 'VARCHAR2'
          },
          {
            label: 'INTEGER',
            value: 'INTEGER'
          },
          {
            label: 'NUMBER',
            value: 'NUMBER'
          },
          {
            label: 'DATE',
            value: 'DATE'
          }
        ]
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
      initTreeOptions() {},
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
        this.temp.isEmpty = 1
        this.temp.category = 2
      },
      handleCreateAction(parentId, dbInstanceId, category) {
        this.resetTemp()
        this.temp.parentId = parentId
        this.temp.dbInstanceId = dbInstanceId
        this.temp.category = category
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        // 开始计算几个cascade控件需要显示的值
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
                if (response.data.category === 1) {
                  this.$parent.$parent.$parent.$parent.initTreeData()
                }
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
                if (response.data.category === 1) {
                  this.$parent.$parent.$parent.$parent.initTreeData()
                }
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
