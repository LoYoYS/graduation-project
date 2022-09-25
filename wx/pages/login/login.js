// pages/login/login.js
const api = require('../../api/login')
Page({
    data: {
    },
    // 登入
    login(){
        wx.getUserProfile({
          desc: '授权获取用户信息以便功能的使用',
          success:(res)=>{
            console.log(res)
                wx.login({
                  timeout: 2000,
                  success (res) {
                    if (res.code) {
                      //发起网络请求
                        api.login({code:res.code}).then((res)=>{
                            console.log(res)
                        },(err)=>{
                            console.log(err)
                        })
                    } else {
                      console.log('登入失败!' + res.errMsg)
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
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        wx.showLoading({title: '加载中',})
   /*      wx.switchTab({
          url: '/pages/home/home',
        }) */
    },
    onReady() {
        wx.hideLoading()
    },

})