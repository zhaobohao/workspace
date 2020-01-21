<style lang="less" scoped>
@import "./urlbutton.less";
</style>

<!--
    mode:base || diy (固定样式(默认) or 自定义)
   -->
<template>
  <div class="urlbutton">
    <div v-if="mode == 'base'">
      <el-input v-model="urldata" placeholder="Enter URL" style="width: 300px" disabled/>
      <el-button @click="cardShow" type="primary">选择链接</el-button>
    </div>
    <div v-if="cardpopup" class="card-popup-container">
      <el-card class="card-content" :bordered="false">
        <p slot="title">选择链接</p>
        <p class="extra" slot="extra" @click="cardClose">
          <i type="ios-close-circle"></i>
        <!--  <Icon type="ios-close-circle"/>-->
        </p>
        <el-tabs class="tabs" value="name1">
          <el-tab-pane label="商城页面" name="name1">
            <div class="panel">
              <div class="panel-title">
                <div>商城页面</div>
              </div>
              <div class="panel-content">
                <el-radio-group type="button" v-model="urldata" size="large" @on-change="btnfunc">
                  <el-radio label="/pages/index/index">商城首页</el-radio>
                  <el-radio label="/pages/goods/index/index">分类导航</el-radio>
                  <el-radio v-if="showService" label="service">在线客服</el-radio>
                  <!-- <el-radio label="/pages/member/cart/index">购物车</el-radio> -->
                </el-radio-group>
              </div>
            </div>
            <div class="panel">
              <div class="panel-title">
                <div>个人中心</div>
              </div>
              <div class="panel-content">
                <el-radio-group type="button" v-model="urldata" size="large" @on-change="btnfunc">
                  <!-- <el-radio label="/pages/member/index/index">个人中心</el-radio> -->
                  <el-radio label="/pages/order/index">全部订单</el-radio>
                  <el-radio label="/pages/order/index?status=0">待付款订单</el-radio>
                  <el-radio label="/pages/order/index?status=1">待发货订单</el-radio>
                  <el-radio label="/pages/order/index?status=2">待收货订单</el-radio>
                  <el-radio label="/pages/order/index?status=3">已完成订单</el-radio>
                  <!-- <el-radio label="/pages/member/favorite">我的收藏</el-radio>
                  <el-radio label="/pages/member/bind/index">个人资料</el-radio>
                  <el-radio label="/pages/member/address/index">收货地址管理</el-radio> -->
                </el-radio-group>
              </div>
            </div>
            <div class="panel">
              <div class="panel-title">
                <div>自定义页面</div>
              </div>
              <div class="panel-content">
                <el-radio-group type="button" v-model="urldata" size="large" @on-change="panelfunc">
                  {{urldata}}
                  <el-radio :label="'/pages/custom/index?pageid=' + item.id" v-for="(item,index) in customData" :key="index">{{item.name}}</el-radio>
                </el-radio-group>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="商品分类" name="name2">
            <div class="goods-url-box">
              <div v-for="(item,index) in classData" :key="index" class="goods-url">
                <div class="goods-p">
                  <p>{{item.name}}</p>
                </div>
                <div class="goods-btn">
                  <el-button @click="selectClass(index)" :type="item.isSelected ? 'primary' : 'default'">{{item.isSelected ? "已选择" : "选择"}}</el-button>
                </div>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="商品" name="name3">
            <div>
              <div class="header">
                <el-input style="width:450px" v-model="idAndName" placeholder="输入商品名称搜索"/>
                <el-button @click="getData(0)" type="primary">搜索</el-button>
              </div>
              <div>
                <Table border :columns="columnsList" :data="tempData"></Table>
              </div>
              <div class="footer">
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
                  :total="dataCount"
                  :page-size="size"
                  show-total
                  @on-change="changepage"
                ></Page>-->
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="小程序" name="name5" v-if="!hide">
            <div class="diy">
              <div>
                <span class="span-left">APPID</span>
                <el-input v-model="SmallProcedures.appid" style="width: 400px" placeholder="请在此输入跳转小程序的APPID"></el-input>
                <p class="red-p">注：该appID需先添加到小程序配置文件中，声明需要跳转的小程序appID列表，并提交审核通过后方可实现跳转。</p>
              </div>
              <div>
                <span class="span-left">跳转路径</span>
                <el-input v-model="SmallProcedures.urldata" style="width: 400px" placeholder="请在此输入跳转小程序的页面路径"></el-input>
                <p class="red-p">注：小程序页面路径为空，默认为跳转到该小程序首页。</p>
              </div>
              <div class="btn">
                 <el-button class="button" @click="SmallPro" type="primary">确认</el-button>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="电话" name="name6" v-if="!hide">
             <div class="diy">
              <span>电话</span>
              <el-input class="input" :maxlength="20" v-model="phoneData" placeholder="请在此处输入所需拨打的电话号码"></el-input>
              <div class="btn">
                <el-button class="button" @click="phoneFun" type="primary">确认</el-button>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="自定义" name="name4" v-if="!hide">
            <div class="diy">
              <span>路径</span>
              <el-input class="input" v-model="diyData" placeholder="请输入地址"></el-input>
              <div class="btn">
                <el-button class="button" @click="diyfunc" type="primary">确认</el-button>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </div>
  </div>
