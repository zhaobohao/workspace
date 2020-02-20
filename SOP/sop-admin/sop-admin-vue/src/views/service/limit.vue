<template>
  <div class="app-container">
    <el-container>
      <el-aside style="min-height: 300px;width: 250px;">
        <el-input v-model="filterText" prefix-icon="el-icon-search" placeholder="搜索服务..." style="margin-bottom:20px;" size="mini" clearable />
        <el-tree
          ref="tree2"
          :data="treeData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          :highlight-current="true"
          :expand-on-click-node="false"
          empty-text="无数据"
          node-key="id"
          class="filter-tree"
          default-expand-all
          @node-click="onNodeClick"
        >
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <div>
              <el-tooltip v-show="data.custom" content="自定义服务" class="item" effect="light" placement="left">
                <i class="el-icon-warning-outline"></i>
              </el-tooltip>
              <span v-if="data.label.length < serviceTextLimitSize">{{ data.label }}</span>
              <span v-else>
                <el-tooltip :content="data.label" class="item" effect="light" placement="right">
                  <span>{{ data.label.substring(0, serviceTextLimitSize) + '...' }}</span>
                </el-tooltip>
              </span>
            </div>
            <span>
              <el-button
                v-if="data.custom === 1"
                type="text"
                size="mini"
                icon="el-icon-delete"
                title="删除服务"
                @click.stop="() => onDelService(data)"/>
            </span>
          </span>
        </el-tree>
      </el-aside>
      <el-main style="padding-top:0">
        <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
          <el-form-item label="路由ID">
            <el-input v-model="searchFormData.routeId" placeholder="接口名，支持模糊查询" clearable />
          </el-form-item>
          <el-form-item label="AppKey">
            <el-input v-model="searchFormData.appKey" placeholder="AppKey，支持模糊查询" clearable />
          </el-form-item>
          <el-form-item label="IP">
            <el-input v-model="searchFormData.limitIp" placeholder="ip，支持模糊查询" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="onSearchTable">查询</el-button>
          </el-form-item>
        </el-form>
        <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onAdd">新增限流</el-button>
        <el-table
          :data="pageInfo.list"
          border
        >
          <el-table-column
            prop="limitKey"
            label="限流维度"
            width="400"
          >
            <template slot-scope="scope">
              <div v-html="limitRender(scope.row)"></div>
            </template>
          </el-table-column>
          <el-table-column
            prop="limitType"
            label="限流策略"
            width="120"
          >
            <template slot="header" slot-scope>
              限流策略 <i class="el-icon-question" style="cursor: pointer" @click="onLimitTypeTipClick"></i>
            </template>
            <template slot-scope="scope">
              <span v-if="scope.row.limitType === 1">窗口策略</span>
              <span v-if="scope.row.limitType === 2">令牌桶策略</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="info"
            label="限流信息"
            width="250"
          >
            <template slot-scope="scope">
              <span v-html="infoRender(scope.row)"></span>
            </template>
          </el-table-column>
          <el-table-column
            prop="limitStatus"
            label="状态"
            width="80"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.limitStatus === 1" style="color:#67C23A">已开启</span>
              <span v-if="scope.row.limitStatus === 0" style="color:#909399">已关闭</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="orderIndex"
            label="排序"
            width="80"
          />
          <el-table-column
            prop="remark"
            label="备注"
            width="150"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            prop="gmtCreate"
            label="创建时间"
            width="160"
          />
          <el-table-column
            prop="gmtModified"
            label="修改时间"
            width="160"
          />
          <el-table-column
            label="操作"
            fixed="right"
            width="80"
          >
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="onTableUpdate(scope.row)">修改</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          style="margin-top: 5px"
          :current-page="searchFormData.pageIndex"
          :page-size="searchFormData.pageSize"
          :page-sizes="[5, 10, 20, 40]"
          :total="pageInfo.total"
          layout="total, sizes, prev, pager, next"
          @size-change="onSizeChange"
          @current-change="onPageIndexChange"
        />
        <!-- dialog -->
        <el-dialog
          :title="dlgTitle"
          :visible.sync="limitDialogVisible"
          :close-on-click-modal="false"
          @close="onLimitDialogClose"
        >
          <el-form
            ref="limitDialogForm"
            :model="limitDialogFormData"
            :rules="rulesLimit"
            label-width="150px"
            size="mini"
          >
            <el-form-item label="限流维度" prop="typeKey">
              <el-checkbox-group v-model="limitDialogFormData.typeKey">
                <el-checkbox v-model="limitDialogFormData.typeKey[0]" :label="1" name="typeKey">路由ID</el-checkbox>
                <el-checkbox v-model="limitDialogFormData.typeKey[1]" :label="2" name="typeKey">AppKey</el-checkbox>
                <el-checkbox v-model="limitDialogFormData.typeKey[2]" :label="3" name="typeKey">IP</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item v-show="checkTypeKey(1)" prop="routeId" label="路由ID" :rules="checkTypeKey(1) ? rulesLimit.routeId : []">
              <el-select v-model="limitDialogFormData.routeId" filterable placeholder="可筛选" style="width: 300px;">
                <el-option
                  v-for="item in routeList"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id"
                >
                  <span style="float: left">{{ item.name }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.version }}</span>
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-show="checkTypeKey(2)" prop="appKey" label="AppKey" :rules="checkTypeKey(2) ? rulesLimit.appKey : []">
              <el-input v-model="limitDialogFormData.appKey" placeholder="需要限流的appKey" />
            </el-form-item>
            <el-form-item v-show="checkTypeKey(3)" label="限流IP" prop="limitIp" :rules="checkTypeKey(3) ? rulesLimit.ip : []">
              <el-input v-model="limitDialogFormData.limitIp" type="textarea" :rows="2" placeholder="多个用英文逗号隔开" />
            </el-form-item>
            <el-form-item label="限流策略">
              <el-radio-group v-model="limitDialogFormData.limitType">
                <el-radio :label="1">窗口策略</el-radio>
                <el-radio :label="2">令牌桶策略</el-radio>
              </el-radio-group>
              <i class="el-icon-question limit-tip" @click="onLimitTypeTipClick"></i>
            </el-form-item>
            <el-form-item label="开启状态">
              <el-switch
                v-model="limitDialogFormData.limitStatus"
                active-color="#13ce66"
                inactive-color="#ff4949"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
            </el-form-item>
            <el-form-item label="排序" prop="orderIndex">
              <el-input-number v-model="limitDialogFormData.orderIndex" controls-position="right" :min="0" />
              <el-tooltip class="item" content="值小优先执行" placement="top">
                <i class="el-icon-question limit-tip"></i>
              </el-tooltip>
            </el-form-item>
            <el-form-item v-show="isWindowType()" label="请求数" prop="execCountPerSecond" :rules="isWindowType() ? rulesLimit.execCountPerSecond : []">
              每 <el-input-number v-model="limitDialogFormData.durationSeconds" controls-position="right" :min="1" /> 秒可处理
              <el-input-number v-model="limitDialogFormData.execCountPerSecond" controls-position="right" :min="1" /> 个请求
            </el-form-item>
            <el-form-item v-show="isTokenType()" label="令牌桶容量" prop="tokenBucketCount" :rules="isTokenType() ? rulesLimit.tokenBucketCount : []">
              <el-input-number v-model="limitDialogFormData.tokenBucketCount" controls-position="right" :min="1" />
            </el-form-item>
            <el-form-item label="备注" prop="remark">
              <el-input v-model="limitDialogFormData.remark" type="textarea" :rows="2" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="limitDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="onLimitDialogSave">保 存</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>
