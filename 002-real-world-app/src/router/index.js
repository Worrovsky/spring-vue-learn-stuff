import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('@/views/GlobalFeed'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/Register.vue'),
  },
  {
    path: '/signin',
    name: 'signin',
    component: () => import('@/views/SignIn.vue'),
  },
  {
    path: '/userProfile',
    name: 'userProfile',
    component: () => import('@/views/GlobalFeed'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
