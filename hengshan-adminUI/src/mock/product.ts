import type { MockMethod } from 'vite-plugin-mock'

const product: any = [
  { id: 1, name: '手机1', price: 1999, img: '/src/assets/shouji1.jpg', desc: '<p>描述信息</p>' },
  { id: 2, name: '手机2', price: 2999, img: '/src/assets/shouji2.jpg', desc: '<p>描述信息</p>' },
  { id: 3, name: '手机3', price: 3999, img: '/src/assets/shouji3.jpg', desc: '<p>描述信息</p>' },
  { id: 4, name: '平板1', price: 2999, img: '/src/assets/pingban1.jpg', desc: '<p>描述信息</p>' },
  { id: 5, name: '电脑1', price: 4999, img: '/src/assets/diannao1.jpg', desc: '<p>描述信息</p>' }
]

export default [
  {
    url: '/getProductList',
    method: 'post',
    response: ({ body }: any) => {
      const { page, limit, name } = body
      // console.log(body) // 会在终端打印，不在浏览器打印
      let filterList = product
      if (name && name.trim().length !== 0) {
        filterList = product.filter((item: any) => item.name.includes(name))
      }
      const pageList = filterList.slice(limit * (page - 1), limit * page)
      return { code: 200, list: pageList, pageTotal: filterList.length }
    }
  },
  {
    url: '/deleteProduct',
    method: 'post',
    response: ({ body }: any) => {
      const { id } = body
      for (let i = 0; i < id.length; i++) {
        const index = product.findIndex((item: any) => item.id === id[i])
        product.splice(index, 1)
      }
      return { code: 200, data: '操作成功' }
    }
  },
  {
    url: '/updateProduct',
    method: 'post',
    response: ({ body }: any) => {
      const target = product.find((item: any) => item.id === body.id)
      for (const key in body) {
        target[key] = body[key]
      }
      return { code: 200, data: target }
    }
  },
  {
    url: '/addProduct',
    method: 'post',
    response: ({ body }: any) => {
      product.unshift(body)
      return { code: 200, data: '添加成功' }
    }
  },
  {
    url: '/getProductById',
    method: 'get',
    response: (data: any) => {
      // console.log(data)
      const id = data.query.id
      return { code: 200, data: product.find((item: any) => item.id === Number(id)) }
    }
  }
] as MockMethod[]
