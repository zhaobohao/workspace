<template>
  <div class="loginPage">
    <div class="login">
      <p class="title">用户登录</p>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-form-item prop="userNo" >
          <el-input v-model.trim="form.userNo" placeholder="账号" autoComplete="on"></el-input>
        </el-form-item>
        <el-form-item prop="userPwd">
          <el-input :type="pwdShow" v-model.trim="form.userPwd" placeholder="密码" @keyup.enter.native="login" autoComplete="on">
            <i slot="suffix" class="iconfont eye" :class='pwdClass'  @click="changeEye"></i>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login" :loading="loginStatus" :disabled="loginStatus" class='login-btn'>登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import ApiUtil from '@/utils/ApiUtil'
  import hex_md5 from '@/utils/MD5'
  export default {
    name: 'login',
    data () {
      var clear = (rule, value, callback) => {
        this.error = ''
        callback()
      };
      return {
        // 控制登录按钮是否显示加载状态
        loginStatus:false,
        form: {
          userNo: '',
          userPwd: ''
        },
        pwdShow:'password',// 显示密码
        pwdClass:'icon-icon-eye-close',
        error: '',// 登录错误信息
        remember:false,//记住账号
        rules: {
          userNo: [{ required: true, message: '账号不能为空', trigger: 'blur' }, {validator: clear, trigger: 'change'}],
          userPwd: [{ required: true, message: '密码不能为空', trigger: 'blur' }, {validator: clear, trigger: 'change'}]
        }
      }
    },
    methods: {
      login: function () {
        var that = this
        var JWT_KEY = ApiUtil.JWT_KEY
        this.$refs.form.validate((valid) => {
          if (valid) {
            var pwd = this.form.userPwd
            pwd = hex_md5(pwd);
            ApiUtil.post('nologin.admin.login',{
              username:this.form.userNo,
              password:pwd
            },function(resp){
              localStorage.setItem(JWT_KEY,resp.data)
              that.$router.push({path:"/"})
            });
          }
        })
      },
      // 改变密码显示状态
      changeEye(){
        if(this.pwdShow==='password'){
          this.pwdShow  = '';
          this.pwdClass = 'icon-icon-eye-open'
        }else{
          this.pwdShow  = 'password';
          this.pwdClass = 'icon-icon-eye-close'
        }
      }
    },
    mounted(){
      let account =  window.localStorage.account;
      if(account){
        this.form.userNo = account;
        this.remember = true;
      }
    }
  }
</script>

<style scoped lang="scss">
.el-form-item.is-success .el-input__inner, .el-form-item.is-success .el-input__inner:focus, .el-form-item.is-success .el-textarea__inner, .el-form-item.is-success .el-textarea__inner:focus{
  border-color:#4965d6!important;
}
  .loginPage{
    width: 100%;
    height:100%;
    display: flex;
    justify-content: center;
    align-items: center;
    background: url('/config/static/background.jpg');
    // background: -webkit-linear-gradient(#859398 , #283048); /* Safari 5.1 - 6.0 */
    // background: -o-linear-gradient(#859398 , #283048); /* Opera 11.1 - 12.0 */
    // background: -moz-linear-gradient(#859398 , #283048); /* Firefox 3.6 - 15 */
    // background: linear-gradient(#859398 , #283048); /* 标准的语法 */
    .login{
      background-color: #fff;
      filter:alpha(opacity=100);
      opacity: 1;
      -moz-opacity:1;
      width: 400px;
      /*padding: 10% auto;*/
      border: 1px solid #000;
      border-radius: 10px;
      text-align: center;
      .title{
        font-size: 24px;
        color: #222;
        line-height: 80px;
        margin-bottom: 20px;
        font-family:'Courier New', Courier, monospace
      }
      .login-btn{
        background: linear-gradient(#8643d6 , #4965d6); /* 标准的语法 */
      }
      .el-form{
        width: 80%;
        margin: 0 auto;
        .el-button{
          width: 100%;
        }
        .eye{
          margin: 0 5px;
          &:hover{
            cursor: pointer;
          }
        }
        .el-button:hover {
          border-color: #66b1ff;
          color: #fff;
          opacity: 0.9;
        }
        .rememberErr{
          display: flex;
          justify-content: space-between;
          align-items: center;
          .el-checkbox{
            color:#fff;
          }
        }
      }
    }
  }
</style>
