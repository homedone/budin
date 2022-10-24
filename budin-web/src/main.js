import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index'
import  {request}  from './plugins/network';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'

const app = createApp(App)
app.config.globalProperties.$request = request
app.use(router)
app.use(store)
app.use(ElementPlus)
app.mount('#app')
import '@/assets/css/iconfont.css';
// import './assets/main.css'

// new Vue({
//     router,
//     render: h => h(App)
//   }).$mount('#app')
// createApp(App).mount('#app')
