<template>
  <div class="app-container">
    <el-button class="el-icon-back" type="text" @click="onBack">返回</el-button>
    <el-form
      ref="isvKeysForm"
      :rules="rulesIsvKeysForm"
      :model="isvKeysFormData"
      label-width="160px"
      size="mini"
      style="width: 700px;"
    >
      <el-form-item label="">
        <el-alert
          title="带 ★ 的分配给开发者"
          type="warning"
          :closable="false"
        />
      </el-form-item>
      <el-form-item :label="selfLabel('appId')">
        <div>{{ isvKeysFormData.appKey }}</div>
      </el-form-item>
      <el-form-item v-show="showKeys()" label="秘钥格式">
        <el-radio-group v-model="isvKeysFormData.keyFormat">
          <el-radio :label="1" name="keyFormat">PKCS8(JAVA适用)</el-radio>
          <el-radio :label="2" name="keyFormat">PKCS1(非JAVA适用)</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-show="isvKeysFormData.signType === 2" prop="secret" :label="selfLabel('secret')">
        <el-input v-model="isvKeysFormData.secret" /> <el-button type="text" @click="onGenSecret">重新生成</el-button>
      </el-form-item>
      <el-tabs v-show="showKeys()" v-model="activeName" type="card" class="keyTabs">
        <el-tab-pane label="ISV公私钥" name="first">
          <el-form-item class="gen-key">
            <el-button type="text" @click="onGenKeysIsv">重新生成</el-button>
          </el-form-item>
          <el-form-item prop="publicKeyIsv" label="ISV公钥">
            <el-input v-model="isvKeysFormData.publicKeyIsv" type="textarea" />
          </el-form-item>
          <el-form-item prop="privateKeyIsv" :label="selfLabel('ISV私钥')">
            <el-input v-model="isvKeysFormData.privateKeyIsv" type="textarea" />
          </el-form-item>
        </el-tab-pane>
        <el-tab-pane label="平台公私钥[可选]" name="second">
          <el-form-item class="gen-key">
            <el-button type="text" @click="onGenKeysPlatform">重新生成</el-button>
          </el-form-item>
          <el-form-item prop="publicKeyPlatform" label="平台公钥">
            <el-input v-model="isvKeysFormData.publicKeyPlatform" type="textarea" />
          </el-form-item>
          <el-form-item prop="privateKeyPlatform" label="平台私钥">
            <el-input v-model="isvKeysFormData.privateKeyPlatform" type="textarea" />
          </el-form-item>
        </el-tab-pane>
      </el-tabs>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
        <el-button @click="onBack">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style>
  .gen-key {margin-bottom: 0px !important;}
  fieldset {border: 1px solid #ccc; color: gray;margin-left: 40px;margin-bottom: 20px;}
  fieldset label {width: 110px !important;}
  fieldset .el-form-item__content {margin-left: 110px !important;}
  .keyTabs .el-tabs__header{margin-left: 70px;}
</style>
<script>
export default {
  data() {
    const validateSecret = (rule, value, callback) => {
      if (this.isvKeysFormData.signType === 2) {
        if (value === '') {
          callback(new Error('不能为空'))
        }
        if (value.length > 200) {
          callback(new Error('长度不能超过200'))
        }
      }
      callback()
    }
    const validatePubPriKey = (rule, value, callback) => {
      if (this.isvKeysFormData.signType === 1) {
        if (value === '') {
          callback(new Error('不能为空'))
        }
      }
      callback()
    }
    return {
      isvKeysFormData: {
        appKey: '',
        secret: '',
        keyFormat: 1,
        publicKeyIsv: '',
        privateKeyIsv: '',
        publicKeyPlatform: '',
        privateKeyPlatform: '',
        signType: 1
      },
      rulesIsvKeysForm: {
        secret: [
          { validator: validateSecret, trigger: 'blur' }
        ],
        publicKeyIsv: [
          { validator: validatePubPriKey, trigger: 'blur' }
        ],
        privateKeyIsv: [
          { validator: validatePubPriKey, trigger: 'blur' }
        ]
      },
      activeName: 'first'
    }
  },
  created() {
    const query = this.$route.query
    this.isvKeysFormData.appKey = query.appKey
    this.loadForm()
  },
  methods: {
    loadForm: function() {
      this.post('isv.keys.get', { appKey: this.isvKeysFormData.appKey }, function(resp) {
        Object.assign(this.isvKeysFormData, resp.data)
      })
    },
    selfLabel: function(lab) {
      return '★ ' + lab
    },
    onSubmit: function() {
      this.$refs.isvKeysForm.validate((valid) => {
        if (valid) {
          this.post('isv.keys.update', this.isvKeysFormData, function() {
            this.tip('保存成功')
          })
        }
      })
    },
    onBack: function() {
      this.$router.push({ path: 'list' })
    },
    onGenKeysPlatform: function() {
      this.post('isv.keys.gen', {}, function(resp) {
        this.tip('生成公私钥成功')
        const data = resp.data
        this.isvKeysFormData.publicKeyPlatform = data.publicKey
        this.isvKeysFormData.privateKeyPlatform = data.privateKey
      })
    },
    onGenKeysIsv: function() {
      this.post('isv.keys.gen', { keyFormat: this.isvKeysFormData.keyFormat }, function(resp) {
        this.tip('生成公私钥成功')
        const data = resp.data
        this.isvKeysFormData.publicKeyIsv = data.publicKey
        this.isvKeysFormData.privateKeyIsv = data.privateKey
      })
    },
    onGenSecret: function() {
      this.post('isv.secret.gen', {}, function(resp) {
        this.isvKeysFormData.secret = resp.data
      })
    },
    showKeys: function() {
      return this.isvKeysFormData.signType === 1
    }
  }
}
</script>
