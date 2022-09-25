// pages/order/order.js
const utils = require('../../../utils/util')
Page({
    /**
     * 页面的初始数据
     */
    data: {
        dateArr: [],
        datetSelectIndex: 0,
    },
    
    changeSelect(e) {
        let { index } = e.currentTarget.dataset
        this.setData({
          datetSelectIndex: index,
        })
      },  
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        this.setData({
            dateArr:utils.getDate()
        })
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
    onHide() {
        this.setData({datetSelectIndex:0})
    },
})