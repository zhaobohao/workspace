<template>
  <scroll-bar>
    <el-menu
      mode="vertical"
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
    >
      <sidebar-item :routes="menus"></sidebar-item>
    </el-menu>
  </scroll-bar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import ScrollBar from '@/components/ScrollBar'
import {findPermissions} from '@/api/permission'

export default {
  data() {
    return {
      menus:[]
    }
  },

  created() {
    this.getData();
  },
  methods: {
    getData() {
      findPermissions(this.findPermissions).then(response => {
        if(response.code == 200)
         {
        this.menus = response.data;

        }
    });
    }
  },
  components: { SidebarItem, ScrollBar },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),

    isCollapse() {
      return !this.sidebar.opened
    }
  }
}
</script>
