<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
    <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 400px; margin-left:50px;">

      <el-form-item label="参数名称" prop="paramName">
        <el-input v-model="temp.paramName" />
      </el-form-item>
      <el-form-item label="参数键名" prop="paramKey">
        <el-input v-model="temp.paramKey" />
      </el-form-item>
      <el-form-item label="参数键值" prop="paramValue">
        <el-input v-model="temp.paramValue" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="temp.remark" />
      </el-form-item>

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
  } from '@/api/system/param'

  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/param'
  // 引入相关utils
  import notify from '@/utils/notify'
  export default {
    // TODO:本页面的名称
    name: 'params-dataForm',
    components: {

    },
    directives: {
      waves
    },
    filters: {

    },
    props: {

    },
    data() {
      // 初始化整个页面用到的数据
      return {
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

    },
    methods: {

      resetTemp() {
        this.temp = listQuery().query
      },
      handleCreateAction() {
        this.resetTemp()
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
