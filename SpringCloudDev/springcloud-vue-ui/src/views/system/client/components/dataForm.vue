<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="120px"
      style="width: 1000px; margin-left:10px;">
      基本信息<h1></h1>
      <el-row>
        <el-col :span="24">
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="应用id" prop="clientId">
                <el-input v-model="temp.clientId" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用密钥" prop="clientSecret">
                <el-input v-model="temp.clientSecret" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="授权类型" prop="authorizedGrantTypes">
                <el-input v-model="temp.authorizedGrantTypes" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="授权范围" prop="scope">
                <el-input v-model="temp.scope" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="令牌秒数" prop="accessTokenValidity">
                <el-input v-model="temp.accessTokenValidity" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="刷新秒数" prop="refreshTokenValidity">
                <el-input v-model="temp.refreshTokenValidity" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="回调地址" prop="webServerRedirectUri">
                <el-input v-model="temp.webServerRedirectUri" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源集合" prop="resourceIds">
                <el-input v-model="temp.resourceIds" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row type="flex" class="row-bg">
            <el-col :span="12">
              <el-form-item label="权限" prop="authorities">
                <el-input v-model="temp.authorities" style="width: 305px;" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="自动授权" prop="autoapprove">
                <el-input v-model="temp.autoapprove" style="width: 305px;" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>

      <el-form-item label="附加说明" prop="additionalInformation">
        <el-input v-model="temp.additionalInformation" type="textarea" autosize style="width: 925px;" />
      </el-form-item>

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
  } from '@/api/system/client'

  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/client'
  // 引入相关utils
  import notify from '@/utils/notify'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'client-dataForm',
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
