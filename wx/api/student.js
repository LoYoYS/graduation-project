const {request} = require("../utils/request.js")
module.exports={
    // 获取出勤教练列表
    getCoachList:(params)=>request('order/getCoachList','POST',params),
    // 获取预约时间段
    getInterval:(parmas)=>request('order/getInterval','POST',parmas,{}),
    // 提交预约
    submitOrder:(parmas)=>request('order/submitOrder','POST',parmas),
    // 学员获取预约记录
    getRecords:(parmas)=>request('order/getRecords','POST',parmas),
    // 更新预约记录
    updateRecord:(parmas)=>request('order/updateRecord','POST',parmas),
    // 删除预约记录
    deleteRecord:(parmas)=>request('order/deleteRecord','GET',parmas),
    // 学员获取待评价记录
    getComments:(parmas)=>request('comment/getRecords','POST',parmas),
    // 提交评价
    submitComment:(parmas)=>request('comment/submitComment','POST',parmas),
    // 获取已评价记录
    getCommentList:(parmas)=>request('comment/getComments','GET',parmas),
    // 删除评价
    deleteComment:(parmas)=>request('comment/deleteComment','GET',parmas),
    // 获取教练列表(查看教练模块)
    getCoaches:(parmas)=>request('coach/getCoaches','POST',parmas,{}),
    // 查看某个教练的评价
    getCoachComment:(parmas)=>request('comment/getCoachComment','GET',parmas,{}),
    // 提交科目更新申请
    submitApply:(parmas)=>request('student/saveApply','POST',parmas),
    // 获取科目申请列表
    getApplicationListById:(parmas)=>request('student/getApplicationListById','GET',parmas),
    // 撤销申请
    cancelApply:(parmas)=>request('student/cancelApply','PUT',parmas,{})
}