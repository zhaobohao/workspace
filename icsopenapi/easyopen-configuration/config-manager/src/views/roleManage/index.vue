<template>
      <div class="main">
        <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="addRoleFlag = true">添加角色</el-button>
        <add-role :addVisiable="addRoleFlag" @closeDialog="close" :form='tableData'></add-role>
        <edit-role :editVisiable="editRoleFlag" :formObj="form" @closeDialog="close" ref='editRole'></edit-role>
        <api-role ref='apirole' :apiVisiable="apiRoleFlag" :apiListObj="curApiList" @closeDialog="close" :apiList1='apiList'></api-role>
        <el-row :gutter="20" v-loading="loading">
          <el-col :span="6" v-for="(item) of tableData" :key='item.roleCode' style='margin-bottom:20px;'>
            <el-card class="box-card">
              <div slot="header" class="clearfix">
                <span style="font-weight:bold">{{item.roleCode}}</span>
              </div>
              <div class="component-item">
                <div class='description'>角色描述:<span class='item-text'>{{item.description}}</span></div>
                <div class='addtime'>添加时间:<span class='item-text'>{{format(item.gmtCreate)}}</span></div>
                <div class='foot-btn'>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="handleEdit(item)">修改
                  </el-button>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="handleEditRole(item)">接口权限
                  </el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  import formatTime from '@/utils/formatTime'
  import addRole from '@/views/roleManage/addRole'
  import editRole from '@/views/roleManage/editRole'
  import apiRole from '@/views/roleManage/apiRole'

  export default {
    data() {
      return {
        addRoleFlag: false,
        editRoleFlag:false,
        apiRoleFlag:false,
        tableData: [],
        form:{},
        loading:true,
        app:'',
        apiList:[],
        curApiList:[]
      }
    },
    components: {
        addRole,
        editRole,
        apiRole
    },
    mounted() {
      this.getReq()
    },
    methods: {
      format(time){
        return formatTime(time)
      },
      handleEdit(row){
        this.editRoleFlag = true
        this.form = row
        this.$refs.editRole.received(row)
      },
      handleEditRole(row){
        var that = this
        that.curApiList = []
        ApiUtil.post("role.api.list",row, function (resp) {
          if (resp.code == '0') {
            that.loading = false
            if(resp.data){
            resp.data.forEach( (val) => {
            that.curApiList.push(val.apiId)
            
            })
            that.$refs.apirole.checked(that.curApiList,that.apiList,row.roleCode)
           }
          }
        })
        this.apiRoleFlag = true
        
      },
      close(flag,reLoad){
        this.addRoleFlag = flag
        this.editRoleFlag = flag
        this.apiRoleFlag = flag 
        if(reLoad){
          this.getReq()
        }
      },
      getReq() {
        var that = this
        that.loading = true
        ApiUtil.post("role.listall", {}, function (resp) {
          if (resp.code == '0') {
            that.loading = false
            that.tableData = resp.data.rows
          }
        })
        that.app = that.$route.params.app
        ApiUtil.post("app.api.tree.listall", {
          app:that.app
        }, function (resp) {
          if (resp.code == '0') {
            that.loading = false
            that.apiList = resp.data[0].children
          }
        })
      },
      
    },
    watch: {
      '$route' (to, from) {
        this.getReq()
      }
    }
  }
</script>
<style scoped>
  .el-button {
    margin-bottom: 10px;
  }
  .foot-btn{
    text-align: center;
    position:absolute;
    bottom: 0;
    right:0

  }
  .item-text{
    color:#222;
    margin-left: 10px;
    font-weight: normal;
  }
  .el-row{
    margin-top: 0;
  }
  .component-item{
    height: 150px;
    font-weight: bold;
    position: relative;
  }
  .description,.addtime{
    font-size: 15px;
    margin-bottom: 20px;
  }
  .el-pagination {
    margin-top: 20px;
  }
  
</style>
