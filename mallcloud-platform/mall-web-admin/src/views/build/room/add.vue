<template> 
  <room-detail :is-edit='false'></room-detail>
</template>
<script>
  import RoomDetail from './components/RoomDetail'
  export default {
    name: 'addRoom',
    components: { RoomDetail }
  }
</script>
<style>
</style>


