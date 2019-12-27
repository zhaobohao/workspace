<template>
  <div id="app">
    <router-view />
    <!-- 将上传组件全局注册 -->
    <global-uploader></global-uploader>
  </div>
</template>

<script>
  import
  globalUploader
  from '@/components/GlobalUploader'
  export default {
    name: 'App',
    components: {
      globalUploader
    },
    data() {
      return {}
    },
    computed: {},
    watch: {},
    created() {

    },
    methods: {}
  }

</script>
<style lang="scss">
  #app {
    width: 100%;
    height: 100%;
    overflow: hidden;
  }

</style>
