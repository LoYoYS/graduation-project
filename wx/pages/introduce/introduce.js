const api = require("../../api/common")
Page({
    /**
     * 页面的初始数据
     */
    data: {
        info:{
            logo:'/icon/car1.png',
            back:'https://pic1.imgdb.cn/item/6362578916f2c2beb1e754f4.jpg',
            name:'XXX驾校',
            latitude: 23.496771,
            longitude: 111.317102,
            slogans:['专业','品质','高效','保障'],
            phone:'0771-7735991',
            address:'梧州市万秀区富明街道富明三路82号',
            text:''
        }
    },
    openLocation(){
        wx.openLocation({
            latitude: +this.data.info.latitude,
            longitude: +this.data.info.longitude,
            name: '梧州学院',
            address: this.data.info.address
        })
    },
    clickPhone(){
        wx.makePhoneCall({
          phoneNumber: this.data.info.phone//仅为示例，并非真实的电话号码
        })
      },    
    onLoad(options) {
        this.getInfo()
    },
    // 获取驾校信息
    async getInfo(){
        let res = await api.getDInfo()
        if(res.code===2000)
            this.setData({
                info:res.data
            })
    }
 



})