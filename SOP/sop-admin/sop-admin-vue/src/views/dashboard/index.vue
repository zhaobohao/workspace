<template>
  <div class="dashboard-container">
    <div class="dashboard-text">欢迎使用SOP Admin</div>
  </div>
</template>

<script>
export default {
  created() {
    this.post('admin.userinfo.get', {}, function(resp) {
    })
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 18px;
    line-height: 46px;
  }
}
</style>
