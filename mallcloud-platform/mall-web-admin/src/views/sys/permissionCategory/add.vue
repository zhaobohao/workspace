<template> 
  <permissionCategory-detail :is-edit='false'></permissionCategory-detail>
</template>
<script>
  import PermissionCategoryDetail from './components/PermissionCategoryDetail'
  export default {
    name: 'addPermissionCategory',
    components: { PermissionCategoryDetail }
  }
</script>
<style>
</style>


