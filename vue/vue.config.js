const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer:{
    proxy:{
      '/api':{
        target: 'http://localhost:8081',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/api': ''}
      },
      '/photo':{
        target: 'https://img.kusu.icu/api/v1',// 代理目标的基础路径
        changeOrigin: true,
        pathRewrite: {'^/photo': ''}
      }
    }
  }
})
