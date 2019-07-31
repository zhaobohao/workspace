<template>
    <div class="main">
      <!-- 查询和其他操作 -->
        <div class="filter-container">
          <el-input clearable class="filter-item" style="width: 200px;" placeholder="请输入appKey" v-model.trim="appkey" @keyup.enter.native="onSearch">
          </el-input>
          <el-button class="filter-item" type="primary" icon="el-icon-search" @click="onSearch">查找</el-button>
          <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="addEnterFlag = true">添加</el-button>
        </div>

      <add-enter :addVisiable="addEnterFlag" @closeDialog="close" :roleList="roleList"></add-enter>
      <edit-enter :editVisiable="editEnterFlag" @closeDialog="close" :formObj="form" :roleList='roleList' :roleCodeList='roleCodeList'></edit-enter>
      <show-key :keyVisiable="showkeyFlag" @closeDialog="close" :curObj="curObj"></show-key>

      <el-table
        :data="tableData"
        border
        v-loading="loading"
        style="width: 100%">
        <el-table-column
          prop="appKey"
          label="appKey"
         >
        </el-table-column>
        <el-table-column
          prop="secret"
          label="secret"
          >
        </el-table-column>
        <el-table-column
          prop="roleList"
          label="角色"
          >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.roleList.length==0" type="primary" disable-transitions>未设置</el-tag>
            <!-- <span v-else v-for="item of scope.row.roleList" :key='item.id'>{{item.description}} </span> -->
            <el-tag v-else v-for="item of scope.row.roleList" :key='item.id'
              type="success"
              disable-transitions
              style="margin:0 10px 2px 0;"
              >{{item.description}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="gmtCreate"
          label="添加时间"
          width='200'
          >
          <template slot-scope="scope">
            <span>{{format(scope.row.gmtCreate)}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="priKey"
          label="公钥/私钥"
          width="120">
          <template slot-scope="scope">
            <span v-if="!scope.row.priKey && !scope.row.pubKey">未设置</span>
            <el-button v-else type="text" @click="showKey(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          width="120">
          <template slot-scope="scope">
            <el-button
              :type="scope.row.status == '1' ? 'primary' : 'success'"
              disable-transitions
              plain
              size='mini'
              @click='toggleOpen(scope.$index, scope.row)'
              >{{scope.row.status=='1'? '禁用':'启用'}}</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop=""
          label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.$index, scope.row)">修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageIndex"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  import addEnter from '@/views/enterManage/addEnter'
  import showKey from '@/views/enterManage/showKey'
  import editEnter from '@/views/enterManage/editEnter'
  import formatTime from '@/utils/formatTime'

  export default {
    data() {
      return {
        addEnterFlag: false,
        editEnterFlag: false,
        showkeyFlag:false,
        tableData: [],
        total: 0,
        pageSize: 10,
        pageIndex: 1,
        form:{},
        app:'',
        keys:'未设置',
        appkey:'',
        curObj:{},
        roleList:[],
        roleCodeList:[],
        loading:true
      }
    },
    components: {
      addEnter,
      showKey,
      editEnter
    },
    methods: {
      format(time){
        return formatTime(time)
      },
      onSearch(){
        this.pageIndex = 1
        this.pageSize = 10
        this.getReq()
      },
      showKey(curObj){
        this.curObj = curObj
        this.showkeyFlag = true
      },
      toggleOpen(index,row){
        var that = this
        that.form = row
        that.form.app = that.$route.params.app
        if(row.roleList){
          that.roleCodeList = []
            row.roleList.forEach( (val) => {
            that.roleCodeList.push(val.roleCode)
          } )
        }
        if(that.roleCodeList.length==0){
          that.$message({
            type:'warning',
            message:'请先设置角色'
          })
          return
        }
        that.form.roleCode = that.roleCodeList
        if(that.form.status==0){
          that.form.status = 1
        }else{
          that.form.status = 0
        }
        ApiUtil.post("app.client.update",that.form,function(resp){
              if(resp.code=='0'){
                that.getReq()
              }
            })
      },
      handleEdit(index,row){
        this.editEnterFlag = true
        this.form = row
        
        if(row.roleList){
          this.roleCodeList = []
            row.roleList.forEach( (val) => {
            this.roleCodeList.push(val.roleCode)
          } )
        }
      },
      close(flag,reLoad){
        this.addEnterFlag = flag
        this.editEnterFlag = flag
        this.showkeyFlag = flag
        if(reLoad){
          this.getReq()
        }
      },
      getReq() {
        var that = this
        that.loading = true
        this.app = that.$route.params.app
        ApiUtil.post("app.client.list", {
          appkey:that.appkey,
          page: that.pageIndex,
          rows: that.pageSize,
          app:that.app,
        }, function (resp) {
          if (resp.code == '0') {
            that.loading = false
            that.tableData = resp.data.rows
            that.total = resp.data.total
          }
        })
        ApiUtil.post("role.tree.listall", {}, function (resp) {
          if (resp.code == '0') {
            that.roleList = resp.data[0].children
          }
        })
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.getReq()
      },
      handleCurrentChange(val) {
        this.pageIndex = val
        this.getReq()
      }
    },
    mounted() {
      this.getReq()
    },
    
    watch: {
      '$route' (to, from) {
        this.appkey = ''
        this.pageSize = 10
        this.pageIndex = 1
        this.getReq()
      }
    }

  }
</script>
<style scoped>
  .el-pagination {
    margin-top: 20px;
  }
  .filter-container{
    margin-bottom:10px;
  }
  .el-table td, .el-table th{
    padding: 8px 0;
  }
</style>
