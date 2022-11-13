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
    onHide() {},
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
            show:true,
            title:'选择科目',
            actions:[{name: '科目二',value:0},
                    {name: '科目三',value:1}]
        })
    },
    // 打开教练面板   
    openCoach() {
        if(!this.data.subject){
            Toast.fail('请先选择科目')
            return
        }
        this.setData({
            actions:[],
            show:true,
            title:'选择教练',
        })
        this.getCoachList()
    },
    // 打开时间面板
    openTime(){
        if(!this.data.coach){
            Toast.fail('请选择教练')
            return
        }
        this.setData({
            actions:[],
            title:'选择时间',
            show1:true
        })
        this.getNumber()
    },
    // 获取教练列表
    async getCoachList(){
        let res = await api.getCoachList({
            date:this.data.dateArr[this.data.datetSelectIndex].dateValue,
            type:this.data.type,
            subject:this.data.subject.value
        })
        if(res.code===2000){
            this.setData({
                actions:res.data
            })
        }
    },
    // 获取预约余量
    async getNumber(){
        let res = await api.getNumber({
            c_id:this.data.coach.id,
            date:this.data.dateArr[this.data.datetSelectIndex].dateValue,
        })
        this.setData({
            actions:[
                {name:'08:00~10:00',number:res.data.a,value:'a',
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 08:00:00`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 10:00:00`},
                {name:'10:00~12:00',number:res.data.b,value:'b',
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 10:00:00`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 12:00:00`},
                {name:'12:00~14:00',number:res.data.c,value:'c',
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 12:00:00`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 14:00:00`},
                {name:'14:00~16:00',number:res.data.d,value:'d',
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 14:00:00`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 16:00:00`},
                {name:'16:00~18:00',number:res.data.e,value:'e',
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 16:00:00`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 18:00:00`},
                {name:'18:00~20:00',number:res.data.f,value:'f',
                start:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 18:00:00`,
                end:`${this.data.dateArr[this.data.datetSelectIndex].dateValue} 20:00:00`}
            ]
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
            subject:this.data.subject.value,
            date:this.data.dateArr[this.data.datetSelectIndex].dateValue,
            time:this.data.time.name,
            startTime:this.data.time.start,
            endTime:this.data.time.end,
            interval:this.data.time.value
        })
        if(res.code===2000){
            Toast.success(res.data)
            this.setData({
                subject:'',
                coach:null,
                time:''
            })
        }
        else
            Toast.fail(res.data)
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
            Toast.fail('您的科目一还未通过哦，通过后再来预约吧！')
            return false
        }  
        // 科目二未通过
        if(!app.globalData.identifyInfo.subject.b){
            if(this.data.subject.value===1){
                Toast.fail('科目二还未通过,还不能预约科目三哦！')
                return false}
        }
        // 已经全部通过
        if(app.globalData.identifyInfo.subject.c){
            Toast.success('恭喜您，已经完成全部考试，不需要再练车啦！')
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
