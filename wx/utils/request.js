module.exports = {
  request : function(url, method = 'GET',data = {}, header={}){
    let fullUrl = `http://localhost:8081/${url}`
    return new Promise((resolve,reject)=>{
      wx.request({
        url: fullUrl,
        method,
        data,
        header,
        success(res){
          if(res.data.code == 2000) {
            resolve(res.data)
          }else{
            wx.showToast({
              title: '请求失败',
              icon:'error'
            })
            reject(res.data)
          }
        },
        fail(){
          wx.showToast({
            title: '接口请求错误',
            icon:'none'
          })
          reject('接口请求错误')
        }
      })
    })
  }
}