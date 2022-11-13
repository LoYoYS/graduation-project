module.exports = {
  request : function(url, method = 'GET',data = {}, header){
    // 192.168.1.101
    // 192.168.1.102
    // localhost
    // 172.20.10.5
    let fullUrl = `http://192.168.1.103:8081/${url}`
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