<style>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  .el-input.is-disabled .el-input__inner {color: #909399;}
  .el-radio__input.is-disabled+span.el-radio__label {color: #909399;}
</style>
<script>
export default {
  data() {
    return {
      serviceTextLimitSize: 20,
      filterText: '',
      treeData: [],
      tableData: [],
      serviceId: '',
      searchFormData: {
        pageIndex: 1,
        pageSize: 5
      },
      pageInfo: {
        list: [],
        total: 0
      },
      routeList: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      // dialog
      dlgTitle: '设置限流',
      limitDialogVisible: false,
      limitDialogFormData: {
        id: 0,
        routeId: '',
        appKey: '',
        limitIp: '',
        limitKey: '',
        execCountPerSecond: 5,
        durationSeconds: 1,
        limitCode: '',
        limitMsg: '',
        tokenBucketCount: 5,
        limitStatus: 0, // 0: 停用，1：启用
        limitType: 1,
        orderIndex: 0,
        remark: '',
        typeKey: []
      },
      rulesLimit: {
        typeKey: [
          { type: 'array', required: true, message: '请至少选择一个', trigger: 'change' }
        ],
        routeId: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        appKey: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        ip: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: 'blur' }
        ],
        // window
        execCountPerSecond: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        // token
        tokenBucketCount: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        orderIndex: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        remark: [
          { max: 128, message: '长度不能超过128字符', trigger: 'blur' }
        ]
      }

    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    this.loadTree()
  },
  methods: {
    // 加载树
    loadTree: function() {
      this.post('registry.service.list', {}, function(resp) {
        const respData = resp.data
        this.treeData = this.convertToTreeData(respData, 0)
      })
    },
    // 树搜索
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 树点击事件
    onNodeClick(data, node, tree) {
      if (data.parentId) {
        this.serviceId = data.label
        this.searchFormData.serviceId = this.serviceId
        this.loadTable()
        this.loadRouteList(this.serviceId)
      }
    },
    /**
     * 数组转成树状结构
     * @param data 数据结构 [{
        "_parentId": 14,
        "gmtCreate": "2019-01-15 09:44:38",
        "gmtUpdate": "2019-01-15 09:44:38",
        "id": 15,
        "isShow": 1,
        "name": "用户注册",
        "orderIndex": 10000,
        "parentId": 14
    },...]
     * @param pid 初始父节点id，一般是0
     * @return 返回结果 [{
      label: '一级 1',
      children: [{
        label: '二级 1-1',
        children: [{
          label: '三级 1-1-1'
        }]
      }]
    }
     */
    convertToTreeData(data, pid) {
      const result = []
      const root = {
        label: data.length === 0 ? '无服务' : '服务列表',
        parentId: pid
      }
      const children = []
      for (let i = 0; i < data.length; i++) {
        const child = { parentId: 1, label: data[i] }
        children.push(child)
      }
      root.children = children
      result.push(root)
      return result
    },
    // table
    loadTable: function() {
      this.post('config.limit.list', this.searchFormData, function(resp) {
        this.pageInfo = resp.data
      })
    },
    loadRouteList: function(serviceId) {
      this.post('route.list/1.2', { serviceId: serviceId }, function(resp) {
        this.routeList = resp.data
      })
    },
    onAdd: function() {
      if (!this.serviceId) {
        this.tip('请选择服务', 'info')
        return
      }
      this.dlgTitle = '新增限流'
      this.limitDialogFormData.id = 0
      this.limitDialogVisible = true
    },
    onSearchTable: function() {
      this.loadTable()
    },
    onTableUpdate: function(row) {
      this.dlgTitle = '修改限流'
      this.limitDialogVisible = true
      this.$nextTick(() => {
        Object.assign(this.limitDialogFormData, row)
        if (row.routeId) {
          this.limitDialogFormData.typeKey.push(1)
        }
        if (row.appKey) {
          this.limitDialogFormData.typeKey.push(2)
        }
        if (row.limitIp) {
          this.limitDialogFormData.typeKey.push(3)
        }
      })
    },
    onLimitDialogClose: function() {
      this.resetForm('limitDialogForm')
      this.limitDialogFormData.limitStatus = 0
    },
    infoRender: function(row) {
      if (row.limitType === 1) {
        const durationSeconds = row.durationSeconds
        return `每 ${durationSeconds} 秒可处理 ${row.execCountPerSecond} 个请求`
      } else if (row.limitType === 2) {
        return `令牌桶容量：${row.tokenBucketCount}`
      }
    },
    onLimitDialogSave: function() {
      this.$refs['limitDialogForm'].validate((valid) => {
        if (valid) {
          this.cleanCheckboxData()
          this.limitDialogFormData.serviceId = this.serviceId
          const uri = this.limitDialogFormData.id ? 'config.limit.update' : 'config.limit.add'
          this.post(uri, this.limitDialogFormData, function(resp) {
            this.limitDialogVisible = false
            this.loadTable()
          })
        }
      })
    },
    cleanCheckboxData: function() {
      // 如果没有勾选则清空
      if (!this.checkTypeKey(1)) {
        this.limitDialogFormData.routeId = ''
      }
      if (!this.checkTypeKey(2)) {
        this.limitDialogFormData.appKey = ''
      }
      if (!this.checkTypeKey(3)) {
        this.limitDialogFormData.limitIp = ''
      }
    },
    onLimitTypeTipClick: function() {
      const windowRemark = '窗口策略：每秒处理固定数量的请求，超出请求数量返回错误信息。'
      const tokenRemark = '令牌桶策略：每秒放置固定数量的令牌数，每个请求进来后先去拿令牌，拿到了令牌才能继续，拿不到则等候令牌重新生成了再拿。'
      const content = windowRemark + '<br>' + tokenRemark
      this.$alert(content, '限流策略', {
        dangerouslyUseHTMLString: true
      })
    },
    onSizeChange: function(size) {
      this.searchFormData.pageSize = size
      this.loadTable()
    },
    onPageIndexChange: function(pageIndex) {
      this.searchFormData.pageIndex = pageIndex
      this.loadTable()
    },
    checkTypeKey: function(val) {
      return this.limitDialogFormData.typeKey.find((value, index, arr) => {
        return value === val
      })
    },
    isWindowType: function() {
      return this.limitDialogFormData.limitType === 1
    },
    isTokenType: function() {
      return this.limitDialogFormData.limitType === 2
    },
    limitRender: function(row) {
      const html = []
      const val = []
      if (row.routeId) {
        val.push(row.routeId)
        html.push('路由ID')
      }
      if (row.appKey) {
        val.push(row.appKey)
        html.push('AppKey')
      }
      if (row.limitIp) {
        val.push(row.limitIp)
        html.push('IP')
      }
      return val.join(' + ') + '<br>(' + html.join(' + ') + ')'
    }
  }
}
</script>
<style scoped>
  .limit-tip {
    cursor: pointer;
    margin-left: 10px;
  }
</style>
