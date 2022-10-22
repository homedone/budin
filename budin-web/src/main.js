import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import store from './store/index'
import  {request}  from './plugins/network';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'
// import axios from 'axios';
// axios.defaults.baseURL = '/api'

const app = createApp(App)
app.config.globalProperties.$axios = request
app.use(router)
app.use(store)
app.use(ElementPlus)
app.mount('#app')
// import './assets/main.css'

// new Vue({
//     router,
//     render: h => h(App)
//   }).$mount('#app')
// createApp(App).mount('#app')
