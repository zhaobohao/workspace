<template>
  <!--弹出框 -->
  <el-dialog v-el-drag-dialog width="1000px" title="请输入 菜单图标" :visible.sync="iconDialogVisible">
    <div class="icons-container">
      <div class="grid">
        <div v-for="item of svgIcons" :key="item" @click="handleClickAction(item)">
          <el-tooltip placement="top">
            <div slot="content">
              {{ generateIconCode(item) }}
            </div>
            <div class="icon-item">
              <svg-icon :icon-class="item" class-name="disabled" />
              <span>{{ item }}</span>
            </div>
          </el-tooltip>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
<script>
  import svgIcons from './svg-icons'
  import elDragDialog from '@/directive/el-drag-dialog' // base on element-ui
  export default {
    name: 'icons',
    directives: {
      elDragDialog
    },
    data() {
      return {
        svgIcons
      }
    },
    props: {
      iconDialogVisible: {
        type: Boolean,
        default: false
      }
    },
    methods: {
      generateIconCode(symbol) {
        return `
  <svg-icon icon-class="${symbol}" />`
      },
      handleClickAction(item) {
        this.$parent.$parent.$parent.$parent.$refs.dataForm.handleUpdateIcon(item)
        this.$emit('update:iconDialogVisible', false)
      }
    }
  }

</script>

<style lang="scss" scoped>
  .icons-container {
    margin: 10px 10px 0;
    overflow: hidden;

    .grid {
      position: relative;
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
    }

    .icon-item {
      margin: 10px;
      height: 85px;
      text-align: center;
      width: 80px;
      float: left;
      font-size: 30px;
      color: #24292e;
      cursor: pointer;
    }

    span {
      display: block;
      font-size: 16px;
      margin-top: 10px;
    }

    .disabled {
      pointer-events: none;
    }
  }

</style>
