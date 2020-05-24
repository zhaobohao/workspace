<template>
  <div id="app">
    <router-view />
    <!-- 将上传组件全局注册 -->
    <global-uploader></global-uploader>
    <vue-picture-viewer v-if="showPreviewImg" :img-data="previewImgs" :select-index="previewIndex"
      @close-viewer="showPreviewImg=false" />
  </div>
</template>

<script>
  /** 常用图片预览创建组件 */
  import VuePictureViewer from '@/components/vuePictureViewer/index'
  import
  globalUploader
  from '@/components/GlobalUploader'
  export default {
    name: 'App',
    components: {
      globalUploader,
      VuePictureViewer
    },
    data() {
      return {
        showPreviewImg: false,
        previewIndex: 0,
        previewImgs: []
      }
    },
    computed: {},
    watch: {
      $route(to, from) {
        this.showPreviewImg = false // 切换页面隐藏图片预览
        if (to.meta.menuIndex) {
          this.$store.commit('SET_ACTIVEINDEX', to.meta.menuIndex)
        } else {
          this.$store.commit('SET_ACTIVEINDEX', to.path)
        }
      }
    },
    created() {

    },
    mounted() {
      this.addBus()
    },
    methods: {
      addBus() {
        var self = this
        this.$bus.on('preview-image-bus', function (data) {
          self.previewIndex = data.index
          self.previewImgs = data.data
          self.showPreviewImg = true
        })
      }
    }
  }

</script>
<style lang="scss">
  #app {
    width: 100%;
    height: 100%;
    overflow: hidden;
    position: relative;
  }

</style>
