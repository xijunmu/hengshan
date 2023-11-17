import type { MockMethod } from 'vite-plugin-mock'
import Mock from 'mockjs'

export default [
  {
    url: '/login',
    method: 'post',
    response: ({ body }: any) => {
      // 获取请求体携带过来的用户名与密码
      const { username, password } = body
      console.log(username, password)
      return { code: 200, msg: '成功', data: { id: '1', token: Mock.Random.guid() } }
    }
  },
  {
    url: '/logout',
    method: 'get',
    response: () => {
      return { code: 200, msg: '成功' }
    }
  },
  {
    url: '/getImgCode',
    method: 'get',
    response: () => {
      const code = Mock.mock(/\d{4}/)
      const imgcode = Mock.mock(
        Mock.Random.image('80x30', '#f1f2f5', code)
      )
      return imgcode
    }
  }
] as MockMethod[]
