/* eslint-disable */
<template>
  <el-card v-show="isSearchCardShow" style="margin-bottom: 20px">
    <!--查询条件区域-->
    <div class="filter-container">
      <!--具体的查询条件，使用placeholder来显示标题-->
      <el-input v-model="listQuery.query.title" placeholder="通知标题" style="width: 200px;"
        @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.query.category" placeholder="通知类型" class="filter-item">
        <el-option v-for="item in noticeOptions" :key="parseInt(item.dictKey)" :label="item.dictValue"
          :value="item.dictKey" />
      </el-select>
      <el-date-picker v-model="listQuery.query.releaseTime" type="datetime" placeholder="通知日期" align="right"
        :picker-options="pickerOptions" @keyup.enter.native="handleFilter">
      </el-date-picker>
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
  import listQuery from '@/entitys/notice'
  // 按钮的水波纹
  import waves from '@/directive/waves' // Waves directive
  // model 文件
  export default {
    // TODO:本页面的名称
    name: 'notices-searchCard',
    directives: {
      waves
    },
    props: {
      isSearchCardShow: {
        type: Boolean,
        default: false
      },
      noticeOptions: {
        type: Array,
        default: function () {
          return []
        }
      }
    },
    data() {
      // 初始化整个页面用到的数据
      return {
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
        },
        rangeDate: undefined,
        listQuery: listQuery()
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
        this.$parent.$refs.listTable.getList(this.listQuery)
      }
    }
  }

</script>
