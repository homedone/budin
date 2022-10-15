import { createApp } from 'vue'
import App from './App.vue'
import router from './plugins/router.js'
import { network } from './plugins/network';
import './plugins/element.js'
import './assets/main.css'

new Vue({
    router,
    render: h => h(App)
  }).$mount('#app')
// createApp(App).mount('#app')
