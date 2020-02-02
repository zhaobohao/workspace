<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="searchOwnerList()"
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
                    @click="addOwner()"
                    size="mini">
                添加
            </el-button>
            <el-button @click="exportExcel" type="success" size="mini" icon="el-icon-download">导出</el-button>

            <el-upload
                    style="display: inline"
                    :show-file-list="false"
                    :on-success="onSuccess"
                    :on-error="onError"
                    :before-upload="beforeUpload"
                    :action="importExcel">
                <el-button size="mini" type="success" :disabled="!enabledUploadBtn" :icon="uploadBtnIcon">导入</el-button>
            </el-upload>
        </el-card>
        <div class="table-container">
            <el-table ref="ownerTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>

                <el-table-column label="业主成员ID" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="业主ID" align="center">
                    <template slot-scope="scope">{{scope.row.ownerId}}</template>
                </el-table-column>
                <el-table-column label="房间ID" align="center">
                    <template slot-scope="scope">{{scope.row.roomId}}</template>
                </el-table-column>
                <el-table-column label="类型" align="center">
                    <template slot-scope="scope">{{scope.row.type |formatStatus}}</template>
                </el-table-column>
                <el-table-column label="业主名称" align="center">
                    <template slot-scope="scope">{{scope.row.name}}</template>
                </el-table-column>
                <el-table-column label="性别" align="center">
                    <template slot-scope="scope">{{scope.row.sex}}</template>
                </el-table-column>
                <el-table-column label="年龄" align="center">
                    <template slot-scope="scope">{{scope.row.age}}</template>
                </el-table-column>
                <el-table-column label="联系人手机号" align="center">
                    <template slot-scope="scope">{{scope.row.phone}}</template>
                </el-table-column>
                <el-table-column label="备注" align="center">
                    <template slot-scope="scope">{{scope.row.remark}}</template>
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
    </div>
</template>
<script>
    import {fetchList, deleteOwner} from '@/api/build/owner'
    import {formatDate} from '@/utils/date';
    export default {
        name: 'ownerList',
        data() {
            return {
                importExcel: process.env.BASE_API+'/building/owner/importExcel',
                uploadBtnIcon:'el-icon-upload2',
                enabledUploadBtn:true,
                dialogVisible:false,
                btnText:'数据导入',
                operates: [
                    {
                        label: "显示品牌",
                        value: "showOwner"
                    },
                    {
                        label: "隐藏品牌",
                        value: "hideOwner"
                    }
                ],
                operateType: null,
                cid:0,
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
                    return '业主本人';
                } else if (value === 2) {
                    return '家庭成员';
                }
            },
        },
        methods: {
            onSuccess(response,file,fileList){
                this.enabledUploadBtn=true;
                this.uploadBtnIcon="el-icon-upload2";
                this.btnText='数据导入';
                alert("数据导入成功！");

            },

            onError(err,file,fileList){
                this.enabledUploadBtn=true;
                this.uploadBtnIcon="el-icon-upload2";
                this.btnText='数据导入';
                alert("数据导入失败！请检查是否有重复数据,和网络连接状况！");
            },
            beforeUpload(file){
                this.enabledUploadBtn=false;
                this.uploadBtnIcon="el-icon-loading";
                this.btnText='正在导入';
            },
            exportExcel(){
                window.open(process.env.BASE_API+'/building/owner/exportExcel')
            },
            getList() {
                this.listLoading = true;
                this.cid=this.$route.query.cid;
                if (this.cid) {
                    this.listQuery.roomId=this.cid;
                }
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
                this.$router.push({path: '/build/updateOwner', query: {id: row.id}})
            },
            handleDelete(index, row) {
                this.$confirm('是否要删除该品牌', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    deleteOwner(row.id).then(response => {
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
            searchOwnerList() {
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
                if (this.operateType === 'showOwner') {
                    showStatus = 1;
                } else if (this.operateType === 'hideOwner') {
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
            addOwner() {
                this.$router.push({path: '/build/addOwner',query:{cid:this.$route.query.cid}});
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss" scoped>


</style>


