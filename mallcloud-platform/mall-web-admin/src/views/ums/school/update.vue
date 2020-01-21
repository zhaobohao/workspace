<template> 
  <school-detail :is-edit='true'></school-detail>
</template>
<script>
  import SchoolDetail from './components/SchoolDetail'
  export default {
    name: 'updateSchool',
    components: { SchoolDetail }
  }
</script>
<style>
</style>


