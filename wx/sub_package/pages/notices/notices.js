const api = require("../../../api/common")
Page({

    data: {
        noticeList:[],
        page:1,
        loading:true
    },

    onLoad(options) {
        api.getNoticeList(this.data.page).then((res)=>{
            if(res.code===2000)
                this.setData({
                    noticeList:res.data.list
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