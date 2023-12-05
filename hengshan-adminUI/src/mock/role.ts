import type { MockMethod } from 'vite-plugin-mock'
import Mock from 'mockjs'

const menus: any = [
  { id: 1, pid: 0, title: '主页', children: [] },
  {
    id: 2,
    pid: 0,
    title: '商品管理',
    children: [
      { id: 21, pid: 2, title: '商品列表', children: [] },
      { id: 22, pid: 2, title: '商品分类', children: [] },
      { id: 23, pid: 2, title: '商品规格', children: [] }
    ]
  },
  {
    id: 3,
    pid: 0,
    title: '权限管理',
    children: [
      { id: 31, pid: 3, title: '角色', children: [] },
      { id: 32, pid: 3, title: '用户', children: [] }
    ]
  }
]

const ruleId = [] as number[]
menus.forEach((item: any) => {
  ruleId.push(item.id)
  if ('children' in item) {
    item.children.forEach((item1: any) => {
      ruleId.push(item1.id)
    })
  }
})

const list = Mock.mock({
  'data|4': [{
    'id|+1': 1, // id会自增
    'name|+1': ['管理员', '普通用户', '开发用户', '测试用户'], // 生成角色
    state: '@boolean', // 随机生成布尔值 表示账户状态
    menu_id: '@shuffle(ruleId, 1, 8)', // 随机获取权限id
    createTime: '@datetime(yyyy-MM-dd HH:mm:ss)' // 随机生成创建时间
  }]
})

export default [
  {
    url: '/getMenus',
    method: 'get',
    response: () => {
      return { code: 200, menus }
    }
  },
  {
    url: '/getRoleList',
    method: 'post',
    response: ({ body }: any) => {
      const { page, limit, name } = body
      // console.log(body) // 会在终端打印，不在浏览器打印
      let filterList = list.data
      if (name && name.trim().length !== 0) {
        filterList = list.data.filter((item: any) => item.name.includes(name))
      }
      const pageList = filterList.slice(limit * (page - 1), limit * page)
      return { code: 200, list: pageList, pageTotal: filterList.length }
    }
  },
  {
    url: '/deleteRole',
    method: 'post',
    response: ({ body }: any) => {
      const { id } = body
      for (let i = 0; i < id.length; i++) {
        const index = list.data.findIndex((item: any) => item.id === id[i])
        list.data.splice(index, 1)
      }
      return { code: 200, data: '操作成功' }
    }
  },
  {
    url: '/updateRole',
    method: 'post',
    response: ({ body }: any) => {
      const target = list.data.find((item: any) => item.id === body.id)
      for (const key in body) {
        target[key] = body[key]
      }
      return { code: 200, data: target }
    }
  },
  {
    url: '/addRole',
    method: 'post',
    response: ({ body }: any) => {
      list.data.unshift(body)
      return { code: 200, data: '添加成功' }
    }
  }
] as MockMethod[]
