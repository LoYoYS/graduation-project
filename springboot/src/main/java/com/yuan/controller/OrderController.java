package com.yuan.controller;


import com.yuan.domain.Arrange;
import com.yuan.domain.Coach;
import com.yuan.domain.Order;
import com.yuan.domain.ResultData;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;
import com.yuan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @RequestMapping("/getCoachList")
    public ResultData<List<Coach>> getCoachList(@RequestBody GetCoachListQo qo){
        return service.getCoachList(qo);
    }

    @RequestMapping("/getNumber")
    public ResultData<Arrange> getNumber(@RequestParam("c_id") Integer id, @RequestParam("date") String date){
        return service.getNumber(id,date);
    }
    @RequestMapping("/submitOrder")
    public ResultData<String> submitOrder(@RequestBody Order o){return service.submitOrder(o);}

    @RequestMapping("/getRecords")
    public ResultData<List<Order>> getRecords(@RequestBody OrdersQo qo){return service.getRecords(qo);}

    @RequestMapping("/updateRecord")
    public ResultData<String> updateRecord(@RequestBody OrdersQo qo){return service.updateRecord(qo);}

    @RequestMapping("/deleteRecord")
    public ResultData<String> deleteRecord(Integer id){return service.deleteRecord(id);}

    @RequestMapping("/getRecordList")
    public ResultData<List<Order>> getRecordList(@RequestBody OrdersQo qo){
        return service.getRecordList(qo);
    }

}
