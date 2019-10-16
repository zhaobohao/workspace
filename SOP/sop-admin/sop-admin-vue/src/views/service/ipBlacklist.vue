<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
      <el-form-item label="IP">
        <el-input v-model="searchFormData.ip" :clearable="true" placeholder="输入IP" style="width: 250px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="loadTable">查询</el-button>
      </el-form-item>
    </el-form>
    <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onAdd">新增IP</el-button>
    <el-table
      :data="pageInfo.rows"
      border
      highlight-current-row
    >
      <el-table-column
        prop="ip"
        label="IP"
        width="200"
      />
      <el-table-column
        prop="remark"
        label="备注"
        width="300"
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
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      @close="resetForm('dialogForm')"
    >
      <el-form
        ref="dialogForm"
        :rules="dialogFormRules"
        :model="dialogFormData"
        label-width="120px"
        size="mini"
      >
        <el-form-item prop="ip" label="IP">
          <el-input v-show="dialogFormData.id === 0" v-model="dialogFormData.ip" />
          <span v-show="dialogFormData.id > 0">{{ dialogFormData.ip }}</span>
        </el-form-item>
        <el-form-item prop="remark" label="备注">
          <el-input v-model="dialogFormData.remark" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onDialogSave">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    const regexIP = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
    const ipValidator = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入IP'))
      } else {
        if (!regexIP.test(value)) {
          callback(new Error('IP格式不正确'))
        }
        callback()
      }
    }
    return {
      searchFormData: {
        ip: '',
        pageIndex: 1,
        pageSize: 10
      },
      pageInfo: {
        rows: [],
        total: 0
      },
      dialogVisible: false,
      dialogTitle: '',
      dialogFormData: {
        id: 0,
        ip: '',
        remark: ''
      },
      dialogFormRules: {
        ip: [
          { validator: ipValidator, trigger: 'blur' },
          { min: 1, max: 64, message: '长度在 1 到 64 个字符', trigger: 'blur' }
        ],
        remark: [
          { max: 100, message: '不能超过 100 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable: function() {
      this.post('ip.blacklist.page', this.searchFormData, function(resp) {
        this.pageInfo = resp.data
      })
    },
    onTableUpdate: function(row) {
      this.dialogTitle = '修改IP'
      this.dialogVisible = true
      this.$nextTick(() => {
        Object.assign(this.dialogFormData, row)
      })
    },
    onTableDelete: function(row) {
      this.confirm(`确认要移除IP【${row.ip}】吗？`, function(done) {
        const data = {
          id: row.id
        }
        this.post('ip.blacklist.del', data, function() {
          done()
          this.tip('删除成功')
          this.loadTable()
        })
      })
    },
    onDialogSave: function() {
      this.$refs.dialogForm.validate((valid) => {
        if (valid) {
          const uri = this.dialogFormData.id ? 'ip.blacklist.update' : 'ip.blacklist.add'
          this.post(uri, this.dialogFormData, function() {
            this.dialogVisible = false
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
      this.dialogTitle = '新增IP'
      this.dialogVisible = true
      this.dialogFormData.id = 0
    },
    onPageIndexChange: function(pageIndex) {
      this.searchFormData.pageIndex = pageIndex
      this.loadTable()
    }
  }
}
</script>
