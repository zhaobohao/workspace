<!--
    mode:base || diy (固定样式(默认) or 自定义)
   -->
<style lang="less" scoped>
@import './addGift.less';
</style>

<template>
  <div class="tableGoodsList">
    <div class="addGoods" v-if="mode == 'base'" style="margin-bottom:20px">
      <el-button @click="addGoods()" type="primary">{{res}}</el-button>
    </div>
    <div class="table-popup-container" v-if="tableshow">
      <div class="table-popup-content" :style="{'height':(clientHeight*0.9)+'px','width':(clientWidth*0.66)+'px'}">

        <div class="title-ag">
          <div>赠品选择</div>
        </div>

        <div class="tablePage addGift">
          <div class="search-con search-con-top">
            <Row style="height:50px;">
              <i-col span="8" style="height:100%;margin:0px 15px 0px 0px;">
                <el-input
                  class="search-input col-8"
                  v-model="titlekey"
                  style="height:36px;margin-left: 10px;"
                />
              </i-col>
              <i-col span="3" style="height:100%;">
                <el-button
                  @click="getdata(1)"
                  class="search-btn"
                  type="primary"
                  style="height:36px;"
                >搜索</el-button>
              </i-col>
            </Row>
          </div>
          <Table
            :height="tableHeight"
            ref="tablesMain"
            :data="showData"
            :columns="columns"
            :border="true"
            :show-header="true"
          ></Table>
        </div>

        <div class="tablePage-bottomall">
          <div class="btnAction">
            <el-button type="primary" @click="tableEnsure()" style="margin-right:10px;">确定</el-button>
            <el-button type="primary" @click="tableCancel()">取消</el-button>
          </div>
          <div class="btnPage">
            <el-pagination
                    background

                    @current-change="changePage"
                    layout="total, sizes,prev, pager, next,jumper"
                    :page-size="size"
                    :page-sizes="[5,10,15]"
                    :current-page.sync="current"
                    :total="total">
            </el-pagination>
            <!--<Page
              style="float:right"
              :total="dataCount"
              :page-size="pageSize"
              show-total
              @on-change="changepage"
            ></Page>-->
          </div>
        </div>

      </div>
    </div>
    <div class="goodslist">
      <Table
        ref="tablesMain"
        :data="containerData"
        :columns="showcolumns"
        :border="true"
        :show-header="true"
      ></Table>
    </div>
  </div>
</template>

<script>

import goods from "@/api/pms/Gifts";

import Treeselect from "@riophae/vue-treeselect";

import "@riophae/vue-treeselect/dist/vue-treeselect.css";

import goodsUtils from "@/utils/goodsUtils";

