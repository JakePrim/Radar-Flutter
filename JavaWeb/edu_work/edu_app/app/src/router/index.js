import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '@/components/Login.vue'
import Index from '@/components/Index.vue'
import Course from '@/components/Course.vue'

Vue.use(VueRouter)

const routes = [
  //访问/也跳转到login 重定向到login
  {
    path: '/',
    // redirect: 'login',
    redirect: 'index',
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
  },
  {
    path: '/index',
    name: 'index',
    component: Index,
    //添加子路由
    children: [
      //课程信息子路由
      {
        path: '/course',
        name: 'course',
        component: Course,
      },
    ],
  },
]

const router = new VueRouter({
  routes,
})

export default router
