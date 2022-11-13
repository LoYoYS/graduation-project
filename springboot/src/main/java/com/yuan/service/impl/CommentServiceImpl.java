package com.yuan.service.impl;

import com.yuan.domain.*;
import com.yuan.mapper.CommentMapper;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;
import com.yuan.service.CommentService;
import com.yuan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper mapper;


    @Override
    public ResultData<List<Order>> getRecords(OrdersQo qo) {
        List<Order> records = mapper.getRecords(qo);
        return ResultData.success(records);
    }

    @Override
    public ResultData<String> submitComment(Comment comment) {
        Integer integer = mapper.submitComment(comment);
        if(0<integer){
            mapper.updateRecord(comment.getO_id());
            return  ResultData.success("评价成功！");
        }
        return ResultData.fail("评价失败！");
    }

    @Override
    public ResultData<List<Comment>> getComments(Integer id) {
        List<Comment> comments = mapper.getComments(id);
        return ResultData.success(comments);
    }

    @Override
    public ResultData<String> deleteComment(Integer id) {
        Integer integer = mapper.deleteComment(id);
        if(0<integer)
            return ResultData.success("删除成功！");
        return ResultData.fail("删除失败！");
    }

    @Override
    public ResultData<List<Comment>> getCoachComment(Integer id) {
        List<Comment> comments = mapper.getCoachComment(id);
        return ResultData.success(comments);
    }
}
