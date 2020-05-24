/** eslint-disable */
<template>
  <div class="app-container">

    <search-card ref="searchCard" :is-search-card-show="isSearchCardShow" :post-options="postOptions" />
    <listTable ref="listTable" :is-search-card-show.sync="isSearchCardShow" :post-options="postOptions" />
    <dataForm ref="dataForm" :post-options="postOptions" />
  </div>
</template>
<script>
  import {
    getDictionary
  }
  from '@/api/system/dict'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  // 分页组件
  import searchCard from './components/searchCard' // 搜索组件
  import listTable from './components/listTable' // 列表组件
  import dataForm from './components/dataForm' // form表单组件
  export default {
    // TODO:本页面的名称
    name: 'post',
    components: {
      searchCard,
      listTable,
      dataForm
    },
    directives: {
      waves
    },
    filters: {

    },
    data() {
      // 初始化整个页面用到的数据
      return {
        isSearchCardShow: false,
        postOptions: []
      }
    },
    watch: {

    },
    // 初始化所有的数据
    created() {
      this.initTreeData()
    },
    mounted() {

    },
    methods: {
      initTreeData() {
        getDictionary('post_category').then(response => {
          this.postOptions = response.data
        })
      }

    }
  }

</script>
<style scoped>
  #aside-style {
    min-width: 160px;
    max-width: 280px;
    background: #ffffff;
    padding: 0;
  }

</style>
