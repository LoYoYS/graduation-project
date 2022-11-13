const api = require("../../api/common")
const app = getApp()
Page({

    data: {
        images:[
            'https://pic1.imgdb.cn/item/63620eba16f2c2beb1f9633e.jpg',
            'https://pic1.imgdb.cn/item/63620eb616f2c2beb1f9565b.jpg',
            'https://pic1.imgdb.cn/item/63620ea916f2c2beb1f92e71.jpg',
            'https://pic1.imgdb.cn/item/63620eb016f2c2beb1f943ff.jpg'
        ],
        notices:[],
        isStudent:true,
    },
    onLoad(options) {
        // 获取公告列表
        api.getNoticeList(1).then((res)=>{
            if(res.code===2000)
                this.setData({notices:res.data.list})
        })
    },
    onShow(){
        if(app.globalData.userInfo)
            this.setData({isStudent:app.globalData.userInfo.role}) 
    },
     // 图片预览
    showImage(e){
        wx.previewImage({
          current:this.data.images[e.currentTarget.dataset.index],
          urls: this.data.images,
        })
    },
    // 前往预约
    gotoOrder(){
        let flag = app.checkUser()
        if(!flag)
            return
        wx.navigateTo({url: '/sub_package/pages/order/order'})
    },
     // 前往预约列表
     gotoOrderList(){
        let flag = app.checkUser()
        if(!flag)
            return
        wx.navigateTo({url: '/sub_packageB/pages/orders/orders'})
    },
    // 打开学员功能页面
    gotoNewPage({currentTarget:{dataset:{index}}}){
        if(index===1)
            wx.navigateTo({url: '/sub_package/pages/order/record/record'})
        else if(index===2)
            wx.navigateTo({url: '/sub_package/pages/progress/progress'})
        else if(index===3)
            wx.navigateTo({url: '/sub_package/pages/comment/comment'})
        else
            wx.navigateTo({url: '/sub_package/pages/findCoach/findCoach'})
    },
    // 打开教练功能页面
    gotoNewPageB({currentTarget:{dataset:{index}}}){
        if(index===1)
            wx.navigateTo({url: '/sub_packageB/pages/comment/comment'})
        else if(index===2)
            wx.navigateTo({url: '/sub_packageB/pages/vacate/vacate'})
        else if(index===3)
            wx.navigateTo({url: '/sub_packageB/pages/car/car'})
        else
            wx.showToast({
              title: '功能正在开发中',
              icon:'error'
            })
            
    },
    // 查看公告
    openNotice(e){
        wx.navigateTo({
          url: `/sub_package/pages/notices/notice/notice?id=${e.currentTarget.dataset.id}`,
        })
    },
    // 查看公告列表
    openNoticeList(){
        wx.navigateTo({
          url: '/sub_package/pages/notices/notices',
        })
    },
})