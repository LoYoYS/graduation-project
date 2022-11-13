package com.yuan.service;

import com.yuan.domain.Arrange;
import com.yuan.domain.Coach;
import com.yuan.domain.Order;
import com.yuan.domain.ResultData;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;

import java.util.List;

public interface OrderService {

    //获取当天出勤教练
    ResultData<List<Coach>> getCoachList(GetCoachListQo qo);
    //获取预约余量
    ResultData<Arrange> getNumber(Integer id,String date);
    //提交预约
    ResultData<String>submitOrder(Order o);
    //查询预约记录
    ResultData<List<Order>> getRecords(OrdersQo qo);
    //更新预约记录状态
    ResultData<String> updateRecord(OrdersQo qo);
    //删除预约记录
    ResultData<String> deleteRecord(Integer id);
    //教练获取待教学预约记录
    ResultData<List<Order>> getRecordList(OrdersQo qo);
}
