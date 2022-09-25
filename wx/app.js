// app.js
App({
  onLaunch() {
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
