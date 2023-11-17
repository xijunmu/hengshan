import request from '@/utils/request'

interface loginUser {
  username: string
  password: string
  imgcode: string
}

interface pageList {
  address: string
  name: string
  pageIndex: number
  pageSize: number
}

export const login = (user: loginUser) => {
  return request.post('/login', user)
}

export const logout = () => {
  return request.get('/logout')
}

export const getImgCode = () => {
  return request.get('/getImgCode')
}

export const getUserList = (data: pageList) => {
  return request.post('/getUserList', data)
}