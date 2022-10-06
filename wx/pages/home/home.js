// pages/home/home.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        images:[
            /* 'https://s1.ax1x.com/2022/09/22/xFJ4ij.jpg',
            'https://s1.ax1x.com/2022/09/22/xFski6.jpg',
            'https://s1.ax1x.com/2022/09/22/xFsmsH.jpg', */
            'https://imgdata.kusu.icu/3/6332bfc31373a.jpg',
            'https://imgdata.kusu.icu/3/6332bfc3c1b22.jpg',
            'https://imgdata.kusu.icu/3/6332bfc74322d.jpg'
        ]
    },
    // 图片预览
    showImage(e){
        wx.previewImage({
          current:this.data.images[e.currentTarget.dataset.index],
          urls: this.data.images,
        })
    },
    // 小动画
    gotologin(){
        wx.navigateTo({
          url: '/pages/login/login',
        })
      /*   wx.redirectTo({
          url: '/pages/login/login',
        }) */
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})