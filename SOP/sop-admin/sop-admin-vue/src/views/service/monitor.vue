<template>
  <div class="app-container">
    <el-form :inline="true" :model="searchFormData" class="demo-form-inline" size="mini">
      <el-form-item label="接口名">
        <el-input v-model="searchFormData.routeId" :clearable="true" placeholder="输入接口名或版本号" style="width: 250px;" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="loadTable">搜索</el-button>
      </el-form-item>
    </el-form>
    <el-alert
      title="监控数据保存在网关服务器，重启网关数据会清空。"
      type="info"
      :closable="false"
      style="margin-bottom: 10px"
    />
    <el-table
      :data="tableData"
      border
      :default-expand-all="false"
      row-key="id"
      height="500"
      empty-text="无数据"
    >
      <el-table-column
        fixed
        prop="instanceId"
        label="网关实例"
        width="200"
      >
        <template slot-scope="scope">
          <span v-if="!scope.row.children">{{ scope.row.instanceId }}</span>
        </template>
      </el-table-column>
      <el-table-column
        fixed
        prop="name"
        label="接口名 (版本号)"
        width="280"
      >
        <template slot-scope="scope">
          {{ scope.row.name + (scope.row.version ? ' (' + scope.row.version + ')' : '') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="serviceId"
        label="serviceId"
        width="170"
      />
      <el-table-column
        prop="maxTime"
        label="最大耗时(ms)"
        width="125"
      >
        <template slot="header">
          最大耗时(ms)
          <i
            class="el-icon-question"
            style="cursor: pointer"
            @click="$alert('耗时计算：签名验证成功后开始，微服务返回结果后结束')"
          ></i>
        </template>
      </el-table-column>
      <el-table-column
        prop="minTime"
        label="最小耗时(ms)"
        width="120"
      />
      <el-table-column
        prop="avgTime"
        label="平均耗时(ms)"
        width="120"
      />
      <el-table-column
        prop="totalCount"
        label="总调用次数"
        width="100"
      />
      <el-table-column
        prop="successCount"
        label="成功次数"
        width="100"
      />
      <el-table-column
        prop="errorCount"
        label="失败次数"
        width="100"
      >
        <template slot="header">
          失败次数
          <i
            class="el-icon-question"
            style="cursor: pointer"
            @click="$alert('只统计微服务返回的未知错误，JSR-303验证错误算作成功')"
          ></i>
        </template>
        <template slot-scope="scope">
          <el-link
            v-if="scope.row.errorCount > 0"
            :underline="false"
            type="danger"
            style="text-decoration: underline;"
            @click="onShowErrorDetail(scope.row)"
          >
            {{ scope.row.errorCount }}
          </el-link>
          <span v-if="scope.row.errorCount === 0">0</span>
        </template>
      </el-table-column>
    </el-table>
    <!-- dialog -->
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
      searchFormData: {
        routeId: ''
      },
      tableData: [],
      logDetailVisible: false,
      errorMsgDetail: ''
    }
  },
  created() {
    this.loadTable()
  },
  methods: {
    loadTable: function() {
      this.post('monitor.data.list', this.searchFormData, function(resp) {
        const data = resp.data
        this.tableData = data.monitorInfoData
      })
    },
    onShowErrorDetail: function(row) {
      const errorMsgList = row.errorMsgList
      this.errorMsgDetail = errorMsgList.length > 0 ? errorMsgList.join('<br>') : '无内容'
      this.logDetailVisible = true
    }
  }
}
</script>
