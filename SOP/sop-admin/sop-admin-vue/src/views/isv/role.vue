<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
      <el-form-item label="角色码">
        <el-input v-model="searchFormData.roleCode" :clearable="true" placeholder="输入角色码" style="width: 250px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="loadTable">查询</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onAdd">新增角色</el-button>
    <el-table
      :data="pageInfo.rows"
      border
      highlight-current-row
    >
      <el-table-column
        prop="roleCode"
        label="角色码"
        width="200"
      />
      <el-table-column
        prop="description"
        label="角色描述"
        width="200"
      />
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
        label="操作"
        width="150"
      >
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="onTableUpdate(scope.row)">修改</el-button>
          <el-button type="text" size="mini" @click="onTableDelete(scope.row)">删除</el-button>
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
    <!--dialog-->
    <el-dialog
      :title="roleDialogTitle"
      :visible.sync="roleDialogVisible"
      :close-on-click-modal="false"
      @close="resetForm('roleForm')"
    >
      <el-form
        ref="roleForm"
        :rules="roleDialogFormRules"
        :model="roleDialogFormData"
        label-width="120px"
        size="mini"
      >
        <el-form-item prop="roleCode" label="角色码">
          <el-input v-show="roleDialogFormData.id === 0" v-model="roleDialogFormData.roleCode" />
          <span v-show="roleDialogFormData.id > 0">{{ roleDialogFormData.roleCode }}</span>
        </el-form-item>
        <el-form-item prop="description" label="角色描述">
          <el-input v-model="roleDialogFormData.description" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onRoleDialogSave">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchFormData: {
        roleCode: '',
        pageIndex: 1,
        pageSize: 10
      },
      pageInfo: {
        rows: [],
        total: 0
      },
      roleDialogVisible: false,
      roleDialogTitle: '',
      roleDialogFormData: {
        id: 0,
        roleCode: '',
        description: ''
      },
      roleDialogFormRules: {
        roleCode: [
          { required: true, message: '不能为空', trigger: 'blur' },
          { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
        ],
        description: [
          { max: 64, message: '不能超过 64 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable: function() {
      this.post('role.page', this.searchFormData, function(resp) {
        this.pageInfo = resp.data
      })
    },
    onTableUpdate: function(row) {
      this.roleDialogTitle = '修改角色'
      this.roleDialogVisible = true
      this.$nextTick(() => {
        Object.assign(this.roleDialogFormData, row)
      })
    },
    onTableDelete: function(row) {
      this.confirm(`确认要删除角色【${row.roleCode}】吗？`, function(done) {
        const data = {
          id: row.id
        }
        this.post('role.del', data, function() {
          done()
          this.tip('删除成功')
          this.loadTable()
        })
      })
    },
    onRoleDialogSave: function() {
      this.$refs.roleForm.validate((valid) => {
        if (valid) {
          const uri = this.roleDialogFormData.id ? 'role.update' : 'role.add'
          this.post(uri, this.roleDialogFormData, function() {
            this.roleDialogVisible = false
            this.loadTable()
          })
        }
      })
    },
    onSizeChange: function(size) {
      this.searchFormData.pageSize = size
      this.loadTable()
    },
    onAdd: function() {
      this.roleDialogTitle = '新增角色'
      this.roleDialogVisible = true
      this.roleDialogFormData.id = 0
    },
    onPageIndexChange: function(pageIndex) {
      this.searchFormData.pageIndex = pageIndex
      this.loadTable()
    }
  }
}
</script>
