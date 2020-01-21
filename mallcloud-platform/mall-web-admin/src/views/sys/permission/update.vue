<template> 
  <permission-detail :is-edit='true'></permission-detail>
</template>
<script>
  import PermissionDetail from './components/PermisiionDetail'
  export default {
    name: 'updatePermission',
    components: { PermissionDetail }
  }
</script>
<style>
</style>


