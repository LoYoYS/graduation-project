// pages/introduce/introduce.js
Page({
    /**
     * 页面的初始数据
     */
    data: {

    },
    openLocation(){
        wx.openLocation({
            latitude: 23.496771,
            longitude: 111.317102,
            name: '梧州学院',
            address: '梧州市万秀区富民街道富民三路82号'
        })
    },
    clickPhone(){
        wx.makePhoneCall({
          phoneNumber: '0071-7735991' //仅为示例，并非真实的电话号码
        })
      },    
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {

    },

 



})