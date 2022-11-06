import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: 'localhost',
    port: 3010,
    // 是否开启 https
    https: false,
    proxy:{
      // 直接走网关
      '/api/': {
          target: 'http://localhost:3000',
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }

      // '/api/file': {
      //   target: 'http://localhost:3020',
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/api/, '')
      // },
      // '/api/center': {
      //   target: 'http://localhost:3030',
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/api/, '')
      // } 
    },
   
  }
 
})
