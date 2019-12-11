<template>
  <div class="dashboard-container">

  </div>
</template>

<script>
  import {
    mapGetters
  } from 'vuex'

  export default {
    name: 'sss',
    components: {},
    data() {
      return {
        currentRole: 'adminDashboard'
      }
    },
    computed: {
      ...mapGetters([
        'roles'
      ])
    },
    created() {
      if (!this.roles.includes('admin')) {
        this.currentRole = 'editorDashboard'
      }
    }
  }

</script>

<style>
</style>
