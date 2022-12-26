const api = require("../../../api/common")
Page({

    data: {
        noticeList:[],
        loading:true
    },

    onLoad(options) {
        api.getList().then((res)=>{
            if(res.code===2000)
                this.setData({
                    noticeList:res.data
                })
        })
        this.setData({loading:false})
    },
    openNotice(e){
        wx.navigateTo({
          url: `/sub_package/pages/notices/notice/notice?id=${e.currentTarget.dataset.id}`,
        })
    },
})