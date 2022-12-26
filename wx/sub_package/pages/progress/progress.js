const app = getApp()
Page({
    data: {
      dataList:[],
      loading:true,
      CustomBar:0
    },
    onLoad(){
        this.getData()
    },
    getData(){
        if(app.globalData.identifyInfo){
            let subject = app.globalData.identifyInfo.subject  
            this.setData({
                dataList:[
                    {
                        name:'科目一',
                        icon:subject.a?'cuIcon-emojifill':'cuIcon-usefullfill',
                        text:subject.a?'已通过':'进行中',
                        class:subject.a?'text-green':'text-blue turn',
                    },
                    {
                        name:'科目二',
                        icon:subject.a?(subject.b?'cuIcon-emojifill':'cuIcon-usefullfill'):'cuIcon-radioboxfill',
                        text:subject.a?(subject.b?'已通过':'进行中'):'等待中',
                        class:subject.a?(subject.b?'text-green':'text-blue turn'):'text-grey',
                    },
                    {
                        name:'科目三',
                        icon:subject.b?(subject.c?'cuIcon-emojifill':'cuIcon-usefullfill'):'cuIcon-radioboxfill',
                        text:subject.b?(subject.c?'已通过':'进行中'):'等待中',
                        class:subject.b?(subject.c?'text-green':'text-blue turn'):'text-grey',
                    },
                    {
                        name:'科目四',
                        icon:subject.c?(subject.d?'cuIcon-emojifill':'cuIcon-usefullfill'):'cuIcon-radioboxfill',
                        text:subject.c?(subject.d?'已通过':'进行中'):'等待中',
                        class:subject.c?(subject.d?'text-green':'text-blue turn'):'text-grey',
                    },
                ]
            })
        }
        this.setData({loading:false,CustomBar:Number.parseInt(app.globalData.CustomBar)})
    }
  })