<template> 
  <div class="app-container">
    <div class="table-layout">
      <el-row>
        <el-col :span="4" class="table-cell-title">商品</el-col>
        <el-col :span="4" class="table-cell-title">商品价格</el-col>
        <el-col :span="4" class="table-cell-title">拼团价格</el-col>
        <el-col :span="4" class="table-cell-title">开始时间</el-col>
        <el-col :span="4" class="table-cell-title">结束时间</el-col>
        <el-col :span="4" class="table-cell-title">拼团小时</el-col>

      </el-row>
      <el-row>
        <el-col :span="4" class="table-cell">{{group.goodsName}}</el-col>
        <el-col :span="4" class="table-cell">{{group.originPrice }}</el-col>
        <el-col :span="4" class="table-cell">{{group.groupPrice}}</el-col>
        <el-col :span="4" class="table-cell">{{group.startTime|formatDate}}</el-col>
        <el-col :span="4" class="table-cell">{{group.endTime|formatDate}}</el-col>
        <el-col :span="4" class="table-cell">{{group.hours}}</el-col>
      </el-row>
      <el-row>


        <el-col :span="4" class="table-cell-title">拼团总人数</el-col>
        <el-col :span="4" class="table-cell-title">状态</el-col>
        <el-col :span="4" class="table-cell-title">创建时间</el-col>

      </el-row>
      <el-row>

        <el-col :span="4" class="table-cell">{{group.maxPeople}}</el-col>
        <el-col :span="4" class="table-cell">{{group.endTime | formatStatus}}</el-col>
        <el-col :span="4" class="table-cell">{{group.createTime | formatDate}}</el-col>
      </el-row>
    </div>
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
          style="float:right"
          type="primary"
          @click="handleSearchList()"
          size="small">
          查询搜索
        </el-button>
        <el-button
          style="float:right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small">
          重置
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="使用状态：">
            <el-select v-model="listQuery.status" placeholder="全部" clearable class="input-width">
              <el-option v-for="item in useTypeOptions"
                         :key="item.value"
                         :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

        </el-form>
      </div>
    </el-card>
    <div class="table-container">
      <el-table ref="groupHistoryTable"
                :data="list"
                style="width: 100%;"
                v-loading="listLoading" border>

        <el-table-column label="编号" width="140" align="center">
          <template slot-scope="scope">{{scope.row.id}}</template>
        </el-table-column>
        <el-table-column label="拼团人员" width="140" align="center">
          <template slot-scope="scope">{{scope.row.list | formatList}}</template>
        </el-table-column>

        <el-table-column label="状态" width="100" align="center">
          <template slot-scope="scope">{{scope.row.status | formatMemberStatus}}</template>
        </el-table-column>
        <el-table-column label="创建时间"  align="center">
          <template slot-scope="scope">{{scope.row.createTime | formatDate}}</template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes,prev, pager, next,jumper"
        :current-page.sync="listQuery.pageNum"
        :page-size="listQuery.pageSize"
        :page-sizes="[5,10,15]"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script>
  import {formatDate} from '@/utils/date';
  import {listGroupMember,getGroup} from '@/api/sms/group';
  const defaultListQuery = {
    pageNum: 1,
    pageSize: 10,

    groupId:''
  };
  const defaultUseTypeOptions= [
    {
      label: "未开始",
      value: 1
    },
    {
      label: "活动中",
      value: 2
    },
    {
      label: "已结束",
      value: 3
    },
    {
      label: "已失效",
      value: 4
    }
  ];

  export default {
    name: 'groupHistoryList',
    data() {
      return {
        group: {},
        listQuery: Object.assign({}, defaultListQuery),
        useTypeOptions:Object.assign({},defaultUseTypeOptions),
        list:null,
        total:null,
        listLoading:false
      }
    },
    created() {
      getGroup(this.$route.query.id).then(response => {
        this.group = response.data;
      });
      this.listQuery.groupId=this.$route.query.id;
      this.getList();
    },
    filters: {
      formatType(type) {
        for (let i = 0; i < defaultTypeOptions.length; i++) {
          if (type === defaultTypeOptions[i].value) {
            return defaultTypeOptions[i].label;
          }
        }
        return '';
      },

      formatDate(time) {
        if (time == null || time === '') {
          return 'N/A';
        }
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      },
      formatMemberStatus(value) {
        console.log(value)
        if (value === 2) {
          return '拼团成功';
        } else if (value === 1) {
          return '失败';
        }
      },
      formatList(value){
        if(value==null){
          return '';
        }else{
          let result='';
          for(let i=0;i<value.length;i++){
            result+=value[i].name;
            result+=":";
          }
          return result;
        }
      },
      formatStatus(endTime){
        let now = new Date().getTime();
        let date = new Date(endTime);
        if(date.getTime()>now){
          return '未过期'
        }else{
          return '已过期';
        }
      },
      formatGetType(type) {
        if(type===1){
          return '主动获取';
        }else{
          return '后台赠送';
        }
      },
      formatCouponHistoryUseType(useType) {
        if (useType === 0) {
          return '未使用';
        } else if (useType === 1) {
          return '已使用';
        } else {
          return '已过期';
        }
      },
      formatTime(time) {
        if (time == null || time === '') {
          return 'N/A';
        }
        let date = new Date(time);
        return formatDate(date, 'yyyy-MM-dd hh:mm:ss')
      },
    },
    methods: {
      getList(){
        this.listLoading=true;
        listGroupMember(this.listQuery).then(response=>{
          this.listLoading=false;
          this.list=response.data;
          this.total=response.data.total;
        });
      },
      handleResetSearch() {
        this.listQuery = Object.assign({}, defaultListQuery);
        this.listQuery.groupId=this.$route.query.id;
      },
      handleSearchList() {
        this.listQuery.pageNum = 1;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 1;
        this.listQuery.pageSize = val;
        this.getList();
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val;
        this.getList();
      }
    }
  }
</script>
<style scoped>
  .app-container {
    width: 80%;
    margin: 20px auto;
  }

  .filter-container {
    margin-top: 20px;
  }

  .table-layout {
    margin-top: 20px;
    border-left: 1px solid #DCDFE6;
    border-top: 1px solid #DCDFE6;
  }

  .table-cell {
    height: 60px;
    line-height: 40px;
    border-right: 1px solid #DCDFE6;
    border-bottom: 1px solid #DCDFE6;
    padding: 10px;
    font-size: 14px;
    color: #606266;
    text-align: center;
    overflow: hidden;
  }

  .table-cell-title {
    border-right: 1px solid #DCDFE6;
    border-bottom: 1px solid #DCDFE6;
    padding: 10px;
    background: #F2F6FC;
    text-align: center;
    font-size: 14px;
    color: #303133;
  }
</style>