export default {
  name: "addGift",
  props: ["options", "res", "settings", "selectGoods"],
  data() {
    return {
      mode: "base",
      clientHeight:'',
      tableHeight:"",
      clientWidth:'',
      columns: [
        {
          title: "商品名称",
          render: (h, params) => {
            return h('div',[
              h("img", {
                attrs: {
                    src: params.row.thumb,
                },
                style: {
                  height: "40px",
                  marginRight: "5px",
                  verticalAlign: "middle",
                  cursor: "pointer"
                }
              },
              params.row.title
              ),
              h("span", {

                style: {
                  cursor: "pointer"
                }
              },
              params.row.title
              ),
            ]
            )
          }
        },
        { title: "价格", key: "price"},
        // { title: "库存", key: "vituralStock" },
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
       showcolumns: [
        {
          title: "商品",
          key:'goodsName',
          width: "200px",
          align: "center",
          render: (h, params) => {
            return h(
              "div",
              {
                attrs: {
                  style: "height: 40px;justify-content: center;display:flex;align-items: center;"
                }
              },
              [
                h("img", {
                  props: {
                    type: "primary",
                    size: "small"
                  },
                  attrs: {
                    src: params.row.thumb,
                    style:
                      "height: 40px;weight:35px;vertical-align: middle;margin-right: 5px;"
                  }
                }),
                h("span", {}, params.row.title)
              ]
            );
          }
        },
        { title: "价格", key: "price", align: "center" },
        // { title: "库存", key: "vituralStock", align: "center" },
        {
          title: "操作",
          key: "action",
          width: 100,
          align: "center",
          render: (h, params) => {
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
                      this.cancelSelect(params);
                    }
                  }
                },
                "删除"
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
      pageSize: 10,

      // 控制表格显示
      tableshow: false,
      // 弹出框中的数据

      titlekey:"",
      // 商品ID
      goodidlist: [],
      goodidlistts: [],

      //列表显示的数据
      containerData: [],
      // 商品组暂存
      containerDatats: []
    };
  },
  created() {
    this.init(this.settings);
    this.resetData()
  },
  components: {
    Treeselect
  },
  methods: {
    resetData() {
      console.log('我是赠品的组件,我运行了')
      // 要显示的数据
      this.showData = []
      // 初始化信息总条数
      this.dataCount = 0
      // 每页显示多少条
      this.pageSize = 10

      // 控制表格显示
      this.tableshow = false
      // 弹出框中的数据

      this.titlekey = ""
      // 商品ID
      this.goodidlist = []
      this.goodidlistts = []

      //列表显示的数据
      this.containerData = []
      // 商品组暂存
      this.containerDatats = []
    },
    init(options) {
      let _this = this;
      if (options) {
        _this.mode = options.mode || "base";
      } else {
        _this.mode = "base";
      }
    },
    // 获取数据
    getdata: function(page) {
      let _this = this;
      console.log('goodidlist',this.goodidlist)
      console.log('goodidlistts',this.goodidlistts)
      goods
        .fetchList({
          pageSize: _this.pageSize,
          pageNum: page || 1,
          name: _this.titlekey,
        })
        .then(res => {
          let data = res.data;
          _this.dataCount = data.total;
          let goods = [];
          data.rows.map((v, i) => {
            if (
              this.goodidlist.indexOf(v.id) === -1 &&
              this.goodidlistts.indexOf(v.id) === -1
            ) {
              goods.push({
                ...v,
                isSelected: false,
              });
            } else {
              goods.push({
                ...v,
                isSelected: true,
              });
            }
          });
          _this.showData = goods || [];
        })
    },

    // 选择数据
    handleSelect: function(params) {
      this.containerDatats.push(params.row);
      this.goodidlistts.push(params.row.id);
      this.showData.map((v, i) => {
        if (v.id == params.row.id) {
          this.showData[i].isSelected = true;
        }
      });
    },

    // 按钮取消选择
    cancelSelect: function(params) {

      // 改变按钮状态
      let newData = this.containerData || []

      newData.map((v, i) => {
        if (v.id == params.row.id) {
          newData.splice(i, 1);
        }
      });

      this.containerDatats.map((v,i) => {
        if (v.id == params.row.id) {
          this.containerDatats.splice(i, 1);
        }
      })

      console.log(this.containerDatats)

      this.containerData = newData

      this.showData.map((v, i) => {
        if (v.id == params.row.id) {
          this.showData[i].isSelected = false;
        }
      });

      this.submitContainerData()
    },

    // 确定按钮
    tableEnsure: function() {
      this.tableshow = false;
      this.containerData = this.containerData ? this.containerData.concat(this.containerDatats) : [].concat(this.containerDatats);
      this.goodidlist = this.goodidlist.concat(this.goodidlistts);
      let newContainerDate = []
      this.containerData.map((v,i) => {
        newContainerDate.push({
          ...v,
          goodsName:v.title,
          pic:v.thumb
        })
      })
      this.containerData = newContainerDate

      this.goodidlistts = []

      this.submitContainerData()
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

      this.containerData = datas;

      let newgoodidlistts = [];

      datas ? datas.map((v, i) => {
        newgoodidlistts.push(v);
      }) : [];

      this.goodidlistts = newgoodidlistts;

      this.$forceUpdate();
    },

    // 传递父组件
    submitContainerData(){
      this.$emit("listentgoodData", this.containerData);
    }

  },
  watch: {
    options: function() {
      this.containerData = this.options || [];
    },
    settings() {
      this.init(this.settings);
    },
    selectGoods:{
      handler(value) {
        console.log(this.selectGoods)
        let newSelectGoods = this.selectGoods
        this.calcHasData(newSelectGoods);
      },
      deep:true
    },
    $route(to, from) {
      console.log('我是路由123',from, to)
      this.resetData()
    }
  },
  mounted() {
    // 获取浏览器可视区域高度
    this.clientHeight = `${document.documentElement.clientHeight}`;
    this.clientWidth = `${document.documentElement.clientWidth}`;
    let tableHeight = `${document.documentElement.clientHeight}`;
    this.tableHeight = tableHeight*0.65
    this.calcHasData(this.selectGoods)
  },
};
</script>
