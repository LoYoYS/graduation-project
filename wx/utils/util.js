const getDate = function(){
    let date = new Date()
    let day = date.getDate()
    let week =date.getDay()
    let month =formatdate(date.getMonth()+1)
    let year = date.getFullYear()
    let dateArr = []
    for(let i =0; i<8;i++){
      let newday=formatdate(day+i)
      let newweek =getWeek(week+i)
      dateArr[i]={title:newweek,dateValue:(year+'-'+month+'-'+newday),isSelect:false}
    }
    return dateArr
}
function getWeek(n){
    n=n%7
    switch(n){
        case 0:return '星期日'
        case 1:return '星期一'
        case 2:return '星期二'
        case 3:return '星期三'
        case 4:return '星期四'
        case 5:return '星期五'
        case 6:return '星期六'
    }
  }
function formatdate(n){
    n = n.toString()
    return n[1] ? n : `0${n}`
}
module.exports = {
    getDate
}
