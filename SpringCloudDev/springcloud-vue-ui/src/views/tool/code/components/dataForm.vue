<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 1000px; margin-left:10px;">
      基本信息<h1></h1>
      <el-form-item label="数据源" prop="datasourceId">
        <el-select v-model="temp.datasourceId" placeholder="驱动类" style="width: 305px;" class="filter-item"
          @keyup.enter.native="handleFilter">
          <el-option v-for="item in dicData" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-row>
        <el-col :span="24">
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="模块名" prop="codeName">
                <el-input v-model="temp.codeName" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="服务名" prop="serviceName">
                <el-input v-model="temp.serviceName" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="表名" prop="tableName">
                <el-input v-model="temp.tableName" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="表前缀" prop="tablePrefix">
                <el-input v-model="temp.tablePrefix" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="主键名" prop="pkName">
                <el-input v-model="temp.pkName" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="包名" prop="packageName">
                <el-input v-model="temp.packageName" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="基础业务" prop="baseMode">
                <el-radio-group v-model="temp.baseMode">
                  <el-radio v-for="(item) in dicOptions" :key="parseFloat(item.dictKey)"
                    :label="parseFloat(item.dictKey)" size="small" style="margin-left: 10px;" border>
                    {{ item.dictValue }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="包装器" prop="wrapMode">
                <el-radio-group v-model="temp.wrapMode">
                  <el-radio v-for="(item) in dicOptions" :key="parseFloat(item.dictKey)"
                    :label="parseFloat(item.dictKey)" size="small" style="margin-left: 10px;" border>
                    {{ item.dictValue }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="后端生成路径" prop="apiPath">
            <el-input v-model="temp.apiPath" type="textarea" autosize style="width: 825px;" />
          </el-form-item>
          <el-form-item label="前端生成路径" prop="webPath">
            <el-input v-model="temp.webPath" type="textarea" autosize style="width: 825px;" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button v-waves @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
      <el-button v-waves type="primary" @click="dialogStatus==='create'?createData():updateData()">
        {{ $t('table.confirm') }}
      </el-button>
    </div>
  </el-dialog>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    add,
    update
  } from '@/api/tool/code'

  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/code'
  // 引入相关utils
  import notify from '@/utils/notify'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'codes-dataForm',
    components: {

    },
    directives: {
      waves,
      elDragDialog
    },
    filters: {

    },
    props: {
      dicData: {
        type: Array,
        default: function () {
          return []
        }
      },
      dicOptions: {
        type: Array,
        default: function () {
          return []
        }
      }
    },
    data() {
      // 初始化整个页面用到的数据
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
        rules: listQuery().rules

      }
    },
    computed: {

    },
    watch: {

    },
    // 初始化所有的数据
    created() {

    },
    mounted() {
      window.onresize = () => {
        return (() => {
          this.setDialogWidth()
        })()
      }
    },
    methods: {
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
      },
      handleCreateAction() {
        this.resetTemp()
        this.temp.baseMode = 1
        this.temp.wrapMode = 1
        this.dialogStatus = 'create'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
      },
      createData() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            // 开始保存数据
            add(this.temp).then((response) => {
              if (response.code === 200) {
                this.$parent.$refs.listTable.list.unshift(response.data)
                this.dialogFormVisible = false
                notify.success(this)
              } else {
                notify.error(this)
              }
            })
          }
        })
      },
      handleUpdate(row) {
        this.temp = Object.assign({}, row) // copy obj
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs.dataForm.clearValidate()
        })
      },
      updateData() {
        this.$refs.dataForm.validate((valid) => {
          if (valid) {
            const tempData = Object.assign({}, this.temp)
            // 开始更新数据
            update(tempData).then((response) => {
              if (response.code === 200) {
                for (const v of this.$parent.$refs.listTable.list) {
                  if (v.id === this.temp.id) {
                    const index = this.$parent.$refs.listTable.list.indexOf(v)
                    this.$parent.$refs.listTable.list.splice(index, 1, tempData)
                    break
                  }
                }
                this.dialogFormVisible = false
                notify.successEdit(this)
              } else {
                notify.errorEdit(this)
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
