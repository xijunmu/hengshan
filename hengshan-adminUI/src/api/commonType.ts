export interface LoginUser {
  username: string
  password: string
  imgcode: string
}

export interface PageList {
  page: number
  limit: number
  name?: string
  address?: string
  state?: boolean
}

export interface Ids {
  id: number[]
}

export interface IUser {
  id: number
  name: string
  sex: number
  age: number | null
  state: boolean
  date: Date
  address: string
}

export interface IRole {
  id: number
  name?: string
  state: boolean
  menu_id?: number[]
}

export interface IMenu {
  id: number
  pid: number
  title: string
  children: IMenu[]
}

export interface Order {
  id: number
  name: string
  price: number
  phone: string
  address: string
  createTime: Date
}

export interface Product {
  id: number
  name: string
  price: number
  img: string
  desc: string
  createTime: Date
}