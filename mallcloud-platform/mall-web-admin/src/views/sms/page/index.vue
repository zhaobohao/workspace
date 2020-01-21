<template> 
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
                style="float: right"
                @click="searchSmsDiyPageList()"
                type="primary"
                size="small">
          查询结果
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="类型名称/关键字"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button
              class="btn-add"
              @click="addSmsDiyPage()"
              size="mini">
        添加
      </el-button>
    </el-card>
    <div class="table-container">
      <el-table ref="smsDiyPageTable"
                :data="list"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                v-loading="listLoading"
                border>
        <el-table-column type="selection" width="60" align="center"></el-table-column>



                <el-table-column prop="id" label="id">
                  <template slot-scope="scope">
                    {{scope.row.id }}
                  </template>
                </el-table-column>


                <el-table-column prop="type" label="页面类型">
                  <template slot-scope="scope">
                    {{scope.row.type |filterType}}
                  </template>
                </el-table-column>


                <el-table-column prop="name" label="名称">
                  <template slot-scope="scope">
                    {{scope.row.name }}
                  </template>
                </el-table-column>


          <el-table-column prop="title" label="标题">
              <template slot-scope="scope">
                  {{scope.row.title }}
              </template>
          </el-table-column>


          <el-table-column label="状态" align="center">
              <template slot-scope="scope">
                  <el-switch
                          @change="handleShowChange(scope.$index, scope.row)"
                          :active-value="1"
                          :inactive-value="0"
                          v-model="scope.row.showStatus">
                  </el-switch>
              </template>
          </el-table-column>

                <el-table-column prop="创建时间" label="createTime">
                  <template slot-scope="scope">
                    {{scope.row.createTime |formatCreateTime}}
                  </template>
                </el-table-column>






        <el-table-column label="操作" width="250" align="center">
          <template slot-scope="scope">
              <el-button
                      size="mini"
                      @click="copyFun(scope.$index, scope.row)">复制
              </el-button>
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
    <div class="batch-operate-container">

    </div>
    <div class="pagination-container">
      <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              layout="total, sizes,prev, pager, next,jumper"
              :page-size="listQuery.pageSize"
              :page-sizes="[5,10,15]"
              :current-page.sync="listQuery.pageNum"
              :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  //将$都替换为$
  import {fetchList, deleteSmsDiyPage,updateShowStatus} from '@/api/sms/smsDiyPage'
  import {formatDate} from '@/utils/date';

  export default {
    name: 'smsDiyPageList',
    data() {
      return {
        operates: [

        ],
        operateType: null,
        listQuery: {
          keyword: null,
          pageNum: 1,
          pageSize: 10
        },
        list: null,
        total: null,
        listLoading: true,
        multipleSelection: []
      }
    },
    created() {
      this.getList();
    },
    filters: {
      formatCreateTime(time) {
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      },

      formatStatus(value) {
        if (value === 1) {
          return '未开始';
        } else if (value === 2) {
          return '活动中';
        } else if (value === 3) {
          return '已结束';
        } else if (value === 4) {
          return '已失效';
        }
      },
        filterType(value) {
            if (value === 1) {
                return '全部页面';
            } else if (value === 2) {
                return '商城首页';
            } else if (value === 3) {
                return '会员中心页';
            } else if (value === 4) {
                return '商城详情页';
            }else {
                return '自定义页面';
            }
        },
        // 1全部页面 2商城首页 3会员中心页 4商城详情页 5自定义页面
    },
    methods: {
      getList() {
        this.listLoading = true;
        fetchList(this.listQuery).then(response => {
          this.listLoading = false;
        this.list = response.data.records;
        this.total = response.data.total;
        this.totalPage = response.data.pages;
        this.pageSize = response.data.size;
      });

      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleUpdate(index, row) {
          this.$router.push( `/@/components/templatePage?type=` + row.type + "&id=" + row.id + "&status=" + row.status);
      },
        copyFun(index, row) {
            this.$router.push(`/@/components/templatePage?role=1&type=` + row.type + "&id=" + row.id);
        },
      handleDelete(index, row) {
        this.$confirm('是否要删除该类型', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteSmsDiyPage(row.id
      ).
        then(response => {
          this.$message({
            message: '删除成功',
            type: 'success',
            duration: 1000
          });
        this.getList();
      });

      });

      },

        handleShowChange(index, row) {
            let params = new URLSearchParams();
            params.append('ids', row.id);
            params.append('showStatus', row.status);
            updateShowStatus(params).then(response => {
                this.$message({
                    message: '修改成功',
                    type: 'success',
                    duration: 1000
                });
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
      searchSmsDiyPageList() {
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
        if (this.operateType === 'showSmsDiyPage') {
          showStatus = 1;
        } else if (this.operateType === 'hideSmsDiyPage') {
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
      })
        ;
      },
      addSmsDiyPage() {
        //手动将router,改成$router
          this.$router.push(`/@/components/templatePage?type=5`);
        //this.$router.push({path: '/sms/addSmsDiyPage'})
      }
    }
  }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


