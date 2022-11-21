const api = require('../../../api/coach')
Page({

    data: {
        keyWord:'',
        students:[],
        loading:false,
        show:false,
        student:null,
        dataList:[]
    },
    async search(){
        if(this.data.keyWord.trim()=='')
            return wx.showToast({
              title: '请输入学员姓名',
              icon:'error'
            })
        this.setData({loading:true})
        let res = await api.findStudent({keyWord:this.data.keyWord})
        this.setData({students:res.data,loading:false})
    },
    open({currentTarget:{dataset}}){
        let subject = dataset.student.subject
        this.setData({
            student:dataset.student,
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
            ],
            show:true})
    },
    onClose(){this.setData({show:false})}

})