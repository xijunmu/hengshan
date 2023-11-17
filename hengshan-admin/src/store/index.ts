import { createStore } from 'vuex'

const store = createStore({
  state () {
    return {
      count: 1,
      isCollapse: false
    }
  },
  mutations: {
    handleCollapse (state) {
      state.isCollapse = !state.isCollapse
    },
    increment (state) {
      state.count++
    }
  }
})

export default store