/* eslint-disable */
<template>
  <el-card v-show="isSearchCardShow" style="margin-bottom: 20px">
    <!--查询条件区域-->
    <div class="filter-container">
      <!--具体的查询条件，使用placeholder来显示标题-->
      <el-input-number v-model="temp.actPrmCateId" placeholder="表id编号" :precision="0" :step="1" :max="100"
        style="width: 305px;" />
      <el-input v-model="listQuery.query.apCode" placeholder="所属活动类别编号" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.code" placeholder="类别项类别编号" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.name" placeholder="类别项类别名称" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.apcDesc" placeholder="备注" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.reserveCokumn1" placeholder="预留字段1" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.reserveCokumn2" placeholder="预留字段2" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.query.reserveCokumn3" placeholder="预留字段3" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-input-number v-model="temp.apOrder" placeholder="显示顺序" :precision="0" :step="1" :max="100"
        style="width: 305px;" />
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
  import listQuery from '@/entitys/actprmcate'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // model 文件
  export default {
    // TODO:本页面的名称
    name: 'actprmcate-searchCard',
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
