<template>
  <div class="app-container">
    <el-container>
      <el-aside id="aside-style" width="10%">

        <el-tree :props="treeProps" :data="treeData" :default-expand-all="true" :highlight-current="true"
          :expand-on-click-node="false" node-key="id" @node-click="handleNodeClick">
        </el-tree>

      </el-aside>

      <el-container>
        <el-main style="padding:0px 0px 0px 20px">
          <listTable ref="listTable"></listTable>
          <dataForm ref="dataForm"></dataForm>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
  import {
    initTreeData
  } from '@/utils/index'
  // 调用相应的api文件中的方法，来操纵数据
  import {
    getDeptTree
  } from '@/api/system/dept'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // 引入相应的工具来处理数据转换需求

  // 分页组件
  import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
  import listTable from './components/listTable' // 列表组件
  import dataForm from './components/dataForm' // form表单组件

  export default {
    // TODO:本页面的名称
    name: 'dept',
    components: {
      Pagination,
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
          isLeaf: 'isLeaf',
          children: 'children'
        },
        treeData: []
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
        getDeptTree(
          '000000'
        ).then(response => {
          // console.log(response.data)
          this.treeData = initTreeData(response.data, '部门总览')
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
