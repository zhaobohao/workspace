<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchAdminLogList()"
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
                    <el-form-item label="提交时间：">
                        <el-date-picker
                                class="input-width"
                                v-model="listQuery.startTime"
                                value-format="yyyy-MM-dd"
                                type="date"
                                placeholder="请选择开始时间">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="提交时间：">
                        <el-date-picker
                                class="input-width"
                                v-model="listQuery.endTime"
                                value-format="yyyy-MM-dd"
                                type="date"
                                placeholder="请选择结束时间">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>

        </el-card>
        <div class="table-container">
            <el-table ref="adminLogStatic"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>


                <el-table-column label="接口名" align="center">
                    <template slot-scope="scope">{{scope.row.method}}</template>
                </el-table-column>
                <el-table-column label="调用次数" align="center">
                    <template slot-scope="scope">{{scope.row.count}}</template>
                </el-table-column>
                <el-table-column label="平均时长" align="center">
                    <template slot-scope="scope">{{scope.row.avgMin}}</template>
                </el-table-column>
                <el-table-column label="总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum}}</template>
                </el-table-column>
                <el-table-column label="超过0.1秒个数" align="center">
                    <template slot-scope="scope">{{scope.row.count1}}</template>
                </el-table-column>
                <el-table-column label="超过0.3秒个数" align="center">
                    <template slot-scope="scope">{{scope.row.count2}}</template>
                </el-table-column>
                <el-table-column label="超过0.6秒个数" align="center">
                    <template slot-scope="scope">{{scope.row.count3}}</template>
                </el-table-column>
                <el-table-column label="超过1秒个数" align="center">
                    <template slot-scope="scope">{{scope.row.count4}}</template>
                </el-table-column>
                <el-table-column label="超过1.5秒个数" align="center">
                    <template slot-scope="scope">{{scope.row.count5}}</template>
                </el-table-column>
                <el-table-column label="超过3秒个数" align="center">
                    <template slot-scope="scope">{{scope.row.count6}}</template>
                </el-table-column>

                <el-table-column label="超过0.1秒总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum1}}</template>
                </el-table-column>
                <el-table-column label="超过0.3秒总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum2}}</template>
                </el-table-column>
                <el-table-column label="超过0.6秒总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum3}}</template>
                </el-table-column>
                <el-table-column label="超过1秒总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum4}}</template>
                </el-table-column>
                <el-table-column label="超过1.5秒总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum5}}</template>
                </el-table-column>
                <el-table-column label="超过3秒总时长" align="center">
                    <template slot-scope="scope">{{scope.row.sum6}}</template>
                </el-table-column>
            </el-table>
        </div>


    </div>
</template>
<script>
    import {fetchList, deleteAdminLog,logStatic} from '@/api/sys/adminLog'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'adminLogList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showAdminLog"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideAdminLog"
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
                logStatic(this.listQuery).then(response => {
                    this.listLoading = false;
                    this.list = response.data;
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/sys/updateAdminLog', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteAdminLog(row.id).then(response => {
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
            searchAdminLogList() {
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
                if (this.operateType === 'showAdminLog') {
                    showStatus = 1;
                } else if (this.operateType === 'hideAdminLog') {
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
            addAdminLog() {
                this.$router.push({path: '/build/add${className}'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


