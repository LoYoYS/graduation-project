import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast';
const api = require('../../../api/coach')
const app = getApp()
Page({
    data: {
        id:0,
        list:[],
        show:false,
        info:null,
        loading:true
    },

    onLoad(options) {
        if(app.globalData.identifyInfo)
            this.setData({id:app.globalData.identifyInfo.id})
       
    },
    onShow() { this.getList()},
    async getList(){
        let res = await api.getList({id:this.data.id})
        if(res.code===2000)
            this.setData({list:res.data,loading:false})
    },
    showInfo(e){
        this.setData({show:true,info:e.currentTarget.dataset.info})
    },
    async cancel(e){
        let res = await api.cancel({id:e.currentTarget.dataset.id})
        if(res.code===2000){
            this.setData({show:false,loading:true})
            Toast.success(res.data)
            this.getList()
        }
        else
            Toast.fail(res.data)    
    },
    async confirm(e){
        let res = await api.confirm({id:e.currentTarget.dataset.id})
        if(res.code===2000){
            this.setData({show:false,loading:true})
            Toast.success(res.data)
            this.getList()
        }
        else
            Toast.fail(res.data)
    },
    gotoApplication(){
        wx.navigateTo({
          url: './application/application',
        })
    }
})