</template>
<script>
import {goodsClassAll,goodsOneClassEnable,goodsList,getGoodsUpper,shopCustomData} from '@/api/request/goods'
export default {
  name: "urlbutton",
  props: {
    urloptions: {
      type: String
    },
    settings: {
      type: null
    },
    hide: {
      type: Boolean
    }
  },
  data() {
    return {
      urldata: "",
      cardpopup: false,
      mode: "base",
      phoneData: '',
      // 自定义的数据
      diyData: '',
      SmallProcedures: {
        appid: '',
        urldata: ''
      },
      showService:false,
      classData: [],
      columnsList: [
        {
          title: "商品名字",
          // align: "center",
          // width: 145,
          render: (h, params) => {
            return h("div", {}, [
              params.row.thumb ? h("img", {
                  style: {
                    display: "inline-block",
                    height: "40px",
                    width: "40px",
                    marginRight: "10px",
                    verticalAlign: "top"
                  },
                  attrs: {
                    src: params.row.thumb
                  }
                })
              : "",
              h('div',{
                style: {
                  display: 'inline-block',
                  width: '150px'
                },
              },[
                h('p',{
                  class: {
                    'textOverflow': true
                  }
                },params.row.title)
              ])
            ]);
          }
        },
        {
          title: "价格",
          align: "center",
          render: (h, params) => {
            return h(
              "p",
              {
                style: {}
              },
              `￥${params.row.price}`
            );
          }
        },
        {
          title: "虚拟库存",
          key: "vituralStock",
          align: "center",
          width: 110
        },
        {
          title: "操作",
          key: "operation",
          align: "center",
          width: 110,
          render: (h, params) => {
            let status = params.row.isSelected
            return h("div", [
              h("Button",{
                  props: {
                    type: status ? "error" : "primary",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.select(params.index);
                    }
                  }
                }, status ? "已选择" : '选择')
            ]);
          }
        }
      ],
      tableData: [],

      current: 1,
      total: 0,
      size: 10,
      dataCount: 0,
      tempData: [],
      idAndName: '',
      // 点击的商品
      tempGoods: {
        id: 0
      },
      tempClass: {
        id: 0
      },
      customData: []
    };
  },
  watch: {
    settings: {
      handler() {
        let _this = this;
        console.log('wishing发生改变了',_this.settings)
        _this.init(_this.settings);
        _this.idAndName = ''
        this.getData()
      },
      deep: true
    }
  },
  created() {
    console.log('按合法合法hi额发hi阿虎费阿和覅合法hi')
    this.init(this.settings);
    this.urloptionsdata();
    this.getData()
  },
  methods: {
    /**
     * 分页
     */
    changePage(e) {
      console.log(e);
      this.current = e;
      this.getImg(this.selectedId);
    },
    init(options) {
      console.log('我是初始化',options)
      let _this = this;
      _this.showService = options.showService
      _this.mode = options.mode;
      _this.urldata = options.link
    },
    getData(page) {
      console.log('我是触发的')
      // 获取自定义的页面
      shopCustomData({
        'type': 5
      }).then(res => {
        if (res.code == 200) {
          this.customData = res.data
          console.log('???????',res.data)
        }
      }).catch(err => {
        console.log('自定义模板查询失败')
      })
      getGoodsUpper({
        size: this.size,
        current: page ? page : 0,
        idAndName: this.idAndName ? this.idAndName : ''
      }).then(res => {
        if (res.code == 200) {
          let data = res.data
          this.dataCount = data.total
          let goods = [];
          data.records.map((v, i) => {
            goods.push({
              ...v,
              link:'/pages/goods/detail/index?id='+v.id,
              isSelected: false
            })
          });
          goods.map((v, i) => {
            if (v.link == this.urldata) {
              goods[i].isSelected = true
            }
          })
          this.tempData = goods;
        }
      }).catch(err => {
        console.log(err);
      });
      // 获取一级分类的
      goodsOneClassEnable().then(res => {
        console.log('一级分类',res)
        let arr = []
        res.data.records.map((v, i) => {
          if (v.id != 1) {
            arr.push({
              ...v,
              link:'/pages/goods/index/index?cate='+v.id,
              isSelected: false
            })
          }
        });
        arr.map((v, i) => {
          if (v.link == this.urldata) {
            arr[i].isSelected = true
          }
        })
        this.classData = arr
        console.log(this.classData)
      }).catch(err => {
        console.log(err)
      })
    },
    urloptionsdata: function() {
      if (this.urloptions) {
        if (this.urloptions.length > 0) {
          this.urldata = this.urloptions;
        }
      } else {
        this.urldata = "";
      }
    },
    SmallPro() {
      this.cardpopup = false;
      console.log('我是小程序的',this.SmallProcedures)
      this.$emit("listenurldata", this.SmallProcedures);
    },
    phoneFun() {
        // else {
        //   let res = /^1[34578]\d{9}$/;
        //   if (!res.test(this.phoneData)) {
        //     this.$Message.error("手机号输入不正确");
        //     return;
        //   }
        // }
       if (this.phoneData != "") {
        // if (isNaN(this.phoneData)) {
        //   this.$Message.error("电话号码必须是数字");
        //   return;
        // }
        let reg = /^[0-9]+$/gi
        if (!reg.test(this.phoneData)) {
          this.$Message.error("电话号码必须是数字");
          return;
        }
      }
      this.cardpopup = false;
      this.$emit("listenurldata", this.phoneData);
    },
    diyfunc() {
      this.cardpopup = false;
      console.log('我是地址',this.diyData)
      this.$emit("listenurldata", this.diyData);
    },
    btnfunc: function() {
      this.cardpopup = false;
      this.$emit("listenurldata", this.urldata);
      // this.$forceUpdate()
    },
    panelfunc() {
       this.cardpopup = false;
      // console.log(this.urldata)
      this.$emit("listenurldata", this.urldata);
      // this.$forceUpdate()
    },
    cardShow: function() {
      this.cardpopup = true;
      this.SmallProcedures = {
        appid: '',
        urldata: ''
      }
      this.phoneData = ""
      this.diyData = ""
    },
    cardClose: function() {
      this.cardpopup = false;
    },
    changepage(index) {
      this.getData(index)
    },
    select(index) {
      this.tempData[index].isSelected = !this.tempData[index].isSelected
      let id = this.tempData[index].id
      this.tempData.forEach(item => {
        if (id != item.id) {
          item.isSelected = false
        }
      });
      this.urldata = this.tempData[index].link
      this.tempGoods = this.tempData[index]
      this.btnfunc()
    },
    selectClass(index) {
      this.classData[index].isSelected = !this.classData[index].isSelected
      let id = this.classData[index].id
      this.classData.forEach(item => {
        if (id != item.id) {
          item.isSelected = false
        }
      });
      this.urldata = this.classData[index].link
      this.tempClass = this.classData[index]
      this.btnfunc()
    }
  }
};
</script>
