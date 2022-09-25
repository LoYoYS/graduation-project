// app.js
App({
  onLaunch() {
    // app.js

     
    this.globalData = {} // 务必确保这一行在前面
    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        this.globalData.Custom = custom;  
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })

    this.globalData.userInfo = wx.getStorageSync('user') || null
    // console.log(this.globalData.userInfo)
    /* if(this.globalData.userInfo){
        wx.checkSession({
            success (res) {
                console.log(res)
              },
              fail () {
                // session_key 已经失效，需要重新执行登录流程
                wx.redirectTo({
                    url: '/pages/login/login',
                })
              }
        })
    } */
/*     else{
        wx.redirectTo({
            url: '/pages/login/login',
        })
    } */
  },
  globalData: {
    userInfo: null
  }
})
