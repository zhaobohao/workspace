<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchAdvList()"
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
            <el-button v-if="cid>0"
                    class="btn-add"
                    @click="addAdv()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="advTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column type="selection" width="60" align="center"></el-table-column>
                <el-table-column label="编号" width="120" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="广告名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>

                <el-table-column label="广告图片" width="120" align="center">
                    <template slot-scope="scope"><img style="height: 80px" :src="scope.row.pic"></template>
                </el-table-column>
                <el-table-column label="时间" width="220" align="center">
                    <template slot-scope="scope">
                        <p>开始时间：{{scope.row.startTime | formatCreateTime}}</p>
                        <p>到期时间：{{scope.row.endTime | formatCreateTime}}</p>
                    </template>
                </el-table-column>
                <el-table-column label="上线/下线" width="120" align="center">
                    <template slot-scope="scope">
                        <el-switch
                                @change="handleUpdateStatus(scope.$index, scope.row)"
                                :active-value="1"
                                :inactive-value="0"
                                v-model="scope.row.status">
                        </el-switch>
                    </template>
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
    import {fetchList, deleteAdv,updateStatus} from '@/api/build/adv'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'advList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showAdv"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideAdv"
                    }
                ],
                cid:0,
                cname:'',
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
                this.cid=this.$route.query.cid;
                if (this.cid) {
                    this.listQuery.communityId=this.cid;
                }
                fetchList(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.data.records;
                    this.total = response.data.total;
                    //this.total = response.data.records.length;
                    this.totalPage = response.data.pages;
                    this.pageSize = response.data.size;
            });
            },
            handleUpdateStatus(index,row){
                this.$confirm('是否要修改上线/下线状态?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    updateStatus(row.id,{status:row.status}).then(response=>{
                        this.getList();
                        this.$message({
                            type: 'success',
                            message: '修改成功!'
                        });
                    });
                }).catch(() => {
                    this.$message({
                        type: 'success',
                        message: '已取消操作!'
                    });
                    this.getList();
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            addAdv() {
                this.$router.push({path:'/build/addAdv',query:{cid:this.$route.query.cid,cname:this.$route.query.cname}});
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/build/updateAdv', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteAdv(row.id).then(response => {
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
            searchAdvList() {
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
                if (this.operateType === 'showAdv') {
                    showStatus = 1;
                } else if (this.operateType === 'hideAdv') {
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

        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


