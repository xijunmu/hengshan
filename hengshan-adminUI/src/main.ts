import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { createPinia } from 'pinia'
import 'element-plus/dist/index.css'
import '@/styles/index.scss'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
// 注册elementplus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(createPinia())
app.use(router)
app.use(store)
app.mount('#app')
