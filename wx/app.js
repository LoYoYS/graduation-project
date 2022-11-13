// app.js
import api from './api/common'
App({
  onLaunch() {
    // 务必确保这一行在前面
    this.globalData = {}
    // 自定义状态栏
    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        this.globalData.Custom = custom;  
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })
    this.globalData.userInfo = wx.getStorageSync('user') || null
    this.getInfo()
  },
  //用户校验   
  checkUser(){
      let flag=false
      if(this.globalData.userInfo)
          flag=true
      else{
        wx.navigateTo({
            url: '/sub_package/pages/login/login',
        })
      }
      return flag  
  },
  async getInfo(){
    if(this.globalData.userInfo){
        if(this.globalData.userInfo.a_id){
            let result = await api.refresh(this.globalData.userInfo)
            if(result.code===2000){
                this.globalData.userInfo=result.data
                wx.setStorageSync('user', result.data)
                if(this.globalData.userInfo.a_id){
                    let res = await api.getInfo({
                        id:this.globalData.userInfo.a_id,
                        role:this.globalData.userInfo.role,})
                    if(res.code===2000)
                        this.globalData.identifyInfo=res.data
                }
            }  
        }
    }
  },   
 //全局数据
  globalData: {
    userInfo: null,
    identifyInfo:null
  }
})
