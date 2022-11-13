const api = require('../../../api/common')
const app = getApp()
Page({
    data: {
    },
    // 登入
    login(){ 
        wx.getUserProfile({
          desc: '授权登入',
          success:(r)=>{
                wx.login({
                  timeout: 2000,
                  success (res) {
                    if(res.code){
                      //登入
                        api.login({
                            username:res.code,
                            nickName:r.userInfo.nickName,
                            url:r.userInfo.avatarUrl
                        }).then((res)=>{
                            // 保存用户信息
                            wx.setStorageSync('user', res.data)
                            app.globalData.userInfo=res.data
                            // 如果已进行身份认证则获取绑定信息
                            if(res.data.a_id){
                                api.getInfo({
                                    id:res.data.a_id,
                                    role:res.data.role,}).then((result)=>{
                                    if(result.code===2000){
                                        // 保存绑定身份的信息
                                         app.globalData.identifyInfo=result.data
                                    }
                                })
                            }
                            wx.navigateBack({
                                delta: 1
                            })
                        })
                    } 
                    else {
                        wx.showToast({
                            title: '登入失败',
                            icon:'error'
                        })
                    }
                  }
                })
          },
          fail:()=>{
                wx.showToast({
                  title: '您取消了授权',
                  icon:'error'
                })
            }
        })
    },
    onLoad(options) {
        wx.showLoading({title: '加载中',})
    },
    onReady() {
        wx.hideLoading()
    },
})