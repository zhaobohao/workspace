<style lang="less" scoped>
@import "./memberTem.less";
</style>

<template>
  <div
    class="member diyitem"
    :style="{background:member_img ? 'url('+member_img+')' : member_color,'background-size':'cover','background-position':'center'}"
  >
    <div class="member-avatar">
      <img src="@/assets/images/people.png" class="icon-member" alt>
    </div>
    <div class="member-info">
      <div class="info-item" v-if="member_name == '1'">
        <div class="item-label">用户昵称</div>
      </div>
      <div class="info-item" v-if="member_level == '1'">
        <div class="item-label">会员等级：</div>
      </div>
      <div class="info-item" v-if="member_credit == '1'">
        <div class="item-label">会员积分：</div>
      </div>
    </div>
    <div class="member-review">{{ member_review == '1' ? member_text ? member_text : '修改信息>' : ''}}</div>
  </div>
</template>

<script>
export default {
  props: ["options"],
  data() {
    return {
      member_color: "#f1f1f1",
      member_img: "",
      member_name: "1",
      member_level: "2",
      member_credit: "2",
      member_review: "1",
      member_text: "",
      member_link: "",
      newOptions: {}
    };
  },
  watch: {
    options() {
      let _this = this;
      _this.newOptions = _this.options;
      console.log("选项卡模板", _this.newOptions);
      _this.init(_this.newOptions);
    }
  },
  created() {
    let _this = this;
    _this.init(_this.options);
  },
  methods: {
    // 初始化
    init(options) {
      let _this = this;
      if (JSON.stringify(options) == "{}") {
        _this.restore();
      } else {
        _this.member_color = options.member_color;
        _this.member_img = options.member_img;
        _this.member_name = options.member_name;
        _this.member_level = options.member_level;
        _this.member_credit = options.member_credit;
        _this.member_review = options.member_review;
        _this.member_text = options.member_text;
        _this.member_link = options.member_link;
      }
    },
    // 点击tab
    clickTab(index) {
      console.log(index);
      this.current = index;
    },
    // 恢复初始
    restore() {
      let _this = this;
      _this.member_color = "#f1f1f1";
      _this.member_img = "";
      _this.member_name = "1";
      _this.member_level = "2";
      _this.member_credit = "2";
      _this.member_review = "1";
      _this.member_text = "";
      _this.member_link = "";
    }
  }
};
</script>
