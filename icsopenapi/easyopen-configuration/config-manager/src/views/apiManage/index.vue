<template>
      <div class="main">
        <give-role :RoleVisiable="handleRoleFlag" @closeDialog="close" ref='giveRole'></give-role>
        <div class="filter-container">
          <el-input clearable class="filter-item" style="width: 200px;" placeholder="请输入接口名" v-model.trim="apikey" @keyup.enter.native="onSearch">
          </el-input>
          <el-button class="filter-item" type="primary" icon="el-icon-search" @click="onSearch">查找</el-button>
        </div>

        <el-table
          :data="tableData"
          border
          v-loading="loading"
          style="width: 100%">
          <el-table-column
            prop="name"
            label="接口名"
            width="250">
          </el-table-column>
          <el-table-column
            prop="version"
            label="版本号"
            width="200">
          </el-table-column>
          <el-table-column
            prop="description"
            label="描述"
            width="300">
          </el-table-column>
          <el-table-column
            prop="gmtCreate"
            label="添加时间"
            width="250">
            <template slot-scope="scope">
            <span>{{format(scope.row.gmtCreate)}}</span>
          </template>
          </el-table-column>
          <el-table-column
            prop="status"
            label="状态"
            width="200">
            <template slot-scope="scope">
            <el-tag
              :type="scope.row.status == 0 ? 'success' : 'primary'"
              disable-transitions>{{scope.row.status == 0 ? '使用中':'未使用'}}</el-tag>
          </template>
          </el-table-column>
           <el-table-column
            prop="hasAuthed"
            label="授权状态"
            width="200">
            <template slot-scope="scope">
            <el-tag
              :type="scope.row.hasAuthed == false ? 'primary' : 'success'"
              disable-transitions>{{scope.row.hasAuthed == false? '未授权':'已授权'}}</el-tag>
          </template>
          </el-table-column>
          <el-table-column
            prop=""
            label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="handleRole(scope.$index, scope.row)">授权
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
  import giveRole from '@/views/apiManage/giveRole'
  import formatTime from '@/utils/formatTime'
  export default {
    data() {
      return {
        handleRoleFlag: false,
        tableData: [],
        total: 0,
        pageSize: 10,
        pageIndex: 1,
        loading:true,
        apikey:'',
        app:'',
        roleList:[],
        roleCodes:[]
      }
    },
    components: {
      giveRole,
    },
    mounted() {
      this.getReq()
    },
    methods: {
      onSearch(){
        this.pageIndex = 1
        this.pageSize = 10
        this.getReq()
      },
      format(time){
        return formatTime(time)
      },
      handleRole(index,row){
        var that = this
        that.handleRoleFlag = true
        that.roleCodes = []
        ApiUtil.post("api.role.listall", {
          apiId:row.id
        }, function (resp) {
          if (resp.code == '0') {
            resp.data.forEach( (val) => {
              that.roleCodes.push(val.roleCode)
            })
          }
           that.$refs.giveRole.receive(that.roleCodes,that.roleList,row.id)
        })
        
        
      },
      close(flag,reLoad){
        this.handleRoleFlag = flag
        if(reLoad){
          this.getReq()
        }
      },
      getReq() {
        var that = this
        that.loading = true
        that.app = that.$route.params.app
        ApiUtil.post("app.api.pagelist", {
          name:that.apikey,
          page: that.pageIndex,
          rows: that.pageSize,
          app: that.app,
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
    watch: {
      '$route' (to, from) {
        this.apikey = ''
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
    margin-bottom: 10px;
  }
</style>
