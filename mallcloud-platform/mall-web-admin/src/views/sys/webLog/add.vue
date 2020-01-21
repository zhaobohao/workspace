<template> 
  <webLog-detail :is-edit='false'></webLog-detail>
</template>
<script>
  import WebLogDetail from './components/WebLogDetail'
  export default {
    name: 'addWebLog',
    components: { WebLogDetail }
  }
</script>
<style>
</style>


