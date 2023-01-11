module.exports = {
  request : function(url, method = 'GET',data = {}, header){
    // let fullUrl = `https://yuanshu.love/driveSchool/${url}`
    let fullUrl = `http://192.168.1.2:8081/driveSchool/${url}`
    return new Promise((resolve,reject)=>{
      wx.request({
        url: fullUrl,
        method,
        data,
        header:{
            'content-type':header?'application/x-www-form-urlencoded':''||'application/json'
        },
        success(res){
            resolve(res.data)
        },
        fail(){
          wx.showToast({
            title: '接口请求错误',
            icon:'error'
          })
          reject('接口请求错误')
        }
      })
    })
  }
}