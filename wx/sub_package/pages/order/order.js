import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast';
const utils = require('../../../utils/util')
const api = require('../../../api/student')
const app = getApp()
Page({
    data: {
        dateArr: [],
        datetSelectIndex: 0,
        show:false,
        show1:false,
        showInfo:false,
        title:'',
        actions: [],
        type:'C1',
        subject:'',
        coach:null,
        time:''
    },
    onLoad(options) {
        this.setData({dateArr:utils.getDate()})
    },
    onReady() {},
    onShow() {
        // 获取学员学习的驾照类型
        if(app.globalData.identifyInfo)
            this.setData({type:app.globalData.identifyInfo.type})
    },
    // 关闭动作面板
    onClose(){this.setData({show:false,show1:false,showInfo:false})}, 
    // 切换日期
    changeSelect(e) {
        let d = e.currentTarget.dataset
        this.setData({
          datetSelectIndex: d.index,
          coach:null,
          subject:'',
          time:''
        })
    },
    // 打开科目面板  
    openSubject(){
        this.setData({
            title:'选择科目',
            actions:[{name: '科目二',value:0},
                    {name: '科目三',value:1}],
            show:true,
        })
    },
    // 打开教练面板   
    openCoach() {
        this.setData({actions:[]})
        if(!this.data.subject){
            Toast.fail('请先选择科目')
            return
        }
        else{
            this.setData({title:'选择教练',show:true})
            this.getCoachList() 
        }        
    },
    // 打开时间面板
    openTime(){
        this.setData({actions:[]})
        if(!this.data.coach){
            Toast.fail('请选择教练')
            return
        }
        else{
            this.setData({title:'选择时间',show1:true})
            this.getInterval()
        }
            
    },
    // 获取教练列表
    async getCoachList(){
        let res = await api.getCoachList({
            date:this.data.dateArr[this.data.datetSelectIndex].dateValue,
            type:this.data.type,
            subject:this.data.subject.value
        })
        if(res.code && res.code===2000){
            this.setData({actions:res.data})
        }
    },
    // 获取预约余量
    async getInterval(){
        let res = await api.getInterval({
            c_id:this.data.coach.id,
            date:this.data.dateArr[this.data.datetSelectIndex].dateValue,
        })
        let list=[]
        res.data.forEach(element => {
            list.push({
                id:element.id,
                name:`${element.start} ~ ${element.end}`,
                number:element.number,
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} ${element.start}`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} ${element.end}`,
            })
        });
        this.setData({
            actions:list
        })
    },
     // 选择选项
     onSelect(e) {
        if(this.data.title==='选择科目'){
            this.setData({
                subject:e.detail,
                coach:null,
                time:''
            })
        }  
        if(this.data.title==='选择教练'){
            this.setData({
                coach:e.detail,
                time:''
            })
        }     
        if(this.data.title==='选择时间'){
            this.setData({
                show1:false,
                time:e.currentTarget.dataset.item})
        }    
    },
    // 预提交
    submit(){
       let r =  this.checkData()
       if(!r)
            return
       let flag =  this.checkUser()
       if(!flag)
            return
        this.setData({showInfo:true})
    },
    // 确认提交
    async confirmSubmit(){
        let res = await api.submitOrder({
            s_id:app.globalData.identifyInfo.id,
            c_id:this.data.coach.id,
            i_id:this.data.time.id,
            subject:this.data.subject.value,
            date:this.data.dateArr[this.data.datetSelectIndex].dateValue,
            type:this.data.type,
            time:this.data.time.name,
            startTime:this.data.time.start,
            endTime:this.data.time.end,
        })
        if(res.code && res.code===2000){
            Toast.success(res.data)
            this.setData({
                subject:'',
                coach:null,
                time:''
            })
            setTimeout(()=>{
                wx.navigateTo({url: './record/record',})
            },1200)
        }
        else
            Toast({type:'fail',message:res.data,duration:3000})
    },
    // 检查数据
    checkData(){
        if(!this.data.subject){
            Toast.fail('请选择科目！')
            return false
        }
        else if(!this.data.coach){
            Toast.fail('请选择教练！')
            return false
        }
        else if(!this.data.time){
            Toast.fail('请选择时间！')
            return false
        }
        return true
    },
    // 检查学员信息
    checkUser(){
        // 身份未认证
        if(!app.globalData.identifyInfo){
            this.showDialog()
            return false
        }   
        // 科目一未通过 
        if(!app.globalData.identifyInfo.subject.a){
            Toast({type:'fail',message:'您的科目一还未通过哦，通过后再来预约吧！',duration:3000})
            return false
        }  
        // 科目二未通过
        if(!app.globalData.identifyInfo.subject.b){
            if(this.data.subject.value===1){
            Toast({type:'fail',message:'科目二还未通过,还不能预约科目三哦！',duration:3000})
                return false}
        }
        if(app.globalData.identifyInfo.subject.b){
            if(this.data.subject.value===0){
                Toast({type:'fail',message:'科目二已通过不需要再预约咯。',duration:3000})
                return false}
        }
        // 已经全部通过
        if(app.globalData.identifyInfo.subject.c){
            Toast({type:'fail',message:'科目三已通过不需要再预约咯。',duration:3000})
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
})
