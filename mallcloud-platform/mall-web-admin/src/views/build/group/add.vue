<template> 
  <group-detail :is-edit='false'></group-detail>
</template>
<script>
  import GroupDetail from './components/GroupDetail'
  export default {
    name: 'addGroup',
    components: { GroupDetail }
  }
</script>
<style>
</style>


