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
    price: '@float(1, 1000, 2, 2)', // 随机生成价格
    phone: Mock.Random.phone(), // 拓展mockjs 生成随机的手机号码
    address: '@county(true)', // 随机生成地址
    product: '@pick(["奶粉", "衣服", "电脑", "食品"])',
    createTime: '@datetime(yyyy-MM-dd HH:mm:ss)'
  }]
})

export default [
  {
    url: '/getOrderList',
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
    url: '/deleteOrder',
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
    url: '/updateOrder',
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
    url: '/addOrder',
    method: 'post',
    response: ({ body }: any) => {
      list.data.unshift(body)
      return { code: 200, data: '添加成功' }
    }
  }
] as MockMethod[]
