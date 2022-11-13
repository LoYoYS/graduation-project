const api = require("../../../../api/common")
Page({

    data: {
        notice:'',
        loading:true
    },

    onLoad(options) {
       api.getNotice(options.id).then((res)=>{
            if(res.code===2000)
                this.setData({
                    notice:res.data
            })
       })
       this.setData({loading:false})
    },
    onReady() {

    },

    onShow() {

    },
})