<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchStoreList()"
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
                    @click="addStore()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="storeTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                                    <el-table-column label="短信数量" align="center">
                        <template slot-scope="scope">{{scope.row.smsQuantity}}</template>
                    </el-table-column>
                                    <el-table-column label="注册类型" align="center">
                        <template slot-scope="scope">{{scope.row.registerType}}</template>
                    </el-table-column>
                                    <el-table-column label="到期时间" align="center">
                        <template slot-scope="scope">{{scope.row.expireTime}}</template>
                    </el-table-column>
                                    <el-table-column label="尝试时间" align="center">
                        <template slot-scope="scope">{{scope.row.tryTime}}</template>
                    </el-table-column>
                                    <el-table-column label="联系电话" align="center">
                        <template slot-scope="scope">{{scope.row.contactMobile}}</template>
                    </el-table-column>
                                    <el-table-column label="地区省" align="center">
                        <template slot-scope="scope">{{scope.row.addressProvince}}</template>
                    </el-table-column>
                                    <el-table-column label="所购物品计划时间" align="center">
                        <template slot-scope="scope">{{scope.row.buyPlanTimes}}</template>
                    </el-table-column>
                                    <el-table-column label="创建时间" align="center">
                        <template slot-scope="scope">{{scope.row.createTime}}</template>
                    </el-table-column>
                                    <el-table-column label="是否选中" align="center">
                        <template slot-scope="scope">{{scope.row.isChecked}}</template>
                    </el-table-column>
                                    <el-table-column label="是否删除" align="center">
                        <template slot-scope="scope">{{scope.row.isDeleted}}</template>
                    </el-table-column>
                                    <el-table-column label="服务电话" align="center">
                        <template slot-scope="scope">{{scope.row.servicePhone}}</template>
                    </el-table-column>
                                    <el-table-column label="地址名" align="center">
                        <template slot-scope="scope">{{scope.row.addressLat}}</template>
                    </el-table-column>
                                    <el-table-column label="联系人名" align="center">
                        <template slot-scope="scope">{{scope.row.contactName}}</template>
                    </el-table-column>
                                    <el-table-column label="删除时间" align="center">
                        <template slot-scope="scope">{{scope.row.deleteTime}}</template>
                    </el-table-column>
                                    <el-table-column label="自己配置文件" align="center">
                        <template slot-scope="scope">{{scope.row.diyProfile}}</template>
                    </el-table-column>
                                    <el-table-column label="行业" align="center">
                        <template slot-scope="scope">{{scope.row.industryTwo}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.isStar}}</template>
                    </el-table-column>
                                    <el-table-column label="尝试" align="center">
                        <template slot-scope="scope">{{scope.row.isTry}}</template>
                    </el-table-column>
                                    <el-table-column label="图标" align="center">
                        <template slot-scope="scope">{{scope.row.logo}}</template>
                    </el-table-column>
                                    <el-table-column label="地址细节" align="center">
                        <template slot-scope="scope">{{scope.row.addressDetail}}</template>
                    </el-table-column>
                                    <el-table-column label="计划id" align="center">
                        <template slot-scope="scope">{{scope.row.planId}}</template>
                    </el-table-column>
                                    <el-table-column label="支持，维持名称" align="center">
                        <template slot-scope="scope">{{scope.row.supportName}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.name}}</template>
                    </el-table-column>
                                    <el-table-column label="1为出售中，3为已售罄，-2为仓库中，-1为回收站" align="center">
                        <template slot-scope="scope">{{scope.row.status}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.uid}}</template>
                    </el-table-column>
                                    <el-table-column label="类型" align="center">
                        <template slot-scope="scope">{{scope.row.type}}</template>
                    </el-table-column>
                                    <el-table-column label="联系QQ" align="center">
                        <template slot-scope="scope">{{scope.row.contactQq}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.addressLng}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.lastLoginTime}}</template>
                    </el-table-column>
                                    <el-table-column label="支持电话" align="center">
                        <template slot-scope="scope">{{scope.row.supportPhone}}</template>
                    </el-table-column>
                                    <el-table-column label="地址区域" align="center">
                        <template slot-scope="scope">{{scope.row.addressArea}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.contactQrcode}}</template>
                    </el-table-column>
                                    <el-table-column label="描述" align="center">
                        <template slot-scope="scope">{{scope.row.description}}</template>
                    </el-table-column>
                                    <el-table-column label="" align="center">
                        <template slot-scope="scope">{{scope.row.id}}</template>
                    </el-table-column>
                                    <el-table-column label="行业1" align="center">
                        <template slot-scope="scope">{{scope.row.industryOne}}</template>
                    </el-table-column>
                                    <el-table-column label="地址城市" align="center">
                        <template slot-scope="scope">{{scope.row.addressCity}}</template>
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
    //将$都替换为$
    import {fetchList, deleteStore} from '@/api/sys/store'
    import {formatDate} from '@/utils/date';

    export default {
        name: 'storeList',
        data() {
            return {
                operates: [
                    {
                        label: "显示类型",
                        value: "showStore"
                    },
                    {
                        label: "隐藏类型",
                        value: "hideStore"
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
                        {path: '/sys/updateStore', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该类型', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteStore(row.id
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
            searchStoreList() {
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
                if (this.operateType === 'showStore') {
                    showStatus = 1;
                } else if (this.operateType === 'hideStore') {
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
            addStore() {
                //手动将router,改成$router
                this.router.push({path: '/sys/addStore'})
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


