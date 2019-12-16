<template>

  <el-card>

    <el-button v-waves v-permission="['29']" class="filter-item" style="margin-left: 10px;" round type="primary"
      icon="el-icon-edit" @click="handleCreateAction">{{
      $t('table.add') }}</el-button>
    <el-button v-waves v-permission="['31']" class="filter-item" style="margin-left: 10px;" round type="danger"
      icon="el-icon-delete" @click="handleBatchDeleteAction">{{
      $t('table.delete') }}</el-button>
    <el-button v-waves class="filter-item" style="margin-left: 10px;" round type="warning" icon="el-icon-refresh"
      @click="getList">{{
      $t('table.refresh') }}</el-button>
    <el-button v-waves :loading="downloadLoading" class="filter-item" round type="success" icon="el-icon-download"
      @click="handleDownload">{{
      $t('table.export') }}</el-button>
    <!--主表显示 区域-->
    <el-table ref="messageTable" :key="tableKey" v-loading="listLoading" :header-cell-style="{background:'#fafafa','color': 'rgb(103, 194, 58)',
    'border-bottom': '1px rgb(103, 194, 58) solid'}" :data="list" :height="tableHeight" :stripe="isStripe" border fit
      highlight-current-row style="border:2px solid #ebeef5;margin:10px 0 0 0;width: 100%;" @sort-change="sortChange"
      @selection-change="handleSelectionChange">
      <!--表格行的多选-->
      <el-table-column type="selection" fixed width="55"></el-table-column>
      <!--表格的序号-->
      <el-table-column :label="$t('table.id')" type="index" width="50px"></el-table-column>

      <el-table-column label="部门名称" min-width="150px">
        <template slot-scope="scope">
          <span class="link-type" @click="handleUpdate(scope.row)">{{ scope.row.deptName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="所属租户" min-width="150px">
        <template slot-scope="scope">
          {{ scope.row.tenantId }}
        </template>
      </el-table-column>

      <el-table-column label="部门全称" min-width="150px">
        <template slot-scope="scope">
          {{ scope.row.fullName }}
        </template>
      </el-table-column>
      <el-table-column label="排序" min-width="150px">
        <template slot-scope="scope">
          {{ scope.row.sort }}
        </template>
      </el-table-column>
      <el-table-column label="备注" min-width="150px">
        <template slot-scope="scope">
          {{ scope.row.remark }}
        </template>
      </el-table-column>
      <el-table-column :label="$t('table.actions')" fixed="right" align="center" width="180"
        class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-waves v-permission="['30']" type="primary" size="mini" @click="handleUpdate(scope.row)">
            {{ $t('table.edit') }}
          </el-button>
          <el-button v-if="scope.row.isDeleted!='1'" v-waves v-permission="['31']" size="mini" type="danger"
            @click="handleDeleteAction(scope.row)">{{
            $t('table.delete') }}
          </el-button>
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
    getList,
    remove
  } from '@/api/system/dept'
  import listQuery from '@/entitys/dept'
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
  // 引入指令
  import permission from '@/directive/permission/index.js' // 权限判断指令
  export default {
    // TODO:本页面的名称
    name: 'dept-list',
    components: {
      Pagination
    },
    directives: {
      waves,
      permission
    },
    filters: {

    },
    props: {
      isSearchCardShow: { // true ,显示搜索区域
        type: Boolean,
        default: false
      },
      typeOptions: {
        type: Array,
        default: function () {
          return []
        }
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
        listQuery: listQuery(), // 你的查询条件，和searchCard.vue的查询区域相对应
        // 增加options的数据源
        temp: {},
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
      // 重新初始化listQuery
      resetListQuery() {
        this.listQuery = listQuery()
      },
      // 处理多选的数据
      handleSelectionChange(val) {
        this.multipleSelection = val
        // console.log(this.multipleSelection)
      },
      // 调取table所需要的数据，会根据listQuery对象进行查找
      getList(listQuery) {
        if (listQuery && listQuery.current) {
          this.listQuery = listQuery
        }
        this.listLoading = true
        getList(this.listQuery.current, this.listQuery.size, this.listQuery.query).then(response => {
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
      // 批量删除函数
      handleBatchDeleteAction() {
        this.handleDeleteAction()
      },
      // 删除函数 ，当row为空时，取table的选择框做为ids.
      handleDeleteAction(row) {
        const ids = []
        if (row === undefined) {
          if (this.multipleSelection.length === 0) {
            notify.error(this, {
              title: '删除失败',
              message: '请选择要删除的数据项'
            })
            return ''
          }
          this.multipleSelection.forEach(item => ids.push(item.id))
        } else {
          if (row.id === undefined) {
            notify.error(this, {
              title: '删除失败',
              message: '无法获取数据id,请重试！'
            })
            return
          }
          ids.push(row.id)
        }

        this.listLoading = true
        // 开始调用删除
        remove(
          ids.join(',')
        ).then(response => {
          console.log(response)
          if (response.code === 200) {
            this.list = this.list.filter(item => !ids.includes(item.id))
            this.listLoading = false
            this.$parent.$parent.$parent.$parent.initTreeData()
            notify.success(this, {
              title: '删除成功',
              message: response.msg
            })
          } else {
            notify.error(this, {
              title: '删除失败',
              message: response.msg
            })
          }
        }).catch(err => {
          notify.error(this, {
            title: '删除失败',
            message: err.message
          })
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
      // 处理创建按钮
      handleCreateAction() {
        this.$parent.$parent.$parent.$parent.$refs.dataForm.handleCreateAction()
      },
      // 处理编辑按钮
      handleUpdate(row) {
        this.$parent.$parent.$parent.$parent.$refs.dataForm.handleUpdate(row)
      },
      // excel文件下载函数
      handleDownload() {
        this.downloadLoading = true
        import('@/vendor/Export2Excel').then(excel => {
          const tHeader = ['id', '部门名称', '所属租户', '部门全称', '上级部门', '排序', '备注']
          const filterVal = ['id', 'deptName', 'tenantId', 'fullName', 'parentId', 'sort', 'remark']
          const data = this.formatJson(filterVal, this.list)
          excel.export_json_to_excel({
            header: tHeader,
            data,
            filename: 'table-list'
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
