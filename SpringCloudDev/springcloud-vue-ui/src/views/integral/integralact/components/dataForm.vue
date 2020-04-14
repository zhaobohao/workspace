<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 1000px; margin-left:10px;">

      <el-form-item label="积分活动编号" prop="actCode">
        <el-input v-model="temp.actCode" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="积分活动名称" prop="actName">
        <el-input v-model="temp.actName" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="营销活动编号" prop="marketActId">
        <el-input v-model="temp.marketActId" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="营销活动结束时间" prop="endTime">
        <el-input v-model="temp.endTime" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="所属部门" prop="department">
        <el-input v-model="temp.department" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="积分类型" prop="integralType">
        <el-input v-model="temp.integralType" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="积分有效期类型" prop="integralLimitTimeType">
        <el-input v-model="temp.integralLimitTimeType" style="width: 305px;" />
      </el-form-item>
    <el-form-item label="积分有效期年限" prop="integralLimitYearNum">
        <el-input-number v-model="temp.integralLimitYearNum" :precision="0" :step="1" :max="100" style="width: 305px;" />
    </el-form-item>
      <el-form-item label="积分到期月份" prop="integralEndMonth">
        <el-input v-model="temp.integralEndMonth" style="width: 305px;" />
      </el-form-item>
    <el-form-item label="计划积分" prop="prepareIntegralNum">
        <el-input-number v-model="temp.prepareIntegralNum" :precision="0" :step="1" :max="100" style="width: 305px;" />
    </el-form-item>
    <el-form-item label="计划人数" prop="prepareCount">
        <el-input-number v-model="temp.prepareCount" :precision="0" :step="1" :max="100" style="width: 305px;" />
    </el-form-item>
      <el-form-item label="活动描述" prop="actBreif">
        <el-input v-model="temp.actBreif" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="状态: audit_status_tosub：新增，audit_status_wait提交待审核，audit_status_pass审核通过，audit_status_nopass退回" prop="state">
        <el-input v-model="temp.state" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="所属规则组" prop="ruleTeam">
        <el-input v-model="temp.ruleTeam" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="审核意见" prop="opinion">
        <el-input v-model="temp.opinion" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="创建人" prop="crtUser">
        <el-input v-model="temp.crtUser" style="width: 305px;" />
      </el-form-item>
    <el-form-item label="创建时间" prop="crtTime">
        <el-date-picker
            v-model="temp.crtTime"
            type="datetime"
            placeholder="选择日期时间"
            align="right"
            :picker-options="pickerOptions">
        </el-date-picker>
    </el-form-item>
      <el-form-item label="最后更新人" prop="lstUpdUser">
        <el-input v-model="temp.lstUpdUser" style="width: 305px;" />
      </el-form-item>
    <el-form-item label="最后更新时间" prop="lstUpdTime">
        <el-date-picker
            v-model="temp.lstUpdTime"
            type="datetime"
            placeholder="选择日期时间"
            align="right"
            :picker-options="pickerOptions">
        </el-date-picker>
    </el-form-item>
      <el-form-item label="预留字段一" prop="reserveColumn1">
        <el-input v-model="temp.reserveColumn1" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="预留字段二" prop="reserveColumn2">
        <el-input v-model="temp.reserveColumn2" style="width: 305px;" />
      </el-form-item>
      <el-form-item label="预留字段三" prop="reserveColumn3">
        <el-input v-model="temp.reserveColumn3" style="width: 305px;" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button v-waves @click="dialogFormVisible = false">{{ $t('table.cancel')  }}</el-button>
      <el-button v-waves type="primary" @click="dialogStatus==='create'?createData():updateData()">{{ $t('table.confirm')  }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    add,
    update
  } from '@/api/system/integralact'

  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/integralact'
  // 引入相关utils
  import notify from '@/utils/notify'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'integralact-dataForm',
    components: {

    },
    directives: {
      waves,
      elDragDialog
    },
    filters: {

    },
    props: {
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
        rules: listQuery().rules,
        pickerOptions: {
              shortcuts: [{
                  text: '今天',
                  onClick(picker) {
                      picker.$emit('pick', new Date());
                  }
              }, {
                  text: '昨天',
                  onClick(picker) {
                      const date = new Date();
                      date.setTime(date.getTime() - 3600 * 1000 * 24);
                      picker.$emit('pick', date);
                  }
              }, {
                  text: '一周前',
                  onClick(picker) {
                      const date = new Date();
                      date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                      picker.$emit('pick', date);
                  }
              }]
          }
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
          this.dialogFormVisible= true
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
