<template> 
  <room-detail :is-edit='true'></room-detail>
</template>
<script>
  import RoomDetail from './components/RoomDetail'
  export default {
    name: 'updateRoom',
    components: { RoomDetail }
  }
</script>
<style>
</style>


