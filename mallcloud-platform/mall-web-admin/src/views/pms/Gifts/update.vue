<template> 
  <Gifts-detail :is-edit='true'></Gifts-detail>
</template>
<script>
  import GiftsDetail from './components/GiftsDetail'
  export default {
    name: 'updateGifts',
    components: { GiftsDetail }
  }
</script>
<style>
</style>


