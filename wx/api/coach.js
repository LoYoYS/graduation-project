const {request} = require("../utils/request.js")
module.exports={
    // 获取预约列表
    getRecords:(parmas)=>request('order/getRecordList','POST',parmas),
    // 获取车辆信息
    getCarInfo:(parmas)=>request('car/getCarInfo','GET',parmas),
    // 查看学员评价
    getComments:(parmas)=>request('comment/getCoachComment','GET',parmas,{}),
    // 提交请假信息
    submitVacate:(parmas)=>request('vacate/submitVacate','POST',parmas),
    // 获取请假记录
    getList:(parmas)=>request('vacate/getList','POST',parmas,{}),
    // 撤销申请
    cancel:(parmas)=>request('vacate/cancel','POST',parmas,{}),
    // 销假
    confirm:(parmas)=>request('vacate/confirm','POST',parmas,{}),
    // 查找学员
    findStudent:(parmas)=>request('student/findStudent','POST',parmas,{}),
    // 获取排班表
    getArranges:(parmas)=>request('arrange/getArranges','GET',parmas,{}),
}