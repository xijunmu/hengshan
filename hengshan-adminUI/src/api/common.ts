import request from '@/utils/request'
import * as commonType from '@/api/commonType'

export const login = (data: commonType.LoginUser) => {
  return request.post('/login', data)
}

export const logout = () => {
  return request.get('/logout')
}

export const getImgCode = () => {
  return request.get('/getImgCode')
}

export const getUserList = (data: commonType.PageList) => {
  return request.post('/getUserList', data)
}

export const addUser = (data: commonType.IUser) => {
  return request.post('/addUser', data)
}

export const getUserById = (id: number) => {
  return request.get(`/getUserById?id=${id}`)
}

export const deleteUser = (data: commonType.Ids) => {
  return request.post('/deleteUser', data)
}

export const updateUser = (data: commonType.IUser) => {
  return request.post('/updateUser', data)
}

export const getMenus = () => {
  return request.get('/getMenus')
}

export const getRoleList = (data: commonType.PageList) => {
  return request.post('/getRoleList', data)
}

export const addRole = (data: commonType.IRole) => {
  return request.post('/addRole', data)
}

export const deleteRole = (data: commonType.Ids) => {
  return request.post('/deleteRole', data)
}

export const updateRole = (data: commonType.IRole) => {
  return request.post('/updateRole', data)
}

export const getOrderList = (data: commonType.PageList) => {
  return request.post('/getOrderList', data)
}

export const addOrder = (data: commonType.Order) => {
  return request.post('/addOrder', data)
}

export const deleteOrder = (data: commonType.Ids) => {
  return request.post('/deleteOrder', data)
}

export const updateOrder = (data: commonType.Order) => {
  return request.post('/updateOrder', data)
}

export const getProductList = (data: commonType.PageList) => {
  return request.post('/getProductList', data)
}

export const addProduct = (data: commonType.Product) => {
  return request.post('/addProduct', data)
}

export const deleteProduct = (data: commonType.Ids) => {
  return request.post('/deleteProduct', data)
}

export const updateProduct = (data: commonType.Product) => {
  return request.post('/updateProduct', data)
}