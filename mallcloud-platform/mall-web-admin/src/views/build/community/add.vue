<template> 
  <community-detail :is-edit='false'></community-detail>
</template>
<script>
  import CommunityDetail from './components/CommunityDetail'
  export default {
    name: 'addCommunity',
    components: { CommunityDetail }
  }
</script>
<style>
</style>


