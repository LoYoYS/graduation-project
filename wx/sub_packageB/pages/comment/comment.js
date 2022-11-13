const api = require('../../../api/coach')
const app = getApp()
Page({

    data: {
        id:0,
        list:[],
        loading:true
    },
    onLoad(options) {
        if(app.globalData.identifyInfo){
            this.setData({id:app.globalData.identifyInfo.id})
            this.getComments()
        }
           
    },
    async getComments(){
        let res = await api.getComments({id:this.data.id})
        if(res.code===2000)
            this.setData({list:res.data,loading:false})
    }
})