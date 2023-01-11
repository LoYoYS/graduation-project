import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast'
const api = require('../../../api/student')
const app =getApp()
Page({

    data: {
        isShow:false,
        showDetail:false,
        s_id:0,
        info:null,
        loading:true,
        name:'',
        phone:'',
        subject:'',
        urls:[],
        applicationList:[],
        fileList:[],
        images:[],
        subjectList:[
            {name:'科目一',value:'a'},
            {name:'科目二',value:'b'},
            {name:'科目三',value:'c'},
            {name:'科目四',value:'d'}
        ]
    },
    onLoad(){
        if(app.globalData.identifyInfo){
            this.setData({
                s_id:app.globalData.identifyInfo.id,
                name:app.globalData.identifyInfo.name,
                phone:app.globalData.identifyInfo.phone,
            })
        }
        this.getApplicationListById()
    },
    onShow() {
        if(app.globalData.identifyInfo){
            this.setData({
                s_id:app.globalData.identifyInfo.id,
                name:app.globalData.identifyInfo.name,
                phone:app.globalData.identifyInfo.phone,
            })
        }
    },
    addApply(){
        this.setData({
            fileList:[],
            urls:[],
            images:[],
            subject:'',
            subjectList:[
                {name:'科目一',value:'a'},
                {name:'科目二',value:'b'},
                {name:'科目三',value:'c'},
                {name:'科目四',value:'d'}
            ],isShow:true})
    },
    onClose(){
        this.setData({subjectList:[],isShow:false,showDetail:false})
    },
    // 获取申请列表
    async getApplicationListById(){
        let res = await api.getApplicationListById({id:this.data.s_id})
        this.setData({applicationList:res.data,loading:false})
    },
    // 选择科目
    radioChange({detail}){
        this.setData({subject:detail.value})
    },
    // 图片上传后
    afterRead({detail:{file}}){
        this.data.images.push(file)
        this.setData({fileList:this.data.images})
    },
    // 删除图片
    deleteImg({detail:{index}}){
        this.data.images.splice(index,1)
        this.setData({fileList:this.data.images})
    },
    // 提交申请
    submit(){
        if(!this.check())
            return
        for(let i=0;i<this.data.fileList.length;i++){
            wx.uploadFile({
                // url: 'https://yuanshu.love/driveSchool/student/getUrl',
                url: 'http://192.168.1.2:8081/driveSchool/student/getUrl',
                filePath: this.data.fileList[i].url,
                name: 'file',
                success: ({data})=>{
                    let d = JSON.parse(data)
                    this.data.urls.push(d.data)
                    let index =this.data.images.length-1
                    if((index<0?0:index)==i){
                        let res = api.submitApply({
                            s_id:this.data.s_id,
                            name:this.data.name,
                            phone:this.data.phone,
                            subject:this.data.subject,
                            urls:this.data.urls
                        })
                        res.then((data)=>{
                            if(data.code===2000){
                                Toast.success(data.data)
                                this.getApplicationListById()
                            }    
                            else
                                Toast.fail(data.data)
                        })
                    }    
                },
                fail(){
                    Toast.fail('申请失败！')
                }
            })
        } 
    },
    // 检查数据
    check(){
        if(!this.data.subject){
            Toast.fail('提交失败，请选择需要更新的科目！')
            return false
        }
        if(!this.data.fileList.length){
            Toast.fail('提交失败，请上传相关证明图片！')
            return false
        }
        if(!app.globalData.identifyInfo){
            this.showDialog()
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
    // 图片预览
    preView(e){
        wx.previewImage({
            urls: [e.currentTarget.dataset.url]
          })
    },
    // 查看详情
    showDetail(e){
        this.setData({info:e.currentTarget.dataset.item,showDetail:true})
        
    },
    // 撤销申请
    async cancelApply(){
        let res = await api.cancelApply({id:this.data.info.id})
        if(res.code===2000)
            Toast.success(res.data)
        else
            Toast.fail(res.data)
        this.onClose()
        this.getApplicationListById()
    }
})