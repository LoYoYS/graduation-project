const app = getApp()
import Notify from '../../miniprogram_npm/@vant/weapp/notify/notify';
Page({
    data: {
        userInfo:null
    },
    onLoad(options) {
        
    },
    onShow(){
        this.setData({
            userInfo:app.globalData.userInfo
        })
    },
    gotoLogin(){
        wx.navigateTo({
          url: '/sub_package/pages/login/login',
        })
    },
    // 清理缓存
    clearCache(){
       wx.showModal({
         cancelColor: '#e54d42',
         confirmColor: '#2EA7E0',
         content: '清除后需重新登入，确定清除？',
         showCancel: true,
         title: '提示',
         success: (res) => {
             if(res.confirm){
                wx.removeStorageSync('user')
                wx.removeStorageSync('identifyInfo')
                app.globalData.userInfo=null
                app.globalData.identifyInfo=null
                this.onShow()
                Notify({ 
                    type: 'success',
                     message: '清除成功',
                     top:50,
                     safeAreaInsetTop:true,
                     color:'#2EA7E0',
                     background: '#fff'
                })
             }
             else 
                return
         }
       })
    },  
})