const getDate = function(){
    let date = new Date()
    let year = date.getFullYear()
    let month =date.getMonth()+1
    let week =date.getDay()+1
    let day = date.getDate()+1
    let days = new Date(year,month,0).getDate()
    let dateArr = []
    for(let i =0; i<8;i++){
        let y=year,m=month
        if(day+i>days){
          m=12<(month+1)?formatdate((month+1)%12):formatdate(month+1)
          y=12<(month+1)?year+1:year
        }
        else
            m=formatdate(m)
        let newday=day+i>days?formatdate((day+i)%days):formatdate(day+i)
        let newweek =getWeek(week+i)
        dateArr[i]={title:newweek,dateValue:(y+'-'+m+'-'+newday),isSelect:false}
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
