package com.yuan.service.impl;

import com.yuan.domain.Arrange;
import com.yuan.domain.Coach;
import com.yuan.domain.Order;
import com.yuan.domain.ResultData;
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
    public ResultData<Arrange> getNumber(Integer id, String date) {
        Arrange number = mapper.getNumber(id, date);
        return ResultData.success(number);
    }

    @Override
    public ResultData<String> submitOrder(Order o) {
        Integer record = mapper.getRecord(o.getS_id(), o.getDate());
        if(0<record)
            return ResultData.fail("预约失败，您今天已经预约过咯！");
        Integer integer = mapper.saveOrder(o);
        if(0<integer){
            mapper.updateNumber(o);
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
        if(qo.getStatus()==1){
            Integer integer = mapper.finishOrder(qo);
            if(0<integer)
                return ResultData.success("已确认！");
            return ResultData.fail("当前时间无法确认！");
        }
        else if(qo.getStatus()==3){
            Integer integer = mapper.cancelOrder(qo);
            if(0<integer){
                mapper.cancelUpdate(qo);
                return ResultData.success("已取消！");
            }
            return ResultData.fail("当前时间无法取消！");
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
