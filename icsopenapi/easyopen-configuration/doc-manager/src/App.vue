<template>
  <div id="app">
      <router-view/>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "App",
  computed: {
    ...mapState(["loading"])
  }
};
</script>

<style>
#app {
  height: 100%;
  font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Helvetica,Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
}
body {
  background: #f8f8f8;
}
.el-popup-parent--hidden{padding-right: 0!important;}
html{}
html,
body {
  margin: 0;
  padding: 0;
  height: 100%;
  overflow: hidden;
}
h1,
h2,
h3,
h4,
h5,
h6 {
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
    Microsoft YaHei, \\5fae\8f6f\96c5\9ed1, Arial, sans-serif;
  line-height: 1.5;
  margin: 15px 0 !important;
  color: #657180;
  font-weight: 600;
}
table {
  border-collapse: collapse;
  width: 100%;
}
table th {
  background: #f8f8f9;
}
.margin-right-5 {
  margin-right: 5px;
}
.padding-left-20 {
  padding-left: 20px;
}
.el-input-group__append {
  padding: 0;
}
.el-badge__content.is-fixed {
  right: 0px!important;
  top: 15px!important;
}
.el-badge .el-badge__content {
  border: 0;
  line-height: 17px;
}
.el-dialog {
  width: 400px;
}
.el-dialog__body {
  padding: 5px 20px;
}
.el-dialog {
  z-index: 9999;
}
#app .test-btn{background: #fe7300;border: 1px solid #fe7300;}
#app .test-btn:hover{background: #f75c3d;}
.el-select-dropdown__list{padding-bottom: 20px;}
</style>
