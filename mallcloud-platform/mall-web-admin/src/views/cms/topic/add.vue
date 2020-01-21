<template> 
  <topic-detail :is-edit='false'></topic-detail>
</template>
<script>
  import TopicDetail from './components/TopicDetail'
  export default {
    name: 'addTopic',
    components: { TopicDetail }
  }
</script>
<style>
</style>


