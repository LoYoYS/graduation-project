const api = require('../../../api/student')
Page({

    data: {
        coaches:[],
        keyWord:'',
        loading:true
    },
    onLoad(){
        this.getCoachList()
        this.setData({loading:false})
    },
    async getCoachList(){
        let res = await api.getCoaches({keyWord:this.data.keyWord})
        if(res.code===2000){
            this.setData({coaches:res.data})
        }
        this.setData({loading:false})     
    },
    setKeyWord(e){
        this.setData({keyWord:e.detail})
    },
    search(){
        this.setData({loading:true})
        this.getCoachList()
    },
    goBack(){
        this.setData({keyWord:'',loading:true})
        this.getCoachList()
    },
    gotoDetail(e){
        let coach = JSON.stringify(e.currentTarget.dataset.coach)
        wx.navigateTo({
          url: `./detail/detail?coach=${coach}`,
        })
    }
})