package com.yuan.mapper;

import com.yuan.domain.Arrange;
import com.yuan.domain.Coach;
import com.yuan.domain.Order;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    //获取当天出勤教练
    List<Coach> getCoachList(GetCoachListQo qo);
    //获取预约余量
    Arrange getNumber(Integer id,String date);
    //查询学员当天是否有预约记录
    Integer getRecord(Integer s_id, String date);
    //保存预约记录
    Integer saveOrder(Order o);
    //预约后更新预约余量
    void updateNumber(Order o);
    //更新过期记录
    void updateDueOrder(Integer id);
    //查询记录
    List<Order> getRecords(OrdersQo qo);
    //完成预约
    Integer finishOrder(OrdersQo qo);
    //取消预约
    Integer cancelOrder(OrdersQo qo);
    //取消预约更新预约余量
    void cancelUpdate(OrdersQo qo);
    //删除预约记录
    Integer deleteOrder(Integer id);
    //教练获取待教学记录
    List<Order> getRecordList(OrdersQo qo);
    //教练获取已完成记录
    List<Order> getCompleteRecords(OrdersQo qo);
}
