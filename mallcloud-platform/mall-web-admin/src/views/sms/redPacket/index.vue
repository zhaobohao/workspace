<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchRedPacketList()"
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
                    @click="addRedPacket()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="redPacketTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="红包编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="发红包的用户id" align="center">
                    <template slot-scope="scope">
                  <p> {{scope.row.userId}}</p>
                  <p>
                    <el-button
                      type="text"
                      @click="handleShowVeriyEditDialog(scope.$index, scope.row)">领取记录
                    </el-button>
                  </p>
                    </template>
                </el-table-column>
                <el-table-column label="红包金额" align="center">
                    <template slot-scope="scope">{{scope.row.amount}}</template>
                </el-table-column>

                <el-table-column label="红包总数" align="center">
                    <template slot-scope="scope">{{scope.row.total}}</template>
                </el-table-column>
                <el-table-column label="单个红包的金额" align="center">
                    <template slot-scope="scope">{{scope.row.unitAmount}}</template>
                </el-table-column>
                <el-table-column label="红包剩余个数" align="center">
                    <template slot-scope="scope">{{scope.row.stock}}</template>
                </el-table-column>
                <el-table-column label="红包类型" align="center">
                    <template slot-scope="scope">{{scope.row.type|filterType}}</template>
                </el-table-column>
                <el-table-column label="备注" align="center">
                    <template slot-scope="scope">{{scope.row.note}}</template>
                </el-table-column>
              <el-table-column label="发红包日期" align="center">
                <template slot-scope="scope">{{scope.row.sendDate |formatCreateTime}}</template>
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
        title="领取记录"
        :visible.sync="dialogVisible"
        width="40%">
        <el-table style="width: 100%;margin-top: 20px"
                  :data="userRedList"
                  border>

          <el-table-column label="用户红包编号" align="center">
            <template slot-scope="scope">{{scope.row.id}}</template>
          </el-table-column>

          <el-table-column label="发红包用户的编号" align="center">
            <template slot-scope="scope">{{scope.row.adminId}}</template>
          </el-table-column>
          <el-table-column label="抢红包用户的编号" align="center">
            <template slot-scope="scope">{{scope.row.userId}}</template>
          </el-table-column>
          <el-table-column label="抢到的红包金额" align="center">
            <template slot-scope="scope">{{scope.row.amount}}</template>
          </el-table-column>
          <el-table-column label="抢红包时间" align="center">
            <template slot-scope="scope">{{scope.row.grabTime|formatCreateTime}}</template>
          </el-table-column>
          <el-table-column label="备注" align="center">
            <template slot-scope="scope">{{scope.row.note}}</template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </div>
</template>
<script>
    import {fetchList, deleteRedPacket} from '@/api/sms/redPacket'
    import {fetchList as fetchUserRedList, deleteUserRedPacket} from '@/api/sms/userRedPacket'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'redPacketList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showRedPacket"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideRedPacket"
                    }
                ],
                operateType: null,
              dialogVisible:false,
              listQueryNew:{
                redPacketId:null,
              },
                listQuery: {
                    keyword: null,
                    pageNum: 1,
                    pageSize: 10
                },
              userRedList:null,
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

            filterType(status) {
              if(status==1){
                return '随机红包';
              }else if(status==2){
                return '等额红包';
              }
            },
        },
        methods: {
          handleShowVeriyEditDialog(index,row){
            this.dialogVisible=true;
            this.listQueryNew.redPacketId = row.id;
            fetchUserRedList(this.listQueryNew).then(response=>{
              this.userRedList=response.data.records;
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
                this.$router.push({path: '/sms/updateRedPacket', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteRedPacket(row.id).then(response => {
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
            searchRedPacketList() {
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
                if (this.operateType === 'showRedPacket') {
                    showStatus = 1;
                } else if (this.operateType === 'hideRedPacket') {
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
            addRedPacket() {
                this.$router.push({path: '/sms/addRedPacket'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


