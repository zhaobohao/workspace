<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchSubjectList()"
                        type="primary"
                        size="small">
                    查询结果
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="输入搜索：">
                        <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="品牌名称/关键字"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button
                    class="btn-add"
                    @click="addSubject()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="subjectTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="分类" align="center">
                    <template slot-scope="scope">{{scope.row.categoryId}}</template>
                </el-table-column>
              <el-table-column label="专题分类名称" align="center">
                <template slot-scope="scope">{{scope.row.categoryName}}</template>
              </el-table-column>
                <el-table-column label="标题" align="center">
                    <template slot-scope="scope">{{scope.row.title}}</template>
                </el-table-column>
                <el-table-column label="专题主图" align="center">
                  <template slot-scope="scope"><img style="height: 80px" :src="scope.row.pic"></template>
                </el-table-column>
                <el-table-column label="关联产品数量" align="center">
                    <template slot-scope="scope">{{scope.row.productCount}}</template>
                </el-table-column>
                <el-table-column label="推荐" align="center">
                  <template slot-scope="scope">
                  <el-switch
                    @change="handleRecomChange(scope.$index, scope.row)"
                    :active-value="1"
                    :inactive-value="0"
                    v-model="scope.row.recommendStatus">
                  </el-switch>
                  </template>
                </el-table-column>

                <el-table-column label="收藏量" align="center">
                    <template slot-scope="scope">{{scope.row.collectCount}}</template>
                </el-table-column>
                <el-table-column label="点击量" align="center">
                    <template slot-scope="scope">{{scope.row.readCount}}</template>
                </el-table-column>
                <el-table-column label="评论量" align="center">
                    <template slot-scope="scope">{{scope.row.commentCount}}</template>
                </el-table-column>

                <el-table-column label="显示状态" align="center">
                  <template slot-scope="scope">
                    <el-switch
                      @change="handleShowChange(scope.$index, scope.row)"
                      :active-value="1"
                      :inactive-value="0"
                      v-model="scope.row.showStatus">
                    </el-switch>
                  </template>
                </el-table-column>

                <el-table-column label="转发数" align="center">
                    <template slot-scope="scope">{{scope.row.forwardCount}}</template>
                </el-table-column>

              <el-table-column label="创建时间" align="center">
                <template slot-scope="scope">{{scope.row.createTime|formatCreateTime}}</template>
              </el-table-column>

                <el-table-column label="操作" width="200" align="center">
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
  import {formatDate} from '@/utils/date';
    import {fetchList, deleteSubject,updateRecommendStatus,updateShowStatus} from '@/api/cms/subject'

    export default {
        name: 'subjectList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showSubject"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideSubject"
                    }
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
          handleRecomChange(index, row) {
            let params = new URLSearchParams();
            params.append('ids', row.id);
            params.append('recommendStatus', row.recommendStatus);
            updateRecommendStatus(params).then(response => {
              this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
          });
          },
          handleShowChange(index, row) {
            let params = new URLSearchParams();
            params.append('ids', row.id);
            params.append('showStatus', row.showStatus);
            updateShowStatus(params).then(response => {
              this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
          });
          },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/cms/updateSubject', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteSubject(row.id).then(response => {
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


            handleSizeChange(val) {
                this.listQuery.pageNum = 1;
                this.listQuery.pageSize = val;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listQuery.pageNum = val;
                this.getList();
            },
            searchSubjectList() {
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
                if (this.operateType === 'showSubject') {
                    showStatus = 1;
                } else if (this.operateType === 'hideSubject') {
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
            addSubject() {
                this.$router.push({path: '/cms/addSubject'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


