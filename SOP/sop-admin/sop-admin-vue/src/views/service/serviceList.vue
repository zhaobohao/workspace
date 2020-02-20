<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
      <el-form-item label="serviceId">
        <el-input v-model="searchFormData.serviceId" :clearable="true" placeholder="serviceId" style="width: 250px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSearchTable">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      style="width: 100%;margin-bottom: 20px;"
      border
      row-key="id"
    >
      <el-table-column
        prop="serviceId"
        label="服务名称"
        width="200"
      >
        <template slot-scope="scope">
          <span v-html="renderServiceName(scope.row)"></span>
        </template>
      </el-table-column>
      <el-table-column
        prop="ipPort"
        label="IP端口"
        width="250"
      />
      <el-table-column
        prop="metadata"
        label="当前环境"
        width="100"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.status === 'UP'">
            <el-tag v-if="scope.row.parentId > 0 && scope.row.metadata.env === 'pre'" type="warning">预发布</el-tag>
            <el-tag v-if="scope.row.parentId > 0 && scope.row.metadata.env === 'gray'" type="info">灰度</el-tag>
            <el-tag v-if="scope.row.parentId > 0 && !scope.row.metadata.env" type="success">线上</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column
        prop="metadata"
        label="metadata"
        width="250"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.parentId > 0">{{ JSON.stringify(scope.row.metadata) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="服务状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.parentId > 0 && scope.row.status === 'UP'" type="success">正常</el-tag>
          <el-tag v-if="scope.row.parentId > 0 && scope.row.status === 'STARTING'" type="info">正在启动</el-tag>
          <el-tag v-if="scope.row.parentId > 0 && scope.row.status === 'UNKNOWN'">未知</el-tag>
          <el-tag v-if="scope.row.parentId > 0 && (scope.row.status === 'OUT_OF_SERVICE' || scope.row.status === 'DOWN')" type="danger">已禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        width="250"
      >
        <template slot-scope="scope">
          <div v-if="scope.row.serviceId.toLowerCase() !== 'sop-gateway'">
            <div v-if="scope.row.parentId === 0">
              <el-button type="text" size="mini" @click="onGrayConfigUpdate(scope.row)">设置灰度参数</el-button>
            </div>
            <div v-if="scope.row.parentId > 0">
            <span v-if="scope.row.status === 'UP'">
              <el-button v-if="scope.row.metadata.env === 'pre'" type="text" size="mini" @click="onEnvPreClose(scope.row)">结束预发布</el-button>
              <el-button v-if="scope.row.metadata.env === 'gray'" type="text" size="mini" @click="onEnvGrayClose(scope.row)">结束灰度</el-button>
              <el-button v-if="!scope.row.metadata.env" type="text" size="mini" @click="onEnvPreOpen(scope.row)">开启预发布</el-button>
              <el-button v-if="!scope.row.metadata.env" type="text" size="mini" @click="onEnvGrayOpen(scope.row)">开启灰度</el-button>
            </span>
              <span style="margin-left: 10px;">
              <el-button v-if="scope.row.status === 'UP'" type="text" size="mini" @click="onDisable(scope.row)">禁用</el-button>
              <el-button v-if="scope.row.status === 'OUT_OF_SERVICE'" type="text" size="mini" @click="onEnable(scope.row)">启用</el-button>
            </span>
            </div>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- dialog -->
    <el-dialog
      title="灰度设置"
      :visible.sync="grayDialogVisible"
      :close-on-click-modal="false"
      @close="resetForm('grayForm')"
    >
      <el-form
        ref="grayForm"
        :model="grayForm"
        :rules="grayFormRules"
        size="mini"
      >
        <el-form-item label="serviceId">
          {{ grayForm.serviceId }}
        </el-form-item>
        <el-tabs v-model="tabsActiveName" type="card">
          <el-tab-pane label="灰度用户" name="first">
            <el-alert
              title="可以是appId或IP地址，多个用英文逗号隔开"
              type="info"
              :closable="false"
              style="margin-bottom: 20px;"
            />
            <el-form-item prop="userKeyContent">
              <el-input
                v-model="grayForm.userKeyContent"
                placeholder="可以是appId或IP地址，多个用英文逗号隔开"
                type="textarea"
                :rows="6"
              />
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="接口配置" name="second">
            <el-form-item>
              <el-button type="text" @click="addNameVersion">新增灰度接口</el-button>
            </el-form-item>
            <table cellpadding="0" cellspacing="0">
              <tr
                v-for="(grayRouteConfig, index) in grayForm.grayRouteConfigList"
                :key="grayRouteConfig.key"
              >
                <td>
                  <el-form-item
                    :key="grayRouteConfig.key"
                    :prop="'grayRouteConfigList.' + index + '.oldRouteId'"
                    :rules="{required: true, message: '不能为空', trigger: ['blur', 'change']}"
                  >
                    老接口：
                    <el-select
                      v-model="grayRouteConfig.oldRouteId"
                      style="margin-right: 10px;"
                      @change="onChangeOldRoute(grayRouteConfig)"
                    >
                      <el-option
                        v-for="route in routeList"
                        :key="route.id"
                        :label="route.name + '(' + route.version + ')'"
                        :value="route.id"
                      />
                    </el-select>
                  </el-form-item>
                </td>
                <td>
                  <el-form-item
                    :key="grayRouteConfig.key + 1"
                    :prop="'grayRouteConfigList.' + index + '.newVersion'"
                    :rules="{required: true, message: '不能为空', trigger: ['blur', 'change']}"
                  >
                    灰度接口：
                    <el-select
                      v-model="grayRouteConfig.newVersion"
                      no-data-text="无数据"
                    >
                      <el-option
                        v-for="routeNew in getGraySelectData(grayRouteConfig.oldRouteId)"
                        :key="routeNew.id"
                        :label="routeNew.name + '(' + routeNew.version + ')'"
                        :value="routeNew.version"
                      />
                    </el-select>
                  </el-form-item>
                </td>
                <td style="vertical-align: baseline;">
                  <el-button v-show="grayForm.grayRouteConfigList.length > 1" type="text" @click.prevent="removeNameVersion(grayRouteConfig)">删除</el-button>
                </td>
              </tr>
            </table>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="grayDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onGrayConfigSave">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    const regex = /^\S+(,\S+)*$/
    const userKeyContentValidator = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('不能为空'))
      } else {
        if (!regex.test(value)) {
          callback(new Error('格式不正确'))
        }
        callback()
      }
    }
    return {
      searchFormData: {
        serviceId: ''
      },
      grayDialogVisible: false,
      grayForm: {
        serviceId: '',
        userKeyContent: '',
        onlyUpdateGrayUserkey: false,
        grayRouteConfigList: []
      },
      tabsActiveName: 'first',
      routeList: [],
      selectNameVersion: [],
      grayFormRules: {
        userKeyContent: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { validator: userKeyContentValidator, trigger: 'blur' }
        ]
      },
      tableData: []
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable: function() {
      this.post('service.instance.list', this.searchFormData, function(resp) {
        this.tableData = this.buildTreeData(resp.data)
      })
    },
    loadRouteList: function(serviceId) {
      this.post('route.list/1.2', { serviceId: serviceId.toLowerCase() }, function(resp) {
        this.routeList = resp.data
      })
    },
    getGraySelectData: function(oldRouteId) {
      return this.routeList.filter(routeNew => {
        return oldRouteId !== routeNew.id && oldRouteId.indexOf(routeNew.name) > -1
      })
    },
    buildTreeData: function(data) {
      data.forEach(ele => {
        const parentId = ele.parentId
        if (parentId === 0) {
          // 是根元素 ,不做任何操作,如果是正常的for-i循环,可以直接continue.
        } else {
          // 如果ele是子元素的话 ,把ele扔到他的父亲的child数组中.
          data.forEach(d => {
            if (d.id === parentId) {
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
    onSearchTable: function() {
      this.loadTable()
    },
    onDisable: function(row) {
      this.confirm(`确定要禁用 ${row.serviceId}(${row.ipPort}) 吗?`, function(done) {
        this.post('service.instance.offline', row, function() {
          this.tip('禁用成功')
          done()
          this.loadTableDelay()
        })
      })
    },
    onEnable: function(row) {
      this.confirm(`确定要启用 ${row.serviceId}(${row.ipPort}) 吗?`, function(done) {
        this.post('service.instance.online', row, function() {
          this.tip('启用成功')
          done()
          this.loadTableDelay()
        })
      })
    },
    doEnvOnline: function(row, callback) {
      this.post('service.instance.env.online', row, function() {
        callback && callback.call(this)
      })
    },
    onEnvPreOpen: function(row) {
      this.confirm(`确定要开启 ${row.serviceId}(${row.ipPort}) 预发布吗?`, function(done) {
        this.post('service.instance.env.pre.open', row, function() {
          this.tip('预发布成功')
          done()
          this.loadTableDelay()
        })
      })
    },
    onEnvPreClose: function(row) {
      this.confirm(`确定要结束 ${row.serviceId}(${row.ipPort}) 预发布吗?`, function(done) {
        this.doEnvOnline(row, function() {
          this.tip('操作成功')
          done()
          this.loadTableDelay()
        })
      })
    },
    onEnvGrayOpen: function(row) {
      this.confirm(`确定要开启 ${row.serviceId}(${row.ipPort}) 灰度吗?`, function(done) {
        this.post('service.instance.env.gray.open', row, function() {
          this.tip('开启成功')
          done()
          this.loadTableDelay()
        })
      })
    },
    onEnvGrayClose: function(row) {
      this.confirm(`确定要结束 ${row.serviceId}(${row.ipPort}) 灰度吗?`, function(done) {
        this.doEnvOnline(row, function() {
          this.tip('操作成功')
          done()
          this.loadTableDelay()
        })
      })
    },
    onGrayConfigUpdate: function(row) {
      const serviceId = row.serviceId
      this.loadRouteList(serviceId)
      this.post('service.gray.config.get', { serviceId: serviceId }, function(resp) {
        this.grayDialogVisible = true
        this.$nextTick(() => {
          const data = resp.data
          Object.assign(this.grayForm, {
            serviceId: serviceId,
            userKeyContent: data.userKeyContent || '',
            grayRouteConfigList: this.createGrayRouteConfigList(data.nameVersionContent)
          })
        })
      })
    },
    onGrayConfigSave: function() {
      this.$refs.grayForm.validate((valid) => {
        if (valid) {
          const nameVersionContents = []
          const grayRouteConfigList = this.grayForm.grayRouteConfigList
          for (let i = 0; i < grayRouteConfigList.length; i++) {
            const config = grayRouteConfigList[i]
            nameVersionContents.push(config.oldRouteId + '=' + config.newVersion)
          }
          this.grayForm.nameVersionContent = nameVersionContents.join(',')
          this.post('service.gray.config.save', this.grayForm, function() {
            this.grayDialogVisible = false
            this.tip('保存成功')
          })
        }
      })
    },
    createGrayRouteConfigList: function(nameVersionContent) {
      if (!nameVersionContent) {
        return [{
          oldRouteId: '',
          newVersion: '',
          key: Date.now()
        }]
      }
      const list = []
      const arr = nameVersionContent.split(',')
      for (let i = 0; i < arr.length; i++) {
        const el = arr[i]
        const elArr = el.split('=')
        list.push({
          oldRouteId: elArr[0],
          newVersion: elArr[1],
          key: Date.now()
        })
      }
      return list
    },
    onChangeOldRoute: function(config) {
      config.newVersion = ''
    },
    addNameVersion: function() {
      this.grayForm.grayRouteConfigList.push({
        oldRouteId: '',
        newVersion: '',
        key: Date.now()
      })
    },
    removeNameVersion: function(item) {
      const index = this.grayForm.grayRouteConfigList.indexOf(item)
      if (index !== -1) {
        this.grayForm.grayRouteConfigList.splice(index, 1)
      }
    },
    renderServiceName: function(row) {
      let instanceCount = ''
      // 如果是父节点
      if (row.parentId === 0) {
        const children = row.children || []
        const childCount = children.length
        const onlineCount = children.filter(el => {
          return el.status === 'UP'
        }).length
        instanceCount = `(${onlineCount}/${childCount})`
      }
      return row.serviceId + instanceCount
    },
    loadTableDelay: function() {
      const that = this
      setTimeout(function() {
        that.loadTable()
      }, 2000)
    }
  }
}
</script>
