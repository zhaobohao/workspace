<style lang="less" scoped>
@import "./mofang.less";
</style>
<template :options="options">
  <div class="d-config">
    <el-tabs value="content" class="d-tab">
      <el-tab-pane label="内容" name="content">
        <div class="pannelcontent">
          <div class="pannelItem">
            <div class="tit">魔方布局</div>
            <div
              class="list-group"
              @mouseleave="mouseleave"
              ref="listGroup"
              style="width:304px;height:304px;"
            >
              <ul class="list-mf" v-for="(item,index) in elementLists" :key="index">
                <li
                  :class="[child.isSelect ? 'mf-active' : '','mf-item']"
                  v-for="(child,cid) in item"
                  :key="cid"
                  :style="{width:child.itemWidth + 'px',height:child.itemHeight + 'px','line-height':child.itemHeight + 'px',cursor:editable == 'enable' ? 'pointer' : 'not-allowed'}"
                  :data-positionX="child.ix"
                  :data-positionY="child.iy"
                  @mouseup="mouseup(child.ix,child.iy)"
                  @mousemove="mousemove(child.ix,child.iy)"
                >
                  <span v-if="!child.isSelect">+</span>
                </li>
              </ul>
              <div
                v-if="combineLists.length > 0"
                :class="[item.isSelect ? 'list-block-select' : '','list-block']"
                v-for="item in combineLists"
                :data-id="item.id"
                :key="item.id"
                :style="{display:'flex',flexDirection:'column',justifyContent:'center',alignItems:'center',left:item.left + 'px',top:item.top + 'px',width:item.width + 'px',height:item.height + 'px','z-index':item.zIndex}"
                @click="clickBlock"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
              >
                {{(750 / 304 * (item.width - 1)).toFixed(1)}} * {{(750 / 304 * (item.height)).toFixed(1)}}
                <div
                  class="icon-closespe"
                  v-if="item.isSelect"
                  :data-id="item.id"
                  @click="deleteBlock"
                >×</div>
              </div>
            </div>
            <!-- <div class="tit" style="margin-top:30px;">魔方格数</div>
            <div class="list-group slide">
              <el-radio-group v-model="girdnum" style="width:100%" @on-change="changeGirdNum">
                <el-radio label="3"></el-radio>
                <el-radio label="4"></el-radio>
              </el-radio-group>
            </div> -->
          </div>
        </div>
        <div class="pannelcontent" v-if="combineLists.length > 0">
          <div class="pannelItem">
            <div class="item">
              <div class="item-inner">
                <span class="labeltext">图片</span>
                <div show="0" class="small" style="align-self: start;">
                  <div class="clearfix">
                    <div class="clearfix pull-left"></div>
                    <div class="uploadSource" @click="showImgSelect" v-if="currentImg == ''">
                      <i class="ivu-icon ivu-icon-ios-add" style="font-size: 28px;"></i>
                    </div>
                    <img
                      v-else
                      :src="currentImg"
                      @click="showImgSelect"
                      class="uploadSource"
                      style="object-fit:cover;"
                      alt
                    >
                  </div>
                </div>
              </div>
              <div class="item-inner">
                <span class="labeltext">链接</span>
                <div class="readonly-div">
                  <el-input v-model="currentLink" class="itemInput" disabled placeholder="请选择或填写小程序路径">
                    <el-button slot="append" @click="showLinkSelect">选择跳转</el-button>
                  </el-input>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- <div class="pannelcontent">
          <div class="pannelItem" style="border-bottom:0px">
            <div class="tit">图片间隙</div>
            <div class="list-group slide">
              <el-slider class="slide-content" v-model="imgPadding" :max="48"></el-slider>
              <div class="slide-tips">{{imgPadding}}PX</div>
            </div>
          </div>
        </div> -->
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  props: ["options","editable"],
  data() {
    return {
      isMove: false,
      // 4*4 5*5 6*6... 默认为4 * 4
      elementNums: 16,
      // 5*5 为5 6*6为6
      elementBase: 4,
      elementLists: [],
      // 给与添加合并格子的元素
      combineLists: [],
      iX: "",
      iY: "",
      fX: "",
      fY: "",
      imgPadding: 0,
      currentId: "",
      blockWidth: 304,
      blockHeight: 304,
      girdnum: "4",
      newOptions: {},
      currentImg: "",
      currentLink: "",
      // 已选择的坐标点
      isCurrentPoint: []
    };
  },
  created() {
    let _this = this;
    _this.init(_this.options);
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
      _this.init(_this.newOptions);
    }
  },
  mounted() {
    let _this = this;
    this.$nextTick(() => {
      let height = this.$refs.listGroup.offsetHeight;
      let width = this.$refs.listGroup.offsetWidth;
      _this.blockWidth = width;
      _this.blockHeight = height;
    });
  },
  methods: {
    // 初始化
    init(op) {
      let _this = this;
      let optionsParams = op.options;
      if (JSON.stringify(optionsParams) == "{}") {
        _this.restore();
      } else {
        _this.elementLists = optionsParams.elementLists;
        _this.combineLists = optionsParams.combineLists;
        _this.imgPadding = optionsParams.imgPadding;
        _this.currentId = optionsParams.currentId;
        for (let index = 0; index < _this.combineLists.length; index++) {
          if (_this.combineLists[index].id == parseInt(_this.currentId)) {
            _this.currentImg = _this.combineLists[index].imageUrl;
            _this.currentLink = _this.combineLists[index].linkUrl;
            break;
          } else {
            _this.currentImg = "";
            _this.currentLink = "";
          }
        }
      }
    },
    // 生成元素组
    makeElements() {
      let ebaseLists = [];

      let _this = this;
      let itemWidth = _this.blockWidth / _this.elementBase;
      let itemHeight = _this.blockHeight / _this.elementBase;
      for (let i = 1; i <= _this.elementBase; i++) {
        ebaseLists[i - 1] = [];
        for (let j = 0; j < _this.elementNums; j++) {
          let bid = Math.floor(j / _this.elementBase);
          if (bid + 1 == i) {
            ebaseLists[i - 1].push({
              id: j + 1,
              ix: i,
              iy: (j % _this.elementBase) + 1,
              // 处理移动时 加载的背景图
              isSelect: false,
              itemWidth: itemWidth,
              itemHeight: itemHeight
            });
          }
        }
      }
      _this.elementLists = ebaseLists;
    },
    // 移除范围
    mouseleave() {
      let _this = this;
      _this.isMove = false;
      _this.clearCanvas();
    },
    // 点击抬起时，开启移动状态
    mouseup(px, py) {
      let _this = this;
      if(this.editable == 'enable'){
        if (_this.isMove) {
          _this.isMove = false;
          // 合并初始位置 -> 终点位置的元素块 并关闭可移动
          _this.confrimCanvas(px, py);
        } else {
          // 点击时 改变元素背景
          _this.isMove = true;
          _this.iX = px;
          _this.iY = py;
          _this.changeSelect(px, py);
        }
      }
    },
    // 移动
    mousemove(px, py) {
      let _this = this;
      // 如果移动状态开启 可以移动并计算
      if (_this.isMove) {
        _this.moveSelect(px, py);
      }
      // 如果移动状态关闭 不做处理
    },
    // 改变元素背景
    changeSelect(fx, fy) {
      let _this = this;
      _this.elementLists[fx - 1][fy - 1].isSelect = true;
    },
    // 移动改变背景
    moveSelect(fx, fy) {
      let _this = this;
      _this.clearCanvas();
      // 再生成最新的背景
      if (_this.iX > fx && _this.iY <= fy) {
        for (let i = _this.iX; i >= fx; i--) {
          for (let j = _this.iY; j <= fy; j++) {
            _this.avoidBlock(i, j);
            _this.elementLists[i - 1][j - 1].isSelect = true;
          }
        }
      } else if (_this.iY > fy && _this.iX <= fx) {
        for (let i = _this.iX; i <= fx; i++) {
          for (let j = _this.iY; j >= fy; j--) {
            _this.avoidBlock(i, j);
            _this.elementLists[i - 1][j - 1].isSelect = true;
          }
        }
      } else if (_this.iY > fy && _this.iX > fx) {
        for (let i = _this.iX; i >= fx; i--) {
          for (let j = _this.iY; j >= fy; j--) {
            _this.avoidBlock(i, j);
            _this.elementLists[i - 1][j - 1].isSelect = true;
          }
        }
      } else {
        for (let i = _this.iX; i <= fx; i++) {
          for (let j = _this.iY; j <= fy; j++) {
            _this.avoidBlock(i, j);
            _this.elementLists[i - 1][j - 1].isSelect = true;
          }
        }
      }
    },
    // 避开已选择的画板
    avoidBlock(px, py) {
      let _this = this;
      let currentBlock = px + "," + py;
      if (_this.isCurrentPoint.includes(currentBlock)) {
        // 有重复的 清楚画板 并且 取消可移动
        _this.clearCanvas();
        _this.isMove = false;
      } else {
        console.log("无重复的");
      }
    },
    // 清楚画板
    clearCanvas() {
      let _this = this;
      _this.elementLists.map((v, i) => {
        v.map((vs, is) => {
          vs.isSelect = false;
        });
      });
    },
    // 确认画板
    confrimCanvas(px, py) {
      let _this = this;
      let width,
        height,
        top,
        left,
        boxwidth = _this.blockWidth / _this.elementBase,
        boxheight = _this.blockHeight / _this.elementBase;

      // 生成已选择的坐标点
      let initX = Math.min(_this.iX, px);
      let Dx = Math.max(_this.iX, px);

      let initY = Math.min(_this.iY, py);
      let Dy = Math.max(_this.iY, py);

      // 当前的点数
      let currentNum = [];

      for (let i = initX; i <= Dx; i++) {
        for (let j = initY; j <= Dy; j++) {
          currentNum.push(i + "," + j);
          // 赋予已选择的坐标点
          _this.isCurrentPoint.push(i + "," + j);
        }
      }

      let newNum = []
      _this.combineLists.map((v,i) => {
        newNum.push(v.id)
      })

      let newObj = {
        id: newNum.length == 0 ? _this.elementLists.length + 1 : newNum[_this.combineLists.length - 1] + 1,
        width: (Math.abs(px - _this.iX) + 1) * boxwidth + 1,
        height: (Math.abs(py - _this.iY) + 1) * boxheight,
        top: (Math.min(py, _this.iY) - 1) * boxheight,
        left: (Math.min(px, _this.iX) - 1) * boxwidth,
        isSelect: true,
        zIndex: 3,
        imageUrl: "",
        linkUrl: "",
        currentNum: currentNum
      };
      // 设置默认点击的id
      _this.currentId = newObj.id;
      _this.chooseEvent(newObj);
      _this.clearCanvas();
    },
    // 处理选择事件
    chooseEvent(newObj) {
      let _this = this;
      if (_this.combineLists.length > 0) {
        _this.combineLists.map((v, i) => {
          v.isSelect = false;
        });
      }
      _this.combineLists.push(newObj);
      _this.changeForm();
    },
    // 点击块状
    clickBlock(e) {
      let _this = this;
      let id = e.srcElement.dataset.id;
      _this.combineLists.map((v, i) => {
        if (v.id == id) {
          v.isSelect = true;
          v.zIndex = 3;
          _this.currentId = id;
        } else {
          v.isSelect = false;
          v.zIndex = 2;
        }
      });
      _this.changeForm();
    },
    // 删除块状
    deleteBlock(e) {
      let _this = this;
      let id = e.srcElement.dataset.id;
      let newIsCurrentPoint = _this.isCurrentPoint;
      _this.combineLists.map((v, i) => {
        if (v.id == id) {
          let objectPoint = _this.combineLists[i].currentNum;
          _this.deletePoint(objectPoint);
          // 删除对应的坐标点
          _this.combineLists.splice(i, 1);
        }
      });
      _this.changeForm();
    },
    deletePoint(ary) {
      let _this = this;
      let newIsCurrentPoint = _this.isCurrentPoint;
      for (let j = 0; j < ary.length; j++) {
        for (let i = 0; i < newIsCurrentPoint.length; i++) {
          if (newIsCurrentPoint[i] == ary[j]) {
            newIsCurrentPoint.splice(i, 1);
          }
        }
      }
      _this.isCurrentPoint = newIsCurrentPoint;
    },
    // 监听选择grid数量
    changeGirdNum(e) {
      let _this = this;
      _this.girdnum = e;
      _this.elementNums = e * e;
      // 5*5 为5 6*6为6
      _this.elementBase = e;
      _this.clearCanvas();
      _this.elementLists = [];
      _this.makeElements();
      _this.combineLists = [];
    },
    // 恢复初始状态
    restore() {
      let _this = this;
      _this.clearCanvas();
      _this.elementLists = [];
      _this.makeElements();
      _this.combineLists = [];
      _this.imgPadding = 0;
      _this.girdnum = "4";
      _this.changeForm();
    },
    //改变值并且给父组件传递事件
    changeForm() {
      let _this = this;
      let changeData = {
        elementLists: _this.elementLists,
        combineLists: _this.combineLists,
        imgPadding: _this.imgPadding,
        currentId: _this.currentId
      };
      _this.$emit("listenToForm", changeData);
    },
    // 点击显示图片的选择弹框
    showImgSelect() {
      let _this = this;
      let changeData = {
        openImg: true,
        mode: "diy",
        type: "image",
        id: "mofang"
      };
      _this.$emit("listenToOpenImgSelect", changeData);
    },

    // 点击显示链接的选择弹框
    showLinkSelect() {
      let _this = this;
      let changeData = {
        mode: "diy",
        id: "mofang",
        link: _this.currentLink
      };
      _this.$emit("listenToOpenLinkSelect", changeData);
    }
  }
};
</script>
