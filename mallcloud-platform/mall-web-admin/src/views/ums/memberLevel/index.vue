<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchMemberLevelList()"
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
                    @click="addMemberLevel()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="memberLevelTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="编号" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="成长值" align="center">
                    <template slot-scope="scope">{{scope.row.growthPoint}}</template>
                </el-table-column>

                <el-table-column label="免运费标准" align="center">
                    <template slot-scope="scope">{{scope.row.freeFreightPoint}}</template>
                </el-table-column>
                <el-table-column label="每次评价获取的成长值" align="center">
                    <template slot-scope="scope">{{scope.row.commentGrowthPoint}}</template>
                </el-table-column>
                <el-table-column label="是否有免邮特权" align="center">
                    <template slot-scope="scope">{{scope.row.priviledgeFreeFreight}}</template>
                </el-table-column>
                <el-table-column label="是否有签到特权" align="center">
                    <template slot-scope="scope">{{scope.row.priviledgeSignIn}}</template>
                </el-table-column>
                <el-table-column label="是否有评论获奖励特权" align="center">
                    <template slot-scope="scope">{{scope.row.priviledgeComment}}</template>
                </el-table-column>
                <el-table-column label="是否有专享活动特权" align="center">
                    <template slot-scope="scope">{{scope.row.priviledgePromotion}}</template>
                </el-table-column>
                <el-table-column label="是否有会员价格特权" align="center">
                    <template slot-scope="scope">{{scope.row.priviledgeMemberPrice}}</template>
                </el-table-column>
                <el-table-column label="是否有生日特权" align="center">
                    <template slot-scope="scope">{{scope.row.priviledgeBirthday}}</template>
                </el-table-column>
                <el-table-column label="可发文章数" align="center">
                    <template slot-scope="scope">{{scope.row.articlecount}}</template>
                </el-table-column>
                <el-table-column label="可发商品数" align="center">
                    <template slot-scope="scope">{{scope.row.goodscount}}</template>
                </el-table-column>
                <el-table-column label="成为会员的价格" align="center">
                    <template slot-scope="scope">{{scope.row.price}}</template>
                </el-table-column>
                <el-table-column label="备注" align="center">
                    <template slot-scope="scope">{{scope.row.note}}</template>
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
    import {fetchList, deleteMemberLevel} from '@/api/memberLevel'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'memberLevelList',
        data() {
            return {
                operates: [
                    {
                        label: "显示品牌",
                        value: "showMemberLevel"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideMemberLevel"
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
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            handleUpdate(index, row) {
                this.$router.push({path: '/ums/updateMemberLevel', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteMemberLevel(row.id).then(response => {
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
            searchMemberLevelList() {
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
                if (this.operateType === 'showMemberLevel') {
                    showStatus = 1;
                } else if (this.operateType === 'hideMemberLevel') {
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
            addMemberLevel() {
                this.$router.push({path: '/ums/addMemberLevel'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


