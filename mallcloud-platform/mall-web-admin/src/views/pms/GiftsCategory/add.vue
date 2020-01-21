<template> 
  <GiftsCategory-detail :is-edit='false'></GiftsCategory-detail>
</template>
<script>
  import GiftsCategoryDetail from './components/GiftsCategoryDetail'
  export default {
    name: 'addGiftsCategory',
    components: { GiftsCategoryDetail }
  }
</script>
<style>
</style>


