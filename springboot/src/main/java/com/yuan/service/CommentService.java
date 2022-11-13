package com.yuan.service;

import com.yuan.domain.*;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;

import java.util.List;

public interface CommentService {

    //获取待评价记录
    ResultData<List<Order>> getRecords(OrdersQo qo);
    //提交评价
    ResultData<String> submitComment(Comment comment);
    //获取已评价记录
    ResultData<List<Comment>> getComments(Integer id);
    //删除评价
    ResultData<String> deleteComment(Integer id);
    //获取某个教练的评价
    ResultData<List<Comment>> getCoachComment(Integer id);
}
