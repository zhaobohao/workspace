<template> 
  <repair-detail :is-edit='false'></repair-detail>
</template>
<script>
  import RepairDetail from './components/RepairDetail'
  export default {
    name: 'addRepair',
    components: { RepairDetail }
  }
</script>
<style>
</style>


