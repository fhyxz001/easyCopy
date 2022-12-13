const { defineConfig } = require('@vue/cli-service')
const serverPath = 'http://localhost:8081' // 开发环境
// const serverPath = 'http://47.94.164.14:8081' // 正式环境
module.exports = defineConfig({
  transpileDependencies: true,
    lintOnSave: false,
    devServer: {
        port: 8100,
        proxy: {
            '/api': {
                target: serverPath,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
})
