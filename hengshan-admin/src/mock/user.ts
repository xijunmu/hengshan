import type { MockMethod } from 'vite-plugin-mock'
import Mock from 'mockjs'

Mock.Random.extend({
  phone () {
    const phonePrefixs = ['135', '189', '136'] // 自己随便写前缀
    return this.pick(phonePrefixs) + Mock.mock(/\d{8}/) // 使用pick 随机返回前缀
  }
})

const userList = Mock.mock({
  'data|50': [{ // 生成10条数据
    'id|+1': 1, // id会自增
    name: '@cname', // 随机生成姓名 --中文名
    sex: '@integer(0, 1)', // 性别一般是用数字表示 1男生 0女生
    phone: Mock.Random.phone(), // 拓展mockjs 生成随机的手机号码
    email: '@email', // 随机生成邮箱地址
    'age|18-60': 18, // 随机生成年龄
    address: '@county(true)', // 随机生成地址
    state: '@boolean', // 随机生成布尔值 表示账户状态
    thumb: '@image( 30x30, #FF6600, #fff, png, 头像 )', // 随机生成头像
    date: '@date(yyyy-MM-dd)',
    createTime: '@datetime(yyyy-MM-dd HH:mm:ss)', // 随机生成创建时间
    role: '@pick(["董事长", "总监", "经理", "组长"])', // 随机生成角色
    ip: '@ip'// 模拟ip地址
  }]
})

export default [
  {
    url: '/getUserList',
    method: 'post',
    response: ({ body }: any) => {
      const { pageIndex, pageSize, name } = body
      let filterList = userList.data
      if (name && name.trim().length !== 0) {
        filterList = userList.data.filter((user: { name: string | any[] }) => user.name.includes(name))
      }
      const pageList = filterList.slice(pageSize * (pageIndex - 1), pageSize * pageIndex)
      return { code: 200, list: pageList, pageTotal: filterList.length }
    }
  }
] as MockMethod[]
