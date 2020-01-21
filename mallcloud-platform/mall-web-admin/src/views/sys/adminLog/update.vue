<template> 
  <adminLog-detail :is-edit='true'></adminLog-detail>
</template>
<script>
  import AdminLogDetail from './components/AdminLogDetail'
  export default {
    name: 'updateAdminLog',
    components: { AdminLogDetail }
  }
</script>
<style>
</style>


