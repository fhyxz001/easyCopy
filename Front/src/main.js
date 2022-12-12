import { createApp } from 'vue'
import App from './App.vue'
import  'element-plus/dist/index.css'
import ElementPlus from 'element-plus'
// 图标和组件需要分开引入
const app = createApp(App)
app.use(ElementPlus)
app.mount('#app')

