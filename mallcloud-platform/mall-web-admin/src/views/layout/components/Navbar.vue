<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger class="hamburger-container" :toggleClick="toggleSideBar" :isActive="sidebar.opened"></hamburger>
    <breadcrumb></breadcrumb>
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <img class="user-avatar" :src="avatar">
        <i class="el-icon-caret-bottom"></i>
      </div>
      <el-dropdown-menu class="user-dropdown" slot="dropdown">
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            首页
          </el-dropdown-item>
        </router-link>
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>
            {{storeName}}
          </el-dropdown-item>
        </router-link>
        <router-link class="inlineBlock" to="/" v-for="item in communityUser"
                     :key="item.key">
          <el-dropdown-item>
            {{item.name}}
          </el-dropdown-item>
        </router-link>
        <el-dropdown-item divided>
          <span @click="updatePassword" style="display:block;">修改密码</span>
        </el-dropdown-item>
        <el-dropdown-item divided>
          <span @click="logout" style="display:block;">退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>

    </el-dropdown>
    <div>
      <el-dialog
              title="修改密码"
              :visible.sync="blance.dialogVisible"
              width="40%">
        <el-form :model="blance"   :rules="loginRules" ref="brandFrom" label-width="150px">

          <el-form-item label="旧密码：" prop="detail">
            <el-input v-model="blance.password"></el-input>
          </el-form-item>
          <el-form-item label="新密码：" prop="detail">
            <el-input v-model="blance.newPassword"></el-input>
          </el-form-item>

          <el-form-item label="确认新密码：" prop="detail">
            <el-input v-model="blance.renewPassword"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button @click="blance.dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="handleEditBlance">确 定</el-button>
          </el-form-item>
        </el-form>

      </el-dialog>
    </div>
  </el-menu>

</template>

<script>
import { mapGetters } from 'vuex'
import { get } from '@/utils/auth'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { communityUser ,updatePassword} from '@/api/admin'
export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data() {

    return {
      loginRules: {
        password: [{required: true, trigger: 'blur'}],
        renewPassword: [{required: true, trigger: 'blur'}],
        newPassword: [{required: true, trigger: 'blur'}]
      },
      blance:{
        dialogVisible:false,
        id:null,

      },
      redList: null,
      communityUser: null,
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'storeName',
      'avatar'
    ])
  },
  created() {
    /*communityUser(get('USRTID')).then(res => {
      this.communityUser=res.data;
    });*/
  },
  methods: {
    handleEditBlance(){
      this.$confirm('是否要修改密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        let params = new URLSearchParams();
        params.append('password', this.blance.password);
        params.append('renewPassword', this.blance.renewPassword);
        params.append('newPassword', this.blance.newPassword);

        updatePassword(params).then(response => {
          this.$message({
            message: '修改密码成功',
            type: 'success',
            duration: 1000
          });
        });
        this.blance.dialogVisible=false;

      });
    },

    updatePassword(){
      this.blance.dialogVisible=true;


    },
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

