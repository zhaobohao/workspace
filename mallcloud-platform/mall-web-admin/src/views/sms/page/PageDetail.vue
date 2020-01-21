<template>
  <div class="container">
    <!-- 标题 -->
    <div class="page-title">页面管理</div>
    <!-- 内容区域 -->
    <div class="page-content">
      <!-- 页面导航区域 -->
      <div class="page-nav">
        <el-button type="primary" class="comMg" @click="handleShowClick">+添加新页面</el-button>
        <el-select v-model="model1" style="width:200px" class="comMg">
          <el-option v-for="item in list" :value="item.value" :key="item.value">{{ item.label }}</el-option>
        </el-select>
        <el-input placeholder="输入页面关键词搜索" style="width: 300px;" class="comMg"></el-input>
        <el-button type="primary" class="comMg">搜索</el-button>
      </div>
      <!-- 选项卡区域 -->
      <div class="page-tab">
        <!-- 选项卡 -->
        <el-tabs type="card">
          <!-- 标签1 -->
          <el-tab-pane label="全部页面">
            <!-- 表格 -->
            <Table border :columns="columns7" :data="data6"></Table>
            <!-- 表格下边栏 -->
            <div class="table-status">
              <!-- 左边 -->
              <div class="lf">
                <el-checkbox class="status"></el-checkbox>
                <el-button type="default" class="status">启用</el-button>
                <el-button type="default" class="status">禁用</el-button>
                <el-button type="default" class="status">删除</el-button>
              </div>
              <!-- 右边 -->
              <div class="rt">
                <Page
                  :total="100"
                  prev-text="上一页"
                  next-text="下一页"
                  show-total
                  show-elevator
                />
              </div>
            </div>
          </el-tab-pane>

          <el-tab-pane label="商城首页">标签二的内容</el-tab-pane>
          <el-tab-pane label="会员中心页">标签三的内容</el-tab-pane>
          <el-tab-pane label="商品详情页">
            <!-- 表格 -->
            <Table border :columns="columns7" :data="data6"></Table>
            <!-- 表格下边栏 -->
            <div class="table-status">
              <!-- 左边 -->
              <div class="lf">
                <el-checkbox class="status"></el-checkbox>
                <el-button type="default" class="status">启用</el-button>
                <el-button type="default" class="status">禁用</el-button>
                <el-button type="default" class="status">删除</el-button>
              </div>
              <!-- 右边 -->
              <div class="rt">
                <Page
                  :total="100"
                  prev-text="上一页"
                  next-text="下一页"
                  show-total
                  show-elevator
                />
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="自定义页">标签二的内容</el-tab-pane>
          <el-tab-pane label="启动页">标签三的内容</el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <!-- 遮罩层 -->
    <div class="mask" v-if="show">
      <!-- 遮罩层内部盒子 -->
      <div class="maskDesc-box">
        <!-- 盒子头部 关闭按钮 -->
        <div class="desc-top">
          选择连接
          <span class="close" @click="handleHiddenClick">x</span>
        </div>
        <!-- 盒子内容 -->
        <div class="desc-content">
          <!-- 盒子每一项 -->
          <a href="http://www.baidu.com/" class="link">
            <div class="desc-item">
             <!-- <Icon type="ios-home" size="30"/>-->
              <i  type="ios-home" size="30"></i>
              <div class="desc-text">商城首页</div>
              <div class="desc-create">立即创建</div>
            </div>
          </a>
          <!-- 盒子每一项 -->
          <a href="#" class="link">
            <div class="desc-item">
            <!--  <Icon type="ios-cart" size="30"/>-->
              <i  type="ios-home" size="30"></i>
              <div class="desc-text">商品详情</div>
              <div class="desc-create">立即创建</div>
            </div>
          </a>
          <!-- 盒子每一项 -->
          <a href="#" class="link">
            <div class="desc-item">
             <!-- <Icon type="md-person" size="30"/>-->
              <i  type="ios-home" size="30"></i>
              <div class="desc-text">个人中心</div>
              <div class="desc-create">立即创建</div>
            </div>
          </a>
          <!-- 盒子每一项 -->
          <a href="#" class="link">
            <div class="desc-item">
              <!--<Icon type="ios-create" size="30"/>-->
              <i  type="ios-create" size="30"></i>
              <i  class="iconsearch" :style="{color:search_icon}" type="ios-search-outline"></i>
              <div class="desc-text">自定义页</div>
              <div class="desc-create">立即创建</div>
            </div>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "pageDetail",
  data() {
    return {
      show: false,
      model1: "",
      list: [
        {
          value: "页面类型",
          label: "页面类型"
        },
        {
          value: "商城首页",
          label: "商城首页"
        },
        {
          value: "会员中心页",
          label: "会员中心页"
        },
        {
          value: "商品详情页",
          label: "商品详情页"
        },
        {
          value: "自定义页",
          label: "自定义页"
        }
      ],
      columns7: [
        {
          type: "selection",
          width: 50,
          align: "center"
        },
        {
          title: "页面名称",
          key: "name",
          align: "center",
          render: (h, params) => {
            //console.log(h);
            //console.log(params);
            return h(
              "div",
              {
                style: {
                  padding: "20px"
                }
              },
              [
                h(
                  "Button",
                  {
                    props: {
                      type: "primary"
                    }
                  },
                  params.row.defaultName
                ),
                h("span", params.row.name)
              ]
            );
          }
        },
        {
          title: "页面类型",
          key: "type",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "success"
                  }
                },
                params.row.storeName
              )
            ]);
          }
        },
        {
          title: "状态",
          key: "status",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "Button",
                {
                  props: {
                    type: "primary"
                  }
                },
                params.row.status
              )
            ]);
          }
        },
        {
          title: "创建时间",
          key: "createTime",
          align: "center"
        },
        {
          title: "最后修改时间",
          key: "updatedTime",
          align: "center"
        },
        {
          title: "操作",
          key: "action",
          width: 200,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("a", [
                h("Icon", {
                  props: {
                    type: "md-create",
                    size: 30
                  },
                  style: {
                    marginRight: "5px"
                  }
                })
              ]),
              h(
                "Icon",
                {
                  props: {
                    type: "ios-copy",
                    size: 30
                  },
                  style: {
                    marginRight: "5px"
                  },
                  on: {
                    click: () => {
                      this.showInfo(params.index);
                    }
                  }
                },
                "查看信息"
              ),
              h(
                "Icon",
                {
                  class: {
                    iconfont: true,
                    "icon-delete": true
                  },
                  on: {
                    click: () => {
                      this.remove(params.index);
                    }
                  }
                },
                "删除"
              )
            ]);
          }
        }
      ],
      data6: [
        {
          defaultName: "默认首页",
          name: "官方店铺首页",
          storeName: "商城首页",
          status: "启用",
          createTime: "2018-11-24 12:28:47",
          updatedTime: "2018-11-25 16:30:50"
        },
        {
          defaultName: "默认首页",
          name: "官方店铺首页",
          storeName: "商城首页",
          status: "未启用",
          createTime: "2018-12-24 12:28:47",
          updatedTime: "2018-12-25 16:30:50"
        }
      ]
    };
  },
  methods: {
    showInfo(index) {
      this.$Modal.info({
        title: "商城信息",
        content: `默认商城:${this.data6[index].defaultName}<br>当前商城:${
          this.data6[index].name
        }<br>状态：${this.data6[index].status}`
      });
    },
    remove(index) {
      this.data6.splice(index, 1);
    },
    //显示遮罩
    handleShowClick() {
      this.show = true;
    },
    handleHiddenClick() {
      this.show = false;
    }
  }
};
</script>
<style scoped>
.container {
  padding: 0 20px;
  position: relative;
}

