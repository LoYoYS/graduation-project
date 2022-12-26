package com.yuan.service.impl;

import com.yuan.domain.*;
import com.yuan.mapper.OrderMapper;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;
import com.yuan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper mapper;

    @Override
    public ResultData<List<Coach>> getCoachList(GetCoachListQo qo) {
        List<Coach> coachList = mapper.getCoachList(qo);
        return ResultData.success(coachList);
    }

    @Override
    public ResultData<List<Interval>> getInterval(Integer id, String date) {
        List<Interval> number = mapper.getInterval(id, date);
        return ResultData.success(number);
    }

    @Override
    public ResultData<String> submitOrder(Order o) {
        Integer record = mapper.getRecord(o.getS_id(), o.getDate());
        if(0<record)
            return ResultData.fail("预约失败，您今天已经预约过咯！");
        Integer number = mapper.getNumber(o.getI_id());
        if(number<1)
            return ResultData.fail("预约失败，该时段已满人！");
        Integer integer = mapper.saveOrder(o);
        if(0<integer){
            Integer integer1 = mapper.updateNumber(o.getI_id());
            if(0<integer1)
                return ResultData.success("预约成功！");
        }
        return ResultData.fail("预约失败！");
    }

    @Override
    public ResultData<List<Order>> getRecords(OrdersQo qo) {
        mapper.updateDueOrder(qo.getS_id());
        List<Order> records = mapper.getRecords(qo);
        return ResultData.success(records);
    }

    @Override
    public ResultData<String> updateRecord(OrdersQo qo) {
        System.out.println(qo);
        if(qo.getStatus()==1){
            Integer integer = mapper.finishOrder(qo);
            if(0<integer)
                return ResultData.success("您已完成本次练车！");
            return ResultData.fail("当前时间无法确认！");
        }
        else if(qo.getStatus()==3){
            Integer integer = mapper.cancelOrder(qo);
            if(0<integer){
                Integer integer1 = mapper.cancelUpdate(qo.getI_id());
                if(0<integer1)
                    return ResultData.success("取消成功！");
            }
            return ResultData.fail("练车时间已到，无法取消！");
        }
        return ResultData.fail("失败！");
    }

    @Override
    public ResultData<String> deleteRecord(Integer id) {
        Integer integer = mapper.deleteOrder(id);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<List<Order>> getRecordList(OrdersQo qo) {
        List<Order> recordList;
        if(qo.getStatus()==0)
            recordList = mapper.getRecordList(qo);
        else
             recordList = mapper.getCompleteRecords(qo);
        return ResultData.success(recordList);
    }

}
