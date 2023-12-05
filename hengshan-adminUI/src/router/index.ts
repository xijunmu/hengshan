import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import nprogress from 'nprogress'
import 'nprogress/nprogress.css'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('@/views/AppLayout.vue'),
    redirect: '/home',
    meta: { requiresAuth: true },
    children: [
      {
        path: '/home',
        name: 'home',
        meta: { title: '', icon: '', requiresAuth: true },
        component: () => import('@/views/Home.vue')
      },
      {
        path: '/product',
        name: 'product',
        meta: { title: '商品管理', icon: '' },
        children: [
          {
            path: 'list',
            name: 'product-list',
            meta: { title: '商品列表', icon: '' },
            component: () => import('@/views/ProductList.vue')
          },
          {
            path: 'info',
            name: 'product-info',
            meta: { title: '商品信息', icon: '' },
            component: () => import('@/views/ProductInfo.vue')
          },
          {
            path: 'attr',
            name: 'product-attr',
            meta: { title: '商品规格', icon: '' },
            component: () => import('@/views/ProductAttr.vue')
          },
          {
            path: 'reply',
            name: 'product-reply',
            meta: { title: '商品评论', icon: '' },
            component: () => import('@/views/ProductReply.vue')
          }
        ]
      },
      {
        path: '/order',
        name: 'order',
        meta: { title: '订单管理', icon: '' },
        children: [
          {
            path: 'list',
            name: 'order-list',
            meta: { title: '订单列表', icon: '' },
            component: () => import('@/views/Order.vue')
          },
          {
            path: 'offline',
            name: 'order-offline',
            meta: { title: '离线订单', icon: '' },
            component: () => import('@/views/Order.vue')
          }
        ]
      },
      {
        path: '/media',
        name: 'media',
        meta: { title: '媒体管理', icon: '' },
        component: () => import('@/views/Media.vue')
      },
      {
        path: '/permission',
        name: 'permission',
        meta: { title: '权限管理', icon: '' },
        children: [
          {
            path: 'role',
            name: 'permission-role',
            meta: { title: '角色', icon: '' },
            component: () => import('@/views/Role.vue')
          },
          {
            path: 'admin',
            name: 'permission-admin',
            meta: { title: '用户', icon: '' },
            component: () => import('@/views/User.vue')
          },
          {
            path: 'rule',
            name: 'permission-rule',
            meta: { title: '权限规则', icon: '' },
            component: () => import('@/views/Rule.vue')
          }
        ]
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    meta: { title: '退出', icon: '' },
    component: () => import('@/views/Login.vue')
  },
  {
    path: '/test',
    name: 'test',
    component: () => import('@/views/Test.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach(() => {
  nprogress.start()
})

router.afterEach(() => {
  nprogress.done()
})

export default router
