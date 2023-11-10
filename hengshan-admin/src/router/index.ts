import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: async () => await import('../views/Home.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: async () => await import('../views/Login.vue')
    },
    {
      path: '/test',
      name: 'test',
      component: async () => await import('../views/Test.vue')
    }
  ]
})

export default router
