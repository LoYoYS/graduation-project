import Toast from '../../../../miniprogram_npm/@vant/weapp/toast/toast';
import Dialog from '../../../../miniprogram_npm/@vant/weapp/dialog/dialog';
const api = require('../../../../api/coach')
const app = getApp()
Page({

    data: {
        form:{
            c_id:0,
            name:'',
            phone:'',
            type:'',
            status:0,
            startTime:'',
            endTime:'',
            content:'',
        },
        type:'',
        startTime:new Date().getTime(),
        endTime:new Date().getTime(),
        show:false,
        showStartTime:false,
        showEndTime:false,
        actions:[{name:'事假',value:0},{name:'病假',value:1}]
    },  
    onLoad(options) {},
    onShow(){
        if(app.globalData.identifyInfo)
            this.setData({'form.c_id':app.globalData.identifyInfo.id})
    },
    // 打开选择请假类型面板
    open(){this.setData({show:true})},
    // 打开开始时间面板
    openStartTime(){this.setData({showStartTime:true})},
    // 打开结束时间面板
    openEndTime(){this.setData({showEndTime:true})},
    // 关闭面板
    onClose(){this.setData({show:false,showStartTime:false,showEndTime:false})},
    // 收集姓名
    collectName(e){
        this.setData({'form.name':e.detail})
    },
    // 收集手机
    collectPhone(e){
        this.setData({'form.phone':e.detail})
    },
    // 收集理由
    collectContent(e){
        this.setData({'form.content':e.detail})
    },
    // 选择类型
    onSelect(e){this.setData({'form.type':e.detail.value,type:e.detail.name})},
    // 选择开始时间
    selectStartTime({detail}){
        this.setData({
            'form.startTime':this.timeFormat(detail),
            showStartTime:false
        })},
    // 选择结束时间
    selectEndTime({detail}){
        this.setData({
        'form.endTime':this.timeFormat(detail),
        showEndTime:false
    })},
    // 提交
    async submit(){
        let flag = this.hint()
        if(!flag)
            return
        let isLogin = app.checkUser()
        if(!isLogin)
            return
        if(!app.globalData.identifyInfo){
            this.showDialog()
            return
        }
        let res = await api.submitVacate(this.data.form)
        if(res.code===2000)
            Toast.success(res.data)
        else
            Toast.fail(res.data)
    },
    // 提示
    hint(){
        if(this.data.form.name.trim()===''){
            Toast.fail("请填写姓名")
            return false
        }
        else if(this.data.form.phone.trim()===''){
            Toast.fail("请填写手机号")
            return false
        }
        else if(this.data.form.type===''){
            Toast.fail("请选择请假类型")
            return false
        }
        else if(this.data.form.startTime===''){
            Toast.fail("请选择开始时间")
            return false
        }
        else if(this.data.form.endTime===''){
            Toast.fail("请选择结束时间")
            return false
        }
        else if(this.data.form.content.trim()===''){
            Toast.fail("请填写请假事由")
            return false
        }
        return true     
    },
    // 提示身份认证
    showDialog(){
        Dialog.alert({
            message: '清先进行身份认证！',
            theme: 'round-button',
            closeOnClickOverlay:true,
          }).then(() => {
           wx.navigateTo({
             url: '/sub_package/pages/identify/identify',
           })
        })
    },
    // 格式化时间
    timeFormat(val){
        let date = new Date(val)
        let year = date.getFullYear()
        let month = date.getMonth()+1<10?`0${date.getMonth()+1}`:date.getMonth()+1
        let day = date.getDate()<10?'0'+date.getDate():date.getDate() 
        let hour = date.getHours()<10?'0'+date.getHours():date.getHours()
        let minute = date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes()
        return `${year}-${month}-${day} ${hour}:${minute}`
    }
})