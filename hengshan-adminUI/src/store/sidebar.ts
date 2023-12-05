import { defineStore } from 'pinia'

export const sidebarStore = defineStore('sidebar', {
  state: () => {
    return {
      count: 100,
      isCollapse: false,
      username: localStorage.getItem('user'),
      token: localStorage.getItem('token')
    }
  },
  getters: {},
  actions: {
    handleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    increment () {
      this.count++
    },
    setLoginInfo (username: string, token: string) {
      localStorage.setItem('user', username)
      localStorage.setItem('token', token)
    }
  }
})