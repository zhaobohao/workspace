import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/pages/layout/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: 'config',
      children: [{
        path:'config',
        component: () => import('@/pages/config/index'),
        name: 'config',
      },
      {
        path:'getModule/:orderName',
        component: () => import('@/pages/module/getModule'),
        name: 'getModule',
      },
    ]
    },
    {
      path:'/login',
      component: () => import('@/pages/login/index'),
      name:'login'
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
