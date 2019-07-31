<template>
  <el-dialog title="接入方信息" :visible="editVisiable" @close="cancel">
    <el-form :model="setData" status-icon :rules="rules" initial="off" ref="form" label-position="left" label-width="100px" style='width: 80%; margin-left:50px;'>
      <el-form-item label="appKey" prop="appKey">
        <el-input type="text" v-model="form.appKey" :disabled="true"></el-input>
      </el-form-item>
      <el-button type="text" @click="generateSecret" title='点击生成secret'>生成secret</el-button>
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
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="saveConfig">修 改</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import ApiUtil from '@/utils/ApiUtil'
  export default {
    props:['editVisiable','formObj','roleList','roleCodeList'],
    data(){
      return {
        form: {
          appKey:'',
          secret:'',
          priKey:'', //私钥
          pubKey:'', //公钥
          status:0,
          roleCode:[],
          roleList:[],
          app:'',
          id:''
        },
        reLoad:false,
        rules: {
          appKey: [
            { required: true, message: 'appKey不能为空', trigger: 'blur' }
          ],
          secret: [
            { required: true, message: 'secret不能为空', trigger: 'blur' }
          ],
          roleCode: [
            { type: 'array',required: true, message: '角色不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '请选择状态', trigger: 'blur' }
          ]
        },
      }
    },
    methods:{
      generateSecret(){
        var that = this
        ApiUtil.post("client.appkeysecret.create",{},function(resp){
          if(resp.code=='0'){
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
            ApiUtil.post("app.client.update",that.form,function(resp){
              if(resp.code=='0'){
                that.$message({
                  message: '修改成功',
                  type: 'success'
                });
                that.cancel(true)
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
        this.$refs.form.clearValidate();
        this.$emit('closeDialog',false,this.reLoad)
      }
    },
    computed:{
        setData(){
            this.form.appKey = this.formObj.appKey
            this.form.secret = this.formObj.secret
            this.form.pubKey = this.formObj.pubKey
            this.form.priKey = this.formObj.priKey
            this.form.id = this.formObj.id
            this.form.status = this.formObj.status
            this.form.roleCode = this.roleCodeList
            this.form.roleList = this.roleList
            return this.form
        }
    }
  }
</script>
<style></style>
