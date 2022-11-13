const {getCoachComment} = require("../../../../api/student")
Page({
    data: {
        coach:null,
        comments:[],
        loading:true
    },
    onLoad(options) {
        let coach = JSON.parse(options.coach)
        this.setData({coach:coach})
        this.getCoachComment() 
    },
    async getCoachComment(){
        let res = await getCoachComment({id:this.data.coach.id})
        if(res.code===2000)
            this.setData({comments:res.data,loading:false})
        else
            this.setData({loading:false})
    },
})