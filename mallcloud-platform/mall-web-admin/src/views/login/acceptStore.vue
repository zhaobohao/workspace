<template>
  <div>
    <el-card class="login-form-layout">
      <el-form autoComplete="on"
               :model="storeForm"
               :rules="storeRules"
               ref="storeForm"
               label-position="left">
        <div style="text-align: center">
          <svg-icon icon-class="login-mall" style="width: 56px;height: 56px;color: #409EFF"></svg-icon>
        </div>
        <h2 class="login-title color-main">mallplus商户入驻</h2>

        <el-form-item prop="name">
          <el-input name="name"
                    type="text"
                    v-model="storeForm.name"
                    autoComplete="on"
                    placeholder="请输入店铺名">
          <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="supportName">
          <el-input name="supportName"
                    type="text"
                    v-model="storeForm.supportName"
                    autoComplete="on"
                    placeholder="请输入密码">
          <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item label="类别：" prop="type">
                <el-select
                  v-model="storeForm.type"
                  @change="handlecateChange"
                  placeholder="请选择分类">
                  <el-option
                    v-for="item in cateOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
        <el-form-item label="图像：" prop="logo">
          <single-upload v-model="storeForm.logo"></single-upload>
        </el-form-item>
        <el-form-item prop="contactQq">
          <el-input name="contactQq"
                    type="text"
                    v-model="storeForm.contactQq"
                    autoComplete="on"
                    placeholder="请输入QQ">
          <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="supportPhone">
          <el-input name="supportPhone"
                    type="text"
                    v-model="storeForm.supportPhone"
                    autoComplete="on"
                    placeholder="请输入电话">
          <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="addressLat">
          <el-input name="addressLat"
                    type="text"
                    v-model="storeForm.addressLat"
                    autoComplete="on"
                    placeholder="请输入地址">
          <span slot="prefix">
            <svg-icon icon-class="user" class="color-main"></svg-icon>
          </span>
          </el-input>
        </el-form-item>

        <el-form-item style="margin-bottom: 60px">
          <el-button style="width: 100%" type="primary" :loading="loading" @click.native.prevent="handleLogin">
            商户入驻
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <img :src="login_center_bg" class="login-center-layout">
  </div>
</template>

<script>
  import {isvalidUsername} from '@/utils/validate';
   import {fetchList, listBakCate} from '@/api/sys/store'
  import login_center_bg from '@/assets/images/login_center_bg.png'
  import {createStore, getStore, updateStore} from '@/api/sys/store'
  import SingleUpload from '@/components/Upload/singleUpload'
  const defaultAdmin={
      name: '',
      addressLat:'',
      logo:'',
      contactQq:'',
      supportPhone:'',
      addressLat:'',
      supportName:''


  };
  export default {
      components:{SingleUpload},
    name: 'login',
    data() {

      return {
      cateOptions:null,
          storeForm:Object.assign({}, defaultAdmin),
        storeRules: {
            name: [
                {required: true, message: '请输入店铺名称', trigger: 'blur'},
                {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
            ]
        },
        loading: false,
        pwdType: 'password',
        login_center_bg
      }
    },
     created() {

          this.getCateList();
        },
    methods: {
     getCateList() {
            listBakCate({pageNum: 1, pageSize: 100}).then(response => {
              this.cateOptions = [];
            let brandList = response.data;
            for (let i = 0; i < brandList.length; i++) {
              this.cateOptions.push({label: brandList[i].name, value: brandList[i].id});
            }
          });
          },
      showPwd() {
        if (this.pwdType === 'password') {
          this.pwdType = ''
        } else {
          this.pwdType = 'password'
        }
      },
      handleLogin() {
        this.$refs.storeForm.validate(valid => {
          if (valid) {
              createStore(this.storeForm).then(response => {
                  if(response.code==200)
                  {
                      this.storeForm = Object.assign({}, defaultAdmin);
                      this.$message({
                          message: '提交成功',
                          type: 'success',
                          duration: 1000
                      });
                      this.$router.push({path: '/login'})
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });

                  }
              });
          } else {
            console.log('参数验证不合法！');
            return false
          }
        })
      }
    }
  }
</script>

<style scoped>
  .login-form-layout {
    position: absolute;
    left: 0;
    right: 0;
    width: 360px;
    margin: 140px auto;
    border-top: 10px solid #409EFF;
  }

  .login-title {
    text-align: center;
  }

  .login-center-layout {
    background: #409EFF;
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
    margin-top: 200px;
  }
</style>
