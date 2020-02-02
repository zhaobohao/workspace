<template> 
  <groupMember-detail :is-edit='false'></groupMember-detail>
</template>
<script>
  import GroupMemberDetail from './components/GroupMemberDetail'
  export default {
    name: 'addGroupMember',
    components: { GroupMemberDetail }
  }
</script>
<style>
</style>


