import { defineConfig } from 'vite'
import mkcert from 'vite-plugin-mkcert'
import react from '@vitejs/plugin-react-swc'

const env = process.env.VITE_KEYCLOAK_ENDPOINT;
const target = `http://${env}/`;

// https://vitejs.dev/config/
export default defineConfig({
  base: "/",
  server: {
    host: "0.0.0.0",
    port: 5173,
    proxy: {
      "/api": {
        target,
        changeOrigin: true
      },
    }
  },
  plugins: [react(), /* mkcert() */],
})
