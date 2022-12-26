import Dialog from '../../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../../../miniprogram_npm/@vant/weapp/toast/toast';
const api = require('../../../../api/student')
const app = getApp()
Page({
    data: {
        records:[],
        s_id:0,
        status:0,
        loading:true,
        CustomBar:0
    },
    onLoad(options) {
        if(app.globalData.identifyInfo){
            this.setData({s_id:app.globalData.identifyInfo.id})
            this.getRecords()
        }
        this.setData({CustomBar:Number.parseInt(app.globalData.CustomBar)})
    },
    onShow() {this.setData({loading:false})},
    // Tag栏切换
    onChange(e){
        if(app.globalData.identifyInfo){
            this.setData({
            loading:true,
            status:e.detail.name
            })
            this.getRecords()
        }     
    },
    // 获取预约记录
    async getRecords(){
        let res = await api.getRecords({
            s_id:this.data.s_id,
            status:this.data.status,
        })
        if(res.code===2000){
            this.setData({records:res.data,loading:false})
        }
        else
            this.setData({loading:false})
    },
    // 完成练车
    finish(e){
        Dialog.confirm({
            title: '提示!',
            message: '是否已进行练车',
          }).then(async () => {
              let {currentTarget:{dataset:{order}}}=e
              let res = await api.updateRecord({
                  id:order.id,
                  s_id:order.s_id,
                  c_id:order.c_id,
                  i_id:order.i_id,
                  status:1,
                  date:order.date,
              })
              if(res.code===2000){
                  Toast.success(res.data)
                  this.getRecords()
              }
              else
                Toast.fail('请在练车时间内进行确认！')
            }).catch(() => {
              return
            });
    },
    // 取消预约
    cancelOrder(e){
        Dialog.confirm({
            title: '提示!',
            message: '确定要取消预约吗？',
          }).then(async () => {
            let {currentTarget:{dataset:{order}}}=e
            let res = await api.updateRecord({
                id:order.id,
                s_id:order.s_id,
                c_id:order.c_id,
                i_id:order.i_id,
                status:3,
                date:order.date,
            })
            if(res.code===2000){
                Toast.success(res.data)
                this.getRecords()
            }
            else
              Toast.fail(res.data)
            }).catch(() => {
                return
            });
    },
    // 删除记录
    deleteOrder(e){
        Dialog.confirm({
            title: '警告！',
            message: '确定删除此预约记录？',
          }).then(async () => {
            let res = await api.deleteRecord({
                id:e.currentTarget.dataset.id
            })
            if(res.code===2000){
                Toast.success(res.data)
                this.getRecords()
            }
            else
              Toast.fail(res.data)
            }).catch(() => {
                return
            });
    },
})