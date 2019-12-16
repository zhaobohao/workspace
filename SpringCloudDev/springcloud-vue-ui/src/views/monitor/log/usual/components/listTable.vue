<template>
  <el-card>

    <!--主表显示 区域-->
    <el-table :key="tableKey" ref="messageTable" v-loading="listLoading" :data="list" :height="tableHeight"
      :stripe="isStripe" border fit highlight-current-row
      style="border:2px solid #ebeef5;margin:10px 0 0 0;width: 100%;" @sort-change="sortChange"
      @selection-change="handleSelectionChange">
      <!--表格行的多选-->
      <!--表格的序号-->
      <el-table-column :label="$t('table.id')" fixed type="index" width="50px"></el-table-column>
      <el-table-column label="服务id" width="80px">
        <template slot-scope="scope">
          <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.serviceId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="服务host" width="180px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.serverHost }}</span>
        </template>
      </el-table-column>
      <el-table-column label="服务ip" width="180px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.serverIp }}</span>
        </template>
      </el-table-column>
      <el-table-column label="软件环境" width="80px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.env }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志名" width="180px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请求方法" width="80px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.method }}</span>
        </template>
      </el-table-column>
      <el-table-column label="日志时间" width="180px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户代理" width="180px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.userAgent }}</span>
        </template>
      </el-table-column>
      <el-table-column label="请求数据" min-width="180px">
        <template slot-scope="scope">
          <span class="">{{ scope.row.params }}</span>
        </template>
      </el-table-column>
    </el-table>
    <!--分页符-->
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size"
      style="margin-top:0px;padding:10px 26px" @pagination="getList" />
  </el-card>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    getUsualList
  } from '@/api/logs'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import {
    parseTime
  } from '@/utils'
  // 分页组件
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
  // 引入相关utils
  import notify from '@/utils/notify'
  export default {
    // TODO:本页面的名称
    name: 'params-listTable',
    components: {
      Pagination
    },
    directives: {
      waves
    },
    filters: {

    },
    props: {
      isSearchCardShow: { // true ,显示搜索区域
        type: Boolean,
        default: false
      }
    },
    data() {
      // 初始化整个页面用到的数据
      return {
        isStripe: true,
        tableHeight: 250,
        multipleSelection: [],
        tableKey: 0, // 留着，不要删除
        list: null, // Table里显示的数据
        total: 0, // 数据总量，分页用
        listLoading: true, // true,显示table的loading框
        listQuery: {}, // 你的查询条件，和searchCard.vue的查询区域相对应
        downloadLoading: false
      }
    },
    watch: {
      isSearchCardShow(val) {
        // 当搜索框显示与隐藏时，重新更新表格的高度
        this.$nextTick(() => {
          this.tableHeight = window.innerHeight - this.$refs.messageTable.$el.offsetTop - 180
        })
      }
    },
    // 初始化所有的数据
    created() {
      this.$nextTick(this.getList())
    },
    mounted() {
      this.$nextTick(() => {
        const self = this
        this.tableHeight = window.innerHeight - this.$refs.messageTable.$el.offsetTop - 180
        // 通过捕获系统的onresize事件触发去改变原有的高度
        window.onresize = function () {
          self.tableHeight = window.innerHeight - self.$refs.messageTable.$el.offsetTop - 180
        }
      })
    },
    methods: {
      // 调取table所需要的数据，会根据listQuery对象进行查找
      getList(listQuery) {
        if (listQuery && listQuery.current) {
          this.listQuery = listQuery
        }
        this.listLoading = true
        getUsualList(this.listQuery.current, this.listQuery.size, this.listQuery.query).then(response => {
          if (response.code === 200) {
            this.list = response.data.records
            this.total = response.data.total
            this.listLoading = false
          } else {
            this.listLoading = false
            notify.error(this, {
              title: '获取表格数据失败',
              message: response.msg
            })
          }
        })
      },
      // 重新排序，目前没有使用
      sortChange(data) {
        const {
          prop,
          order
        } = data
        if (prop === 'id') {
          this.sortByID(order)
        }
      },
      sortByID(order) {
        if (order === 'ascending') {
          this.listQuery.orderBy = 'id asc'
        } else {
          this.listQuery.orderBy = 'id desc'
        }
        this.getList()
      },
      // excel文件下载函数
      handleDownload() {
        this.downloadLoading = true

        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['id', '参数名称', '参数键名', '参数键值', '备注']
          const filterVal = ['id', 'paramName', 'paramKey', 'paramValue', 'remark']
          const data = this.formatJson(filterVal, this.list)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: 'param-list'
          })
          this.downloadLoading = false
        })
      },
      // 配套工具
      formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => {
          if (j === 'timestamp') {
            return parseTime(v[j])
          } else {
            return v[j]
          }
        }))
      }
    }
  }

</script>
