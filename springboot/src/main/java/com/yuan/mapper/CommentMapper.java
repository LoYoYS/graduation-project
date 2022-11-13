package com.yuan.mapper;

import com.yuan.domain.Arrange;
import com.yuan.domain.Coach;
import com.yuan.domain.Comment;
import com.yuan.domain.Order;
import com.yuan.qo.GetCoachListQo;
import com.yuan.qo.OrdersQo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    //查询待评价记录记录
    List<Order> getRecords(OrdersQo qo);
    //提交评价
    Integer submitComment(Comment comment);
    //更新预约记录
    Integer updateRecord(Integer id);
    //获取已评价记录
    List<Comment>getComments(Integer id);
    //删除评价
    Integer deleteComment(Integer id);
    //获取某个教练的评价
    List<Comment>getCoachComment(Integer id);
}
