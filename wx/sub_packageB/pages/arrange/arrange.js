const api = require('../../../api/coach')
const app = getApp()
Page({


    data: {
        showTime:false,
        date:new Date().getTime(),
        arranges:[],
        c_id:0,
        loading:true,
    },
    onLoad(options) {
        this.setData({date:this.timeFormat(new Date().getTime())})
        if(app.globalData.identifyInfo){
            this.setData({c_id:app.globalData.identifyInfo.id})
            this.getArranges()
        }
    },
    onShow() {

    },
    onDisplay(){
        this.setData({showTime:true})
    },
    async getArranges(){
        let res = await api.getArranges({id:this.data.c_id,date:`${this.data.date}-05`})
        if(res && res.code===2000)
            this.setData({arranges:res.data,loading:false})
        else
            this.setData({loading:false})
    },
    onClose(){this.setData({showTime:false})},
    selecDate({detail}){
        this.setData({date:this.timeFormat(detail),showTime:false,loading:true}),
        this.getArranges()
    },
    // 格式化时间
    timeFormat(val){
        let date = new Date(val)
        let year = date.getFullYear()
        let month = date.getMonth()+1<10?`0${date.getMonth()+1}`:date.getMonth()+1
        return `${year}-${month}`
    }
})