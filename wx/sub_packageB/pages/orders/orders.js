const api = require('../../../api/coach')
const app = getApp()
Page({

    data: {
        show:false,
        records:[],
        date:'',
        c_id:0,
        status:0,
        loading:true,
        minDate: new Date(2022,0,1).getTime(),
        CustomBar:0
    },
    onLoad(options) {
        this.setData({date:this.formatDate(new Date())})
        if(app.globalData.identifyInfo){
            this.setData({c_id:app.globalData.identifyInfo.id,CustomBar:Number.parseInt(app.globalData.CustomBar)})
            this.getRecord()
        }
    },
    onShow() {},
    // 获取当天预约记录
    async getRecord(){
        let res = await api.getRecords({
            c_id:this.data.c_id,
            date:this.data.date,
            status:this.data.status
        })
        if(res.code===2000)
            this.setData({records:res.data,loading:false})
    },
    // 标签栏切换
    onChange(e){    
        if(app.globalData.identifyInfo){
            this.setData({
                loading:true,
                status:e.detail.name
            })
            this.getRecord()
        }   
    },
    // 打开日期选择器
    onDisplay(){this.setData({show:true})},
    // 关闭日期选择器
    onClose(){this.setData({show:false})},
    // 选择日期
    onConfirm({detail}){
        let date = this.formatDate(detail)
        this.setData({show:false,date:date})
        if(app.globalData.identifyInfo){
            this.setData({loading:true})
            this.getRecord()
        }   
    },
    // 日期格式函数
    formatDate(val){
        let date = new Date(val)
        let year = date.getFullYear()
        let month = date.getMonth()+1<10?`0${date.getMonth()+1}`:date.getMonth()+1
        let day = date.getDate()<10?'0'+date.getDate():date.getDate() 
        return `${year}-${month}-${day}`
    },
})