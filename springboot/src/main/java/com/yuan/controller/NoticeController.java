package com.yuan.controller;


import com.github.pagehelper.PageInfo;
import com.yuan.configure.isCheckToken;
import com.yuan.domain.Notice;
import com.yuan.domain.NumberCount;
import com.yuan.domain.ResultData;
import com.yuan.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService service;

    @RequestMapping("/list")
    public ResultData<PageInfo<Notice>> list(Integer page){return ResultData.success(service.list(page));}

    @RequestMapping("/save")
    @isCheckToken
    public ResultData<String> save(@RequestBody Notice notice){ return service.save(notice);}

    @RequestMapping("/getNotice")
    public ResultData<Notice> getNotice(Integer id){ return service.getNotice(id);}

    @RequestMapping("/update")
    @isCheckToken
    public ResultData<String> update(@RequestBody Notice notice){ return service.update(notice);}

    @RequestMapping("/delete")
    @isCheckToken
    public ResultData<String> delete(Integer id){ return service.delete(id);}

    @RequestMapping("/getNumber")
//    @isCheckToken
    public ResultData<NumberCount> getNumber(){ return service.getNumber();}
}
