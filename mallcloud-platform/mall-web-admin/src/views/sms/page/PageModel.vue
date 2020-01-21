<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button
                style="float: right"
                @click="searchGroupList()"
                type="primary"
                size="small">
          查询结果
        </el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true"  size="small" label-width="140px">
          <el-form-item label="输入搜索：">
            <el-input style="width: 203px"  placeholder="拼团名称/关键字"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button
              class="btn-add"
              @click="add()"
              size="mini">
        添加
      </el-button>
    </el-card>
  </div>
</template>


<script>
//import shop from "@/api/request/shop";
import goodsUtils from "@/utils/goodsUtils";
export default {
  data() {
    return {
      name: "",
      listQuery:null,
      tableData: [],
      columns: [
        {
          title: "模板名称",
          align: "center",
          key: "name"
        },
        {
          title: "模板类型",
          align: "center",
          render: (h, params) => {
            let type = params.row.type;
            let res;
            switch (type) {
              case 2:
                res = "商城首页";
                break;
              case 3:
                res = "个人中心页";
                break;
              case 4:
                res = "商品详情页";
                break;
              case 5:
                res = "自定义页";
                break;
            }
            return h("span", {}, res);
          }
        },
        {
          title: "状态",
          align: "center",
          render: (h, params) => {
            let stateRes = params.row.status;
            return h("div", {}, [
              h(
                "Button",
                {
                  props: {
                    type: stateRes ? "primary" : "error",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px",
                    backgroundColor: stateRes ? "#0083b0!important" : "",
                    color: stateRes ? "#fff" : "",
                    'display': this.hasRole(5010202) ?'block':'none'
                  },
                  on: {
                    click: () => {
                      //  stateRes  ? '未启用' : '启用'
                      this.statusFun(params.index);
                    }
                  }
                },
                stateRes ? "已启用" : "未启用"
              )
            ]);
          }
        },
        {
          title: "创建时间",
          align: "center",
          key:'createTime'
        },
        {
          title: "操作",
          align: "center",
          width: 220,
          render: (h, params) => {
            let stateRes = params.row.status;
            return h("div", {
              style:{
                display:'flex',
                flexDirection:'row',
                alignItems:'center',
                justifyContent:'center',
                color:'#0083b0',
                'font-size':'13px'
              }
            }, [
              h("div", {
                class: {},
                style: {
                  borderRight: "1px solid #0083b0",
                  paddingRight:"10px",
                  cursor: "pointer",
                  'display': this.hasRole(5010201) ?'block':'none'
                },
                on: {
                  click: () => {
                    this.operationFun(params.row.type, params.row.id, params.row.status);
                  }
                }
              },"编辑"),

              h("div", {
                class: {},
                style: {
                  borderRight: "1px solid #0083b0",
                  padding:"0 10px",
                  cursor: "pointer",
                  'display': this.hasRole(5010202) ?'block':'none'
                },
                on: {
                  click: () => {
                    this.statusFun(params.index);
                  }
                }
              },stateRes == 1?"禁用":"启用"),

              h("div", {
                class: {},
                style: {
                  borderRight: "1px solid #0083b0",
                  padding:"0 10px",
                  cursor: "pointer",
                  'display': this.hasRole(5010203) ?'block':'none'
                },
                on: {
                  click: () => {
                    this.copyFun(params.row.type, params.row.id);
                  }
                }
              },"复制"),
              h("div", {
                class: {},
                style: {
                  padding:"0 10px",
                  cursor: "pointer",
                   'display': this.hasRole(5010204) ?'block':'none'
                },
                on: {
                  click: () => {
                    this.delFun(params.index);
                  }
                }
              },"删除")
            ]);
          }
        }
      ],
      total: 10,
      size: 10,
      current: 1,
      tempName: "4",
      // 暂存的数据
      tempId: "",
      tempStatus: "",
      tempModelType: "",
      // 删除的弹框
      upperPop: false,
      // 删除显示的内容
      tempMsg: "",
      // 是否可以删除的状态
      tempDel: false,
      // 状态切换
      statusPop: false,
      tempType: "",
      // 长度大于的弹框
      staLengthPop: false,
      // 启用大于10 的信息
      tempTitle: ""
    };
  },
  created() {
    // 请求数据
  //  this.getData();
  },
  activated() {
  //  this.getData();
  },
  methods: {
    // 获取数据
    getData() {
      shop
        .pageAdmin({
          type: this.tempName,
          name: this.name,
          current: this.current,
          size: this.size
        })
        .then(res => {
          if (res.code == "1") {
            this.integrationData(res.data);
          } else {
            this.$Message.error(res.message);
          }
        })
        .catch(err => {
          this.$Message.error("请求失败");
        });
    },
    search() {
      this.current = 1
      this.getData()
    },
    // 整合数据
    integrationData(data) {
      console.log(data);
      this.size = data.size;
      this.current = data.current;
      this.total = data.total;
      let rows = data.records;
      rows.forEach(item => {
        item.createTime = goodsUtils.timeFun(
          new Date(item.createTime).getTime()
        );
        // item.establishTime = goodsUtils.timeFun(new Date(item.establishTime).getTime())
        // item.lasteditTime = goodsUtils.timeFun(new Date(item.lasteditTime).getTime())
      });
      this.tableData = rows;
      console.log("rows", rows);
    },
    add() {
      this.$router.push(`/@/components/templatePage?type=5`);
    },

    // 状态更换
    statusFun(index) {
      // console.log(index)
      // let con = 0;
      // this.tableData.forEach(element => {
      //   if (element.stateRes == 1) {
      //     con += 1;
      //   }
      //   console.log(con);
      // });
      // if (con == 10) {
      //   this.staLengthPop = true;
      //   this.tempTitle = "不能启用该页面模板！";
      //   this.tempMsg = "仅能启用10个页面模板，请先停用已启用模板。";
      // } else {
      this.statusPop = true;
      if (this.tableData[index].status == "1") {
        this.tempMsg = "确认禁用该页面模板？";
      } else {
        this.tempMsg = "确认启用该页面模板";
      }
      this.tempId = this.tableData[index].id;
      this.tempStatus = this.tableData[index].status == 1 ? 0 : 1;
      this.tempType = this.tableData[index].type;
      // }
    },
    atatusOk() {
      // else if(res.code == "3") {
        //   this.staLengthPop = true;
        //   this.tempTitle = "不能启用该页面模板！";
        //   this.tempMsg = "仅能启用10个页面模板，请先停用已启用模板。";
        // }
      shop
        .pageAdminUpdate({
          id: this.tempId,
          status: this.tempStatus,
          typeId: this.tempType
        }).then(res => {
          if (res.code == "1") {
            this.$Message.success("切换成功");
            this.current = this.current;
            this.getData();
          } else {
            this.$Message.error(res.message);
          }
        })
        .catch(err => {
          this.$Message.error("状态切换失败");
        });
    },

    pagefun(index) {
      this.current = index;
      this.getData()
    },
    pageSizeFun(data) {
      this.size = data
      this.getData()
    },
    // 编辑的
    operationFun(type, id, status) {
      this.$router.push( `/@/components/templatePage?type=` + type + "&id=" + id + "&status=" + status);
    },
    // 复制
    copyFun(type, id) {
      this.$router.push(`/@/components/templatePage?role=1&type=` + type + "&id=" + id);
    },
    delFun(index) {
      this.tempDel = false;
      let status = this.tableData[index].status;
      this.upperPop = true;
      if (status == "1") {
        this.tempMsg = "该页面模板启用中，如需删除需先禁用！";
        this.tempDel = true;
      } else {
        this.tempMsg = "是否确认删除这个页面模板?";
      }
      this.tempId = this.tableData[index].id;
    },
    // 删除
    stateConfirm() {
      if (this.tempDel) {
        this.$Message.error(this.tempMsg);
        return;
      } else {
        shop
          .pageAdminDel({
            id: this.tempId
          })
          .then(res => {
            if (res.code == "1") {
              this.$Message.success("删除成功");
              if (this.tableData.length == 1) {
                this.current = this.current - 1;
              }
              if (this.current == 0) {
                this.current = 0;
              }
              this.getData();
            } else {
              this.$Message.error(res.message);
            }
          })
          .catch(err => {
            this.$Message.error("删除失败");
          });
      }
    }
  }
};
</script>

<style lang="less" scoped>
  .currentTitle{
    font-size: 13px;
    color: #000;
    padding: 4px 0;
    background: #f8fbfb;
  }
#page-model {
  #header {
    margin-bottom: 16px;
    .mar-le {
      margin-left: 20px;
    }
  }
  .main {
    .tips {
      display: inline-block;
      width: 36px;
      height: 36px;
      margin-right: 14px;
      background-image: url("../../../assets/images/u368.png");
      background-size: 100% 100%;
      vertical-align: middle;
    }
  }
  .footer {
    margin-top: 20px;
    .page {
      float: right;
    }
    &::after {
      content: "";
      display: block;
      clear: both;
    }
  }
}
</style>

