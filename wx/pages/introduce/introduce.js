// pages/introduce/introduce.js
Page({
    /**
     * 页面的初始数据
     */
    data: {

    },
    openLocation(){
        wx.openLocation({
            latitude: 23.47303,
            longitude: 111.32033,
            name: '梧州学院',
            address: '梧州市万秀区富民街道富民三路82号'
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {

    },

 



})