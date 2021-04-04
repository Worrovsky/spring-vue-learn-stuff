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
    path: '/profiles/:slug',
    name: 'userProfile',
    component: () => import('@/views/UserProfile'),
  },
  {
    path: '/profiles/:slug/favorites',
    name: 'userProfileFavorites',
    component: () => import('@/views/UserProfile'),
  },
  {
    path: '/tags/:slug',
    name: 'tagsFeed',
    component: () => import('@/views/TagsFeed'),
  },
  {
    path: '/articles/:slug',
    name: 'article',
    component: () => import('@/views/Article'),
  },
  {
    path: '/settings',
    name: 'settings',
    component: () => import('@/views/Settings'),
  },
  {
    path: '/articles/:slug/edit',
    name: 'editArticle',
    component: () => import('@/views/EditArticle'),
  },
  {
    path: '/articles/new',
    name: 'createArticle',
    component: () => import('@/views/CreateArticle'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
})

export default router