.container .page-title {
  height: 30px;
  line-height: 30px;
  font-size: 16px;
}

.container .page-content {
  border: 1px solid #aeaeae;
  background: #f3f3f3;
  height: 1000px;
}

.container .page-nav {
  border-bottom: 1px solid #aeaeae;
  padding: 15px 10px;
}

.container .comMg {
  margin-right: 20px;
}

.container .page-tab {
  padding: 15px 10px 0 10px;
}

.container .table-status {
  margin-top: 20px;
  padding: 0 10px;
  position: relative;
  height: 200px;
}

.container .status {
  margin: 0 10px;
}

.table-status .lf,
.table-status .rt {
  position: absolute;
}

.table-status .lf {
  top: 0;
  left: 0;
}

.table-status .rt {
  top: 0;
  right: 0;
}

.container .mask {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 99;
}

.container .maskDesc-box {
  position: absolute;
  width: 600px;
  background: #f2f2f2;
  top: 50%;
  left: 50%;
  margin-top: -300px;
  margin-left: -250px;
}

.container .desc-top {
  color: #fff;
  padding: 10px;
  background: #2688fb;
}

.desc-top .close {
  position: absolute;
  right: 0;
  top: 0;
  background: rgba(0, 0, 0, 0.3);
  height: 37px;
  width: 37px;
  font-size: 25px;
  text-align: center;
  line-height: 37px;
}

.container .desc-content {
  padding: 30px 0;
  display: flex;
  justify-content: space-around;
}

.container .link {
  display: block;
}

.container .desc-item {
  width: 100px;
  height: 130px;
  background: #ffffff;
  border: 1px dashed #aeaeae;
  padding: 10px;
  box-sizing: border-box;
  text-align: center;
}

.container .desc-text {
  margin: 15px;
}

.container .desc-create {
  border: 1px solid #aeaeae;
  padding: 3px 0;
  color: #000;
}

.container .desc-create:hover {
  background: #2688fb;
  color: #fff;
}
</style>
