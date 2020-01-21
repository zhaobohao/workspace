<template> 
  <school-detail :is-edit='false'></school-detail>
</template>
<script>
  import SchoolDetail from './components/SchoolDetail'
  export default {
    name: 'addSchool',
    components: { SchoolDetail }
  }
</script>
<style>
</style>


