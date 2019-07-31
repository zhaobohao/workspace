<template>
      <div class="main">
        <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="addUserFlag = true" style="margin-bottom:10px;">添加配置</el-button>
        <add-config :addVisiable="addUserFlag" @closeDialog="close"></add-config>
        <edit-config :editVisiable="editUserFlag" @closeDialog="close" ref='editConfig'></edit-config>
        <el-table
          :data="tableData"
          border
          v-loading="loading"
          style="width: 100%">
          <el-table-column
            prop="keyName"
            label="配置域"
            width="200">
          </el-table-column>
          <el-table-column
            prop="fieldName"
            label="配置项"
            >
          </el-table-column>
          <el-table-column
            prop="fieldValue"
            label="配置值"
            width="200">
          </el-table-column>
          <el-table-column
            prop="remark"
            label="描述"
           >
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
  import addConfig from '@/views/allscope/addConfig'
  import editConfig from '@/views/allscope/editConfig'

  export default {
    data() {
      return {
        addUserFlag: false,
        editUserFlag:false,
        tableData: [],
        total: 0,
        pageSize: 10,
        pageIndex: 1,
        dialogFormVisible: false,
        loading:true,
        config: {
        color: '0,0,255',
        count: 200,
      }
      }
    },
    components: {
      addConfig,
      editConfig,
    },
    mounted() {
      this.getReq()
    },
    methods: {
      handleEdit(index,row){
        this.editUserFlag = true
        this.$refs.editConfig.received(row)
      },
      close(flag,reLoad){
        this.addUserFlag = flag
        this.editUserFlag = flag
        if(reLoad){
          this.getReq()
        }
      },
      getReq() {
        var that = this
        that.loading = true
        ApiUtil.post("global.config.pagelist", {
          pageIndex:that.pageIndex,
          pageSize:that.pageSize,
        }, function (resp) {
          if (resp.code == '0') {
            that.loading = false
            that.tableData = resp.data.rows
            that.total = resp.data.total
          }
        })
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val
        this.getReq()
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.pageIndex = val
        this.getReq()
      }
    },
    // mounted(){
    //   const config = {
    //   color: '255,0,0',
    //   count: 88,
    // };
    // var element = document.getElementsByClassName('main')[0]
    // // 在 element 地方使用 config 渲染效果
    // const cn = new CanvasNest(element, config);
    // }
  }
</script>
<style scoped>
  .el-pagination {
    margin-top: 20px;
  }

  .main {
    box-sizing: border-box;
    line-height: 50px;
    font-size: 18px;
    color: #555;
  }
</style>
