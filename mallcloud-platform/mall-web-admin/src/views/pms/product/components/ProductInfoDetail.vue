<template>
  <div style="margin-top: 50px">
    <el-form :model="value" :rules="rules" ref="productInfoForm" label-width="120px" style="width: 600px" size="small">
      <el-form-item label="商品分类：" prop="productCategoryId">
        <el-cascader
          change-on-select
          v-model="selectProductCateValue"
          :options="productCateOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品名称：" prop="name">
        <el-input v-model="value.name"></el-input>
      </el-form-item>
      <el-form-item label="副标题：" prop="subTitle">
        <el-input v-model="value.subTitle"></el-input>
      </el-form-item>
      <el-form-item label="商品品牌：" prop="brandId">
        <el-select
          v-model="value.brandId"
          @change="handleBrandChange"
          placeholder="请选择品牌">
          <el-option
            v-for="item in brandOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="区域：" prop="areaId">
        <el-cascader
                change-on-select
                v-model="selectAreaValue"
                :options="areaOptions">
        </el-cascader>
      </el-form-item>
      <el-form-item label="商品介绍：">
        <el-input
          :autoSize="true"
          v-model="value.description"
          type="textarea"
          placeholder="请输入内容"></el-input>
      </el-form-item>
      <el-form-item label="商品货号：">
        <el-input v-model="value.productSn"></el-input>
      </el-form-item>
      <el-form-item label="商品售价：">
        <el-input v-model="value.price"></el-input>
      </el-form-item>
      <el-form-item label="市场价：">
        <el-input v-model="value.originalPrice"></el-input>
      </el-form-item>
      <el-form-item label="商品库存：">
        <el-input v-model="value.stock"></el-input>
      </el-form-item>
      <el-form-item label="计量单位：">
        <el-select
                v-model="value.unit"

                placeholder="请选择计量单位">
          <el-option
                  v-for="item in unitOptions"
                  :key="item.id"
                  :label="item.id"
                  :value="item.id">
          </el-option>
        </el-select>

      </el-form-item>
      <el-form-item label="商品重量：">
        <el-input v-model="value.weight" style="width: 300px"></el-input>
        <span style="margin-left: 20px">千克</span>
      </el-form-item>
      <el-form-item label="固定运费：">
        <el-input v-model="value.transfee" style="width: 300px"></el-input>
        <span style="margin-left: 20px">优先于运费模版</span>
      </el-form-item>
      <el-form-item label="运费模版:：">
         <el-select
              v-model="value.feightTemplateId"
              placeholder="请选择运费模版">
        <el-option
                v-for="item in feightTemplateOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
        </el-option>
      </el-select>

      </el-form-item>

      <el-form-item label="排序">
        <el-input v-model="value.sort"></el-input>
      </el-form-item>
      <el-form-item style="text-align: center">
        <el-button type="primary" size="medium" @click="handleNext('productInfoForm')">下一步，填写商品促销</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import {fetchListWithChildren} from '@/api/productCate'
  import {fetchAreaListWithChildren,getArea} from '@/api/area'

  import {fetchList as fetchBrandList} from '@/api/brand'
  import {fetchList as fetchFeightTemplateList} from '@/api/pms/feightTemplate';
  import {getProduct} from '@/api/product';

  export default {
    name: "ProductInfoDetail",
    props: {
      value: Object,
      isEdit: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        hasEditCreated:false,
        //选中商品分类的值
        selectProductCateValue: [],
        productCateOptions: [],
        //选中商品分类的值
        selectAreaValue: [],
        areaOptions: [],
        brandOptions: [],
        feightTemplateOptions:[],
        unitOptions:[{
          id: "个",
          name: "个",
        },
          {
            id: "箱",
            name: "箱",
          },
          {
            id: "条",
            name: "条",
          },

          {
            id: "盒",
            name: "盒",
          },{
            id: "包",
            name: "包",
          },{
            id: "斤",
            name: "斤",
          },{
            id: "两",
            name: "两",
          },{
            id: "件",
            name: "件",
          },{
            id: "瓶",
            name: "瓶",
          },{
            id: "千克",
            name: "千克",
          }],
        rules: {
          name: [
            {required: true, message: '请输入商品名称', trigger: 'blur'},
            {min: 2, max: 140, message: '长度在 2 到 140 个字符', trigger: 'blur'}
          ],
          subTitle: [{required: true, message: '请输入商品副标题', trigger: 'blur'}],
          productCategoryId: [{required: true, message: '请选择商品分类', trigger: 'blur'}],
          areaId: [{required: true, message: '请选择商品区域', trigger: 'blur'}],
          brandId: [{required: true, message: '请选择商品品牌', trigger: 'blur'}],
          description: [{required: true, message: '请输入商品介绍', trigger: 'blur'}],
          requiredProp: [{required: true, message: '该项为必填项', trigger: 'blur'}]
        }
      };
    },
    created() {
      this.getProductCateList();
      this.getAreaList();
      this.getBrandList();
      this.getFeightTemplateList();
    },
    computed:{
      //商品的编号
      productId(){
        return this.value.id;
      }
    },
    watch: {
      productId:function(newValue){
        if(!this.isEdit)return;
        if(this.hasEditCreated)return;
        console.log(newValue)
        if(newValue===undefined||newValue==null||newValue===0)return;
        this.handleEditCreated();
      },
      selectProductCateValue: function (newValue) {
        console.log(newValue)
        if (newValue != null) {
          this.value.productCategoryId = newValue[newValue.length-1 >0?newValue.length-1:0];
          this.value.productCategoryName= this.getCateNameById(this.value.productCategoryId);
        } else {
          this.value.productCategoryId = null;
          this.value.productCategoryName=null;
        }
      },
      selectAreaValue: function (newValue) {
        console.log(newValue)
        if (newValue != null) {
          this.value.areaId = newValue[newValue.length-1 >0?newValue.length-1:0];
          this.value.areaName= this.getAreaById(this.value.areaId);
        } else {
          this.value.areaId = null;
          this.value.areaName=null;
        }
      }
    /*  selectAreaValue: [],
      areaOptions: [],*/
    },
    methods: {
      //处理编辑逻辑
      handleEditCreated(){
        if(this.value.productCategoryId!=null){

          this.selectProductCateValue.push(this.value.cateParentId);
          this.selectProductCateValue.push(this.value.productCategoryId);
        }
        if(this.value.areaId!=null){
          console.log(this.value.areaId)
          this.selectAreaValue.push(this.value.areaId);
          getArea(this.value.areaId).then(response => {
            this.selectAreaValue.push(response.data.pid);
          });
        }
        this.hasEditCreated=true;
      },

      getAreaList() {
    fetchAreaListWithChildren().then(response => {
      let list = response.data;
      this.areaOptions = [];
      for (let i = 0; i < list.length; i++) {
        let children = [];
        if (list[i].children != null && list[i].children.length > 0) {
          for (let j = 0; j < list[i].children.length; j++) {
            children.push({label: list[i].children[j].name, value: list[i].children[j].id, pid: list[i].children[j].pid});
          }
        }
        this.areaOptions.push({label: list[i].name, value: list[i].id, pid: list[i].pid, children: children});
      }
    });
  },
      getProductCateList() {
        fetchListWithChildren().then(response => {
          let list = response.data;
          this.productCateOptions = [];
          for (let i = 0; i < list.length; i++) {
            let children = [];
            if (list[i].children != null && list[i].children.length > 0) {
              for (let j = 0; j < list[i].children.length; j++) {
                children.push({label: list[i].children[j].name, value: list[i].children[j].id});
              }
            }
            this.productCateOptions.push({label: list[i].name, value: list[i].id, children: children});
          }
        });
      },
      getFeightTemplateList() {
        fetchFeightTemplateList({pageNum: 1, pageSize: 100}).then(response => {
                  this.feightTemplateOptions = [];
                  let feightTemplateList = response.data.records;
                  for (let i = 0; i < feightTemplateList.length; i++) {
                    this.feightTemplateOptions.push({label: feightTemplateList[i].name, value: feightTemplateList[i].id});
                  }
                });
  },
      getBrandList() {
        fetchBrandList({pageNum: 1, pageSize: 100}).then(response => {
          this.brandOptions = [];
          let brandList = response.data.records;
          for (let i = 0; i < brandList.length; i++) {
            this.brandOptions.push({label: brandList[i].name, value: brandList[i].id});
          }
        });
      },
       getAreaById(id){
        let name=null;
        console.log(JSON.stringify(this.areaOptions))
        for(let i=0;i<this.areaOptions.length;i++){
          for(let j=0;i<this.areaOptions[i].children.length;j++){
            if( this.areaOptions[i].children[j].value!=undefined && this.areaOptions[i].children[j].value===id){
              name=this.areaOptions[i].children[j].label;
              return name;
            }
          }
        }
        return name;
      },
      getCateNameById(id){
        let name=null;
       // console.log(JSON.stringify(this.productCateOptions))
        for(let i=0;i<this.productCateOptions.length;i++){
          for(let j=0;i<this.productCateOptions[i].children.length;j++){
            if( this.productCateOptions[i].children[j].value!=undefined && this.productCateOptions[i].children[j].value===id){
              name=this.productCateOptions[i].children[j].label;
              return name;
            }
          }
        }
        return name;
      },
      handleNext(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$emit('nextStep');
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
      handleBrandChange(val) {
        let brandName = '';
        for (let i = 0; i < this.brandOptions.length; i++) {
          if (this.brandOptions[i].value === val) {
            brandName = this.brandOptions[i].label;
            break;
          }
        }
        this.value.brandName = brandName;
      }
    }
  }
</script>

<style scoped>
</style>
