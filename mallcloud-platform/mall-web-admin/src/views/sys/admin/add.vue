<template> 
  <admin-detail :is-edit='false'></admin-detail>
</template>
<script>
  import AdminDetail from './components/AdminDetail'
  export default {
    name: 'addAdmin',
    components: { AdminDetail }
  }
</script>
<style>
</style>


