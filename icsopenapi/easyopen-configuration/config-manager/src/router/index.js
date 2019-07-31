import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/login'
import dashboard from '@/views/dashboard'
import Allscope from '@/views/allscope'
import enterManage from '@/views/enterManage/index'
import roleManage from '@/views/roleManage/index'
import apiManage from '@/views/apiManage/index'
import netManage from '@/views/netManage/index'
import Layout from '@/views/layout/index'

Vue.use(Router)

export default new Router({
// mode: 'history',
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '',
      component: Layout,
      redirect: 'dashboard',
      children: [{
        path: 'dashboard',
        component: dashboard,
        name: 'dashboard',
        meta: { title: '首页', icon: 'dashboard' }
      }]
    },
    {
      path: '/',
      component: Layout,
      redirect: 'noredirect',
      name: 'userManage',
      meta: {
        title: '接口配置',
        icon: 'chart'
      },
      children: [
        { path: 'enterManage/:app', component: enterManage, name: 'enterManage', meta: { title: '接入方管理'}},
        { path: 'roleManage/:app', component: roleManage, name: 'roleManage', meta: { title: '权限管理'}},
        { path: 'apiManage/:app', component: apiManage, name: 'apiManage', meta: { title: '接口管理' }},
        { path: 'netManage/:app', component: netManage, name: 'netManage', meta: { title: '限流设置'}},
      ]
    },
    {
      path: '/',
      component: Layout,
      redirect: 'noredirect',
      name: 'allscope',
      meta: {
        title: '系统菜单',
        icon: 'chart'
      },
      children: [
        { path: 'allscope', component: Allscope, name: 'Allscope', meta: { title: '全局配置', noCache: true }},
      ]
    },
    {
      path: '/404',
      component: ()=>import('@/components/errorPage/404')
    },
     // 输入其他地址跳转到登录页
     {
      path: '*',
      redirect: '/404'
    }
  ]
})
