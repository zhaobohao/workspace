<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
      <el-form-item label="appId">
        <el-input v-model="searchFormData.appKey" :clearable="true" placeholder="appId" style="width: 250px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="onSearchTable">查询</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onAdd">新增ISV</el-button>
    <el-table
      :data="pageInfo.list"
      border
      fit
      highlight-current-row
    >
      <el-table-column
        prop="id"
        label="ID"
        width="80"
      />
      <el-table-column
        prop="appKey"
        label="appId"
        width="250"
      />
      <el-table-column
        prop=""
        label="秘钥"
        width="80"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="onShowKeys(scope.row)">查看</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="roleList"
        label="角色"
        width="100"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <div v-html="roleRender(scope.row)"></div>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="80"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.status === 1" style="color:#67C23A">已启用</span>
          <span v-if="scope.row.status === 2" style="color:#F56C6C">已禁用</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="gmtCreate"
        label="添加时间"
        width="160"
      />
      <el-table-column
        prop="gmtModified"
        label="修改时间"
        width="160"
      />
      <el-table-column
        prop="remark"
        label="备注"
        width="120"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="操作"
        width="150"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="onTableUpdate(scope.row)">修改</el-button>
          <el-button type="text" size="mini" @click="onKeysUpdate(scope.row)">秘钥管理</el-button>
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
      :title="isvDialogTitle"
      :visible.sync="isvDialogVisible"
      :close-on-click-modal="false"
      @close="onIsvDialogClose"
    >
      <el-form
        ref="isvForm"
        :rules="rulesIsvForm"
        :model="isvDialogFormData"
        label-width="120px"
        size="mini"
      >
        <el-form-item label="appId">
          <span v-if="isvDialogFormData.id === 0" style="color: gray;">(系统自动生成)</span>
          <span v-else>{{ isvDialogFormData.appKey }}</span>
        </el-form-item>
        <el-form-item label="角色">
          <el-checkbox-group v-model="isvDialogFormData.roleCode">
            <el-checkbox v-for="item in roles" :key="item.roleCode" :label="item.roleCode">{{ item.description }}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="isvDialogFormData.remark" type="textarea" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="isvDialogFormData.status">
            <el-radio :label="1" name="status">启用</el-radio>
            <el-radio :label="2" name="status">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="isvDialogVisible = false">取 消</el-button>
        <el-button type="primary" :disabled="isSaveButtonDisabled" @click="onIsvDialogSave">保 存</el-button>
      </div>
    </el-dialog>
    <!--view keys dialog-->
    <el-dialog
      title="秘钥信息"
      :visible.sync="isvKeysDialogVisible"
      @close="resetForm('isvKeysFrom')"
    >
      <el-form
        ref="isvKeysFrom"
        :model="isvKeysFormData"
        label-width="160px"
        size="mini"
        class="key-view"
      >
        <el-form-item label="">
          <el-alert
            title="带 ★ 的分配给开发者"
            type="warning"
            :closable="false"
          />
        </el-form-item>
        <el-form-item :label="selfLabel('appId')">
          <span>{{ isvKeysFormData.appKey }}</span>
        </el-form-item>
        <el-form-item v-show="showKeys()" label="秘钥格式">
          <span v-if="isvKeysFormData.keyFormat === 1">PKCS8(JAVA适用)</span>
          <span v-if="isvKeysFormData.keyFormat === 2">PKCS1(非JAVA适用)</span>
        </el-form-item>
        <el-form-item v-show="isvKeysFormData.signType === 2" :label="selfLabel('secret')">
          <span>{{ isvKeysFormData.secret }}</span>
        </el-form-item>
        <el-tabs v-show="showKeys()" v-model="activeName" type="card" class="keyTabs">
          <el-tab-pane label="ISV公私钥" name="first">
            <el-form-item label="ISV公钥">
              <el-input v-model="isvKeysFormData.publicKeyIsv" type="textarea" readonly />
            </el-form-item>
            <el-form-item :label="selfLabel('ISV私钥')">
              <el-input v-model="isvKeysFormData.privateKeyIsv" type="textarea" readonly />
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="平台公私钥[可选]" name="second">
            <el-form-item label="平台公钥">
              <el-input v-model="isvKeysFormData.publicKeyPlatform" type="textarea" readonly />
            </el-form-item>
            <el-form-item prop="privateKeyPlatform" label="平台私钥">
              <el-input v-model="isvKeysFormData.privateKeyPlatform" type="textarea" readonly />
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isvKeysDialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<style>
  .gen-key {margin-bottom: 0px !important;}
  fieldset {border: 1px solid #ccc; color: gray;margin-left: 40px;margin-bottom: 20px;}
  fieldset label {width: 110px !important;}
  fieldset .el-form-item__content {margin-left: 110px !important;}
  .key-view .el-form-item {margin-bottom: 10px !important;}
  .keyTabs .el-tabs__header{margin-left: 70px;}
</style>
<script>
export default {
  data() {
    return {
      searchFormData: {
        appKey: '',
        pageIndex: 1,
        pageSize: 10
      },
      pageInfo: {
        list: [],
        total: 0
      },
      roles: [],
      // dialog
      isvDialogVisible: false,
      isvDialogTitle: '新增ISV',
      isvDialogFormData: {
        id: 0,
        status: 1,
        remark: '',
        roleCode: []
      },
      rulesIsvForm: {
        remark: [
          { min: 0, max: 100, message: '长度在 1 到 100 个字符', trigger: 'blur' }
        ]
      },
      activeName: 'first',
      isSaveButtonDisabled: false,
      isvKeysDialogVisible: false,
      isvKeysFormData: {
        appKey: '',
        secret: '',
        publicKeyIsv: '',
        privateKeyIsv: '',
        publicKeyPlatform: '',
        privateKeyPlatform: '',
        signType: ''
      }
    }
  },
  created() {
    this.loadTable()
    this.loadRouteRole()
  },
  methods: {
    loadTable() {
      this.post('isv.info.page', this.searchFormData, function(resp) {
        this.pageInfo = resp.data
      })
    },
    loadRouteRole: function() {
      if (this.roles.length === 0) {
        this.post('role.listall', {}, function(resp) {
          this.roles = resp.data
        })
      }
    },
    onShowKeys: function(row) {
      this.post('isv.keys.get', { appKey: row.appKey }, function(resp) {
        this.isvKeysDialogVisible = true
        this.$nextTick(() => {
          Object.assign(this.isvKeysFormData, resp.data)
        })
      })
    },
    onSearchTable: function() {
      this.loadTable()
    },
    onTableUpdate: function(row) {
      this.isvDialogTitle = '修改ISV'
      this.isvDialogVisible = true
      this.$nextTick(() => {
        this.post('isv.info.get', { id: row.id }, function(resp) {
          const isvInfo = resp.data
          const roleList = isvInfo.roleList
          const roleCode = []
          for (let i = 0; i < roleList.length; i++) {
            roleCode.push(roleList[i].roleCode)
          }
          isvInfo.roleCode = roleCode
          Object.assign(this.isvDialogFormData, isvInfo)
        })
      })
    },
    onKeysUpdate: function(row) {
      this.$router.push({ path: `keys?appKey=${row.appKey}` })
    },
    onSizeChange: function(size) {
      this.searchFormData.pageSize = size
      this.loadTable()
    },
    onPageIndexChange: function(pageIndex) {
      this.searchFormData.pageIndex = pageIndex
      this.loadTable()
    },
    onAdd: function() {
      this.isvDialogTitle = '新增ISV'
      this.isvDialogVisible = true
      this.$nextTick(() => {
        this.isvDialogFormData.id = 0
      })
    },
    onIsvDialogSave: function() {
      this.$refs.isvForm.validate((valid) => {
        if (valid) {
          this.isSaveButtonDisabled = true
          const uri = this.isvDialogFormData.id === 0 ? 'isv.info.add' : 'isv.info.update'
          this.post(uri, this.isvDialogFormData, function() {
            this.isvDialogVisible = false
            this.loadTable()
          })
        }
      })
    },
    onIsvDialogClose: function() {
      this.resetForm('isvForm')
      this.isSaveButtonDisabled = false
      this.isvDialogFormData.status = 1
      this.isvDialogFormData.roleCode = []
    },
    selfLabel: function(lab) {
      return '★ ' + lab
    },
    roleRender: function(row) {
      const html = []
      const roleList = row.roleList
      for (let i = 0; i < roleList.length; i++) {
        html.push(roleList[i].description)
      }
      return html.join(', ')
    },
    showKeys: function() {
      return this.isvKeysFormData.signType === 1
    }
  }
}
</script>
