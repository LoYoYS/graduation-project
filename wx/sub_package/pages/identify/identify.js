import Toast from '../../../miniprogram_npm/@vant/weapp/toast/toast';
import Dialog from '../../../miniprogram_npm/@vant/weapp/dialog/dialog';
import api from '../../../api/common'
const app = getApp()
Page({
    data: {
        userInfo:null,
        id_number:'',
        name:'',
        error:'',
        error1:'',
    },
    onLoad(options) {
    },
    onShow(){
        app.checkUser()
        if(app.globalData.userInfo){
            this.setData({userInfo:app.globalData.userInfo})
            if(app.globalData.identifyInfo){
                this.setData({
                    name:this.format(app.globalData.identifyInfo.name),
                    id_number:app.globalData.identifyInfo.id_number.slice(0,4),
                })
            } 
        }
    },
    collectNumber(e){
            this.setData({
                id_number:e.detail,
                error:''
            })
    },
    collectName(e){
           this.setData({
                name:e.detail,
                error1:''
            })
    },
    async submit(){
        if(!this.data.id_number.trim()){
            this.setData({error:'请完善此栏信息！'})
            return
        }
        if(this.data.id_number.length<18){
            this.setData({error:'请输入合法的身份证号！'})
            return
        }      
        if(!this.data.name.trim()){
            this.setData({error1:'请完善此栏信息！'})
            return
        }
        Toast.loading({
            message: '正在提交...',
            forbidClick: true,
            loadingType: 'spinner',
        });
        let res = await api.indentify({
            id:app.globalData.userInfo.id,
            id_number:this.data.id_number,
            name:this.data.name})
        if(res.code===2000){
            wx.setStorageSync('user', res.data)
            app.globalData.userInfo=res.data
                   api.getInfo({
                       id:app.globalData.userInfo.a_id,
                       role:app.globalData.userInfo.role,}).then((result)=>{
                       if(result.code===2000){
                            app.globalData.identifyInfo=result.data
                            this.onShow()
                       }
                   })
            Toast.success(res.message);
        }
        else
            Toast.fail(res.message);
    },
    unbind(){
        this.showDialog()
    },
    showDialog(){
        let that =this
        Dialog.confirm({
            message: '解绑后相关功能权限和信息将丢失，确定要解除身份绑定吗？',
            closeOnClickOverlay:true,
          }).then(async () => {
                let res = await api.unbind(this.data.userInfo)
                if(res.code===2000){
                    Toast.success(res.data)
                    this.setData({
                        'userInfo.a_id':null,
                        'userInfo.role':0,
                        name:'',
                        id_number:''
                    })
                    app.globalData.userInfo=this.data.userInfo
                    app.globalData.identifyInfo=null
                    wx.setStorageSync('user', this.data.userInfo)
                    that.onShow()
                }
                else
                    Toast.fail(res.data)
          }).catch(()=>{return})
    },
    format(str){
        let val = ''
        for(let i =0;i<str.length-1;i++)
            val=val.concat('*')
        val=val.concat(str.slice(-1))
        return val
    }
})