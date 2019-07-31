<template>
  <el-dialog title="公钥私钥" :visible="keyVisiable" @close="cancel()">
    <el-form :model="curObj" status-icon ref="form" style="width:80%;padding-left:40px;">
      
      <el-form-item label="公钥" prop="pubKey">
        <el-button type="text" 
          v-clipboard:copy="curObj.pubKey"
          v-clipboard:success="onCopy"
          v-clipboard:error="onError"
          title='点击复制公钥'>复制
        </el-button>
        <el-input type="textarea" v-model="curObj.pubKey" id='pubkey' rows='3'></el-input>
      </el-form-item>
     
      <el-form-item label="私钥" prop="priKey">
        <el-button type="text" 
          v-clipboard:copy="curObj.priKey"
          v-clipboard:success="onCopy"
          v-clipboard:error="onError"
          title='点击复制私钥'>复制
        </el-button>
        <el-input type="textarea" v-model="curObj.priKey" rows='12'></el-input>
      </el-form-item>
    </el-form>
     <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">返回</el-button>
    </div>
  </el-dialog>
</template>
<script>
  export default {
    props:{
      keyVisiable:{
        type:Boolean,
        default:false
      },
      curObj:{
        type:Object
      }
    },
    methods:{
      cancel(){
        this.$refs.form.resetFields();
        this.$emit('closeDialog',false)
      },
      onCopy: function (e) {
        this.$message({
          type:'success',
          message:'复制成功'
        })
        console.log('你刚刚复制: ' + e.text)
      },
      onError: function (e) {
        console.log('无法复制文本！')
      }
    }
  }
</script>
<style></style>
