import react from '@vitejs/plugin-react'
import { defineConfig, loadEnv } from 'vite'

// https://vite.dev/config/
export default defineConfig(({ mode }) => {
  // 프로젝트 루트(../)의 .env 를 읽는다. 접두사 '' → VITE_ 로 시작하지 않는 값도 읽음.
  // (이 값들은 설정 파일에서만 쓰이고 브라우저 번들에는 들어가지 않는다)
  const env = loadEnv(mode, '..', '')
  const apiTarget =
    env.API_PROXY_TARGET || `http://localhost:${env.SERVER_PORT || 8080}`

  return {
    plugins: [react()],
    server: {
      proxy: {
        '/api': {
          target: apiTarget,
          changeOrigin: true,
        },
      },
    },
  }
})
