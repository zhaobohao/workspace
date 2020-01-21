<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchTopicList()"
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
                    @click="addTopic()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="topicTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="所属分类" align="center">
                    <template slot-scope="scope">{{scope.row.categoryId}}</template>
                </el-table-column>
                <el-table-column label="标题" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>

                <el-table-column label="开始时间" align="center">
                    <template slot-scope="scope">{{scope.row.startTime|formatCreateTime}}</template>
                </el-table-column>
                <el-table-column label="结束时间" align="center">
                    <template slot-scope="scope">{{scope.row.endTime|formatCreateTime}}</template>
                </el-table-column>
                <el-table-column label="参与人数" align="center">
                    <template slot-scope="scope">
                  <p>{{scope.row.attendCount}}</p>
                  <p>
                    <el-button
                      type="text"
                      @click="handleShowVeriyEditDialog(scope.$index, scope.row)">参与人数列表
                    </el-button>
                  </p>
                    </template>
                </el-table-column>
                <el-table-column label="关注人数" align="center">
                    <template slot-scope="scope">{{scope.row.attentionCount}}</template>
                </el-table-column>
                <el-table-column label="点击人数" align="center">
                    <template slot-scope="scope">{{scope.row.readCount}}</template>
                </el-table-column>
                <el-table-column label="奖品名称" align="center">
                    <template slot-scope="scope">{{scope.row.awardName}}</template>
                </el-table-column>
                <el-table-column label="参与方式" align="center">
                    <template slot-scope="scope">{{scope.row.attendType}}</template>
                </el-table-column>
                <el-table-column label="话题内容" align="center">
                    <template slot-scope="scope">{{scope.row.content}}</template>
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
      <el-dialog
        title="审核信息"
        :visible.sync="vertyProduct.dialogVisible"
        width="40%">

        <el-table style="width: 100%;margin-top: 20px"
                  :data="vertyProduct.list"
                  border>
          <el-table-column label="申请状态" width="160" align="center">
            <template slot-scope="scope">
              {{scope.row.status}}
              <el-switch
                @change="handleEditVConfirm(scope.$index, scope.row)"
                :active-value="1"
                :inactive-value="2"
                v-model="scope.row.status">
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column label="申请人" width="160" align="center">
            <template slot-scope="scope">{{scope.row.username}}</template>
          </el-table-column>
          <el-table-column label="申请时间"  align="center">
            <template slot-scope="scope">{{scope.row.createTime|formatCreateTime}}</template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
</template>
<script>
    import {fetchList, deleteTopic,updateVerifyStatus,fetchCmsTopicMember} from '@/api/cms/topic'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'topicList',
        data() {
            return {
              vertyProduct:{
                dialogVisible:false,
                topicId:'',
                list:null
              },
                operates: [
                    {
                        label: "显示品牌",
                        value: "showTopic"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideTopic"
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

          handleShowVeriyEditDialog(index,row){
            this.vertyProduct.dialogVisible=true;
            this.vertyProduct.topicId=row.id;
            fetchCmsTopicMember(row.id).then(response=>{
              this.vertyProduct.list=response.data;
          });
          },
          handleEditVConfirm(index,row){

            this.$confirm('是否要进行审核', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(()=>{

              let params = new URLSearchParams();
            params.append('topicId', this.vertyProduct.topicId);
            params.append('ids', row.id);
            params.append('verifyStatus', row.status);
            updateVerifyStatus(params).then(response => {
              this.$message({
              message: '修改成功',
              type: 'success',
              duration: 1000
            });
            this.getList();
            });
            this.vertyProduct.dialogVisible=false;

          });
          },
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
                this.$router.push({path: '/cms/updateTopic', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteTopic(row.id).then(response => {
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
            searchTopicList() {
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
                if (this.operateType === 'showTopic') {
                    showStatus = 1;
                } else if (this.operateType === 'hideTopic') {
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
            addTopic() {
                this.$router.push({path: '/cms/addTopic'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


