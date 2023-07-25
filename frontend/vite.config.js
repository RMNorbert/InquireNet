import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  build: {
    outDir: '../src/main/resources/static/dist',
    emptyOutDir: true// Output directory within the backend's static resources
  },
  plugins: [react()],
  server: {
    proxy: {
      '/': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => {
            return path;
        }
      }
    }
  },
  css: {
    modules: true,
  }
})
