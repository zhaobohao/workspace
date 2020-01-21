<template>
  <div class="menu-wrapper">
    <template v-for="item in routes" v-if="!item.checked&&item.children">

      <router-link v-if="hasOneShowingChildren(item.children) && !item.children[0].children" :to="item.meta.url+'/'+item.children[0].meta.url"
                   :key="item.children[0].name">
        <el-menu-item :index="item.meta.url+'/'+item.children[0].meta.url" :class="{'submenu-title-noDropdown':!isNest}">
          <svg-icon v-if="item.children[0].meta&&item.children[0].meta.icon" :icon-class="item.children[0].meta.icon"></svg-icon>
          <span v-if="item.children[0].meta&&item.children[0].title" slot="title">{{item.children[0].title}}</span>
        </el-menu-item>
      </router-link>

      <el-submenu v-else :index="item.meta.url" :key="item.meta.url">
        <template slot="title">
          <svg-icon v-if="item.meta&&item.meta.icon" :icon-class="item.meta.icon"></svg-icon>
          <span v-if="item.meta&&item.title" slot="title">{{item.title}}</span>
        </template>

        <template v-for="child in item.children" v-if="!child.checked">
          <sidebar-item :is-nest="true" class="nest-menu" v-if="child.children&&child.children.length>0" :routes="[child]" :key="child.meta.url"></sidebar-item>

          <router-link v-else :to="item.meta.url+'/'+child.meta.url" :key="child.meta.url">
            <el-menu-item :index="item.meta.url+'/'+child.meta.url">
              <svg-icon v-if="child.meta&&child.meta.icon" :icon-class="child.meta.icon"></svg-icon>
              <span v-if="child.meta&&child.title" slot="title">{{child.title}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>

    </template>
  </div>
</template>

<script>
  export default {
    name: 'SidebarItem',
    props: {
      routes: {
        type: Array
      },
      isNest: {
        type: Boolean,
        default: false
      }
    },
    methods: {
      hasOneShowingChildren(children) {
        const showingChildren = children.filter(item => {
          return !item.checked
        })
        if (showingChildren.length === 1) {
          return true
        }
        return false
      }
    }
  }
</script>
