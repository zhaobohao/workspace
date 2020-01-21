<!--
    mode:base || diy (固定样式(默认) or 自定义)
   -->
<template>
  <div class="tablepage">
    <div class="addGoods" v-if="mode == 'base'">
      <el-button @click="addGoods()" type="primary">{{res}}</el-button>
    </div>
    <div class="table-popup-container" v-if="tableshow">
      <div class="table-popup-content">
        <div class="table-popup-tit">
          <div>请选择商品</div>
        </div>
        <div class="tablePage">
          <div class="search-con search-con-top">
            <el-row style="height:50px;">
              <el-col :span="8" style="height:100%;">
                <div style="display: inline-block;">
                  <treeselect
                    :options="classOption"
                    :clearable="false"
                    v-model="categoryId"
                    :searchable="false"
                    placeholder="选择商品分类"
                  />
                </div>
              </el-col>
              <el-col :span="8" style="height:100%;margin:0px 15px 0px 0px;">
                <el-input
                  placeholder="输入关键字搜索"
                  class="search-input col-8"
                  v-model="idAndName"
                  style="height:36px;margin-left: 10px;"
                />
              </el-col>
              <el-col :span="3" style="height:100%;">
                <el-button
                  @click="getdata(1)"
                  class="search-btn"
                  type="primary"
                  style="height:36px;"
                >搜索</el-button>
              </el-col>
            </el-row>
          </div>
         <!-- <Table
            ref="tablesMain"
            :data="showData"
            :columns="columns"
            :border="true"
            :show-header="true"
          ></Table>-->
          <div class="table-container">
            <el-table ref="productTable"
                      :data="showData"
                      style="width: 100%"
                      @select='onTableSelect'
                       @selection-change="handleSelectionChange"
                      border>
              <el-table-column type="selection" width="30" align="center"></el-table-column>

              <el-table-column label="编号" width="60" align="center">
                <template slot-scope="scope">{{scope.row.id}}</template>
              </el-table-column>
              <el-table-column label="商品图片" width="100" align="center">
                <template slot-scope="scope"><img style="height: 40px" :src="scope.row.pic"></template>
              </el-table-column>
              <el-table-column label="商品名称" align="center">
                <template slot-scope="scope">
                  <p>{{scope.row.name}}</p>
                </template>
              </el-table-column>
              <el-table-column label="价格/货号" width="120" align="center">
                <template slot-scope="scope">
                  <p>价格：￥{{scope.row.price}}</p>
                </template>
              </el-table-column>



              <el-table-column label="销量" width="100" align="center">
                <template slot-scope="scope">{{scope.row.sale}}</template>
              </el-table-column>


            </el-table>
          </div>
        </div>
        <div class="tablePage-bottom">
          <div class="btnPage">
            <el-pagination
                    background

                    layout="total, sizes,prev, pager, next,jumper"
                    :page-size="size"
                    :page-sizes="[5,10,15]"
                    :current-page.sync="current"
                    :total="total">
            </el-pagination>
           <!-- <Page
              style="float:right"
              :total="dataCount"
              :page-size="pageSize"
              show-total
              @on-change="changepage"
            ></Page>-->
          </div>
          <div class="btnAction">
            <el-button type="primary" @click="tableEnsure()" style="margin-right:10px;">确定</el-button>
            <el-button type="primary" @click="tableCancel()">取消</el-button>
          </div>
        </div>
      </div>
    </div>
    <div class="goodslist" v-if="mode == 'base'">
      <div class="goodslist-item" v-for="(item,index) in containerData" :key="index">
        <div class="goodslist-delete" @click="delectSelect(item,index)">
          <i type="ios-close-circle"></i>
         <!-- <Icon type="ios-close-circle"/>-->
        </div>
        <div class="goodslist-item-l">
          <img v-if="item.pic" :src="item.pic">
        </div>
        <div class="goodslist-item-r">
          <div>
            <span>商品名称:</span>
            <span>{{item.title}}</span>
          </div>
          <div>
            <span>商品价格:</span>
            <span>{{item.price}}</span>
          </div>
          <div>
            <span>虚拟库存</span>
            <span>{{item.vituralStock}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {goodsClassAll,goodsOneClassEnable,goodsList,getGoodsUpper,shopCustomData} from "@/api/request/goods";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import goodsUtils from "@/utils/goodsUtils";
export default {
  name: "addGood",
  props: ["options", "res", "settings", "selectGoods"],
  components: { Treeselect },
  data() {
    return {
      mode: "base",
      selectStatus:'',
      columns: [
        {
          title: "图片",
          width: "120px",
          align: "center",
          render: (h, params) => {
            return h(
              "div",
              {
                attrs: {
                  style: "height: 40px;width:35px;"
                }
              },params.row.thumb ? [
                h("img", {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  attrs: {
                    src: params.row.thumb,
                    style: "height: 40px;width:35px;"
                  }
                })
              ] : ''
            );
          }
        },
        {
          title: "商品名称",
          render: (h, params) => {
            return h('p',{
              class: {
                textOverflow: true
              },
              style: {
                margin: 0,
                padding: 0,
                // overflow: 'hidden',
                // textOverflow: 'ellipsis',
                // lineClamp: '2'
              }
            }, params.row.title)
          }
        },
        { title: "商品价格", key: "price", editable: false },
        { title: "库存", key: "vituralStock" },
        // { title: "实际库存", key: "stock" },
        {
          title: "操作",
          key: "action",
          width: 100,
          align: "center",
          render: (h, params) => {
            let status = params.row.isSelected;

            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: !status ? "primary" : "error",
                    size: "small"
                  },
                  on: {
                    click: () => {
                      if (!status) {
                        console.log(status);
                        this.handleSelect(params);
                      } else {
                        this.cancelSelect(params);
                      }
                    }
                  }
                },
                !status ? "选择" : "已选择"
              )
            ]);
          }
        }
      ],

      // 要显示的数据
      showData: [],
      // 初始化信息总条数
      dataCount: 0,
      // 每页显示多少条
      pageSize: 5,
      current: 1,
      total: 0,
      size: 10,
      // 控制表格显示
      tableshow: false,
      // 弹出框中的数据
      idAndName: null,
      categoryId: "",
      classOption: [],
      // 商品ID
      goodidlist: [],
      goodidlistts: [],

      //列表显示的数据
      containerData: [],
      // 商品组暂存
      containerDatats: []
    };
  },
  mounted() {
    console.log('我是生命周期')
    console.log('显示的商品',this.containerData);

    this.oneClassFun();
    this.init(this.settings);
  },
  components: {
    Treeselect
  },
  filters: {
    handleUpdateProduct(index,row){
      let status = row.isSelected;
      console.log(status);
      if (!status) {
      //  this.handleSelect(row);
      } else {
      //  this.cancelSelect(row);
      }

      this.selectStatus =status ? "选择" : "已选择"
      return this.selectStatus;
      console.log(this.selectStatus);
    },
  },
  methods: {
    handleSelectionChange(val) {
      console.log(val)
      if (val.isSelected){
        this.multipleSelection = val;
      }

    },
    onTableSelect(rows, row) {
      let selected = rows.length && rows.indexOf(row) !== -1
      console.log(selected)  // true就是选中，0或者false是取消选中
      let status = row.isSelected;
      console.log(status);
      if (!status) {
          this.handleSelect(row);
      } else {
          this.cancelSelect(row);
      }

    },
    init(options) {
      console.log('我是初始化')
      let _this = this;
      if (options) {
        _this.mode = options.mode || "base";
      } else {
        _this.mode = "base";
      }
    },
    oneClassFun() {
      goodsClassAll({})
        .then(res => {
          if (res.code == "1") {
            this.classOption = goodsUtils.classAllFun(res.data);
            this.categoryId = this.classOption[0].id;
          }
        })
        .catch(err => {
          console.log("分类查询失败");
        });
    },
    // 获取数据
    getdata: function(page) {
      let _this = this;

        getGoodsUpper({
          pageSize: _this.pageSize,
          pageNum: page || 1,
          categoryId: this.categoryId,
          name: this.idAndName
        })
        .then(res => {
          let data = res.data;
          _this.dataCount = data.total;
          let goods = [];
          data.records.map((v, i) => {
            if (
              this.goodidlist.indexOf(v.id) === -1 &&
              this.goodidlistts.indexOf(v.id) === -1
            ) {
              this.selectStatus = "选择"
              goods.push({
                ...v,
                isSelected: false
              });
            } else {
              this.selectStatus = "已选择"
              goods.push({
                ...v,
                isSelected: true
              });
            }
          });

          _this.showData = goods || [];

        })
        .catch(err => {
          console.log(err);
        });
    },
    // 选择数据
    handleSelect: function(params) {
      this.containerDatats.push(params);
      this.goodidlistts.push(params.id);
      this.showData.map((v, i) => {
        if (v.id == params.id) {
          this.showData[i].isSelected = true;
        }
      });
      console.log(this.showData);
    },

    // 按钮取消选择
    cancelSelect: function(params) {
      console.log("cancelSelect");
      console.log(params);
      this.containerDatats.pop();
      this.goodidlistts.pop();
      console.log(this.containerData);
      // 改变按钮状态
      this.containerData.map((v, i) => {
        if (v.id == params.id) {
          this.containerData.splice(i, 1);
        }
      });

      this.showData.map((v, i) => {
        if (v.id == params.id) {
          this.showData[i].isSelected = false;
        }
      });
    },

    // 删除选择的数据
    delectSelect: function(data, index) {
      let _this = this;
      this.goodidlist.splice(index, 1);
      this.goodidlistts.splice(index, 1);
      this.containerData.splice(index, 1);
      this.containerDatats.splice(index, 1);
      this.showData.map((v, i) => {
        if (v.id == data.id) {
          _this.showData[i].isSelected = false;
        }
      });
    },
    // 清空数据的方法
    clearData() {
      let _this = this;
      this.goodidlist = []
      this.goodidlistts = []
      this.containerData = []
      this.containerDatats = []
      this.showData.map((v, i) => {
        // if (v.id == data.id) {
        _this.showData[i].isSelected = false;
        // }
      });
    },

    // 确定按钮
    tableEnsure: function() {
      this.tableshow = false;
      this.containerData = this.containerData.concat(this.containerDatats);
      console.log("长度", this.containerDatats);
      this.goodidlist = this.goodidlist.concat(this.goodidlistts);
    },

    // 取消按钮
    tableCancel: function() {
      this.containerDatats = [];
      this.goodidlistts = [];
      this.tableshow = false;
    },

    // 添加推荐商品按钮
    addGoods: function() {
      this.containerDatats = [];
      this.tableshow = true;
      this.optionsfuc();
      this.getdata();
    },

    // 分页逻辑
    changepage: function(page) {
      this.getdata(page);
    },

    // 获取id数组
    optionsfuc: function() {
      let arr = [];
      for (let item in this.containerData) {
        arr.push(this.containerData[item].id);
      }
      this.goodidlist = arr;
    },

    // 处理根据外部传来的已选数据
    calcHasData(datas) {
      console.log("外部的数据", datas);
      this.containerData = datas;
      let newgoodidlistts = [];
      datas.map((v, i) => {
        newgoodidlistts.push(v.id);
      });
      this.goodidlistts = newgoodidlistts;
      this.$forceUpdate();
    }
  },
  watch: {
    containerData: function() {
      this.$emit("listentgoodData", this.containerData);
    },
    options: function() {
      console.log("options", this.settings);
      this.containerData = this.options || [];
    },
    settings() {
      console.log("settings", this.settings);
      this.init(this.settings);
    },
    selectGoods: {
      handler(value) {
        console.log("selectGoods", this.selectGoods);
        // 处理根据外部传来的已选数据
        this.calcHasData(this.selectGoods);
      },
      deep: true
    }
  }
};
</script>
<style scoped>
.btnPage {
  width: 100%;
}
.btnAction {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.tablepage .goodslist .goodslist-item {
  width: 760px;
  height: 120px;
  padding: 10px;

  border: 1px solid #ddd;
  position: relative;
  margin-bottom: 20px;
}
.tablepage .goodslist .goodslist-item .goodslist-delete {
  position: absolute;
  right: -5px;
  top: -12px;
  width: 20px;
  height: 20px;
  line-height: 0;
  font-size: 25px;
  background-color: #fff;
  cursor: pointer;
}
.tablepage .goodslist .goodslist-item .goodslist-item-l {
  height: 100px;
  width: 100px;
  float: left;
}
.tablepage .goodslist .goodslist-item .goodslist-item-l img {
  height: 100px;
  width: 100px;
}
.tablepage .goodslist .goodslist-item .goodslist-item-r {
  float: left;
  margin-left: 10px;
}
.tablepage .goodslist .goodslist-item .goodslist-item-r div {
  margin-bottom: 12px;
  height: 20px;
  line-height: 20px;
}
.table-popup-container {
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
}
.table-popup-container .table-popup-content {
  width: 880px;
  height: 760px;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
}
.table-popup-container .table-popup-content .tablePage-bottom {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
}
.table-popup-tit {
  height: 30px;
  overflow: hidden;
}
.tables {
  margin: 15px 0;
}
.table-popup-tit div:nth-child(1) {
  float: left;
  height: 30px;
  line-height: 30px;
  width: 100px;
}
.table-popup-tit div:nth-child(2) {
  float: right;
  width: 30px;
  height: 30px;
  font-size: 20px;
}
.addGoods {
  margin-top: 15px;
  margin-bottom: 15px;
  width: 200px;
}
.tablePage .search-btn {
  width: 100%;
  text-align: center;
}
.ivu-input {
  height: 36px;
}
.ivu-table-cell{
  line-height:1.5;
}
</style>
