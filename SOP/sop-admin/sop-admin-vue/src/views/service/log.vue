<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
      <el-form-item>
        <el-button type="primary" icon="el-icon-plus" @click="onAddServer">添加监控服务器</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      border
      :default-expand-all="true"
      row-key="treeId"
      empty-text="请添加监控服务器"
    >
      <el-table-column
        prop="monitorName"
        label="网关实例"
        width="300"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.parentId === 0">{{ scope.row.monitorName }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="serviceId"
        label="serviceId"
        width="200"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.parentId > 0">{{ scope.row.serviceId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="接口名 (版本号)"
        width="200"
      >
        <template slot-scope="scope">
          {{ scope.row.name + (scope.row.version ? ' (' + scope.row.version + ')' : '') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="count"
        label="出错次数"
        width="100"
      />
      <el-table-column
        prop="errorMsg"
        label="报错信息"
        width="300"
      >
        <template v-if="scope.row.parentId > 0" slot-scope="scope">
          <div style="display: inline-block;" v-html="showErrorMsg(scope.row)"></div> <el-button type="text" size="mini" @click="onShowErrorDetail(scope.row)">详情</el-button>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="180"
      >
        <template slot-scope="scope">
          <el-button v-if="scope.row.parentId === 0 && scope.row.children" type="text" size="mini" @click="onClearLog(scope.row)">清空日志</el-button>
          <el-button v-if="scope.row.parentId === 0" type="text" size="mini" @click="onDelete(scope.row)">删除实例</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- dialog -->
    <el-dialog
      title="选择服务器实例"
      :visible.sync="logDialogInstanceVisible"
      :close-on-click-modal="false"
    >
      <el-form
        ref="logDialogForm"
        :model="logDialogFormData"
        :rules="rulesLog"
        label-width="150px"
        size="mini"
      >
        <el-form-item>
          <p style="color: #878787;">只能选择网关实例，其它实例不支持</p>
        </el-form-item>
        <el-form-item prop="instanceData" label="服务器实例">
          <el-select v-model="logDialogFormData.instanceData" value-key="id" style="width: 400px;">
            <el-option
              v-for="item in serviceData"
              :key="item.id"
              :label="item.serviceId + '(' + item.ipPort + ')'"
              :value="item"
              :disabled="isOptionDisabled(item)"
            >
              <span style="float: left">{{ item.serviceId }} <span v-if="isOptionDisabled(item)">(已添加)</span></span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.ipPort }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="logDialogInstanceVisible = false">取 消</el-button>
        <el-button type="primary" @click="onLogDialogSave">保 存</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="错误详情"
      :visible.sync="logDetailVisible"
      width="60%"
    >
      <div style="overflow-x: auto" v-html="errorMsgDetail"></div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="logDetailVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchFormData: {},
      tableData: [],
      serviceData: [],
      // 已经添加的实例
      addedInstanceList: [],
      logDialogFormData: {
        instanceData: null
      },
      logDialogInstanceVisible: false,
      logDetailVisible: false,
      rulesLog: {
        instanceData: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      },
      errorMsgDetail: ''
    }
  },
  created() {
    this.loadServiceInstance()
    this.loadTable()
  },
  methods: {
    loadServiceInstance: function() {
      this.post('service.instance.list', {}, function(resp) {
        this.serviceData = resp.data.filter(el => {
          return el.instanceId && el.instanceId.length > 0
        })
      })
      this.post('monitor.instance.list', {}, function(resp) {
        this.addedInstanceList = resp.data
      })
    },
    loadTable: function() {
      this.post('monitor.log.list', {}, function(resp) {
        this.tableData = this.buildTreeData(resp.data)
      })
    },
    isOptionDisabled: function(item) {
      const ipPort = item.ipPort
      const index = this.addedInstanceList.findIndex((value, index, arr) => {
        return value === ipPort
      })
      return index > -1
    },
    buildTreeData: function(data) {
      data.forEach(ele => {
        const parentId = ele.parentId
        if (parentId === 0) {
          // 是根元素 ,不做任何操作,如果是正常的for-i循环,可以直接continue.
        } else {
          // 如果ele是子元素的话 ,把ele扔到他的父亲的child数组中.
          data.forEach(d => {
            if (d.treeId === parentId) {
              let childArray = d.children
              if (!childArray) {
                childArray = []
              }
              childArray.push(ele)
              d.children = childArray
            }
          })
        }
      })
      // 去除重复元素
      data = data.filter(ele => ele.parentId === 0)
      return data
    },
    showErrorMsg: function(row) {
      const msg = row.errorMsg.replace(/\<br\>/g, '')
      return msg.substring(0, 30) + '...'
    },
    onAddServer: function() {
      this.logDialogInstanceVisible = true
    },
    onDelete: function(row) {
      this.confirm('确定要删除实例【' + row.monitorName + '】吗?', function(done) {
        this.post('monitor.instance.del', { id: row.rawId }, function(resp) {
          done()
          this.tip('删除成功')
          this.loadTable()
        })
      })
    },
    onClearLog: function(row) {
      this.confirm('确定要清空日志吗?', function(done) {
        this.post('monitor.log.clear', { id: row.rawId }, function(resp) {
          done()
          this.tip('清空成功')
          this.loadTable()
        })
      })
    },
    onShowErrorDetail: function(row) {
      this.errorMsgDetail = row.errorMsg
      this.logDetailVisible = true
    },
    onLogDialogSave: function() {
      this.$refs['logDialogForm'].validate((valid) => {
        if (valid) {
          const instanceData = this.logDialogFormData.instanceData
          this.post('monitor.instance.add', instanceData, function(resp) {
            this.logDialogInstanceVisible = false
            this.loadTable()
          })
        }
      })
    }
  }
}
</script>
