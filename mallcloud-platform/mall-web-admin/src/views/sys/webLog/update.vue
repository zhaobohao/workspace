<template> 
  <webLog-detail :is-edit='true'></webLog-detail>
</template>
<script>
  import WebLogDetail from './components/WebLogDetail'
  export default {
    name: 'updateWebLog',
    components: { WebLogDetail }
  }
</script>
<style>
</style>


