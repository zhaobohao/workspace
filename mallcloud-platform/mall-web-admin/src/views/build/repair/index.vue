<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchRepairList()"
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
            <!--<el-button
                    class="btn-add"
                    @click="addRepair()"
                    size="mini">
                添加
            </el-button>-->
        </el-card>
        <div class="table-container">
            <el-table ref="repairTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="房屋编号" align="center">
                    <template slot-scope="scope">{{scope.row.roomId}}</template>
                </el-table-column>
                <el-table-column label="报修者电话" align="center">
                    <template slot-scope="scope">{{scope.row.phone}}</template>
                </el-table-column>
                <el-table-column label="房屋具体位置" align="center">
                    <template slot-scope="scope">{{scope.row.roomDesc}}</template>
                </el-table-column>
                <el-table-column label="申请时间" align="center">
                    <template slot-scope="scope">{{scope.row.createTime | formatCreateTime}}</template>
                </el-table-column>
                <el-table-column label="解决时间" align="center">
                    <template slot-scope="scope">{{scope.row.solveTime | formatCreateTime}}</template>
                </el-table-column>
                <el-table-column label="报修原因" align="center">
                    <template slot-scope="scope">{{scope.row.resons}}</template>
                </el-table-column>

                <el-table-column label="解决记录" align="center">
                    <template slot-scope="scope">{{scope.row.solveResons}}</template>
                </el-table-column>

                <el-table-column label="价格" align="center">
                    <template slot-scope="scope">{{scope.row.price}}</template>
                </el-table-column>
                <el-table-column label="状态" align="center">
                    <template slot-scope="scope">{{scope.row.status | formatStatus}}</template>
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
    import {fetchList, deleteRepair} from '@/api/build/repair'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'repairList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showRepair"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideRepair"
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
                    return '已申请';
                } else if (value === 2) {
                    return '处理中';
                } else if (value === 3) {
                    return '已处理';
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
                    //this.total = response.data.records.length;
                    this.totalPage = response.data.pages;
                    this.pageSize = response.data.size;
            });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            addRepair() {
                this.$router.push({path:'/build/addRepair'})
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/build/updateRepair', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteRepair(row.id).then(response => {
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
            searchRepairList() {
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
                if (this.operateType === 'showRepair') {
                    showStatus = 1;
                } else if (this.operateType === 'hideRepair') {
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


