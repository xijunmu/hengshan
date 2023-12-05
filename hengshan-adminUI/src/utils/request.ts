import axios from 'axios'
import { sidebarStore } from '@/store/sidebar'

const request = axios.create({
  baseURL: '/',
  timeout: 5000
})

request.interceptors.request.use(
  (config) => {
    const store = sidebarStore()
    if (store.token) {
      config.headers.Authorization = `Bearer ${store.token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

request.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    return Promise.reject(error)
  }
)

export default request