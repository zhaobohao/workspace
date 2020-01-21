<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float: right"
          @click="searchPermissionList()"
          type="primary"
          size="small" icon="el-icon-search">
          搜索
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input style="width: 203px" v-model="listQuery.name" placeholder="名称"></el-input>
          </el-form-item>
          <el-form-item label="输入搜索：">
            <el-input style="width: 203px" v-model="listQuery.pid" placeholder="父编号"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button
        class="btn-add"
        @click="addPermission()"
        size="mini">
        添加
      </el-button>
    </el-card>
    <div class="table-container">
      <el-table
        :data="list"
        border
        style="width: 100%;">
        <el-table-column
          prop="id"
          header-align="center"
          align="center"
          width="80"
          label="ID">
        </el-table-column>
        <el-table-column
          prop="pid"
          header-align="center"
          align="center"
          width="120"
          label="上级ID">
        </el-table-column>
        <table-tree-column
          prop="name"
          header-align="center"
          treeKey="id"
          width="200"
          label="名称">
        </table-tree-column>
        <el-table-column
          prop="type"
          header-align="center"
          align="center"
          label="类型">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.type === 0" size="small">目录</el-tag>
            <el-tag v-else-if="scope.row.type === 1" size="small" type="success">菜单</el-tag>
            <el-tag v-else-if="scope.row.type === 2" size="small" type="info">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="uri"
          header-align="center"
          align="center"
          width="150"
          :show-overflow-tooltip="true"
          label="菜单URL">
        </el-table-column>
        <el-table-column
          prop="value"
          header-align="center"
          align="center"
          width="150"
          :show-overflow-tooltip="true"
          label="授权标识">
        </el-table-column>
        <el-table-column
                prop="sort"
                header-align="center"
                align="center"
                width="150"
                :show-overflow-tooltip="true"
                label="菜单排序">
        </el-table-column>
        <el-table-column label="是否显示" width="100" align="center">
          <template slot-scope="scope">
            <el-switch
              @change="handleShowStatusChange(scope.$index, scope.row)"
              :active-value="1"
              :inactive-value="0"
              v-model="scope.row.status">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="添加时间" width="180" align="center">
          <template slot-scope="scope">{{scope.row.createTime | formatTime}}</template>
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="handleUpdate(scope.$index, scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  import {formatDate} from '@/utils/date';
  import TableTreeColumn from '@/components/table-tree-column/index';
  import {treeDataTranslate} from '@/utils/index';
  import {deletePermission, fetchList, updateFactoryStatus, updateShowStatus} from '@/api/permission'
  export default {
    name: 'permissionList',
    data() {
      return {
        operates: [
          {
            label: "显示",
            value: "showPermission"
          },
          {
            label: "隐藏",
            value: "hidePermission"
          }
        ],
        operateType: null,
        listQuery: {
          keyword: null
        },
        list: [],
        total: null,
        listLoading: true,
        multipleSelection: []
      }
    },
    created() {
      this.getList();
    },
    components: {
      TableTreeColumn
    },
    filters: {
      // formatMenuType(value) {
      //     if (value === 1) {
      //         return '菜单';
      //     } else if (value === 2) {
      //         return '按钮';
      //     } else if (value === 0) {
      //         return '目录';
      //     }
      // },
      formatTime(time) {
        if (time == null || time === '') {
          return 'N/A';
        }
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      },
      formatStatus(status) {
        for (let i = 0; i < defaultStatusOptions.length; i++) {
          if (status === defaultStatusOptions[i].value) {
            return defaultStatusOptions[i].label;
          }
        }
      },
      formatReturnAmount(row) {
        return row.productRealPrice * row.productCount;
      }
    },
    methods: {
      getList() {
        this.listLoading = true;
        fetchList(this.listQuery).then(response => {

          this.listLoading = false;
          this.list = treeDataTranslate(response.data, 'id',"pid");

          // this.total = response.data.length;
          // this.totalPage = response.data.pages;
          // this.pageSize = response.data.size;
        });
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleUpdate(index, row) {
        this.$router.push({path: '/sys/updatePermission', query: {id: row.id}})
      },
      handleDelete(index, row) {
        this.$confirm('是否要删除该条记录', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deletePermission(row.id).then(response => {
            this.$message({
              message: '删除成功',
              type: 'success',
              duration: 1000
            });
            this.getList();
          });
        });
      },
      getProductList(index, row) {
        console.log(index, row);
      },
      getProductCommentList(index, row) {
        console.log(index, row);
      },
      handleFactoryStatusChange(index, row) {
        var data = new URLSearchParams();
        data.append("ids", row.id);
        data.append("factoryStatus", row.factoryStatus);
        updateFactoryStatus(data).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        }).catch(error => {
          if (row.factoryStatus === 0) {
            row.factoryStatus = 1;
          } else {
            row.factoryStatus = 0;
          }
        });
      },
      handleShowStatusChange(index, row) {
        let data = new URLSearchParams();
        ;
        data.append("ids", row.id);
        data.append("showStatus", row.status);
        updateShowStatus(data).then(response => {
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        }).catch(error => {
          if (row.showStatus === 0) {
            row.showStatus = 1;
          } else {
            row.showStatus = 0;
          }
        });
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 1;
        this.listQuery.pageSize = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val;
        this.getList();
      },
      searchPermissionList() {
        this.listQuery.pageNum = 1;
        this.getList();
      },
      handleBatchOperate() {
        console.log(this.multipleSelection);
        if (this.multipleSelection < 1) {
          this.$message({
            message: '请选择一条记录',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        let showStatus = 0;
        if (this.operateType === 'showPermission') {
          showStatus = 1;
        } else if (this.operateType === 'hidePermission') {
          showStatus = 0;
        } else {
          this.$message({
            message: '请选择批量操作类型',
            type: 'warning',
            duration: 1000
          });
          return;
        }
        let ids = [];
        for (let i = 0; i < this.multipleSelection.length; i++) {
          ids.push(this.multipleSelection[i].id);
        }
        let data = new URLSearchParams();
        data.append("ids", ids);
        data.append("showStatus", showStatus);
        updateShowStatus(data).then(response => {
          this.getList();
          this.$message({
            message: '修改成功',
            type: 'success',
            duration: 1000
          });
        });
      },
      addPermission() {
        this.$router.push({path: '/sys/addPermission'})
      }
    }
  }
</script>
