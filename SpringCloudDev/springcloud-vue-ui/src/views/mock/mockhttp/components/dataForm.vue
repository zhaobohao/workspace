<template>
  <!--form 表单，用来显示和编辑数据 -->
  <el-dialog v-el-drag-dialog :width="dialogWidth" :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
    <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" label-position="left" label-width="100px"
      style="width: 1000px; margin-left:10px;">
      <fieldset style="border-color: #32ded6;border-width: 1px;border-style: solid;">
        <legend>请求参数</legend>
        <el-form-item label="请求url" prop="requestPath">
          <el-tooltip class="item" effect="dark" content="请求的url路径，支持正则表达式" placement="top">
            <el-input v-model="temp.requestPath" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="请求方式" prop="requestMethod">
          <el-tooltip class="item" effect="dark" content="http的method,例如get,put,delete,post,支持正则表达式" placement="top">
            <el-input v-model="temp.requestMethod" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="请求参数" prop="requestParams">
          <el-tooltip class="item" effect="dark" content="http传过来的参数，录入为json结构。key,value支持正则表达式" placement="top">
            <el-input v-model="temp.requestParams" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="请求header" prop="requestHeaders">
          <el-tooltip class="item" effect="dark" content="http传过来的头部参数，录入为json结构，key,value支持正则表达式" placement="top">
            <el-input v-model="temp.requestHeaders" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="请求cookies" prop="requestCookies">
          <el-tooltip class="item" effect="dark" content="htpp传过来的cookies参数，录入为json结构，key,value支持正则表达式" placement="top">
            <el-input v-model="temp.requestCookies" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="JsonBody" prop="requestJsonBody">
          <el-tooltip class="item" effect="dark" content="http传过来的request报文里的jsonbody段所包含的值，默认是使用正则表达式" placement="top">
            <el-input v-model="temp.requestJsonBody" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="请求编码" prop="requestCharsets">
          <el-tooltip class="item" effect="dark" content="request报文的编码，默认为utf-8" placement="top">
            <el-input v-model="temp.requestCharsets" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="FormBody" prop="requestFormBody">
          <el-tooltip class="item" effect="dark" content="http传过来的request报文里的formbody段所包含的值，默认是使用正则表达式" placement="top">
            <el-input v-model="temp.requestFormBody" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
      </fieldset>
      <fieldset style="border-color: #a2ae26;border-width: 1px;border-style: solid;">
        <legend>回执参数（优先级低）</legend>
        <el-form-item label="回执headers" prop="responseHeaders">
          <el-tooltip class="item" effect="dark" content="response报文里的headers,录入为json.程序会正确拆分，附值 。" placement="top">
            <el-input v-model="temp.responseHeaders" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="回执报文" prop="responseBody">
          <el-tooltip class="item" effect="dark" content="response报文里的body" placement="top">
            <el-input style="width: 305px;" v-model="temp.responseBody" :autosize="{ minRows: 2, maxRows: 40}"
              type="textarea" placeholder="输入报文体" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="回执编码" prop="responseCharsets">
          <el-tooltip class="item" effect="dark" content="response报文里的body的编码" placement="top">
            <el-input v-model="temp.responseCharsets" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="回执状态" prop="responseStatusCode">
          <el-tooltip class="item" effect="dark" content="response报文里的 status code.例如：400，302，501" placement="top">
            <el-input-number v-model="temp.responseStatusCode" :precision="0" :step="1" :max="9000"
              style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="回执状态说明" prop="responseReasonPhrase">
          <el-tooltip class="item" effect="dark" content="配合statuscode使用的，状态码解释文本。" placement="top">
            <el-input v-model="temp.responseReasonPhrase" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="回执延迟" prop="responseDelay">
          <el-tooltip class="item" effect="dark" content="延迟响应时候，默认时间单位为秒。" placement="top">
            <el-input-number v-model="temp.responseDelay" :precision="0" :step="1" :max="100" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
      </fieldset>
      <fieldset style="border-color: #12aed6;border-width: 1px;border-style: solid;">
        <legend>跳转参数（优先级中）</legend>
        <el-form-item label="跳转域名" prop="forwardHost">
          <el-tooltip class="item" effect="dark" content="跳转域名" placement="top">
            <el-input v-model="temp.forwardHost" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="跳转端口" prop="forwardPort">
          <el-tooltip class="item" effect="dark" content="跳转端口" placement="top">
            <el-input v-model="temp.forwardPort" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="跳转路径" prop="forwardPath">
          <el-tooltip class="item" effect="dark" content="跳转路径" placement="top">
            <el-input v-model="temp.forwardPath" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="跳转headers" prop="forwardHeaders">
          <el-tooltip class="item" effect="dark" content="跳转时头部参数，录入为json.程序自动拆分配置。" placement="top">
            <el-input v-model="temp.forwardHeaders" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="路转地址" prop="forwardSocketAddress">
          <el-tooltip class="item" effect="dark"
            content="跳转时，重写request时的，请求地址。录入时为json数据格式。程序会自动拆分。withSocketAddress(' target.host.com', 1234,SocketAddress.Scheme.HTTPS)"
            placement="top">
            <el-input v-model="temp.forwardSocketAddress" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="跳转延迟" prop="forwardDelay">
          <el-tooltip class="item" effect="dark" content="跳转时的延迟时间，默认时间单位为秒" placement="top">
            <el-input-number v-model="temp.forwardDelay" :precision="0" :step="1" :max="1000" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="跳转报文" prop="forwardBody">
          <el-tooltip class="item" effect="dark" content="跳转时，重写转给第三方的body." placement="top">
            <el-input style="width: 305px;" v-model="temp.forwardBody" :autosize="{ minRows: 2, maxRows: 40}"
              type="textarea" placeholder="输入报文体" />
          </el-tooltip>
        </el-form-item>
      </fieldset>
      <fieldset style="border-color: #b60404;border-width: 1px;border-style: solid;">
        <legend>错误参数（优先级高）</legend>
        <el-form-item label="丢弃链接" prop="errorDropConnection">
          <el-tooltip class="item" effect="dark" content="是否丢失链接，true,false" placement="top">
            <el-input v-model="temp.errorDropConnection" style="width: 305px;" />
          </el-tooltip>
        </el-form-item>
        <el-form-item label="错误报文" prop="errorResponseBytes">
          <el-tooltip class="item" effect="dark" content="出错时，返回的报文" placement="top">
            <el-input style="width: 305px;" v-model="temp.errorResponseBytes" :autosize="{ minRows: 2, maxRows: 40}"
              type="textarea" placeholder="输入报文体" />
          </el-tooltip>
        </el-form-item>
      </fieldset>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button v-waves @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
      <el-button v-waves type="primary" @click="dialogStatus==='create'?createData():updateData()">
        {{ $t('table.confirm') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    add,
    update
  } from '@/api/mock/mockhttp'

  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import listQuery from '@/entitys/mock/mockhttp'
  // 引入相关utils
  import notify from '@/utils/notify'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    // TODO:本页面的名称
    name: 'mockhttp-dataForm',
    components: {

    },
    directives: {
      waves,
      elDragDialog
    },
    filters: {

    },
    props: {},
    data() {
      // 初始化整个页面用到的数据
      return {
        dialogWidth: '1100px',
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
              picker.$emit('pick', new Date())
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24)
              picker.$emit('pick', date)
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date()
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', date)
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
        const def = 1100 // 默认宽度
        if (val < def) {
          this.dialogWidth = val + 'px'
        } else {
          this.dialogWidth = def + 'px'
        }
      },
      resetTemp() {
        this.temp = listQuery().query
      },
      handleCreateAction(webSiteId) {
        this.resetTemp()
        this.temp.webSiteId = webSiteId
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
                this.$parent.$parent.$parent.$parent.$refs.listTable.list.unshift(response.data)
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
