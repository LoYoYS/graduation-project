const api = require('../../../api/coach')
const app = getApp()
Page({

    data: {
        carNumber:'',
        car:null,
        loading:true
    },
    onLoad(options) {
        if(app.globalData.identifyInfo){
            this.setData({carNumber:app.globalData.identifyInfo.car})
            this.getCarInfo()
        }
               
    },
    onShow(){this.setData({loading:false})},
    // 获取车辆信息
    async getCarInfo(){
        let res = await api.getCarInfo({number:this.data.carNumber})
        if(res.code===2000)
            this.setData({car:res.data})
    },
})