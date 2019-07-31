<template>
    <div class="main">
      <!-- 查询和其他操作 -->
        <div class="filter-container">
          <el-button type="warning" plain @click="manySet" icon='el-icon-edit'>批量设置</el-button>
          <el-input clearable class="filter-item" style="width: 200px;" placeholder="请输入接口名" v-model.trim="appkey" @keyup.enter.native="onSearch">
          </el-input>
          <el-select v-model="status" placeholder="开启状态">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
           <el-select v-model="limitType" placeholder="策略">
            <el-option
              v-for="item in options2"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-button class="filter-item" type="primary" icon="el-icon-search" @click="onSearch">查找</el-button>
        </div>

      <limit-config :limitVisiable="limitFlag" @closeDialog="close" ref='limitConfig'></limit-config>
      <many-set :manyVisiable="manyFlag" @closeDialog="close" ref='manyConfig'></many-set>

      <el-table
        :data="tableData"
        border
        @selection-change="handleSelectionChange"
        element-loading-text="正在查询中。。。"
        v-loading="loading"
        style="width: 100%"
        ref='table'>
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="name"
          label="接口名">
        </el-table-column>
        <el-table-column
          prop="version"
          label="版本号">
        </el-table-column>
        <el-table-column
          prop="limitCount"
          label="每秒可处理请求数"
          sortable
          >
          <template slot-scope="scope">
            <span>{{scope.row.limitType == 'LIMIT' ? scope.row.limitCount:'-'}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="limitCode"
          label="code"
         >
          <template slot-scope="scope">
            <span>{{scope.row.limitType == 'LIMIT' ? scope.row.limitCode:''}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="limitMsg"
          label="msg"
          >
          <template slot-scope="scope">
            <span>{{scope.row.limitType == 'LIMIT' ? scope.row.limitMsg:''}}</span>
          </template>
        </el-table-column>
        <el-table-column
          sortable
          prop="tokenBucketCount"
          label="令牌通容量">
          <template slot-scope="scope">
            <span>{{scope.row.limitType == 'TOKEN_BUCKET' ? scope.row.tokenBucketCount:'-'}}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          label="开启状态">
          <template slot-scope="scope">
            <el-button
              plain
              size='mini'
              @click='toggleOpen(scope.$index, scope.row)'
              :type="scope.row.status == 0 ? 'primary' : 'success'"
              disable-transitions>{{scope.row.status==0? '关闭':'开启'}}</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="limitType"
          label="当前策略"
          >
          <template slot-scope="scope">
            <span>{{scope.row.limitType == 'TOKEN_BUCKET' ? '令牌桶策略':'限流策略'}}</span>
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
  import limitConfig from '@/views/netManage/limit'
  import manySet from '@/views/netManage/manySet'


  export default {
    data() {
      return {
        limitFlag: false,
        manyFlag: false,
        tableData: [],
        total: 0,
        pageSize: 10,
        pageIndex: 1,
        form:{},
        app:'',
        keys:'未设置',
        appkey:'',
        roleList:[],
        roleCodeList:[],
        loading:true,
        status:'',
        limitType:'',
        options1: [
        {
          value: null,
          label: '全部'
        },
        {
          value: '1',
          label: '开启'
        }, {
          value: '0',
          label: '关闭'
        }
         ],
        options2: [
        {
          value: null,
          label: '全部'
        },
        {
          value: 'LIMIT',
          label: '限流策略'
        }, {
          value: 'TOKEN_BUCKET',
          label: '令牌桶策略'
        }
        ],
        status: null,
        limitType: null,
        multipleSelection:[]
      }
    },
    components: {
      limitConfig,
      manySet
    },
    methods: {
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      onSearch(){
        this.pageIndex = 1
        this.pageSize = 10
        this.getReq()

      },
      toggleOpen(index,row){
        var that = this
        that.form = row
        that.form.apiIds = []
        that.form.apiIds.push(that.form.apiId)
        if(that.form.status==0){
          that.form.status = 1
        }else{
          that.form.status = 0
        }
        ApiUtil.post("app.limit.update",that.form,function(resp){
              if(resp.code=='0'){
                console.log(resp)
              }
            })
      },
      // 批量设置
      manySet(){
        if(this.multipleSelection.length==0){
          this.$message({
                  message: '请勾选数据项',
                  type: 'warning'
                });
          return
        }
        this.manyFlag = true
        this.$refs.manyConfig.checked(this.multipleSelection,this.app)
      },
  
      handleEdit(index,row){
        this.limitFlag = true
        this.form = row
        this.$refs.limitConfig.checked(row)

        },
      close(flag,reLoad){
        this.limitFlag = flag
        this.manyFlag = flag
        if(reLoad){
          this.getReq()
        }
      },
      getReq() {
        var that = this
        that.loading = true
        that.app = that.$route.params.app
        ApiUtil.post("app.limit.pagelist", {
          name:that.appkey,
          status:that.status,
          limitType:that.limitType,
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
        this.limitType = null
        this.status = null
        this.getReq()
      }
    }

  }
</script>
<style>
  .el-pagination {
    margin-top: 20px;
  }
  .el-select-dropdown__list{
    margin: 0;
  }
  .filter-container{
    margin-bottom: 10px;
  }

</style>
