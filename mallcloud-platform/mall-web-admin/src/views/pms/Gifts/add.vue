<template> 
  <Gifts-detail :is-edit='false'></Gifts-detail>
</template>
<script>
  import GiftsDetail from './components/GiftsDetail'
  export default {
    name: 'addGifts',
    components: { GiftsDetail }
  }
</script>
<style>
</style>


