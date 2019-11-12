<template>
  <div class="app-container">
    <el-container>
      <el-aside style="min-height: 300px;width: 250px;">
        <el-button
          type="primary"
          plain
          size="mini"
          icon="el-icon-plus"
          style="display: none;"
          @click.stop="addService"
        >
          新建服务
        </el-button>
        <el-input
          v-model="filterText"
          prefix-icon="el-icon-search"
          placeholder="搜索服务..."
          style="margin-bottom:10px;margin-top:10px;"
          size="mini"
          clearable
        />
        <el-tree
          ref="serviceTree"
          :data="treeData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          :highlight-current="true"
          :expand-on-click-node="false"
          empty-text="无数据"
          node-key="serviceId"
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
                @click.stop="() => onDelService(data)"
              />
            </span>
          </span>
        </el-tree>
      </el-aside>
      <el-main style="padding-top:0">
        <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
          <el-form-item label="路由名称">
            <el-input v-model="searchFormData.id" :clearable="true" placeholder="输入接口名或版本号" />
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="searchFormData.permission">授权接口</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="searchFormData.needToken">需要token</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="onSearchTable">查询</el-button>
          </el-form-item>
        </el-form>
        <el-button
          v-show="isCustomService"
          type="primary"
          size="mini"
          icon="el-icon-plus"
          @click.stop="addRoute"
        >
          新建路由
        </el-button>
        <el-table
          :data="pageInfo.rows"
          border
          highlight-current-row
          style="margin-top: 10px;"
        >
          <el-table-column
            prop="name"
            label="接口名 (版本号)"
            width="350"
          >
            <template slot-scope="scope">
              {{ scope.row.name + (scope.row.version ? ' (' + scope.row.version + ')' : '') }}
            </template>
          </el-table-column>
          <el-table-column
            prop="roles"
            label="访问权限"
            width="150"
          >
            <template slot-scope="scope">
              <span v-html="roleRender(scope.row)"></span>
            </template>
          </el-table-column>
          <el-table-column
            prop="ignoreValidate"
            label="签名校验"
            width="80"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.ignoreValidate === 0">校验</span>
              <span v-if="scope.row.ignoreValidate === 1" style="color:#E6A23C">不校验</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="mergeResult"
            label="统一格式输出"
            width="120"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.mergeResult === 1">是</span>
              <span v-if="scope.row.mergeResult === 0" style="color:#E6A23C">否</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="needToken"
            label="需要token"
            width="100"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.needToken === 1" style="font-weight: bold;color: #303133;">是</span>
              <span v-if="scope.row.needToken === 0">否</span>
            </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            width="80"
          >
            <template slot-scope="scope">
              <span v-if="scope.row.status === 0" style="color:#E6A23C">待审核</span>
              <span v-if="scope.row.status === 1" style="color:#67C23A">已启用</span>
              <span v-if="scope.row.status === 2" style="color:#F56C6C">已禁用</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            width="100"
          >
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="onTableUpdate(scope.row)">修改</el-button>
              <el-button v-if="scope.row.permission" type="text" size="mini" @click="onTableAuth(scope.row)">授权</el-button>
              <el-button v-if="scope.row.custom" type="text" size="mini" @click="onTableDel(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          background
          style="margin-top: 5px"
          :current-page="searchFormData.pageIndex"
          :page-size="searchFormData.pageSize"
          :page-sizes="[10, 20, 40]"
          :total="pageInfo.total"
          layout="total, sizes, prev, pager, next"
          @size-change="onSizeChange"
          @current-change="onPageIndexChange"
        />
      </el-main>
    </el-container>
    <!-- route dialog -->
    <el-dialog
      :title="routeDialogTitle"
      :visible.sync="routeDialogVisible"
      :close-on-click-modal="false"
      @close="onCloseRouteDialog"
    >
      <el-form
        ref="routeDialogFormRef"
        :model="routeDialogFormData"
        :rules="routeDialogFormRules"
        label-width="180px"
        size="mini"
      >
        <el-input v-show="false" v-model="routeDialogFormData.id" />
        <el-form-item label="接口名 (版本号)">
          {{ routeDialogFormData.name + (routeDialogFormData.version ? ' (' + routeDialogFormData.version + ')' : '') }}
        </el-form-item>
        <el-form-item label="签名校验">
          {{ routeDialogFormData.ignoreValidate ? '不校验' : '校验' }}
        </el-form-item>
        <el-form-item label="统一格式输出">
          {{ routeDialogFormData.mergeResult === 1 ? '是' : '否' }}
        </el-form-item>
        <el-form-item label="需要token">
          {{ routeDialogFormData.needToken === 1 ? '是' : '否' }}
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="routeDialogFormData.status">
            <el-radio :label="1" name="status">启用</el-radio>
            <el-radio :label="2" name="status" style="color:#F56C6C">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="routeDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onRouteDialogSave">保 存</el-button>
      </div>
    </el-dialog>
    <!-- auth dialog -->
    <el-dialog
      title="路由授权"
      :visible.sync="authDialogVisible"
      :close-on-click-modal="false"
    >
      <el-form
        :model="authDialogFormData"
        label-width="120px"
        size="mini"
      >
        <el-form-item label="路由ID">
          <span>{{ authDialogFormData.routeId }}</span>
        </el-form-item>
        <el-form-item label="角色">
          <el-checkbox-group v-model="authDialogFormData.roleCode">
            <el-checkbox v-for="item in roles" :key="item.roleCode" :label="item.roleCode">{{ item.description }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="authDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onAuthDialogSave">保 存</el-button>
      </div>
    </el-dialog>
    <!--添加服务-->
    <el-dialog
      title="添加服务"
      :visible.sync="addServiceDialogVisible"
      :close-on-click-modal="false"
      @close="closeAddServiceDlg"
    >
      <el-form
        ref="addServiceForm"
        :model="addServiceForm"
        :rules="addServiceFormRules"
        label-width="200px"
      >
        <el-form-item label="服务名（serviceId）" prop="serviceId">
          <el-input v-model="addServiceForm.serviceId" placeholder="服务名，如：order-service" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addServiceDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onAddService">确 定</el-button>
      </span>
    </el-dialog>
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
      isCustomService: false,
      searchFormData: {
        id: '',
        serviceId: '',
        permission: 0,
        needToken: 0,
        pageIndex: 1,
        pageSize: 10
      },
      pageInfo: {
        rows: [],
        total: 0
      },
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      routeDialogTitle: '修改路由',
      // dialog
      routeDialogFormData: {
        id: '',
        name: '',
        version: '1.0',
        uri: '',
        path: '',
        status: 1,
        mergeResult: 1,
        ignoreValidate: 0
      },
      routeDialogFormRules: {
        name: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        version: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        uri: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        path: [
          { min: 0, max: 100, message: '长度不能超过 100 个字符', trigger: 'blur' }
        ]
      },
      routeDialogVisible: false,
      roles: [],
      authDialogFormData: {
        routeId: '',
        roleCode: []
      },
      authDialogVisible: false,
      // addService
      addServiceDialogVisible: false,
      addServiceForm: {
        serviceId: ''
      },
      addServiceFormRules: {
        serviceId: [
          { required: true, message: '请输入服务名称', trigger: 'blur' },
          { min: 1, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.serviceTree.filter(val)
    }
  },
  created() {
    this.loadTree()
    this.loadRouteRole()
  },
  methods: {
    // 加载树
    loadTree: function() {
      this.post('registry.service.list', {}, function(resp) {
        const respData = resp.data
        this.treeData = this.convertToTreeData(respData, 0)
        this.$nextTick(() => {
          // 高亮已选中的
          if (this.serviceId) {
            this.$refs.serviceTree.setCurrentKey(this.serviceId)
          }
        })
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
        this.isCustomService = Boolean(data.custom)
        this.loadTable()
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
    loadTable: function(param) {
      if (!this.searchFormData.serviceId) {
        this.tip('请选择一个服务', 'error')
        return
      }
      const postData = param || this.searchFormData
      this.post('route.page', postData, function(resp) {
        this.pageInfo = resp.data
      })
    },
    onSearchTable: function() {
      this.searchFormData.pageIndex = 1
      this.loadTable()
    },
    onTableUpdate: function(row) {
      this.routeDialogTitle = '修改路由'
      this.routeDialogVisible = true
      this.$nextTick(() => {
        Object.assign(this.routeDialogFormData, row)
      })
    },
    onTableAuth: function(row) {
      this.authDialogFormData.routeId = row.id
      const searchData = { id: row.id, serviceId: this.serviceId }
      this.post('route.role.get', searchData, function(resp) {
        const roleList = resp.data
        const roleCodes = []
        for (let i = 0; i < roleList.length; i++) {
          roleCodes.push(roleList[i].roleCode)
        }
        this.authDialogFormData.roleCode = roleCodes
        this.authDialogVisible = true
      })
    },
    onTableDel: function(row) {
      this.confirm(`确认要删除路由【${row.id}】吗？`, function(done) {
        const data = {
          serviceId: this.serviceId,
          id: row.id
        }
        this.post('route.del', data, function() {
          done()
          this.tip('删除成功')
          this.loadTable()
        })
      })
    },
    onCloseRouteDialog: function() {
      this.resetForm('routeDialogFormRef')
    },
    routePropDisabled: function() {
      if (!this.routeDialogFormData.id) {
        return false
      }
      return !this.isCustomService
    },
    loadRouteRole: function() {
      if (this.roles.length === 0) {
        this.post('role.listall', {}, function(resp) {
          this.roles = resp.data
        })
      }
    },
    addRoute: function() {
      this.routeDialogTitle = '新建路由'
      this.routeDialogVisible = true
      this.$nextTick(() => {
        Object.assign(this.routeDialogFormData, {
          id: ''
        })
      })
    },
    roleRender: function(row) {
      if (!row.permission) {
        return '（公开）'
      }
      const html = []
      const roles = row.roles
      for (let i = 0; i < roles.length; i++) {
        html.push(roles[i].description)
      }
      return html.length > 0 ? html.join(', ') : '<span class="x-red">未授权</span>'
    },
    onRouteDialogSave: function() {
      this.$refs.routeDialogFormRef.validate((valid) => {
        if (valid) {
          const uri = this.routeDialogFormData.id ? 'route.update' : 'route.add'
          this.routeDialogFormData.serviceId = this.serviceId
          this.post(uri, this.routeDialogFormData, function() {
            this.routeDialogVisible = false
            this.loadTable()
          })
        }
      })
    },
    onAuthDialogSave: function() {
      this.post('route.role.update', this.authDialogFormData, function() {
        this.authDialogVisible = false
        this.loadTable()
      })
    },
    addService: function() {
      this.addServiceDialogVisible = true
    },
    closeAddServiceDlg: function() {
      this.$refs.addServiceForm.resetFields()
    },
    onAddService: function() {
      this.$refs.addServiceForm.validate((valid) => {
        if (valid) {
          this.post('service.custom.add', this.addServiceForm, function(resp) {
            this.addServiceDialogVisible = false
            this.tip('添加成功')
            this.loadTree()
          })
        }
      })
    },
    onDelService: function(data) {
      const serviceId = data.serviceId
      this.confirm('确认要删除服务' + serviceId + '吗，【对应的路由配置会一起删除】', function(done) {
        const postData = {
          serviceId: serviceId
        }
        this.post('service.custom.del', postData, function() {
          done()
          this.tip('删除成功')
          this.loadTree()
        })
      })
    },
    onSizeChange: function(size) {
      this.searchFormData.pageSize = size
      this.loadTable()
    },
    onPageIndexChange: function(pageIndex) {
      this.searchFormData.pageIndex = pageIndex
      this.loadTable()
    }
  }
}
</script>
