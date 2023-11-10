import { defineStore } from 'pinia'

export const testStore = defineStore('test', {
  state: () => {
    return {
      aa: 122,
      collapse: false
    }
  },
  getters: {},
  actions: {
    handleCollapse () {
      this.collapse = !this.collapse
    }
  }
})