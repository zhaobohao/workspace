<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  html,body{height: 100%}
  #app {
    height: 100%;
  }
  a{text-decoration: none;}
  .el-button{
    margin-bottom: 0
  }
</style>
