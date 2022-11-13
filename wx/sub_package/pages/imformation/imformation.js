const app = getApp()
Page({
    data: {
        info:null,
        isStudent:'',
        CustomBar: app.globalData.CustomBar,
        loadProgress:0,
        isShow:false,
        loding: ''
    },
    onLoad(options) {
        this.loadProgress()
    },
    onShow() {
        if(app.globalData.userInfo&&app.globalData.identifyInfo){
            this.setData({
                isStudent:app.globalData.userInfo.role,
                info:app.globalData.identifyInfo
            })      
    }},
    gotoIdentify(){
        wx.navigateTo({
          url: '/sub_package/pages/identify/identify',
        })
    },
    loadProgress(){
        this.setData({
          loadProgress: this.data.loadProgress+3,
          loding:'transform:'+'translateX(-'+(100-this.data.loadProgress)+'%);'
        })
        if (this.data.loadProgress<100){
          this.trim = setTimeout(() => {
            this.loadProgress();
          }, 70)
        }else{
          this.setData({
            loadProgress: 0,
            isShow:true
          })
          clearTimeout(this.trim)
        }
    }
})