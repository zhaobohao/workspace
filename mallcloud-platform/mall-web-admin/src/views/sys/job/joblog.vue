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
                    <el-form-item label="输入任务名称：">
                        <el-input style="width: 203px" v-model="listQuery.jobName" placeholder="类型名称/关键字"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>

        </el-card>
        <div class="table-container">
            <el-table ref="dictTable"
                      :data="list"
                      style="width: 100%"

                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

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
                    <template slot-scope="scope">{{scope.row.status |formatStatus}}</template>
                </el-table-column>
                <el-table-column label="创建时间" width="180" align="center">
                    <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
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
    import {fetchJobLogList} from '@/api/sys/job'
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
            handleSizeChange(val) {
                this.listQuery.pageNum = 1;
                this.listQuery.pageSize = val;
                this.getList();
            },
            handleCurrentChange(val) {
                this.listQuery.pageNum = val;
                this.getList();
            },
            getList() {
                this.listLoading = true;
                fetchJobLogList(this.listQuery).then(response => {
                    this.listLoading = false;
                this.list = response.data.records;
                this.total = response.data.total;
                this.totalPage = response.data.totalPage;
                this.pageSize = response.data.pageSize;
            });
            },
            searchJobList() {
                this.listQuery.pageNum = 1;
                this.getList();
            },

        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


