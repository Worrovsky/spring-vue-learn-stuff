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
    path: '/feed',
    name: 'yourFeed',
    component: () => import('@/views/YourFeed'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/Register'),
  },
  {
    path: '/signin',
    name: 'signin',
    component: () => import('@/views/SignIn'),
  },
  {
    path: '/userProfile',
    name: 'userProfile',
    component: () => import('@/views/GlobalFeed'),
  },
  {
    path: '/tags/:slug',
    name: 'tagsFeed',
    component: () => import('@/views/TagsFeed'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
