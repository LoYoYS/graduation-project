import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast';
const api = require('../../../api/student')
const app =getApp()
Page({
    data: {
        records:[],
        comments:[],
        record:null,
        s_id:0,
        value:0,
        show:false,
        loading:true,
        start:0,
        text:'',
        comment:'',
        hidden:1
    },
    onLoad(options) {
        if(app.globalData.identifyInfo){
            this.setData({s_id:app.globalData.identifyInfo.id})
            this.getComments()
        }
        else
            this.setData({loading:false})    
    },
    // 关闭遮罩层
    onClose(){ this.setData({
        show:false,
    })},
    // 切换标签
    onChange({detail}){
        if(app.globalData.identifyInfo){
            if(detail.name===0){
                this.setData({loading:true})
                this.getComments()
            }   
            else if(detail.name===1){
                this.setData({loading:true})
                this.getCommentList()
            }
        }   
    },
    // 评分改变
    rateChange({detail}){
        this.setData({start:detail})
        switch(detail){
            case 1:return this.setData({text:"差"})
            case 2:return this.setData({text:"一般"})
            case 3:return this.setData({text:"良好"})
            case 4:return this.setData({text:"很好"})
            case 5:return this.setData({text:"非常好"})
        } 
    },
    // 开启匿名
    changeHidden(){
        this.setData({hidden:this.data.hidden==1?0:1})
    },
    //评语赋值 
    collectComment(e){
        this.setData({comment:e.detail})
    },
    // 获取待评价记录
    async getComments(){
        let res = await api.getComments({
            s_id:this.data.s_id,
            status:1,
        })
        if(res.code===2000){
            this.setData({records:res.data})
        }
        this.setData({loading:false})
    },
    //获取已评价记录
    async getCommentList(){
        let res = await api.getCommentList({
            id:this.data.s_id,
            status:1,
        })
        if(res.code===2000){
            this.setData({comments:res.data})
        }
        this.setData({loading:false})
    },
    // 填写评价
    comment({currentTarget:{dataset:{record}}}){
        this.setData({
            record:record,
            hidden:1,
            levele:0,
            value:0,
            text:'',
            comment:'',
            show:true
        })
    },
    // 提交评价
    async submitComment(){
        let res = await api.submitComment({
            o_id:this.data.record.id,
            s_id:this.data.record.s_id,
            c_id:this.data.record.c_id,
            subject:this.data.record.subject,
            date:new Date().toLocaleDateString(),
            content:this.data.comment,
            start:this.data.start,
            hidden:this.data.hidden
        })
        if(res.code===2000){
            Toast.success(res.data)
            this.getComments()
        }   
        else
            Toast.fail(res.data)
    },
    // 删除评价
    deleteComment(e){
        Dialog.confirm({
            title: '警告！',
            message: '确定删除此评价吗？',
          }).then(async () => {
            let res = await api.deleteComment({
                id:e.currentTarget.dataset.id
            })
            if(res.code===2000){
                Toast.success(res.data)
                this.getCommentList()
            }
            else
              Toast.fail(res.data)
            }).catch(() => {
                return
            });
    }
})