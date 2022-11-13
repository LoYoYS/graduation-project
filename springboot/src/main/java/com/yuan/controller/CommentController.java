package com.yuan.controller;


import com.yuan.domain.Comment;
import com.yuan.domain.Order;
import com.yuan.domain.ResultData;
import com.yuan.qo.OrdersQo;
import com.yuan.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;

    @RequestMapping("/getRecords")
    public ResultData<List<Order>> getRecords(@RequestBody OrdersQo qo){return service.getRecords(qo);}

    @RequestMapping("/submitComment")
    public ResultData<String> submitComment(@RequestBody Comment comment){
        return service.submitComment(comment);}

    @RequestMapping("/getComments")
    public ResultData<List<Comment>> getComments(Integer id){return service.getComments(id);}

    @RequestMapping("/deleteComment")
    public ResultData<String> deleteComment(Integer id){return service.deleteComment(id);}

    @RequestMapping("/getCoachComment")
    public ResultData<List<Comment>> getCoachComment(Integer id){return service.getCoachComment(id);}

}
