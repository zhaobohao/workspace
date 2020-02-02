<template> 
  <el-card class="form-container" shadow="never">
    <el-form :model="wuyePrice" :rules="rules" ref="wuyePriceFrom" label-width="150px">

      <el-form-item label="小区名称：" prop="floorNum">
        <el-form-item :label="cname" readonly="readonly"></el-form-item>
      </el-form-item>

      <el-form-item v-if="wuyePrice.roomId !== 0" style="margin-bottom: 0px;" label="房屋">
        <treeselect v-model="wuyePrice.roomId" :options="rooms"
                    :disable-branch-nodes="true" style="width: 370px;" placeholder="房屋" />
      </el-form-item>

      <el-form-item label="收费类别：" prop="priceId">
        <el-select
                v-model="wuyePrice.priceId"
                @change="handlecateChange"
                placeholder="请选择收费类别">
          <el-option
                  v-for="item in cateOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="单价：" prop="price">
          <el-form-item :label="wuyePrice.price" readonly="readonly"></el-form-item>
      </el-form-item>
      <el-form-item label="用量：" prop="amount">
        <el-input v-model="wuyePrice.amount"></el-input>
      </el-form-item>
      <el-form-item label="总额：" prop="moneys">
          <el-form-item :label="wuyePrice.price*wuyePrice.amount" readonly="readonly"></el-form-item>

      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="onSubmit('wuyePriceFrom')">提交</el-button>
        <el-button v-if="!isEdit" @click="resetForm('wuyePriceFrom')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
  import {createWuyePrice, getWuyePrice, updateWuyePrice} from '@/api/build/wuyePrice'
  import {fetchList} from '@/api/build/typePrice'
  import {withChilds} from '@/api/build/room'
  import SingleUpload from '@/components/Upload/singleUpload'
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import {formatDate} from '@/utils/date';
  const defaultWuyePrice={
    name: ''
  };
  export default {
    name: 'WuyePriceDetail',
    components:{SingleUpload,Treeselect},
    props: {
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        wuyePrice:Object.assign({}, defaultWuyePrice),
        cname:'',
        cateOptions:null,
          price:0,
        //选中商品分类的值
        rooms: [],
        types: [],
        priceName:null,
        listQuery: {
          keyword: null,
          pageNum: 1,
          pageSize: 1000
        },
        rules: {
          name: [
            {required: true, message: '请输入品牌名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          logo: [
            {required: true, message: '请输入品牌logo', trigger: 'blur'}
          ],
          sort: [
            {type: 'number', message: '排序必须为数字'}
          ],
        }
      }
    },

    created() {
      this.cname=this.$route.query.cname;
      if (this.isEdit) {
        getWuyePrice(this.$route.query.id).then(response => {
          this.wuyePrice = response.data;
        });
      }else{
        this.wuyePrice = Object.assign({},defaultWuyePrice);
      }
      this.getProductCateList();
      this.getTypeList();
    },
    methods: {

      getProductCateList() {
        this.listQuery.communityId=this.$route.query.cid
        withChilds(this.listQuery).then(response => {
          let list = response.data;
          this.rooms = list;

        });
      },
      getTypeList() {
        this.listQuery.communityId=this.$route.query.cid
        this.cateOptions = [];
        fetchList(this.listQuery).then(response => {
          let list = response.data.records;

          for (let i = 0; i < list.length; i++) {
            this.cateOptions.push({label: list[i].name+'每'+list[i].remark, value: list[i].id, price: list[i].price});
          }

        });
      },
      handlecateChange(val) {
        let brandName = '';
          let price = '';
        for (let i = 0; i < this.cateOptions.length; i++) {
          if (this.cateOptions[i].value === val) {
            brandName = this.cateOptions[i].label;
              price=this.cateOptions[i].price;
            break;
          }
        }
        this.wuyePrice.price = price;
          this.wuyePrice.priceName=brandName
        this.priceName = brandName;

      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('是否提交数据', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.wuyePrice.priceName=this.priceName
              this.wuyePrice.communityId=this.$route.query.cid
              if (this.isEdit) {
                updateWuyePrice(this.$route.query.id, this.wuyePrice).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.$message({
                          message: '修改成功',
                          type: 'success',
                          duration:1000
                      });
                      this.$router.back();
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });
                  }

                });
              } else {
                createWuyePrice(this.wuyePrice).then(response => {
                    if(response.code==200){
                      this.$refs[formName].resetFields();
                      this.wuyePrice = Object.assign({},defaultWuyePrice);
                      this.$message({
                          message: '提交成功',
                          type: 'success',
                          duration:1000
                      });
                      this.$router.back();
                  }else{
                      this.$message({
                          message: response.msg,
                          type: 'error',
                          duration:1000
                      });
                  }

                });
              }
            });

          } else {
            this.$message({
              message: '验证失败',
              type: 'error',
              duration:1000
            });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.wuyePrice = Object.assign({},defaultWuyePrice);
      }
    }
  }
</script>
<style>
</style>


