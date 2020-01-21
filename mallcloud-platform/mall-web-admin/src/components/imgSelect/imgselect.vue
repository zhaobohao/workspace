<!--
    type:image || multiimage (单个(默认) or 多个)
    mode:base || diy (固定样式(默认) or 自定义)
   -->
<style lang="less" scoped>
@import "./index.less";
</style>
<template>
  <div class="imgselect">
    <div class="fileshowbtn info" v-if="mode == 'base'" @click="uploadflg()">+</div>
    <div v-show="uploadshow" class="imgselect-container">
      <div class="imgselect-content">
        <div class="loading" v-if="loading">
          <Spin size="large"></Spin>
        </div>

        <div class="imgselect-content-top">
          <div>请选择图片</div>
          <i class="iconfont icon-close" @click="closeModel"></i>
         <!-- <Icon type="ios-close-circle" class="iconfont icon-close" @click="closeModel"/>-->
        </div>
        <div class="imgselect-cont-c clear">
          <div class="imgselect-cont-l">
            <div class="selectdata">
              <div v-for="(item,index) in category" :key="index">
                <div
                  :class="[selectedId == item.id ? 'itemactive' : '','itemcenter']"
                  @click="changeCategory(item.id)"
                >
                  {{item.category}}
                  <div class="deleteCate" v-if="item.id != 1" @click.stop="deleteCate(item.id)">×</div>
                </div>
              </div>
            </div>
            <div class="createCate">
              <div class="catebtn" @click="toggleCategory">+新建分类</div>
              <div class="cateContent" v-if="showAddCate">
                <div class="down-icon"></div>分类名称
                <el-input
                  v-model="categoryName"
                  placeholder="分类名称"
                  style="width: 100%;margin-top:10px;"
                  @on-change="cateInput"
                />
                <div class="cateAction">
                  <div class="catecancel">
                    <div @click="toggleCategory">取消</div>
                  </div>
                  <div class="cateconfrim">
                    <div @click="addCategory">确定</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="imgselect-cont-r">
            <div class="imgselect-cont-head">
              <div class="tool-search">
                <el-input
                  search
                  enter-button="搜索"
                  placeholder="请输入图片名称"
                  @on-change="searchChange"
                  @on-search="searchImg"
                />
              </div>
              <div class="file-sty-container">
                上传图片
                <input
                  ref="file"
                  type="file"
                  @change="fileChange"
                  style="opacity:0;width:100%;height:100%;position:absolute;top:0;left:0;"
                >
              </div>
            </div>
            <ul class="imgselect-cont-r">
              <li
                class="imgitem-cont"
                v-for="file in showfile"
                :key="file.index"
                @click="choiceFile(file,file.index)"
                @mouseover="showDel(file)"
                @mouseleave="hideDel(file)"
              >
                <img class="imgitem" :src="file.pic" width="100" height="100">
                <div v-if="file.isDel" class="delete" @click.stop="remove(file)">
                 <!-- <Icon type="ios-close-circle"/>-->
                  <i class="ios-close-circle"></i>
                </div>
                <div v-if="file.layerflg" class="delete-layer">
                 <!-- <Icon type="md-checkmark"/>-->
                  <i class="md-checkmark"></i>
                </div>
                <div class="imgname">{{file.name}}</div>
              </li>
            </ul>
          </div>
        </div>
        <div class="selectpage">
          <el-pagination
                  background

                  @current-change="changePage"
                  layout="total, sizes,prev, pager, next,jumper"
                  :page-size="size"
                  :page-sizes="[5,10,15]"
                  :current-page.sync="current"
                  :total="total">
          </el-pagination>
         <!-- <Page :current="current" :total="total" :page-size="size" @on-change="changePage"/>-->
        </div>
        <div class="imgselect-cont-footer">
          <div class="selectcancel">
            <div @click="closeModel()">取消</div>
          </div>
          <div class="selectclose">
            <div @click="close()">确定</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import api from "@/api/request"
