// import Vue from 'vue'
import { createRouter, createWebHistory } from "vue-router";
// import VueRouter from 'vue-router';
import vuexIndex from '@/store/index.js'


const Index = () => import('@/views/Portal.vue')
const Files = () => import('@/views/Files.vue')
const Albums = () => import('@/views/Albums.vue')
const Collectes = () => import('@/views/Collectes.vue')
const Login = () => import('@/views/Login.vue')


const routes = [
  { path: '/', redirect: '/index' },
  // { path: '/files', component:Files },
  {
    path: '/index',
    component: Index,
    redirect: '/files/%2Froot',
    children: [
      { path: '/files', redirect: '/files/%2Froot' },
      { path: '/files/:path', name: 'files', component: Files },
      { path: '/albums', component: Albums },
      { path: '/collectes', component: Collectes },
    ]
  },
  { path: '/login', component: Login },
]

// Vue.use(VueRouter)

const router = createRouter({
  history: createWebHistory(), // HTML5模式
  routes,
});

// const router = new VueRouter({
//   mode: 'history',
//   base: process.env.BASE_URL,
//   routes
// })

router.beforeEach((to, from, next) => {
  // if(localStorage.userInfo) vuexIndex.state.userInfo=localStorage.userInfo
  if (to.path != '/login' && !vuexIndex.state.userInfo) {
    router.replace('/login')
  }
  next()
})

export default router
