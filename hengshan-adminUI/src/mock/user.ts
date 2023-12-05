import type { MockMethod } from 'vite-plugin-mock'
import Mock from 'mockjs'

Mock.Random.extend({
  phone () {
    const phonePrefixs = ['135', '189', '136'] // 自己随便写前缀
    return this.pick(phonePrefixs) + Mock.mock(/\d{8}/) // 使用pick 随机返回前缀
  }
})

const list = Mock.mock({
  'data|50': [{ // 生成50条数据
    'id|+1': 1, // id会自增
    name: '@cname', // 随机生成姓名 --中文名
    sex: '@integer(0, 1)', // 性别一般是用数字表示 1男生 0女生
    phone: Mock.Random.phone(), // 拓展mockjs 生成随机的手机号码
    email: '@email', // 随机生成邮箱地址
    'age|18-60': 18, // 随机生成年龄
    address: '@county(true)', // 随机生成地址
    state: '@boolean', // 随机生成布尔值 表示账户状态
    thumb: '@image( 25x25, #FF6600, #fff, png, 头像 )', // 随机生成头像
    date: '@date(yyyy-MM-dd)',
    createTime: '@datetime(yyyy-MM-dd HH:mm:ss)', // 随机生成创建时间
    role: '@pick(["管理员", "普通用户", "开发用户", "测试用户"])', // 随机生成角色
    ip: '@ip'// 模拟ip地址
  }]
})

export default [
  {
    url: '/getUserList',
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
    url: '/deleteUser',
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
    url: '/updateUser',
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
    url: '/addUser',
    method: 'post',
    response: ({ body }: any) => {
      list.data.unshift(body)
      return { code: 200, data: '添加成功' }
    }
  },
  {
    url: '/getUserById',
    method: 'get',
    response: (data: any) => {
      // console.log(data)
      const id = data.query.id
      return { code: 200, data: list.data.find((item: any) => item.id === Number(id)) }
    }
  }
] as MockMethod[]
