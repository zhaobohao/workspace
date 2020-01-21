<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchGroupList()"
                        type="primary"
                        size="small">
                    查询结果
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="输入搜索：">
                        <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="拼团名称/关键字"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button
                    class="btn-add"
                    @click="addGroup()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="groupTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="竞拍名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}
                    <p>
                        <el-button
                                type="text"
                                @click="handleShowVeriyEditDialog(scope.$index, scope.row)">竞拍记录
                        </el-button>
                    </p>
                    </template>
                </el-table-column>
                <el-table-column label="商品ID" align="center">
                    <template slot-scope="scope">{{scope.row.goodId}}</template>
                </el-table-column>
                <el-table-column label="商品" align="center">
                    <template slot-scope="scope">{{scope.row.goodsName}}</template>
                </el-table-column>


                <el-table-column label="开始时间" align="center">
                    <template slot-scope="scope">{{scope.row.startTime | formatCreateTime}}</template>
                </el-table-column>
                <el-table-column label="结束时间" align="center">
                    <template slot-scope="scope">{{scope.row.endTime | formatCreateTime}}</template>
                </el-table-column>

                <el-table-column label="起拍价" align="center">
                    <template slot-scope="scope">{{scope.row.price}}</template>
                </el-table-column>
                <el-table-column label="加价的倍数" align="center">
                    <template slot-scope="scope">{{scope.row.addprice}}</template>
                </el-table-column>
                <el-table-column label="保证金" align="center">
                    <template slot-scope="scope">{{scope.row.proprice}}</template>
                </el-table-column>
                <el-table-column label="活动状态" align="center">
                    <template slot-scope="scope">{{scope.row.state | formatStatus}}</template>
                </el-table-column>
                <el-table-column label="是否展示" width="200" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                @change="updateRecommendStatusStatus(scope.$index, scope.row)"
                                :active-value="0"
                                :inactive-value="1"
                                v-model="scope.row.status">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="创建时间" align="center">
                    <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
                </el-table-column>
                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                      <el-button size="mini"
                                 type="text"
                                 @click="handleView(scope.$index, scope.row)">查看</el-button>
                        <el-button
                                size="mini"  type="text"
                                @click="handleUpdate(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="text"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="batch-operate-container">
            <el-select
                    size="small"
                    v-model="operateType" placeholder="批量操作">
                <el-option
                        v-for="item in operates"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <el-button
                    style="margin-left: 20px"
                    class="search-button"
                    @click="handleBatchOperate()"
                    type="primary"
                    size="small">
                确定
            </el-button>
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

    <el-dialog
            title="竞拍记录"
            :visible.sync="dialogVisible"
            width="40%">
        <el-table style="width: 100%;margin-top: 20px"
                  :data="blanceList"
                  border>

            <el-table-column label="会员编号" align="center">
                <template slot-scope="scope">{{scope.row.memberId}}</template>
            </el-table-column>
            <el-table-column label="商品编号" align="center">
                <template slot-scope="scope">{{scope.row.goodsId}}</template>
            </el-table-column>
            <el-table-column label="价格" align="center">
                <template slot-scope="scope">{{scope.row.price}}</template>
            </el-table-column>
            <el-table-column label="备注" align="center">
                <template slot-scope="scope">{{scope.row.note}}</template>
            </el-table-column>
            <el-table-column label="创建时间" width="180" align="center">
                <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
            </el-table-column>
        </el-table>
    </el-dialog>
    </div>
</template>
<script>
    import {fetchList, deletesmsPaiMai,fetchPaiMaiLog,updateShowStatus} from '@/api/sms/paimai'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'groupList',
        data() {
            return {
                operates: [

                ],
                dialogVisible:false,
                dialogVisible1:false,
                blanceList:null,
                integrationList:null,
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
          if (value === 0) {
            return '未开始';
          } else if (value === 1) {
            return '进行中';
          } else if (value === 2) {
            return '已结束';
          } else if (value === 4) {
            return '已失效';
          }
        },

          formatUserStatus(value) {
              if (value === 0) {
                  return '参团中';
              } else if (value === 1) {
                  return '待抽奖';
              } else if (value === 2) {
                  return '参团失败';
              } else if (value === 3) {
                  return '抽奖失败';
              }else if (value === 3) {
                  return '抽奖成功';
              }
          },
      },
        methods: {
            updateRecommendStatusStatus(index, row) {
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

            handleShowVeriyEditDialog(index,row){
                this.dialogVisible=true;
                fetchPaiMaiLog({drawId:row.id}).then(response=>{
                    this.blanceList=response.data.records;
                });
            },
            getList() {
                this.listLoading = true;
                fetchList(this.listQuery).then(response => {
                    this.listLoading = false;
                    console.log(response);
                this.list = response.data.records;
                this.total = response.data.total;
                this.totalPage = response.data.pages;
                this.pageSize = response.data.size;
            });
            },
          handleView(index, row) {
            this.$router.push({path: '/sms/groupHistory', query: {id: row.id}})
          },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/sms/updatesmsPaiMai', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该抽奖活动', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deletesmsPaiMai(row.id).then(response => {
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
            searchGroupList() {
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
                if (this.operateType === 'showGroup') {
                    showStatus = 1;
                } else if (this.operateType === 'hideGroup') {
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
            addGroup() {
                this.$router.push({path: '/sms/addsmsPaiMai'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


