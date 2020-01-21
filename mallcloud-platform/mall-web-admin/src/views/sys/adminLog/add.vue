<template> 
  <adminLog-detail :is-edit='false'></adminLog-detail>
</template>
<script>
  import AdminLogDetail from './components/AdminLogDetail'
  export default {
    name: 'addAdminLog',
    components: { AdminLogDetail }
  }
</script>
<style>
</style>


