/* eslint-disable */
<template>
  <el-card v-show="isSearchCardShow" style="margin-bottom: 20px">
    <!--查询条件区域-->
    <div class="filter-container">
      <!--具体的查询条件，使用placeholder来显示标题-->
        <el-input-number v-model="temp.id" placeholder="表id" :precision="0" :step="1" :max="100" style="width: 305px;" />
        <el-input-number v-model="temp.groupId" placeholder="drools_group表id" :precision="0" :step="1" :max="100" style="width: 305px;" />
      <el-input v-model="listQuery.query.remarks" placeholder="备注信息" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.ruleBody" placeholder="规则程序" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.tenantId" placeholder="租户ID" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-button v-waves type="primary" style="margin:0 0 0 20px;" round icon="el-icon-search" @click="handleFilter">{{
        $t('table.search') }}</el-button>
      <el-button v-waves style="margin: 10px;" icon="el-icon-delete" round @click="resetListQuery()">{{
        $t('table.reset')
        }}</el-button>
    </div>
  </el-card>
</template>
<script>
  // 调用相应的api文件中的方法，来操纵数据
  import listQuery from '@/entitys/droolsruls'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // model 文件
  export default {
    // TODO:本页面的名称
    name: 'droolsruls-searchCard',
    directives: {
      waves
    },
    props: {
      isSearchCardShow: {
        type: Boolean,
        default: false
      }
    },
    data() {
      // 初始化整个页面用到的数据
      return {
        rangeDate: undefined,
        listQuery: listQuery(),
        pickerOptions: {
              shortcuts: [{
                  text: '今天',
                  onClick(picker) {
                      picker.$emit('pick', new Date())
                  }
              }, {
                  text: '昨天',
                  onClick(picker) {
                      const date = new Date()
                      date.setTime(date.getTime() - 3600 * 1000 * 24)
                      picker.$emit('pick', date)
                  }
              }, {
                  text: '一周前',
                  onClick(picker) {
                      const date = new Date()
                      date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
                      picker.$emit('pick', date)
                  }
              }]
          }
      }
    },
    // 初始化所有的数据
    created() {},
    methods: {
      resetListQuery() {
        this.listQuery = listQuery()
        this.$parent.$refs.listTable.listQuery = this.listQuery // 修改listTable里的listQuery
            },
      handleFilter() {
        this.listQuery.current = 1
                  this.$parent.$refs.listTable.getList(this.listQuery) // 查询列表页面数据
            }
    }
  }

</script>
