<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchJobList()"
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
                    @click="addJob()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="dictTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="任务编号" align="center">
                        <template slot-scope="scope">{{scope.row.jobId}}</template>
                    </el-table-column>
                <el-table-column label="任务名称" width="180" align="center">
                    <template slot-scope="scope">
                        <p> {{scope.row.jobName}}</p>
                        <p>
                            <el-button
                                    type="text"
                                    @click="handleShowVeriyEditDialog(scope.$index, scope.row)">执行日志
                            </el-button>
                        </p>
                    </template>
                </el-table-column>


                                    <el-table-column label="任务分组" align="center">
                        <template slot-scope="scope">{{scope.row.jobGroup}}</template>
                    </el-table-column>
                                    <el-table-column label="调用目标字符串" align="center">
                        <template slot-scope="scope">{{scope.row.invokeTarget}}</template>
                    </el-table-column>

                                    <el-table-column label="执行表达式" align="center">
                        <template slot-scope="scope">{{scope.row.cronExpression}}</template>
                    </el-table-column>
                <el-table-column label="任务状态" align="center" >
                    <template slot-scope="scope">
                        <el-switch
                                @change="handleRecomChange(scope.$index, scope.row)"
                                :active-value="1"
                                :inactive-value="0"
                                v-model="scope.row.status">
                        </el-switch>
                    </template>
                </el-table-column>




                <el-table-column label="创建时间" align="center">
                    <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
                </el-table-column>

                <el-table-column label="操作" width="200" align="center">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleUpdate(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                @click="run(scope.$index, scope.row)">执行一次
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
                title="执行日志"
                :visible.sync="dialogVisible"
                width="40%">
            <el-table style="width: 100%;margin-top: 20px"
                      :data="blanceList"
                      border>

                <el-table-column label="日志编号" align="center">
                    <template slot-scope="scope">{{scope.row.jobLogId}}</template>
                </el-table-column>
                <el-table-column label="任务名称" align="center">
                    <template slot-scope="scope">{{scope.row.jobName}}</template>
                </el-table-column>
                <el-table-column label="调用目标字符串" align="center">
                    <template slot-scope="scope">{{scope.row.invokeTarget}}</template>
                </el-table-column>
                <el-table-column label="日志信息" align="center">
                    <template slot-scope="scope">{{scope.row.jobMessage}}</template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope">{{scope.row.status|formatStatus}}</template>
                </el-table-column>
                <el-table-column label="创建时间" width="180" align="center">
                    <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>
<script>
    //将$都替换为$
    import {fetchList, deleteJob,checkCronExpressionIsValid,changeStatus,run,fetchJobLogList} from '@/api/sys/job'
    import {formatDate} from '@/utils/date';

    export default {
        name: 'dictList',
        data() {
            return {
                dialogVisible:false,
                dialogVisible1:false,
                blanceList:null,
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
                    return '正常';
                } else{
                    return '停止';
                }
            },
        },
        methods: {
            handleShowVeriyEditDialog(index,row){
                this.dialogVisible=true;
                fetchJobLogList({jobId:row.jobId,pageSize:100}).then(response=>{
                    this.blanceList=response.data.records;
                });
            },
            getList() {
                this.listLoading = true;
                fetchList(this.listQuery).then(response => {
                    this.listLoading = false;
                this.list = response.data.records;
                this.total = response.data.total;
                this.totalPage = response.data.totalPage;
                this.pageSize = response.data.pageSize;
            })
                ;
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push(
                        {path: '/sys/updateJob', query: {id: row.jobId}})
            },
            run(index, row) {
                this.$confirm('是否要执行定时任务', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    run({ "jobId": row.jobId,  "jobGroup": row.jobGroup}
                    ).
                    then(response => {
                        this.$message({
                            message: '执行成功',
                            type: 'success',
                            duration: 1000
                        });
                    })
                    ;
                })
                ;
            },
            handleRecomChange(index, row) {
                changeStatus({ "jobId": row.jobId,  "jobGroup": row.jobGroup, "status": row.status}).then(response => {
                    this.$message({
                        message: '修改成功',
                        type: 'success',
                        duration: 1000
                    });
                });
            },

            stop(index, row) {
                this.$confirm('是否要停用任务', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    changeStatus({ "jobId": row.jobId,  "jobGroup": row.jobGroup, "status": 1}
                    ).
                    then(response => {
                        this.$message({
                            message: '停用任务成功',
                            type: 'success',
                            duration: 1000
                        });
                        this.getList();
                    })
                    ;
                })
                ;
            },
            start(index, row) {
                this.$confirm('是否要启用任务', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    changeStatus({ "jobId": row.jobId,  "jobGroup": row.jobGroup, "status": 0}
                    ).
                    then(response => {
                        this.$message({
                            message: '启用任务成功',
                            type: 'success',
                            duration: 1000
                        });
                        this.getList();
                    })
                    ;
                })
                ;
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该类型', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteJob(row.id
            ).
                then(response => {
                    this.$message({
                    message: '删除成功',
                    type: 'success',
                    duration: 1000
                });
                this.getList();
            })
                ;
            })
                ;
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
            searchJobList() {
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
                if (this.operateType === 'showJob') {
                    showStatus = 1;
                } else if (this.operateType === 'hideJob') {
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
            addJob() {
                //手动将router,改成$router
                this.$router.push({path: '/sys/addJob'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


