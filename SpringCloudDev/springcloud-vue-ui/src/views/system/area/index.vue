<template>
  <div class="app-container">
    <el-container>
      <el-aside id="aside-style" width="10%">
        <el-tree :props="treeProps" :data="treeData" :default-expand-all="false" :highlight-current="true"
          :expand-on-click-node="true" accordion node-key="id" @node-click="handleNodeClick">
        </el-tree>
      </el-aside>
      <el-container>
        <el-main style="padding:0px 0px 0px 20px">
          <search-card ref="searchCard" :areatype-options="areatypeOptions" :is-search-card-show="isSearchCardShow" />
          <listTable ref="listTable" :areatype-options="areatypeOptions" :is-search-card-show.sync="isSearchCardShow" />
          <dataForm ref="dataForm" :areatype-options="areatypeOptions" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import {
    getareaTree
  } from '@/api/system/area'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求
  import {
    initTreeData
  } from '@/utils/index'
  // 分页组件
  import searchCard from './components/searchCard' // 搜索组件
  import listTable from './components/listTable' // 列表组件
  import dataForm from './components/dataForm' // form表单组件
  import {
    getDictionary
  }
  from '@/api/system/dict'
  export default {
    // TODO:本页面的名称
    name: 'areas',
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
    props: {

    },
    data() {
      // 初始化整个页面用到的数据
      return {
        treeProps: {
          label: 'title',
          leaf: 'isLeaf',
          children: 'children'
        },
        treeData: [],
        areatypeOptions: [],
        isSearchCardShow: false
      }
    },
    watch: {

    },
    // 初始化所有的数据
    created() {
      this.initTreeData()
    },
    mounted() {},
    methods: {
      handleNodeClick(data) {
        // console.log(data)
        this.$refs.listTable.resetListQuery()
        this.$refs.listTable.listQuery.query.parentId_equal = data.id
        this.$refs.listTable.getList()
      },
      initTreeData() {
        // 第一层结点
        getareaTree().then(response => {
          // console.log(response.data)
          this.treeData = initTreeData(response.data, '区域总览')
        })
        getDictionary('area_type').then(response => {
          this.areatypeOptions = response.data
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
