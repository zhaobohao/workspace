<template> 
  <memberLevel-detail :is-edit='false'></memberLevel-detail>
</template>
<script>
  import MemberLevelDetail from './components/MemberLevelDetail'
  export default {
    name: 'addMemberLevel',
    components: { MemberLevelDetail }
  }
</script>
<style>
</style>


