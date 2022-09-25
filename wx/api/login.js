const {request} = require("../utils/request.js")
  module.exports = {
    // 登录
    login: (params) => request('user/wx_login', 'GET', params),
    // 注册
    register: (params) => request('user/register', 'GET', params),
    // 获取验证码
    getCode: (params) => request('user/code', 'GET', params)
  }