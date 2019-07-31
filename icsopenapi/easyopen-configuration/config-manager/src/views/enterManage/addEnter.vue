<template>
  <el-dialog title="接入方信息" :visible="addVisiable" @close="cancel()">
    <el-form :model="form" status-icon :rules="rules" ref="form" label-position="left" label-width="100px" style='width: 80%; margin-left:50px;'>
      <el-button type="text" @click="generateKey" title='点击生成appKey和secret'>生成appKey和secret</el-button>
      <el-form-item label="appKey" prop="appKey">
        <el-input type="text" v-model="form.appKey" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="secret" prop="secret">
        <el-input type="text" v-model="form.secret" auto-complete="off"></el-input>
      </el-form-item>
      <el-button type="text" @click="generatePubKey" title='点击生成公钥 私钥'>生成公钥 私钥</el-button>
      <el-form-item label="公钥" prop="pubKey">
        <el-input type="textarea" v-model="form.pubKey" rows='3'></el-input>

      </el-form-item>
      <el-form-item label="私钥" prop="priKey">
        <el-input type="textarea" v-model="form.priKey" rows='3'></el-input>
      </el-form-item>
      <el-form-item label="角色" prop="roleCode">
        <el-checkbox-group v-model="form.roleCode">
          <el-checkbox :label="item.id" name="type" v-for="item of roleList" :key="item.id">{{item.text}}</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">开启</el-radio>
          <el-radio :label="1">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="saveConfig">添 加</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  export default {
    props:{
      addVisiable:{
        type:Boolean,
        default:false
      },
      roleList:{
        type:Array
      }
    },

    data(){
      return {
        form: {
          appKey:'',
          secret:'',
          priKey:'', //私钥
          pubKey:'', //公钥
          status:0,
          roleCode:[],
          app:''
        },
        checkAll: false,
        checkedCities: [],
        cities: this.roleList,
        isIndeterminate: true,

        rules: {
          appKey: [
            { required: true, message: 'appKey不能为空', trigger: 'change' }
          ],
          secret: [
            { required: true, message: 'secret不能为空', trigger: 'change' }
          ],
          roleCode: [
            { type: 'array',required: true, message: '角色不能为空', trigger: 'change' }
          ],
          status: [
            { required: true, message: '请选择状态', trigger: 'change' }
          ]
        },
      }
    },
    methods:{
      generateKey(){
        var that = this
        ApiUtil.post("client.appkeysecret.create",{},function(resp){
          if(resp.code=='0'){
            that.form.appKey = resp.data.appKey
            that.form.secret = resp.data.secret
          }
        })
      },
      generatePubKey(){
        var that = this
        ApiUtil.post("client.pubprikey.create",{},function(resp){
          if(resp.code=='0'){
            that.form.priKey = resp.data.priKey
            that.form.pubKey = resp.data.pubKey
          }
        })
      },
      saveConfig() {
        var that = this
        this.$refs.form.validate((valid) => {
          if (valid) {
            that.form.app = that.$route.params.app
            ApiUtil.post("app.client.add",that.form,function(resp){
              if(resp.code=='0'){
                that.$message({
                  message: '添加成功',
                  type: 'success'
                });
                that.cancel(true)
              } else if(resp.code=='-9'){
                that.$message.error('appKey已存在');
                return false;
              }
            })
          } else {
            console.log('请填写完整');
            return false;
          }
        });
      },
      cancel(data){
        if(data==true){
          this.reLoad = data
        }else{
          this.reLoad = false
        }
        this.$refs.form.resetFields();
        this.$emit('closeDialog',false,this.reLoad)
      },
    },
  }
</script>
<style></style>
