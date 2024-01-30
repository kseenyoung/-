import { fileURLToPath, URL } from 'node:url';
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import fs from 'fs';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  // SCSS 전역 사용
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: '@import "@/assets/common.scss";',
      },
    },
  },
  // server: {
  //   https: {
  //     // key: fs.readFileSync('/etc/letsencrypt/live/capstone-6.shop/privkey.pem'),
  //     // cert: fs.readFileSync('/etc/letsencrypt/live/capstone-6.shop/cert.pem'),
  //     // ca: fs.readFileSync('/etc/letsencrypt/live/capstone-6.shop/chain.pem'),
  //   },
  //   // hmr: true,
  //   host: '0.0.0.0',
  // },

  server: {
    https: {
      key: fs.readFileSync('./localhost-key.pem'),
      cert: fs.readFileSync('./localhost.pem'),
    },
  },
});
