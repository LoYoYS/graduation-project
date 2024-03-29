const {request} = require("../utils/request.js")
module.exports={
    // 登录
    login: (params) => request('user/wx_login', 'POST', params),
    // 刷新信息
    refresh: (params) => request('user/refresh', 'POST', params),
    // 身份认证
    indentify: (params) => request('user/identify', 'POST', params),
    // 解除绑定
    unbind: (params) => request('user/unbind', 'POST', params),
    // 获取个人信息
    getInfo: (params) => request('user/getInfo', 'POST', params,{}),
    //获取首页公告列表 
    getNotices:()=>request('common/getNotices','GET'),
    //获取全部公告 
    getList:()=>request('common/getList','GET'),
    // 获取单个公告
    getNotice:(id)=>request('common/getNotice','GET',{id:id}),
    // 获取驾校信息
    getDInfo:()=>request('common/getInfo','GET')
}