import {deleteImg,categoryLists,addCate,categoods,deleteCategory,fetchList} from "@/api/pms/album";
import store from "@/store"
export default {
  name: "imgselect",
  props: ["options"],
  data() {
    return {
      selectfiles: [],
      uploadshow: false,
      cardpopup: "",
      showfile: [],
      tpmfile: [],
      category: [],
      imgLists: [],
      selectedId: 0,
      newOptions: {},
      type: "image",
      mode: "base",
      currentCate: "",
      categoryName: "",
      showAddCate: false,
      current: 1,
      total: 0,
      size: 10,
      loading: false,
      searchName: ""
    };
  },
  watch: {
    options() {
      let _this = this;
      console.log(_this.options);
      _this.newOptions = _this.options;
      _this.init(_this.newOptions);
    }
  },
  created() {
    let _this = this;
    _this.postAction;
    _this.init(_this.options);
  },
  methods: {
    init(options) {
      let _this = this;
      _this.showfile = [];
      _this.category = [];
      _this.tpmfile = [];
      if (options) {
        _this.type = options.type || "image";
        _this.mode = options.mode || "base";
      } else {
        _this.type = "image";
        _this.mode = "base";
      }
    },
    /**
     * 删除图片
     */
    remove(file) {
      let _this = this;
      console.log(file);
      _this.loading = true;
     deleteImg(file.id)
        .then(res => {
          _this.loading = false;
          if (res.code == 200) {
            _this.$Message.success("操作成功");
            _this.getImg(_this.selectedId);
          } else {
            _this.$Message.success("操作失败");
          }
        });
    },
    // 获取分类
    getCategory() {
      let _this = this;
      categoryLists({
        type:'image'
      }).then(res => {
        console.log(res);
        if (res.code == 200) {
          let catelists = res.data.records;
          _this.category = [];
          catelists.map((v, i) => {
            _this.category.push({
              id: v.id,
              category: v.name
            });
          });
          _this.getImg(_this.category[0].id);
        }
      });
    },
    // 获取分类对应得图片
    getImg(id) {
      let _this = this;
      let res = _this.category.find(v => {
        return v.id == id;
      });
      _this.selectedId = res.id;
      categoods({
          type:'image',
        albumId: res.id,
          current: _this.current,
        })
        .then(res => {
          console.log(res);
          if (res.code == 200) {
            let imgLists = res.data.records;
            if (imgLists.length == 0 && _this.current > 1) {
              _this.current--;
              _this.getImg(_this.selectedId);
            } else {
              let newimgLists = [];
              imgLists.map((v, i) => {
                newimgLists.push({
                  ...v,
                  layerflg: false,
                  isDel: false
                });
              });
              _this.current = res.data.current;
              _this.size = res.data.size;
              _this.total = res.data.total;
              this.showfile = newimgLists;
            }
          }
        });
    },
    // 监听鼠标滑动
    showDel(file) {
      file.isDel = true;
    },
    hideDel(file) {
      file.isDel = false;
    },
    /**
     * 切换分类
     */
    changeCategory(id) {
      let _this = this;
      _this.getImg(id);
    },
    // 用于点击选择图片
    choiceFile(file) {
      let _this = this;
      let type = _this.type;
      if (type == "image") {
        // 单选
        _this.tpmfile = [];
        _this.showfile.map((v, i) => {
          v.layerflg = false;
        });
        file.layerflg = true;
        _this.tpmfile.push(file);
      } else {
        // 多选
        if (file.layerflg) {
          file.layerflg = false;
          _this.tpmfile.splice(file.index, 1);
        } else {
          file.layerflg = true;
          _this.tpmfile.push(file);
        }
      }
    },
    uploadflg() {
      this.uploadshow = !this.uploadshow;
      this.getCategory();
    },
    close() {
      console.log(this.tpmfile);
      this.uploadshow = false;
      if (this.tpmfile) {
        this.$emit("listentoptpmfile", this.tpmfile);
      }
    },
    closeModel() {
      this.uploadshow = false;
    },
    // 展示图片分类
    toggleCategory() {
      this.categoryName = "";
      this.showAddCate = !this.showAddCate;
    },
    // 新增图片分类
    addCategory() {
      let _this = this;
      let name = _this.categoryName;
      _this.loading = true;
      addCate({
          name: name,
          type:'image'
        })
        .then(res => {
          if (res.code == 200) {
            _this.loading = false;
            _this.$Message.success("添加成功");
            _this.toggleCategory();
            _this.getCategory();
          } else {
            _this.loading = false;
            _this.$Message.error(res.message);
          }
        });
    },
    /**
     * 删除分类
     */
    deleteCate(id) {
      let _this = this;
      _this.loading = true;
      deleteCategory(id)
        .then(res => {
          console.log(res);
          if (res.code == 200) {
            _this.loading = false;
            _this.$Message.success("删除成功");
            _this.getCategory();
          } else {
            _this.loading = false;
            _this.$Message.error(res.message);
          }
        });
    },
    /**
     * 监听分类输入
     */
    cateInput(e) {
      let _this = this;
      let value = e.srcElement.value;
      _this.categoryName = value;
    },
    /**
     * 上传文件
     */
    fileChange(e) {
      let _this = this;
      let file = e.target.files[0];


      if(file.size > 1024 * 1024){
        this.$Message.error('上传图片不能大于1M')
        return false
      }

      _this.loading = true;
      let formData = new FormData(); //通过formdata上传
      formData.append("file", file);
      formData.append("id", "test-mini");
      formData.append("type", 1);

      formData.append("groupId", _this.selectedId);
      formData.append("token",store.getters.token)


      api.uploadImg("POST", formData).then(res => {
        _this.loading = false;
        _this.$refs.file.value = "";
        if (res.code == 200) {
          _this.getImg(_this.selectedId);
        } else {
          _this.$Message.error(res.message);
        }
      });

    },
    /**
     * 分页
     */
    changePage(e) {
      console.log(e);
      this.current = e;
      this.getImg(this.selectedId);
    },
    /**
     * 搜索
     */
    searchChange(e) {
      let _this = this;
      let value = e.srcElement.value;
      _this.current = 1;
      _this.searchName = value;
      _this.searchContent();
    },
    /**
     * 搜索
     */
    searchImg(value) {
      let _this = this;
      _this.current = 1;
      _this.searchName = value;
      _this.searchContent();
    },

    /**
     * 搜索的分页
     */
    searchContent() {
      let _this = this;
      categoods({
          type: 'image',
          name: _this.searchName,
        albumId: _this.selectedId,
          current: _this.current
        })
        .then(res => {
          console.log(res);
          if (res.code == 200) {
            let imgLists = res.data.records;
            if (imgLists.length == 0 && _this.current > 1) {
              _this.current--;
              _this.searchContent();
            } else {
              let newimgLists = [];
              imgLists.map((v, i) => {
                newimgLists.push({
                  ...v,
                  layerflg: false,
                  isDel: false
                });
              });
              _this.current = res.data.current;
              _this.size = res.data.size;
              _this.total = res.data.total;
              this.showfile = newimgLists;
            }
          }
        });
    }
  }
};
</script>
